package com.aglhz.s1.scene.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.s1.common.Params;
import com.aglhz.yicommunity.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class SceneEditFragment extends BaseFragment {
    public static final String TAG = SceneEditFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_devices)
    RecyclerView recyclerViewDevices;
    @BindView(R.id.recyclerView_movement)
    RecyclerView recyclerViewMovement;
    private Unbinder unbinder;
    private Params params = Params.getInstance();
    AddDeviceRVAdapter deviceAdapter;
    DeviceMovementRVAdapter movementAdapter;

    public static SceneEditFragment newInstance() {
        return new SceneEditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_scene1, container, false);
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
        toolbarTitle.setText("场景编辑");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        recyclerViewDevices.setLayoutManager(new GridLayoutManager(_mActivity,4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        deviceAdapter = new AddDeviceRVAdapter();
        recyclerViewDevices.setAdapter(deviceAdapter);
        List<String> data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        deviceAdapter.setNewData(data);

        recyclerViewMovement.setLayoutManager(new LinearLayoutManager(_mActivity){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        movementAdapter = new DeviceMovementRVAdapter();
        recyclerViewMovement.setAdapter(movementAdapter);
        movementAdapter.setNewData(data);
    }

    private void initListener() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}
