package com.aglhz.s1.scene.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.itsite.abase.common.RxManager;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.mvp.view.base.Decoration;
import cn.itsite.abase.network.http.HttpHelper;

import com.aglhz.s1.common.Constants;
import com.aglhz.yicommunity.widget.PtrHTFrameLayout;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.DeviceListBean;
import com.aglhz.yicommunity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itsite.statemanager.StateManager;
import me.yokeyword.fragmentation.SupportFragment;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.itsite.abase.mvp.view.base.Decoration.VERTICAL_LIST;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class DeviceListFragment extends BaseFragment {
    public static final String TAG = DeviceListFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ptrFrameLayout)
    PtrHTFrameLayout ptrFrameLayout;
    private Unbinder unbinder;
    private Params params = Params.getInstance();
    private DeviceListRVAdapter adapter;
    private StateManager mStateManager;
    private RxManager mRxManager = new RxManager();

    public static DeviceListFragment newInstance() {
        return new DeviceListFragment();
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
        initStateManager();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("设备列表");
        toolbarMenu.setText("添加");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        params.roomId = -1;
        params.category = Constants.DEVICE_CTRL;
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new DeviceListRVAdapter();
        adapter.setEnableLoadMore(true);
        //设置允许加载更多
        adapter.setOnLoadMoreListener(() -> {
            params.page++;
            requestDeviceList();
        }, recyclerView);
        recyclerView.addItemDecoration(new Decoration(_mActivity, VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.pageSize = Constants.PAGE_SIZE;
        //要清空所选中的那些设备。
        adapter.clearSelector();
        requestDeviceList();
    }

    private void requestDeviceList() {
        mRxManager.add(HttpHelper.getService(ApiService.class)
                .requestSubDeviceList(ApiService.requestSubDeviceList,
                        params.token,
                        Constants.FC,
                        params.page,
                        params.pageSize,
                        params.roomId,
                        params.category,
                        params.deviceSn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        responseDeviceList(bean);
                    } else {
                        error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    private void responseDeviceList(DeviceListBean data) {
        ptrFrameLayout.refreshComplete();
        if (data == null || data.getData() == null
                || data.getData().getSubDevices() == null
                || data.getData().getSubDevices().isEmpty()) {
            if (params.page == 1) {
                mStateManager.showEmpty();
            }
            adapter.loadMoreEnd();
            return;
        }

        if (params.page == 1) {
            mStateManager.showContent();
            adapter.setNewData(data.getData().getSubDevices());
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
        } else {
            adapter.addData(data.getData().getSubDevices());
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
        }
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.layout_state_empty)
                .setEmptyText("没有请求到数据，请点击重试！")
                .setEmptyOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setConvertListener((holder, stateLayout) ->
                        holder.setOnClickListener(R.id.bt_empty_state,
                                v -> ptrFrameLayout.autoRefresh())
                                .setText(R.id.bt_empty_state, "点击刷新"))
                .build();
    }

//    private void initListener() {
//        adapter.setOnItemClickListener((adapter1, view, position) -> {
//            view.findViewById()
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mRxManager.clear();
    }

    @OnClick(R.id.toolbar_menu)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_SELECTOR, adapter.getSelector());
        setFragmentResult(SupportFragment.RESULT_OK, bundle);
        pop();
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
        if (params.page == 1) {
            mStateManager.showError();
        } else if (params.page > 1) {
            adapter.loadMoreFail();
            params.page--;
        }
    }
}
