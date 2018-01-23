//package cn.itsite.apayment.payment.network;
//
//import com.aglhz.abase.log.ALog;
//
//import java.io.IOException;
//
//import cn.itsite.apayment.payment.PayParams;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
///**
// * @version v0.0.0
// * @Author leguang
// * @E-mail langmanleguang@qq.com
// * @Blog https://github.com/leguang
// * @Time 2018/1/12 0012 11:17
// * @Description: okhttp3网络请求简单封装.
// */
//
//public class OkHttpClient implements INetworkClient {
//
//    @Override
//    public void get(PayParams payParams, final CallBack callBack) {
//        String baseUrl = payParams.getUrl();
//        StringBuffer sbUrl = new StringBuffer();
//        // TODO 需要和服务器开发人员协商接口形式需要为：微信，支付宝，银联等 预支付信息走一个接口，通过pay_way或者其他字段进行区分。
//        // 以下信息出商品详情介绍(goods_introduction)外，均为必须上传字段，key值由开发者和服务器人员协商自行定义。
//        sbUrl.append(baseUrl)
//                .append("?")
//                .append("token=").append(payParams.getToken())
//                .append("&")
//                .append("billFids=").append(payParams.getBillFids())
//                .append("&")
//                .append("payMethod=").append(payParams.getPayMethod())
//                .append("&")
//                .append("jumpUrl=").append(payParams.getJumpUrl())
//                .append("&")
//                .append("quitUrl=").append(payParams.getQuitUrl())
//                .append("&")
//                .append("payUrl=").append(payParams.getPayMethod())
//                .append("&")
//                .append("code=").append(payParams.getCode());
//
//        ALog.e("sbUrl-->"+sbUrl);
//
//        okhttp3.OkHttpClient mOkHttpClient = new okhttp3.OkHttpClient();
//        Request request = new Request.Builder().url(sbUrl.toString()).build();
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                ALog.e("11111111");
//
//                ALog.e("response.body().string()-->"+response.body().string());
//
//                if (response.isSuccessful()) {
//                    callBack.onSuccess(response.body().string());
//                } else {
//                    callBack.onFailure();
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                ALog.e("11111111");
//                e.printStackTrace();
//                callBack.onFailure();
//            }
//        });
//    }
//
//    @Override
//    public void post(PayParams payParams, final CallBack callBack) {
//        okhttp3.OkHttpClient mOkHttpClient = new okhttp3.OkHttpClient();
//        // TODO 需要和服务器开发人员协商接口形式需要为：微信，支付宝，银联等 预支付信息走一个接口，通过pay_way或者其他字段进行区分。
//        // 以下信息出商品详情介绍(goods_introduction)外，均为必须上传字段，key值由开发者和服务器人员协商自行定义。
//        RequestBody body = new FormBody.Builder()
//                .add("token", payParams.getToken())
//                .add("billFids", payParams.getBillFids())
//                .add("payMethod", payParams.getPayMethod() + "")
//                .add("jumpUrl", payParams.getJumpUrl())
//                .add("quitUrl", payParams.getQuitUrl())
//                .add("payUrl", payParams.getQuitUrl())
//                .add("code", payParams.getCode())
//                .build();
//
//        final Request request = new Request.Builder()
//                .url(payParams.getUrl()).post(body).build();
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    callBack.onSuccess(response.body().string());
//                } else {
//                    callBack.onFailure();
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                callBack.onFailure();
//            }
//        });
//    }
//}
