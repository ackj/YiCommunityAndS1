package com.aglhz.yicommunity.main.about.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.AppUtils;

import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.entity.bean.AppUpdateBean;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.PermissionHelper;
import com.aglhz.yicommunity.common.appupdate.UpdateAppHttpUtils;
import com.aglhz.yicommunity.web.WebActivity;
import com.google.gson.Gson;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: LiuJia on 2017/4/21 17:41.
 * Email: liujia95me@126.com
 * <p>
 * [关于我们]的View层。
 * 打开方式：Start App-->我的-->关于我们
 */
public class AboutUsFragment extends BaseFragment {
    private static final String TAG = AboutUsFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.ll_product_introduction)
    LinearLayout llProductIntroduction;
    @BindView(R.id.ll_service_terms)
    LinearLayout llServiceTerms;
    @BindView(R.id.ll_feedback)
    LinearLayout llFeedback;
    @BindView(R.id.tv_check_update_about_us_fragment)
    TextView tvCheckUpdate;
    Unbinder unbinder;

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("关于我们");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        tvVersionName.setText("版本：" + AppUtils.getVersionName(App.mContext));
    }

    @OnClick({R.id.ll_product_introduction
            , R.id.ll_service_terms
            , R.id.ll_feedback
            , R.id.ll_permission
            , R.id.tv_check_update_about_us_fragment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_product_introduction://[产品介绍]页面跳转
                Intent introductionIntent = new Intent(_mActivity, WebActivity.class);
                introductionIntent.putExtra(Constants.KEY_TITLE, "产品介绍");
                introductionIntent.putExtra(Constants.KEY_LINK, ApiService.PRODUCT_INTRODUCTION);
                startActivity(introductionIntent);
                break;
            case R.id.ll_service_terms://[服务条款]页面跳转
                Intent termsIntent = new Intent(_mActivity, WebActivity.class);
                termsIntent.putExtra(Constants.KEY_TITLE, "服务条款");
                termsIntent.putExtra(Constants.KEY_LINK, ApiService.SERVICE_TERMS);
                startActivity(termsIntent);
                break;
            case R.id.ll_feedback://[意见反馈]页面跳转
                start(FeedbackFragment.newInstance());
                break;
            case R.id.ll_permission://[权限管理]页面跳转
                new PermissionHelper(_mActivity).gotoPermission();
                break;
            case R.id.tv_check_update_about_us_fragment://[检测App更新]
                updateApp();
                break;
            default:
        }
    }

    /**
     * 检测是否有新版本需要下载更新。
     */
    public void updateApp() {
        String random = System.currentTimeMillis() + "";
        String accessKey = Constants.SYS_ACCESS_PREFIX + random + Constants.SYS_ACCESS_KEY;
        ALog.e("random-->" + random);
        ALog.e("accessKey-->" + accessKey);
        ALog.e("getMd5(accessKey)-->" + getMd5(accessKey));

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", getMd5(accessKey));
        params.put("random", random);
        params.put("sc", Constants.SC);
        params.put("appType", Constants.APP_TYPE);


        new UpdateAppManager
                .Builder()
                .setActivity(_mActivity)
                .setHttpManager(new UpdateAppHttpUtils())
                .setUpdateUrl(Constants.APP_UPDATE_URL)
                .setPost(true)
                .setParams(params)
                .dismissNotificationProgress()
                .hideDialogOnDownloading(false)
                .build()
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        ALog.e("json-->" + json);
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        AppUpdateBean mAppUpdateBean = new Gson().fromJson(json, AppUpdateBean.class);
                        ALog.e("AppUtils.getVersionCode(_mActivity)-->" + AppUtils.getVersionCode(_mActivity));
                        updateAppBean
                                //（必须）是否更新Yes,No
                                .setUpdate(AppUtils.getVersionCode(_mActivity) < mAppUpdateBean.getData().getVersionCode() ? "Yes" : "No")
                                //（必须）新版本号，
                                .setNewVersion(mAppUpdateBean.getData().getVersionName())
                                //（必须）下载地址
                                .setApkFileUrl(mAppUpdateBean.getData().getUrl())
                                //（必须）更新内容
                                .setUpdateLog(mAppUpdateBean.getData().getDescription())
                                //大小，不设置不显示大小，可以不设置
                                .setTargetSize(mAppUpdateBean.getData().getSize())
                                //是否强制更新，可以不设置
                                .setConstraint(mAppUpdateBean.getData().isIsForce());

                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        showLoading();
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        dismissLoading();
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp() {
                        DialogHelper.successSnackbar(getView(), "当前版本已是最新版本");
                    }
                });
    }

    private String getMd5(String str) {
        StringBuffer sb = new StringBuffer();
        try {
            //获取加密方式为md5的算法对象
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(str.getBytes());
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 0xff);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                sb = sb.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
