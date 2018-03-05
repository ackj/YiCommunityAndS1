package com.itsite.yicommunity.login.model;

import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.login.presenter.LoginPresenter;

import org.junit.Test;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/13 0013 16:59
 */
public class LoginModelTest {
    @Test
    public void requestLogin() throws Exception {
        LoginPresenter loginModel = new LoginPresenter(null);
        Params params = Params.getInstance();
        params.user = "18086527290";
        params.pwd = "123456";
        loginModel.start(params);
    }

    @Test
    public void requestSip() throws Exception {

    }

    @Test
    public void registerPush() throws Exception {

    }

}