package com.aglhz.yicommunity.main.supermarket.orderform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.main.supermarket.info.DestinationListFragment;
import com.aglhz.yicommunity.main.supermarket.shopcart.SubmitOrderformRVAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author: LiuJia on 2017/11/2 0002 17:48.
 * Email: liujia95me@126.com
 */

public class OrderformDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = OrderformDetailFragment.class.getSimpleName();

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ptrFrameLayout)
    PtrFrameLayout ptrFrameLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private StateManager mStateManager;
    private Params params = Params.getInstance();
    private SubmitOrderformRVAdapter adapter = new SubmitOrderformRVAdapter();

    public static OrderformDetailFragment newInstance() {
        return new OrderformDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderform_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initStateManager();
        initData();
        initListener();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("订单详情");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.pageSize = Constants.PAGE_SIZE;
//        mPresenter.start(params);
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.layout_state_empty)
                .setEmptyText("抱歉，当前定位暂无便利店配送服务，敬请期待~")
                .setErrorOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setConvertListener((holder, stateLayout) ->
                        holder.setOnClickListener(R.id.bt_empty_state,
                                v -> _mActivity.start(DestinationListFragment.newInstance()))
                                .setText(R.id.bt_empty_state, "切换地址"))
                .build();
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.setAdapter(adapter);

        //添加头：派送员
        View headerDeliverer = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_deliverer, null);
        adapter.addHeaderView(headerDeliverer);
        //添加头：地址
        View headerAddress = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_destination, null);
        headerAddress.findViewById(R.id.tv_update).setOnClickListener(this);
        adapter.addHeaderView(headerAddress);
        //添加脚：支付方式
        View footerPayWay = LayoutInflater.from(_mActivity).inflate(R.layout.layout_footer_payway, null);
        adapter.addFooterView(footerPayWay);
        //添加脚：订单号
        View footerOrderNum = LayoutInflater.from(_mActivity).inflate(R.layout.layout_footer_order_number, null);
        adapter.addFooterView(footerOrderNum);

        List<String> datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        adapter.setNewData(datas);

        ptrFrameLayout.refreshComplete();
        if (datas == null || datas.isEmpty()) {
            if (params.page == 1) {
                mStateManager.showEmpty();
            }
            adapter.loadMoreEnd();
            return;
        }
        if (params.page == 1) {
            mStateManager.showContent();
            adapter.setNewData(datas);
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
        } else {
            adapter.addData(datas);
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
        }
    }

    private void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_update:
                _mActivity.start(DestinationListFragment.newInstance());
                break;
            default:
                break;
        }
    }


}
