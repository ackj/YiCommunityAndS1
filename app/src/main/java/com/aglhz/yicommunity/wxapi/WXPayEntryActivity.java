package com.aglhz.yicommunity.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import com.aglhz.abase.log.ALog;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.UserHelper;
import com.aglhz.yicommunity.event.EventPay;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/4/12 0009 17:39
 * 微信支付工具。
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    public static final String TAG = WXPayEntryActivity.class.getName();
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_pay);
        api = WXAPIFactory.createWXAPI(this, UserHelper.WXAPPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq request) {
        if (request instanceof PayReq) {
            ALog.e("extData--" + ((PayReq) request).extData);
        }
        ALog.e(TAG, "request-->" + request.openId);
        ALog.e(TAG, "request-->" + request.getType());
        ALog.e(TAG, "request-->" + request.transaction);
    }

    @Override
    public void onResp(BaseResp response) {
        String extData = "";
        if (response instanceof PayResp) {
            extData = ((PayResp) response).extData;
        }
        ALog.e(TAG, "response-->" + response.errStr);
        ALog.e(TAG, "response-->" + response.openId);
        ALog.e(TAG, "response-->" + response.transaction);
        ALog.e(TAG, "response-->" + response.getType());
        ALog.e(TAG, "response-->" + response.checkArgs());
        ((Vibrator) App.mContext.getSystemService(VIBRATOR_SERVICE)).vibrate(500);
        if (response.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            EventBus.getDefault().post(new EventPay(response.errCode, "", extData));
            finish();
        }
    }
}