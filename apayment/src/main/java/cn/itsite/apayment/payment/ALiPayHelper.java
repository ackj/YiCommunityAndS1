package cn.itsite.apayment.payment;

import android.app.Activity;
import android.os.Vibrator;
import android.text.TextUtils;

import com.aglhz.abase.BaseApp;
import com.aglhz.abase.log.ALog;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.Map;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/4/12 0009 17:39
 * 支付宝工具。
 */
public class ALiPayHelper {
    private static final String TAG = ALiPayHelper.class.getSimpleName();
    private Activity activity;

    public ALiPayHelper() {
    }

    public ALiPayHelper(Activity activity) {
        this.activity = activity;
    }

    public void pay(final Activity mActivity, final String orderInfo) {
        ALog.e("orderInfo-->" + orderInfo);

        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);

                Map<String, String> mapResult = alipay.payV2(orderInfo, true);

                Iterator<Map.Entry<String, String>> it = mapResult.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next();
                    ALog.e(TAG, "key= " + entry.getKey() + " and value= " + entry.getValue());
                }

                if (TextUtils.equals(mapResult.get("resultStatus"), "9000")) {
                    ALiPayResultBean bean = new Gson().fromJson(mapResult.get("result"), ALiPayResultBean.class);

                    ALog.e("已经支付………………");
                    EventPay eventPay = new EventPay(0, "", bean.getAlipay_trade_app_pay_response().getOut_trade_no());
                    EventBus.getDefault().post(eventPay);
                } else {
                    EventBus.getDefault().post(new EventPay(-1));
                }

                ((Vibrator) BaseApp.mContext.getSystemService(VIBRATOR_SERVICE)).vibrate(500);
            }
        }).start();
    }

    public ALiPayHelper with(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void pay(String orderInfo) {
//        ALog.e("orderInfo-->" + orderInfo);
//        Observable.create((Observable.OnSubscribe<String>) subscriber -> {
//            subscriber.onStart();
//            try {
//                PayTask alipay = new PayTask(activity);
//                Map<String, String> mapResult = alipay.payV2(orderInfo, true);
//                for (Map.Entry<String, String> entry : mapResult.entrySet()) {
//                    ALog.e(TAG, "key= " + entry.getKey() + " and value= " + entry.getValue());
//                }
//                subscriber.onNext(mapResult.get("resultStatus"));
//            } catch (Exception e) {
//                e.printStackTrace();
//                subscriber.onError(e);
//            }
//            subscriber.onCompleted();
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//
//                    }
//                });
    }
}



