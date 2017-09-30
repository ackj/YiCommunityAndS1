package com.aglhz.s1.room.view;

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

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.room.contract.DeviceOnOffContract;
import com.aglhz.s1.room.presenter.DeviceOnOffPresenter;
import com.aglhz.yicommunity.R;
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

    Unbinder unbinder;
    private int node;
    private String name;
    private DeviceOnOffRVAdapter adapter;
    private Params params = Params.getInstance();

    public static DeviceOnOffFragment newInstance(String name, int node, int index) {
        DeviceOnOffFragment fragment = new DeviceOnOffFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("node", node);
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
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
        name = getArguments().getString("name");
        node = getArguments().getInt("node");
        params.index = getArguments().getInt("index");
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
        toolbarTitle.setText(name);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
    }

    private void initData() {
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= node; i++) {
            data.add(i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new DeviceOnOffRVAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setNewData(data);
    }

    private void initListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
                params.nodeId = position + "";
                switch (view.getId()) {
                    case R.id.ll_open:
                        params.status = 1;
                        break;
                    case R.id.ll_close:
                        params.status = 0;
                        break;
                }
                mPresenter.requestDeviceCtrl(params);
            }
        });
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
