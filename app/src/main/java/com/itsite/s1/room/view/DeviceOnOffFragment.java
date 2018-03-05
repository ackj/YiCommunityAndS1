package com.itsite.s1.room.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import com.itsite.s1.common.Params;
import com.itsite.s1.entity.bean.BaseBean;
import com.itsite.s1.entity.bean.DeviceListBean;
import com.itsite.s1.entity.bean.RoomsBean;
import com.itsite.s1.room.contract.DeviceOnOffContract;
import com.itsite.s1.room.presenter.DeviceOnOffPresenter;
import com.itsite.yicommunity.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: LiuJia on 2017/8/29 0029 17:28.
 * Email: liujia95me@126.com
 */

public class DeviceOnOffFragment extends BaseFragment<DeviceOnOffContract.Presenter> implements DeviceOnOffContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;

    Unbinder unbinder;

    private DeviceOnOffRVAdapter adapter;
    private Params params = Params.getInstance();
    private DeviceListBean.DataBean.SubDevicesBean bean;
    private RoomsBean.DataBean.RoomListBean selectRoom;

    public static DeviceOnOffFragment newInstance(DeviceListBean.DataBean.SubDevicesBean bean, RoomsBean.DataBean.RoomListBean selectRoom) {
        DeviceOnOffFragment fragment = new DeviceOnOffFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        bundle.putSerializable("selectRoom", selectRoom);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean = (DeviceListBean.DataBean.SubDevicesBean) getArguments().getSerializable("bean");
        selectRoom = (RoomsBean.DataBean.RoomListBean) getArguments().getSerializable("selectRoom");
    }

    @NonNull
    @Override
    protected DeviceOnOffContract.Presenter createPresenter() {
        return new DeviceOnOffPresenter(this);
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
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText(bean.getName());
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });

        toolbarMenu.setText("设置");
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.startForResult(AddDeviceFragment.newInstance(bean, selectRoom), 100);
            }
        });
    }

    private void initData() {
        params.index = bean.getIndex();
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= bean.getExtInfo().getNode(); i++) {
            data.add(i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new DeviceOnOffRVAdapter();
        //具有3个操作的才是窗帘
        adapter.setIsCurtains(bean.getActions().size() == 3);
        recyclerView.setAdapter(adapter);

        adapter.setNewData(data);
    }

    private void initListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
                params.nodeId = position + "";
                switch (view.getId()) {
                    case R.id.ll_stop:
                        params.status = 2;
                        break;
                    case R.id.ll_open:
                        params.status = 1;
                        break;
                    case R.id.ll_close:
                        params.status = 0;
                        break;
                    default:
                }
                mPresenter.requestDeviceCtrl(params);
            }
        });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            pop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void responseOnOffSuccess(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
    }
}
