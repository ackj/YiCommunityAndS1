package com.aglhz.s1.host.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.common.RxManager;
import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.GatewaysBean;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.UserHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */

public class HostSettingsFragment extends BaseFragment {
    public static final String TAG = HostSettingsFragment.class.getSimpleName();
    public static final int RESULT_HOST_SETTINGS = 1234;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_host_name_host_setting_fragment)
    TextView tvHostName;
    @BindView(R.id.tv_alert_sms_host_setting_fragment)
    TextView tvAlertMessage;
    @BindView(R.id.tv_push_host_setting_fragment)
    TextView tvPush;
    @BindView(R.id.tv_volume_host_setting_fragment)
    TextView tvVolume;
    @BindView(R.id.ll_host_name_host_setting_fragment)
    LinearLayout llHostName;
    private Unbinder unbinder;
    private RxManager mRxManager = new RxManager();

    public static HostSettingsFragment newInstance() {
        return new HostSettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initData() {
        tvHostName.setText(UserHelper.deviceName);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("主机设置");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mRxManager.clear();
    }

    @OnClick({R.id.ll_host_name_host_setting_fragment,
            R.id.tv_alert_sms_host_setting_fragment,
            R.id.tv_push_host_setting_fragment,
            R.id.tv_volume_host_setting_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_host_name_host_setting_fragment:
                startForResult(EditHostFragment.newInstance(), RESULT_HOST_SETTINGS);
                break;
//            case R.id.tv_location_host_setting_fragment:
//                startForResult(AddHostFragment.newInstance(hostBean.getName(), null),RESULT_HOST_SETTINGS);
//                break;
            case R.id.tv_alert_sms_host_setting_fragment:
                start(AlertSmsFragment.newInstance());
                break;
            case R.id.tv_push_host_setting_fragment:
                start(PushSettingsFragment.newInstance());
                break;
            case R.id.tv_volume_host_setting_fragment:
                start(VolumeSettingsFragment.newInstance());
                break;
//            case R.id.tv_unbind_host_setting_fragment:
//                new AlertDialog.Builder(_mActivity)
//                        .setTitle("提醒")
//                        .setMessage("确定要解除绑定当前主机吗？")
//                        .setPositiveButton("确定", (dialog, which) ->
//                                mRxManager.add(HttpHelper.getService(ApiService.class)
//                                        .requestUnbindHost(ApiService.requestUnbindHost, UserHelper.token, hostBean.getFid())
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(bean -> {
//                                            if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
//                                                DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
//                                            } else {
//                                                error(bean.getOther().getMessage());
//                                            }
//                                        }, this::error/*, () -> complete(""), disposable -> start("")*/)))
//                        .setNegativeButton("取消", null)
//                        .show();
//                break;
//            case R.id.tv_accredit_host_setting_fragment:
//                start(AuthorizationFragment.newInstance(hostBean));
//                break;
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        ALog.e("requestCode-->" + requestCode);
        ALog.e("resultCode-->" + resultCode);
        if (data != null) {
            String name = data.getString("name");
            tvHostName.setText(name);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(Constants.KEY_HOST, hostBean);
//            setFragmentResult(HostSettingsFragment.RESULT_HOST_SETTINGS, bundle);
        }
    }
}
