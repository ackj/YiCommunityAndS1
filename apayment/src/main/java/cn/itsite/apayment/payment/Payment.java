package cn.itsite.apayment.payment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.aglhz.abase.log.ALog;

import org.json.JSONException;

import java.util.Map;

import cn.itsite.apayment.payment.enums.HttpType;
import cn.itsite.apayment.payment.enums.PayType;
import cn.itsite.apayment.payment.network.INetworkClient;
import cn.itsite.apayment.payment.pay.IPayable;

/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2018/1/12 0012 11:17
 * @Description 支付SDK封装工具类.
 */

public final class Payment {
    public static final String TAG = Payment.class.getSimpleName();
    //请求错误
    public static final int REQUEST_ERROR = -100;
    //解析错误
    public static final int PARSE_ERROR = -200;
    // 通用结果码
    public static final int PAY_OK = 1;
    public static final int PAY_CACELED = 0;
    public static final int PAY_ERROR = -1;
    public static final int VERIFY_ERROR = -2;
    // 微信结果码
    public static final int WECHAT_SENT_FAILED_ERROR = -3;
    public static final int WECHAT_AUTH_DENIED_ERROR = -4;
    public static final int WECHAT_UNSUPPORT_ERROR = -5;
    public static final int WECHAT_BAN_ERROR = -6;
    public static final int WECHAT_NOT_INSTALLED_ERROR = -7;
    // 支付宝结果码
    public static final int ALIPAY_WAIT_CONFIRM_ERROR = 8000;
    public static final int ALIPAY_NET_ERROR = 6002;
    public static final int ALIPAY_UNKNOW_ERROR = 6004;
    public static final int ALIPAY_OTHER_ERROR = 6005;
    // 银联结果码
    public static final int UPPAY_PLUGIN_NOT_INSTALLED = -10;
    public static final int UPPAy_PLUGIN_NEED_UPGRADE = -11;
    //完成支付所需对象
    private PaymentListener.OnRequestListener onRequestListener;
    private PaymentListener.OnParseListener onParseListener;
    private PaymentListener.OnPayListener onPayListener;
    private PaymentListener.OnVerifyListener onVerifyListener;
    private Map<String, String> params;
    private IPayable pay;
    private INetworkClient client;
    private HttpType httpType;
    private String url;
    private Activity activity;

    public Map<String, String> getParams() {
        return params;
    }

    public Payment setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public IPayable getPay() {
        return pay;
    }

    public Payment setPay(@NonNull IPayable pay) {
        this.pay = pay;
        return this;
    }

    public INetworkClient getClient() {
        return client;
    }

    public Payment setClient(@NonNull INetworkClient client) {
        this.client = client;
        return this;
    }

    public HttpType getHttpType() {
        return httpType;
    }

    public Payment setHttpType(HttpType httpType) {
        this.httpType = httpType;
        return this;
    }

    public PaymentListener.OnRequestListener getOnRequestListener() {
        return onRequestListener;
    }

    public Payment setOnRequestListener(PaymentListener.OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
        return this;
    }

    public PaymentListener.OnParseListener getOnParseListener() {
        return onParseListener;
    }

    public Payment setOnParseListener(PaymentListener.OnParseListener onParseListener) {
        this.onParseListener = onParseListener;
        return this;
    }

    public PaymentListener.OnPayListener getOnPayListener() {
        return onPayListener;
    }

    public Payment setOnPayListener(PaymentListener.OnPayListener onPayListener) {
        this.onPayListener = onPayListener;
        return this;
    }

    public PaymentListener.OnVerifyListener getOnVerifyListener() {
        return onVerifyListener;
    }

    public Payment setOnVerifyListener(PaymentListener.OnVerifyListener onVerifyListener) {
        this.onVerifyListener = onVerifyListener;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Payment setUrl(String url) {
        this.url = url;
        return this;
    }

    public Activity getActivity() {
        return activity;
    }

    public Payment setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    /**
     * 支付从这个函数开始：1.请求支付参数（requestPayParams()）。2.获取参数成功后就开始解析参数。3.解析得到订单后就开始支付了pay()。
     *
     * @return
     */
    public Payment start() {
        requestPayParams();
        return this;
    }

    /**
     * 请求APP服务器获取预支付信息：微信，支付宝，银联都需要此步骤。
     */
    private void requestPayParams() {
        if (onRequestListener != null) {
            onRequestListener.onStart();
        }
        INetworkClient.CallBack callBack = new INetworkClient.CallBack<String>() {
            @Override
            public void onSuccess(final String result) {
                ALog.e(Thread.currentThread().getName());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (onRequestListener != null) {
                            onRequestListener.onSuccess(result);
                        }
                        parse(result);
                    }
                });
            }

