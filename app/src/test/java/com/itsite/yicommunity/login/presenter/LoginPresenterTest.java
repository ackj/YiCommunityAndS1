package com.itsite.yicommunity.login.presenter;

import com.itsite.yicommunity.common.Params;

import org.junit.Test;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/13 0013 17:11
 */
public class LoginPresenterTest {
    @Test
    public void start() throws Exception {
        LoginPresenter loginModel = new LoginPresenter(null);
        Params params = Params.getInstance();
        params.user="18086527290";
        params.pwd="123456";
        loginModel.start(params);
    }

}