package cn.itsite.apayment.payment.temp;

import android.app.Activity;

import com.aglhz.abase.BaseApp;
import com.aglhz.abase.log.ALog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/4/12 0009 17:39
 * 微信支付工具。
 */
public class WxPayHelper {
    public static final String TAG = WxPayHelper.class.getSimpleName();

    public static String appId;

    public static void pay(String string) {
        ALog.e("微信支付-->" + string);
        try {
            JSONObject payData = new JSONObject(string);

            PayReq req = new PayReq();
            //{"data":{"appId":"wxe5dbac804d7bb267","timeStamp":"1488436959","noncestr":"cfb9296583e2461caeda09d0d3621f9a","sign":"BC569731EE8560316A719BC1F8EBCF0E","partnerid":"1335876101","prepayid":"wx20170302144239e711b2897c0269541507","package_":"Sign=WXPay","wxOrderSn":"20170302144238868719"},"other":{"code":200,"message":"","time":"","currpage":0,"next":"","forward":"","refresh":"","first":""}}
            req.appId = payData.optString("appId");
            ALog.e("appId", req.appId);
            req.partnerId = payData.optString("partnerId");
            ALog.e("partnerId", req.partnerId);
            req.prepayId = payData.optString("prePayId");
            ALog.e("prepayId", req.prepayId);
            req.packageValue = "Sign=WXPay";
            ALog.e("packageValue", req.packageValue);
            req.nonceStr = payData.optString("nonceStr");
            ALog.e("nonceStr", req.nonceStr);
            req.timeStamp = payData.optString("timestamp");
            ALog.e("timeStamp", req.timeStamp);
            req.sign = payData.optString("sign");
            ALog.e("sign", req.sign);
            req.extData = payData.optString("out_trade_no");
            ALog.e("extData", req.extData);

            appId = req.appId;
            IWXAPI api = WXAPIFactory.createWXAPI(BaseApp.mContext, req.appId);
            api.sendReq(req);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void pay(JSONObject payData) {
        ALog.e("微信支付-->" + payData.toString());
        PayReq req = new PayReq();
        req.appId = payData.optString("appId");
        ALog.e("appId", req.appId);
        req.partnerId = payData.optString("partnerId");
        ALog.e("partnerId", req.partnerId);
        req.prepayId = payData.optString("prePayId");
        ALog.e("prepayId", req.prepayId);
        req.packageValue = "Sign=WXPay";
        ALog.e("packageValue", req.packageValue);
        req.nonceStr = payData.optString("nonceStr");
        ALog.e("nonceStr", req.nonceStr);
        req.timeStamp = payData.optString("timestamp");
        ALog.e("timeStamp", req.timeStamp);
        req.sign = payData.optString("sign");
        ALog.e("sign", req.sign);
        req.extData = payData.optString("outTradeNo");
        ALog.e("extData", req.extData);
        appId = req.appId;
        IWXAPI api = WXAPIFactory.createWXAPI(BaseApp.mContext, req.appId);
        api.sendReq(req);
    }

    public static void h5x(Activity activity, JSONObject payData) {
//        ALog.e("H5Pay-->" + payData.toString());
//        String mwebUrl = payData.optString("mwebUrl");
//        ALog.e("mwebUrl-->" + mwebUrl);

//        String sign = payData.optString("sign");
//        String timestamp = payData.optString("timestamp");
//        String prepay_id = Uri.parse(mwebUrl).getQueryParameter("prepay_id");
//        String strPackage = Uri.parse(mwebUrl).getQueryParameter("package");
//
//        String uri = "weixin://wap/pay?" + "prepayid=" + prepay_id + "&package=" + strPackage + "&noncestr=" + timestamp + "&sign=" + sign.toLowerCase();
//
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("weixin://wap/pay?prepayid%3Dwx201801111058205d91a984840611542960&package=2647709537&noncestr=1515639597&sign=66e707ff880c93c761dbf8e6fd948d1e"));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        activity.startActivity(intent);

//        WebView webView = new WebView(activity);
//        activity.addContentView(webView, new ViewGroup.LayoutParams(-1, -1));
//        Map<String, String> extraHeaders = new HashMap<String, String>();
//        extraHeaders.put("Referer", "http://www.aglhz.com");
//        webView.setVisibility(View.INVISIBLE);
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setDefaultTextEncodingName("UTF-8");
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        WebViewClient webViewClient = new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 如下方案可在非微信内部WebView的H5页面中调出微信支付
//                if (url.startsWith("weixin://wap/pay?")) {
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(url));
//                    activity.startActivity(intent);
//                }
//                return true;
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) { // 重写此方法可以让webview处理https请求
//                handler.proceed();
//            }
//        };
//        webView.setWebViewClient(webViewClient);
//        webView.loadUrl(mwebUrl,extraHeaders);

    }
}