            @Override
            public void onFailure() {
                if (onRequestListener != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onRequestListener.onError(REQUEST_ERROR);
                        }
                    });
                }
                clear();
            }
        };

        if (client != null) {
            switch (httpType) {
                case Get:
                    client.get(url, params, callBack);
                    break;
                case Post:
                default:
                    client.post(url, params, callBack);
                    break;
            }
        }
    }

    /**
     * 对请求的统一订单进行解析。
     *
     * @param result
     */
    private void parse(String result) {
        ALog.e(Thread.currentThread().getName());

        if (onParseListener != null) {
            onParseListener.onStart(result);
            if (TextUtils.isEmpty(result)) {
                onParseListener.onError(PARSE_ERROR);
                clear();
                return;
            }
        }

        PayParams payParams = null;
        try {
            if (pay != null) {
                payParams = pay.parse(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (onParseListener != null) {
                onParseListener.onError(PARSE_ERROR);
                clear();
            }
        }

        if (onParseListener != null) {
            onParseListener.onSuccess(payParams);
        }

        getFragment(activity).setPayment(this);

        if (pay != null) {
            pay.pay(activity, payParams, new PaymentListener.OnPayListener() {
                @Override
                public void onStart(PayType payType) {
                    if (onPayListener != null) {
                        onPayListener.onStart(payType);
                    }
                }

                @Override
                public void onSuccess(PayType payType) {
                    if (onPayListener != null) {
                        onPayListener.onSuccess(payType);
                    }
                    clear();
                }

                @Override
                public void onFailure(PayType payType, int errorCode) {
                    if (onPayListener != null) {
                        onPayListener.onFailure(payType, errorCode);
                    }
                    clear();
                }
            });
        }
    }

    private PayFragment getFragment(Activity activity) {
        if (activity == null) {
            return null;
        }
        PayFragment payFragment = findPayFragment(activity);
        if (payFragment == null) {
            payFragment = new PayFragment();
            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(payFragment, PayFragment.TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return payFragment;
    }

    private PayFragment findPayFragment(Activity activity) {
        if (activity == null) {
            return null;
        }
        return (PayFragment) ((FragmentActivity) activity)
                .getSupportFragmentManager().findFragmentByTag(PayFragment.TAG);
    }

    public void clear() {
        if (params == null) {
            return;
        }

        //还要记得remove掉这个fragment
        FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .remove(findPayFragment(activity))
                .commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        activity = null;
        params = null;
    }


    public static class Builder {
        Map<String, String> params;
        IPayable pay;
        INetworkClient client;
        HttpType httpType;
        String url;
        Activity activity;
        PaymentListener.OnRequestListener onRequestListener;
        PaymentListener.OnParseListener onParseListener;
        PaymentListener.OnPayListener onPayListener;
        PaymentListener.OnVerifyListener onVerifyListener;

        public Builder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder setPay(@NonNull IPayable pay) {
            this.pay = pay;
            return this;
        }

        public Builder setClient(@NonNull INetworkClient client) {
            this.client = client;
            return this;
        }

        public Builder setUrl(@NonNull String url) {
            this.url = url;
            return this;
        }

        public Builder setActivity(@NonNull Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setHttpType(@NonNull HttpType httpType) {
            this.httpType = httpType;
            return this;
        }

        public Builder setOnRequestListener(PaymentListener.OnRequestListener onRequestListener) {
            this.onRequestListener = onRequestListener;
            return this;
        }

        public Builder setOnParseListener(PaymentListener.OnParseListener onParseListener) {
            this.onParseListener = onParseListener;
            return this;
        }

        public Builder setOnPayListener(PaymentListener.OnPayListener onPayListener) {
            this.onPayListener = onPayListener;
            return this;
        }

        public Builder setOnVerifyListener(PaymentListener.OnVerifyListener onVerifyListener) {
            this.onVerifyListener = onVerifyListener;
            return this;
        }

        public Payment build() {
            return new Payment()
                    .setPay(pay)
                    .setParams(params)
                    .setClient(client)
                    .setUrl(url)
                    .setHttpType(httpType)
                    .setActivity(activity)
                    .setOnVerifyListener(onVerifyListener)
                    .setOnRequestListener(onRequestListener)
                    .setOnPayListener(onPayListener)
                    .setOnParseListener(onParseListener);
        }

        public Payment start() {
            return build().start();
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
