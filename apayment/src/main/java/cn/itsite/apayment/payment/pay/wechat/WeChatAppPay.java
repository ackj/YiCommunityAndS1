//package cn.itsite.apayment.payment.pay.wechat;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.support.annotation.NonNull;
//import android.support.v4.content.LocalBroadcastManager;
//
//import com.aglhz.abase.log.ALog;
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import cn.itsite.apayment.payment.PayParams;
//import cn.itsite.apayment.payment.Payment;
//import cn.itsite.apayment.payment.PaymentListener;
//import cn.itsite.apayment.payment.enums.PayType;
//import cn.itsite.apayment.payment.pay.IPayable;
//
///**
// * @version v0.0.0
// * @Author leguang
// * @E-mail langmanleguang@qq.com
// * @Blog https://github.com/leguang
// * @Time 2018/1/12 0012 11:17
// * @Description 微信支付策略.
// */
//
//public class WeChatAppPay implements IPayable {
//    public static final String WECHAT_PAY_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_ACTION";
//    public static final String WECHAT_PAY_RESULT_EXTRA = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_EXTRA";
//    private PaymentListener.OnPayListener onPayListener;
//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int resultCode = intent.getIntExtra(WECHAT_PAY_RESULT_EXTRA, -100);
//
//            ALog.e("微信支付结果码--》" + resultCode);
//
//            if (onPayListener != null) {
//                if (resultCode == 0) {
//                    onPayListener.onSuccess(PayType.WechatPay);
//                } else {
//                    onPayListener.onFailure(PayType.WechatPay, resultCode);
//                }
//            }
//            unRegistPayResultBroadcast(context);
//        }
//    };
//
//    @Override
//    public PayParams parse(@NonNull String result) throws JSONException {
//        ALog.e("ATG", "微信支付-->" + result);
//        JSONObject resultObject = new JSONObject(result);
//        JSONObject jsonData = resultObject.optJSONObject("data");
//        return new PayParams.Builder()
//                .appID(jsonData.optString("appId"))
//                .partnerId(jsonData.optString("partnerId"))
//                .prePayId(jsonData.optString("prePayId"))
//                .packageValue("Sign=WXPay")
//                .nonceStr(jsonData.optString("nonceStr"))
//                .timeStamp(jsonData.optString("timestamp"))
//                .sign(jsonData.optString("sign"))
//                .outTradeNo(jsonData.optString("outTradeNo"))
//                .webUrl(jsonData.optString("mwebUrl"))
//                .build();
//    }
//
//    @Override
//    public void pay(@NonNull PayParams params, PaymentListener.OnPayListener onPayListener) {
//        this.onPayListener = onPayListener;
//        if (onPayListener != null) {
//            onPayListener.onStart(PayType.WechatPay);
//        }
//        appPay(params);
//    }
//
//    private void registPayResultBroadcast(Context mContext) {
//        IntentFilter filter = new IntentFilter(WECHAT_PAY_RESULT_ACTION);
//        LocalBroadcastManager.getInstance(mContext)
//                .registerReceiver(mReceiver, filter);
//    }
//
//    private void unRegistPayResultBroadcast(Context mContext) {
//        if (mReceiver != null) {
//            LocalBroadcastManager.getInstance(mContext)
//                    .unregisterReceiver(mReceiver);
//            mReceiver = null;
//        }
//    }
//
//
//    private void h5xPay(@NonNull PayParams params) {
//        ALog.e("params.webUrl————》" + params.getWebUrl());
//
//
//    }
//
//    private void appPay(@NonNull PayParams params) {
//        IWXAPI wxapi = WXAPIFactory.createWXAPI(params.getContext(), params.getAppID(), true);
//
//        if (!wxapi.isWXAppInstalled()) {
//            if (onPayListener != null) {
//                onPayListener.onFailure(PayType.WechatPay, Payment.WECHAT_NOT_INSTALLED_ERROR);
//            }
//            return;
//        }
//
//        if (!wxapi.isWXAppSupportAPI()) {
//            if (onPayListener != null) {
//                onPayListener.onFailure(PayType.WechatPay, Payment.WECHAT_UNSUPPORT_ERROR);
//            }
//            return;
//        }
//
//        wxapi.registerApp(params.getAppID());
//        registPayResultBroadcast(params.getContext());
//
//        PayReq req = new PayReq();
//        req.appId = params.getAppID();
//        req.partnerId = params.getPartnerId();
//        req.prepayId = params.getPrePayId();
//        req.packageValue = params.getPackageValue();
//        req.nonceStr = params.getNonceStr();
//        req.timeStamp = params.getTimeStamp();
//        req.sign = params.getSign();
//
//        // 发送支付请求：跳转到微信客户端
//        wxapi.sendReq(req);
//    }
//}
