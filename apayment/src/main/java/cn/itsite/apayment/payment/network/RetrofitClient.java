//package cn.itsite.apayment.payment.network;
//
//import com.aglhz.abase.log.ALog;
//
//import java.io.IOException;
//
//import cn.itsite.apayment.payment.PayParams;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
///**
// * @version v0.0.0
// * @Author leguang
// * @E-mail langmanleguang@qq.com
// * @Blog https://github.com/leguang
// * @Time 2018/1/12 0012 11:17
// * @Description retrofit网络请求简单封装.
// */
//
//public class RetrofitClient implements INetworkClient {
//
//    @Override
//    public void get(PayParams params, final CallBack callBack) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(params.getUrl() + "/")
//                .build();
//        PayService service = retrofit.create(PayService.class);
//        Call<ResponseBody> call = service.getOrder(params.getUrl(), params.getToken(), params.getBillFids(), params.getPayMethod());
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        callBack.onSuccess(response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    callBack.onFailure();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                callBack.onFailure();
//            }
//        });
//    }
//
//    @Override
//    public void post(PayParams params, final CallBack callBack) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://www.aglhz.com:8090/sub_property_ysq/")
//                .build();
//        PayService service = retrofit.create(PayService.class);
//        Call<ResponseBody> call = service.postOrder(params.getUrl(), params.getToken(), params.getBillFids(), params.getPayMethod());
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        callBack.onSuccess(response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    callBack.onFailure();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                callBack.onFailure();
//            }
//        });
//    }
//}
