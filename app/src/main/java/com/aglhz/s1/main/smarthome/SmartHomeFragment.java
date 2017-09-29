package com.aglhz.s1.main.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.EquipmentBean;
import com.aglhz.s1.main.home.MainActivity;
import com.aglhz.s1.main.smarthome.contract.SmartHomeContract;
import com.aglhz.s1.main.smarthome.presenter.SmartHomePresenter;
import com.aglhz.s1.qrcode.ScanQRCodeFragment;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.UserHelper;
import com.aglhz.yicommunity.widget.PtrHTFrameLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: LiuJia on 2017/9/25 0025 09:35.
 * Email: liujia95me@126.com
 */

public class SmartHomeFragment extends BaseFragment<SmartHomeContract.Presenter> implements SmartHomeContract.View {

    private static final String TAG = SmartHomeFragment.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ptrFrameLayout)
    PtrHTFrameLayout ptrFrameLayout;

    private Unbinder unbinder;
    private SmartHomeListAdapter adapter;
    private EquipmentBean.DataBean.DataListBean addBean;//添加的item

    Params params = Params.getInstance();

    public static SmartHomeFragment newInstance(String roomDir) {
        ALog.e(TAG, "roomDir:" + roomDir);
        SmartHomeFragment fragment = new SmartHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomDir", roomDir);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SmartHomeFragment newInstance() {
        return new SmartHomeFragment();
    }

    @NonNull
    @Override
    protected SmartHomeContract.Presenter createPresenter() {
        return new SmartHomePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        params.roomDir = getArguments().getString("roomDir");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestEquipmentInfoList(params);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("智能家居");
        toolbarTitle.setOnClickListener(v -> _mActivity.onBackPressedSupport());
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new SmartHomeListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(_mActivity.getResources().getColor(R.color.material_grey_300));
        params.powerCode = "SmartEquipment";

        addBean = new EquipmentBean.DataBean.DataListBean();
        addBean.setDeviceName("添加中控");
    }

    private void initListener() {
        adapter.setOnItemGridClickListener(new SmartHomeListAdapter.OnItemGridClickListener() {
            @Override
            public void click(BaseRecyclerViewAdapter adapter, EquipmentBean.DataBean.DataListBean item, int position) {
                if (adapter.getData().size() - 1 == position) {
                    //最后一个
                    _mActivity.start(ScanQRCodeFragment.newInstance(params.roomDir));
                } else {
                    //todo(高亮) 从这里获取deviceSn
                    UserHelper.deviceSn = item.getDeviceSn();
                    UserHelper.deviceName = item.getDeviceName();
                    Intent intent = new Intent(App.mContext, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.mContext.startActivity(intent);
                }
            }

            @Override
            public void longClick(BaseRecyclerViewAdapter adapter, EquipmentBean.DataBean.DataListBean item, int position) {
                if (adapter.getData().size() - 1 == position) {
                    //最后一个不做操作
                } else {
                    new AlertDialog.Builder(_mActivity)
                            .setTitle("温馨提醒")
                            .setMessage("确定要解绑该网关吗？")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", (dialog, which) -> {
                                params.deviceSn = item.getDeviceSn();
                                mPresenter.requestDelGateway(params);
                            }).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void error(String errorMessage) {
        ptrFrameLayout.refreshComplete();
        DialogHelper.errorSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseEquipmentInfoList(List<EquipmentBean.DataBean.DataListBean> data) {
        ptrFrameLayout.refreshComplete();
        adapter.setNewData(null);
        data.add(addBean);
        adapter.addData(data);
    }

    @Override
    public void responseDelGatewaySuccess(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(),baseBean.getOther().getMessage());
        mPresenter.requestEquipmentInfoList(params);
    }
}
