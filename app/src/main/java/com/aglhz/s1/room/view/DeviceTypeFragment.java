package com.aglhz.s1.room.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;

import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.DevicesBean;
import com.aglhz.s1.room.contract.DeviceTypeContract;
import com.aglhz.s1.room.presenter.DeviceTypePresenter;
import com.aglhz.s1.camera.CameraWifiInput2Fragment;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.event.EventSelectedDeviceType;
import com.aglhz.s1.security.view.AddDetectorRVAdapter;
import com.aglhz.yicommunity.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;

/**
 * Author: LiuJia on 2017/8/30 0030 19:41.
 * Email: liujia95me@126.com
 */

public class DeviceTypeFragment extends BaseFragment<DeviceTypeContract.Presenter> implements DeviceTypeContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Unbinder unbinder;
    private AddDetectorRVAdapter adapter;
    private Params params = Params.getInstance();
    private BaseDialogFragment dialogAddCamera;

    public static DeviceTypeFragment newInstance(String roomFid) {
        DeviceTypeFragment fragment = new DeviceTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fid", roomFid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected DeviceTypeContract.Presenter createPresenter() {
        return new DeviceTypePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        params.roomFid = getArguments().getString("fid");
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("添加设备");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        recyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        adapter = new AddDetectorRVAdapter();
        recyclerView.setAdapter(adapter);

        mPresenter.requestDeviceType(params);
    }

    private void initListener() {
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            DevicesBean.DataBean.DeviceTypeListBean bean = adapter.getItem(position);
            params.deviceType = bean.getCode();
            params.name = bean.getName();
            if ("camera01".equals(bean.getCode())) {
                //弹框选择
                showSelectedDialog();
            } else {
                mPresenter.requestAddDevice(params);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void responseDeviceType(List<DevicesBean.DataBean.DeviceTypeListBean> data) {
        adapter.setNewData(data);
    }

    @Override
    public void responseAddDevice(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        EventBus.getDefault().post(new EventSelectedDeviceType());
        dialogAddCamera.dismiss();
        pop();
    }


    /**
     * --------------------------- 以下是添加摄像头部分 ---------------------------
     **/

    private String[] addSelectedArr = {"新设备配置网络", "添加已联网设备"};

    /**
     * 弹出选择配网还是添加摄像头的弹框
     */
    private void showSelectedDialog() {
        new AlertDialog.Builder(_mActivity)
                .setItems(addSelectedArr, (dialog, which) -> {
                    if (which == 0) {
                        _mActivity.start(CameraWifiInput2Fragment.newInstance());
                    } else {
                        showAddCameraDialog();
                    }
                })
                .show();
    }

    /**
     * 弹出添加摄像头的输入框
     */
    private void showAddCameraDialog() {
        dialogAddCamera = new BaseDialogFragment()
                .setLayoutId(R.layout.fragment_input_video)
                .setConvertListener((holder, dialog) -> {
                    EditText etDeviceId = holder.getView(R.id.et_input_1);
                    EditText etNickname = holder.getView(R.id.et_input_2);
                    EditText etPassword = holder.getView(R.id.et_input_3);
                    holder.setText(R.id.tv_title, "添加设备")
                            .setText(R.id.et_input_2, params.name)
                            .setOnClickListener(R.id.tv_cancel, v -> {
                                dialog.dismiss();
                            })
                            .setOnClickListener(R.id.tv_comfirm, v -> {
                                params.deviceId = etDeviceId.getText().toString().trim();
                                params.name = etNickname.getText().toString().trim();
                                params.devicePassword = etPassword.getText().toString().trim();
                                if (TextUtils.isEmpty(params.deviceId)) {
                                    DialogHelper.warningSnackbar(getView(), "请输入摄像头ID");
                                    return;
                                }
                                if (TextUtils.isEmpty(params.name)) {
                                    DialogHelper.warningSnackbar(getView(), "请输入摄像头昵称");
                                    return;
                                }
                                if (TextUtils.isEmpty(params.devicePassword)) {
                                    DialogHelper.warningSnackbar(getView(), "请输入摄像头密码");
                                    return;
                                }
                                mPresenter.requestAddCamera(params);
                            });
                })
                .setMargin(40)
                .setDimAmount(0.3f)
                .setGravity(Gravity.CENTER)
                .show(getFragmentManager());
    }


}
