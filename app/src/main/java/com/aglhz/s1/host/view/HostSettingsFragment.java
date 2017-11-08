package com.aglhz.s1.host.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.HostSettingsBean;
import com.aglhz.s1.host.contract.HostSettingsContract;
import com.aglhz.s1.host.presenter.HostSettingsPresenter;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.UserHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */

public class HostSettingsFragment extends BaseFragment<HostSettingsContract.Presenter> implements HostSettingsContract.View {
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

    private Params params = Params.getInstance();

    public static HostSettingsFragment newInstance() {
        return new HostSettingsFragment();
    }

    @NonNull
    @Override
    protected HostSettingsContract.Presenter createPresenter() {
        return new HostSettingsPresenter(this);
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
            R.id.tv_volume_host_setting_fragment,
            R.id.tv_goto_test_schema})
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
            case R.id.tv_goto_test_schema:
                new AlertDialog.Builder(_mActivity)
                        .setTitle("提示")
                        .setMessage("检测模式下设备报警不会推送至安防中心，您可对设备进行报警检测，5分钟后自动退出检测模式！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                params.status = 1;
                                mPresenter.requestGatewayTest(params);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
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

    @Override
    public void responseSetHost(BaseBean baseBean) {

    }

    @Override
    public void responseHostSettings(HostSettingsBean baseBean) {

    }

    @Override
    public void responseGatewayTest(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
    }
}
