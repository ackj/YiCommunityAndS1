package com.itsite.yicommunity;

import org.junit.Test;

import java.io.IOException;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.network.http.HttpHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() throws Exception {
        User user = new User();
        user.setToken("1111");
        user.setName("2222");

        System.out.println("11111111111");

        HttpHelper.getService(Api.class).modelPost("http://192.168.7.102:8080/carts/2325235", user)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println(222222222);

                        try {
                            System.out.println(response.body().string());

                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println(e);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println(333333+"");

                        t.printStackTrace();
                        System.out.println(t);


                    }
                });
        System.out.println(4444+"");

    }


    interface Api {

        @POST
        Call<ResponseBody> modelPost(@Url String url, @Body User user);
    }

    class User extends BasiRequest {
        public String name;

        public String getName() {
            return name;
        }

        public void setName(String token) {
            this.name = token;
        }
    }

    public class BasiRequest {
        public String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}