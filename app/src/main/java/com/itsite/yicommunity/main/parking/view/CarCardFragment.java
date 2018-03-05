package com.itsite.yicommunity.main.parking.view;

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
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.entity.bean.CarCardListBean;
import com.itsite.yicommunity.main.guide.GuideHelper;
import com.itsite.yicommunity.main.parking.contract.CarCardContract;
import com.itsite.yicommunity.main.parking.presenter.CarCardPresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/4/19 9:39.
 * [我的车卡]的View层。
 * 打开方式：StartApp-->管家-->智慧管家[我的车卡]
 */
public class CarCardFragment extends BaseFragment<CarCardContract.Presenter> implements CarCardContract.View {
    private static final String TAG = CarCardFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ptrFrameLayout)
    PtrFrameLayout ptrFrameLayout;
    private Unbinder unbinder;
    private CarCardAdapter adapter;
    private Params params = Params.getInstance();
    private int removePosition;
    private StateManager mStateManager;

    public static CarCardFragment newInstance() {
        return new CarCardFragment();
    }

    @NonNull
    @Override
    protected CarCardContract.Presenter createPresenter() {
        return new CarCardPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
        initStateManager();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("我的车卡");
        toolbarMenu.setText("充值记录");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.pageSize = Constants.PAGE_SIZE;
        mPresenter.requestCarCardList(params);//请求车卡列表
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new CarCardAdapter();

        //设置Item动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(true);

        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> {
            params.page++;
            mPresenter.requestCarCardList(params);//请求车卡列表
        }, recyclerView);

        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Bundle bundle = new Bundle();
            CarCardListBean.DataBean.CardListBean item = adapter.getItem(position);
            bundle.putSerializable(Constants.KEY_ITEM, item);
            startForResult(CarcardPayFragment.newInstance(bundle), CarcardPayFragment.RESULT_CODE);
        });
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.state_empty)
                .setEmptyImage(R.drawable.ic_car_card_empty_state_gray_200px)
                .setEmptyText("您当前还未办理车卡！")
                .setErrorOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .build();
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        ALog.e("requestCode--" + requestCode);
        ALog.e("resultCode--" + resultCode);
        ALog.e("data--" + data);
        if (resultCode == CarcardPayFragment.RESULT_CODE) {
            mPresenter.requestCarCardList(params);//刷新车卡列表。
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void error(String errorMessage) {
        ptrFrameLayout.refreshComplete();
        if (params.page == 1) {
            //为后面的pageState做准备
            mStateManager.showError();
        } else if (params.page > 1) {
            adapter.loadMoreFail();
            params.page--;
        }
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    /**
     * 响应请求车卡列表
     *
     * @param datas
     */
    @Override
    public void responseCarCardList(List<CarCardListBean.DataBean.CardListBean> datas) {
        ptrFrameLayout.refreshComplete();
        if (datas == null || datas.isEmpty()) {
            if (params.page == 1) {
                mStateManager.showEmpty();
            }
            adapter.loadMoreEnd();
            return;
        } else {
            if (!GuideHelper.showed(_mActivity)) {
                for (CarCardListBean.DataBean.CardListBean bean : datas) {
                    GuideHelper.showMyCardGuide(_mActivity);
                }
            }
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

    /**
     * 响应请求删除车卡（只有在车卡到期才能删除）
     *
     * @param baseBean
     */
    @Override
    public void responseDeleteSuccess(BaseBean baseBean) {
        adapter.remove(removePosition);
    }

    @OnClick(R.id.toolbar_menu)
    public void onViewClicked() {
        start(RechargeRecordFragment.newInstance());
    }
}
