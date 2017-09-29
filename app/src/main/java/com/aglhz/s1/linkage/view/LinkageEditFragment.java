package com.aglhz.s1.linkage.view;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.DeviceListBean;
import com.aglhz.s1.entity.bean.LinkageBean;
import com.aglhz.s1.entity.bean.SceneBean;
import com.aglhz.s1.event.EventLinkageChanged;
import com.aglhz.s1.linkage.contract.AddLinkageContract;
import com.aglhz.s1.linkage.presenter.AddLinkagePresenter;
import com.aglhz.yicommunity.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 联动模块。
 */
public class LinkageEditFragment extends BaseFragment<AddLinkageContract.Presenter> implements AddLinkageContract.View {
    public static final String TAG = LinkageEditFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_trigger_type)
    TextView tvTriggerType;
    @BindView(R.id.tv_linkage_type)
    TextView tvLinkageType;
    @BindView(R.id.ll_scene_gone_container)
    LinearLayout llSceneGoneContainer;
    @BindView(R.id.ll_date_container)
    LinearLayout llDateContainer;
    @BindView(R.id.ll_sensor_container)
    LinearLayout llSensorContainer;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_sensor)
    TextView tvSensor;
    @BindView(R.id.tv_device_sence)
    TextView tvDeviceSence;
    @BindView(R.id.tv_device_node)
    TextView tvDeviceNode;
    @BindView(R.id.tv_sensor_action)
    TextView tvSensorAction;
    @BindView(R.id.tv_device_action)
    TextView tvDeviceAction;
    @BindView(R.id.tv_device_action_2)
    TextView tvDeviceAction2;
    @BindView(R.id.et_minute)
    EditText etMinute;


    private Unbinder unbinder;
    private Params params = Params.getInstance();
    String[] triggerTypeArr = {"传感器", "时间"};
    String[] linkageTypeArr = {"设备", "场景"};
    String[] weekArr = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    boolean[] weekBoolArr = {false, false, false, false, false, false, false};
    private boolean isSensor = false;
    private DeviceListBean.DataBean.SubDevicesBean selectedDevice;//选中的设备
    private DeviceListBean.DataBean.SubDevicesBean selectedSensor;//选中的探测器
    private DeviceListBean.DataBean.SubDevicesBean.ActionsBean selectedDeviceAct;
    private DeviceListBean.DataBean.SubDevicesBean.ActionsBean selectedDeviceAct2;
    private DeviceListBean.DataBean.SubDevicesBean.ActionsBean selectedSensorAct;
    private SceneBean.DataBean selectedScene;//选中的场景
    private LinkageBean.DataBean bean;
    private boolean isMod = false;

    public static LinkageEditFragment newInstance(LinkageBean.DataBean bean) {
        LinkageEditFragment fragment = new LinkageEditFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static LinkageEditFragment newInstance() {
        return new LinkageEditFragment();
    }

    @NonNull
    @Override
    protected AddLinkageContract.Presenter createPresenter() {
        return new AddLinkagePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linkage_edit, container, false);
        bean = (LinkageBean.DataBean) getArguments().getSerializable("bean");
        unbinder = ButterKnife.bind(this, view);
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
        toolbarTitle.setText("联动编辑");
        toolbarMenu.setText("确定");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        if (bean != null) {
            isMod = true;
            etName.setText(bean.getName());
            if(bean.getTriggerType().equals("sensor")){
                tvTriggerType.setText(triggerTypeArr[0]);
//                tvSensor.setText();
            }else{
                tvTriggerType.setText(triggerTypeArr[0]);
            }
        }
        //todo:触发类型为时间有问题，所以先设置死(2017/9/1 16:21
        tvTriggerType.setText(triggerTypeArr[0]);
    }

    private void initListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void responseAddSuccess(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        EventBus.getDefault().post(new EventLinkageChanged());
        pop();
    }

    @Override
    public void responseSceneList(SceneBean bean) {
        List<SceneBean.DataBean> data = bean.getData();
        String[] arr = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i).getName();
        }
        ALog.e(TAG, "responseDeviceList:" + data.size());
        new AlertDialog.Builder(_mActivity)
                .setItems(arr, (dialog, which) -> {
                    selectedScene = data.get(which);
                    tvDeviceSence.setText(arr[which]);
                }).show();
    }

    @Override
    public void responseDeviceList(List<DeviceListBean.DataBean.SubDevicesBean> data) {
        String[] arr = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i).getName();
        }
        ALog.e(TAG, "responseDeviceList:" + data.size());
        new AlertDialog.Builder(_mActivity)
                .setItems(arr, (dialog, which) -> {
                    if (isSensor) {
                        selectedSensor = data.get(which);
                        tvSensor.setText(arr[which]);
                        tvSensorAction.setText("请选择");
                    } else {
                        selectedDevice = data.get(which);
                        tvDeviceSence.setText(arr[which]);
                        tvDeviceAction.setText("请选择");
                        tvDeviceAction2.setText("请选择");
                        tvDeviceNode.setText("请选择");
                    }
                }).show();
    }

    @OnClick({R.id.tv_trigger_type, R.id.tv_linkage_type,
            R.id.ll_week, R.id.ll_time,
            R.id.tv_device_sence, R.id.tv_sensor,
            R.id.tv_sensor_action, R.id.tv_device_action,
            R.id.tv_device_action_2, R.id.tv_device_node,
            R.id.toolbar_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_trigger_type:
//                new AlertDialog.Builder(_mActivity)
//                        .setItems(triggerTypeArr, (dialog, which) -> {
//                            llDateContainer.setVisibility(which == 0 ? View.GONE : View.VISIBLE);
//                            llSensorContainer.setVisibility(which == 0 ? View.VISIBLE : View.GONE);
//                            tvTriggerType.setText(triggerTypeArr[which]);
//                        }).show();
                break;

            case R.id.tv_linkage_type:
                new AlertDialog.Builder(_mActivity)
                        .setItems(linkageTypeArr, (dialog, which) -> {
                            llSceneGoneContainer.setVisibility(which == 0 ? View.VISIBLE : View.GONE);
                            tvLinkageType.setText(linkageTypeArr[which]);
                            tvDeviceSence.setText("请选择");
                        }).show();
                break;
            case R.id.ll_week:
                new AlertDialog.Builder(_mActivity)
                        .setMultiChoiceItems(weekArr, weekBoolArr, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                weekBoolArr[which] = isChecked;
                            }
                        })
                        .setCancelable(false)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 1; i <= weekBoolArr.length; i++) {
                                    if (weekBoolArr[i - 1]) {
                                        if (TextUtils.isEmpty(sb.toString())) {
                                            sb.append(i);
                                        } else {
                                            sb.append(" , " + i);
                                        }
                                    }
                                }
                                tvWeek.setText("周：" + sb.toString());
                            }
                        })
                        .show();
                break;
            case R.id.ll_time:
                new TimePickerDialog(_mActivity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        StringBuilder sb = new StringBuilder();
                        if (hourOfDay < 10) {
                            sb.append("0" + hourOfDay);
                        } else {
                            sb.append(hourOfDay);
                        }
                        sb.append(":");
                        if (minute < 10) {
                            sb.append("0" + minute);
                        } else {
                            sb.append(minute);
                        }
                        tvTime.setText(sb.toString());
                    }
                }, 0, 0, true)
                        .show();
                break;
            case R.id.tv_sensor:
                params.category = Constants.SENSOR;
                isSensor = true;
                mPresenter.requestDeviceList(params);
                break;
            case R.id.tv_device_sence:
                //
                if (linkageTypeArr[0].equals(tvLinkageType.getText().toString())) {
                    ALog.e(TAG, "请求设备");
                    params.category = Constants.DEVICE_CTRL;
                    isSensor = false;
                    mPresenter.requestDeviceList(params);
                } else {
                    ALog.e(TAG, "请求场景");
                    mPresenter.requestSceneList(params);
                }
                break;
            case R.id.tv_sensor_action:
                if (selectedSensor == null) {
                    DialogHelper.warningSnackbar(getView(), "请选择探测器");
                    return;
                }
                String[] sensorActionArr = new String[selectedSensor.getActions().size()];
                for (int i = 0; i < selectedSensor.getActions().size(); i++) {
                    sensorActionArr[i] = selectedSensor.getActions().get(i).getName();
                }
                new AlertDialog.Builder(_mActivity)
                        .setItems(sensorActionArr, (dialog, which) -> {
                            tvSensorAction.setText(sensorActionArr[which]);
                            selectedSensorAct = selectedSensor.getActions().get(which);
                        }).show();
                break;
            case R.id.tv_device_action:
            case R.id.tv_device_action_2:
                if (selectedDevice == null) {
                    DialogHelper.warningSnackbar(getView(), "请选择设备");
                    return;
                }
                String[] deviceActionArr = new String[selectedDevice.getActions().size()];
                for (int i = 0; i < selectedDevice.getActions().size(); i++) {
                    deviceActionArr[i] = selectedDevice.getActions().get(i).getName();
                }
                new AlertDialog.Builder(_mActivity)
                        .setItems(deviceActionArr, (dialog, which) -> {
                            if (view.getId() == R.id.tv_device_action) {
                                tvDeviceAction.setText(deviceActionArr[which]);
                                selectedDeviceAct = selectedDevice.getActions().get(which);
                            } else {
                                tvDeviceAction2.setText(deviceActionArr[which]);
                                selectedDeviceAct2 = selectedDevice.getActions().get(which);
                            }
                        }).show();
                break;
            case R.id.toolbar_menu:
                //探测器类型
                if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    DialogHelper.warningSnackbar(getView(), "请输入联动名称");
                    return;
                }
                params.name = etName.getText().toString();
                if (triggerTypeArr[0].equals(tvTriggerType.getText().toString())) {
                    if (selectedSensor == null) {
                        DialogHelper.warningSnackbar(getView(), "请选择探测器");
                        return;
                    }
                    if ("请选择".equals(tvSensorAction.getText().toString())) {
                        DialogHelper.warningSnackbar(getView(), "请选择探测器触发动作");
                        return;
                    }
                    params.triggerType = Constants.SENSOR;
                    params.cdt_sensorId = selectedSensor.getIndex();
                    params.cdt_sensorAct = selectedSensorAct.getCmd() + "";
                } else { //时间类型
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < weekBoolArr.length; i++) {
                        if (weekBoolArr[i]) {
                            if (TextUtils.isEmpty(sb.toString())) {
                                sb.append(i);
                            } else {
                                sb.append("," + i);
                            }
                        }
                    }
                    if (TextUtils.isEmpty(sb.toString())) {
                        DialogHelper.warningSnackbar(getView(), "请选择触发的星期");
                        return;
                    }
                    params.triggerType = Constants.TIME;
                    params.cdt_day = sb.toString();
                    params.cdt_time = tvTime.getText().toString();
                }

                //设备联动
                if (linkageTypeArr[0].equals(tvLinkageType.getText().toString())) {
                    if ("请选择".equals(tvDeviceSence.getText().toString())) {
                        DialogHelper.warningSnackbar(getView(), "请选择联动的设备");
                        return;
                    }
                    if ("请选择".equals(tvDeviceNode.getText().toString())) {
                        DialogHelper.warningSnackbar(getView(), "请选择要联动到的节点");
                        return;
                    }
                    if ("请选择".equals(tvDeviceAction.getText().toString())) {
                        DialogHelper.warningSnackbar(getView(), "请选择联动动作");
                        return;
                    }
                    if (TextUtils.isEmpty(etMinute.getText().toString())) {
                        DialogHelper.warningSnackbar(getView(), "请输入延时时长");
                        return;
                    }
                    if ("请选择".equals(tvDeviceAction2.getText().toString())) {
                        DialogHelper.warningSnackbar(getView(), "请选择延时联动动作");
                        return;
                    }
                    params.targetType = Constants.DEVICE;
                    params.targetId = selectedDevice.getIndex() + "";
                    params.nodeId = tvDeviceNode.getText().toString();
                    params.act1 = selectedDeviceAct.getCmd() + "";
                    params.act2 = selectedDeviceAct2.getCmd() + "";
                    params.delay = etMinute.getText().toString();
                } else {//场景联动
                    if ("请选择".equals(tvDeviceSence.getText().toString())) {
                        DialogHelper.warningSnackbar(getView(), "请选择联动的场景");
                        return;
                    }
                    params.targetType = Constants.SCENE;
                    params.targetId = selectedScene.getIndex() + "";
                }
                mPresenter.requestNewLinkage(params);
                break;
            case R.id.tv_device_node:
                if ("请选择".equals(tvDeviceSence.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "请选择联动的设备");
                    return;
                }
                String[] nodeArr = new String[selectedDevice.getExtInfo().getNode()];
                for (int i = 0; i < selectedDevice.getExtInfo().getNode(); i++) {
                    nodeArr[i] = i + "";
                }
                new AlertDialog.Builder(_mActivity)
                        .setItems(nodeArr, (dialog, which) -> {
                            tvDeviceNode.setText(nodeArr[which]);
                        }).show();
                break;
        }
    }
}