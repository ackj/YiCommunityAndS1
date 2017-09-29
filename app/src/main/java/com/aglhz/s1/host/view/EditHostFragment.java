package com.aglhz.s1.host.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.common.RxManager;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.abase.utils.KeyBoardUtils;
import com.aglhz.s1.App;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.GatewaysBean;
import com.aglhz.s1.event.EventDeviceNameChanged;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.UserHelper;

import org.greenrobot.eventbus.EventBus;

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

public class EditHostFragment extends BaseFragment {
    public static final String TAG = EditHostFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name_edit_host_fragment)
    EditText etName;

    private Unbinder unbinder;
    private RxManager rxManager = new RxManager();
    private Params params = Params.getInstance();

    public static EditHostFragment newInstance() {
        return new EditHostFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_host, container, false);
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
        etName.setText(UserHelper.deviceName);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("主机名称");
        toolbarMenu.setText("保存");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(etName, App.mContext);
        unbinder.unbind();
        rxManager.clear();
    }

    @OnClick(R.id.toolbar_menu)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            DialogHelper.warningSnackbar(getView(), "主机名称不能为空！");
            return;
        }

        params.name = etName.getText().toString();
        rxManager.add(HttpHelper.getService(ApiService.class)
                .requestModGateway(ApiService.requestModGateway,
                        params.token,
                        params.deviceSn,
                        params.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
                        Bundle bundle = new Bundle();
                        UserHelper.deviceName = params.name;
                        EventBus.getDefault().post(new EventDeviceNameChanged());
                        bundle.putString("name", params.name);
                        setFragmentResult(HostSettingsFragment.RESULT_HOST_SETTINGS, bundle);
                    } else {
                        error(baseBean.getOther().getMessage());
                    }
                }, this::error/*, () -> complete(null), disposable -> start("")*/));
    }
}
