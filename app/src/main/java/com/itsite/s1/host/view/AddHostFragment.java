package com.itsite.s1.host.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import com.itsite.s1.common.Constants;
import com.itsite.s1.common.LbsManager;
import com.itsite.s1.common.Params;
import com.itsite.s1.entity.bean.BaseBean;
import com.itsite.s1.host.contract.AddHostContract;
import com.itsite.s1.host.presenter.AddHostPresenter;
import com.itsite.yicommunity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */

public class AddHostFragment extends BaseFragment<AddHostContract.Presenter> implements AddHostContract.View {
    public static final String TAG = AddHostFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_device_code_add_host_fragment)
    EditText etDeviceSn;
    @BindView(R.id.et_name_add_host_fragment)
    EditText etName;
    @BindView(R.id.bt_save_add_host_fragment)
    Button btSave;
    @BindView(R.id.ll_device_code_add_host_fragment)
    LinearLayout llDeviceCode;
    @BindView(R.id.ll_name_add_host_fragment)
    LinearLayout llName;
    private Unbinder unbinder;
    private Params params = Params.getInstance();
    private String name;
    private String roomDir;

    public static AddHostFragment newInstance(String name, String roomDir) {
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("roomDir", roomDir);
        AddHostFragment fragment = new AddHostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        roomDir = getArguments().getString("roomDir");
    }

    @NonNull
    @Override
    protected AddHostContract.Presenter createPresenter() {
        return new AddHostPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_host, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("添加主机");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        etDeviceSn.setText(params.deviceSn);
        if(!TextUtils.isEmpty(name)){
            toolbarTitle.setText("修改主机");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        LbsManager.getInstance().stopLocation();
    }

    @Override
    public void responseAddHost(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        pop();
    }

    @Override
    public void responseModSuccess(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOST, params.name);
        setFragmentResult(HostSettingsFragment.RESULT_HOST_SETTINGS, bundle);
        pop();
    }


    @OnClick({ R.id.bt_save_add_host_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_save_add_host_fragment:
                if (TextUtils.isEmpty(etDeviceSn.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "主机编码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "主机名称不能为空！");
                    return;
                }
                params.deviceSn = etDeviceSn.getText().toString();
                params.name = etName.getText().toString();
                if(TextUtils.isEmpty(roomDir)){
                    mPresenter.requestModGateway(params);
                }else{
                    params.roomDir = roomDir;
                    mPresenter.requestAddHost(params);
                }
                break;
        }
    }

}
