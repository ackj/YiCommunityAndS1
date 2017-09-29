package com.aglhz.s1.scene.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.abase.mvp.view.base.Decoration;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.SceneBean;
import com.aglhz.s1.event.EventRefreshSceneList;
import com.aglhz.s1.event.EventSwitchHost;
import com.aglhz.s1.scene.contract.SceneListContract;
import com.aglhz.s1.scene.presenter.SceneListPresenter;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.widget.PtrHTFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.itsite.statemanager.StateManager;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class SceneListFragment extends BaseFragment<SceneListContract.Presenter> implements SceneListContract.View {
    private static final String TAG = SceneListFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ptrFrameLayout)
    PtrHTFrameLayout ptrFrameLayout;
    private SceneRVAdapter adapter;
    Unbinder unbinder;
    private StateManager mStateManager;
    private Params params = Params.getInstance();

    public static SceneListFragment newInstance() {
        return new SceneListFragment();
    }

    @NonNull
    @Override
    protected SceneListContract.Presenter createPresenter() {
        return new SceneListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
        initStateManager();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initData() {
        adapter = new SceneRVAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.addItemDecoration(new Decoration(_mActivity, Decoration.VERTICAL_LIST));
        adapter.setEnableLoadMore(true);
        //设置允许加载更多
        adapter.setOnLoadMoreListener(() -> {
            params.page++;
            mPresenter.requestSceneList(params);//请求开门记录列表
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.pageSize = Constants.PAGE_SIZE;
        mPresenter.requestSceneList(params);
    }

    private void initListener() {
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_delete_item_scene:
                    params.index = adapter.getData().get(position).getIndex();
                    mPresenter.requestDeleteScene(params);
                    params.option = position;
                    break;
                case R.id.tv_toggle_item_scene:
                    params.index = adapter.getData().get(position).getIndex();
                    mPresenter.requestStartScene(params);
                    break;
                default:
                    _mActivity.start(SceneEditFragment.newInstance());
                    break;
            }
        });
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.layout_state_empty)
                .setEmptyText("暂无场景，请点击添加！")
                .setErrorOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> _mActivity.start(AddSceneFragment.newInstance()))
                .setConvertListener((holder, stateLayout) ->
                        holder.setOnClickListener(R.id.bt_empty_state,
                                v -> _mActivity.start(AddSceneFragment.newInstance()))
                                .setText(R.id.bt_empty_state, "点击添加"))
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Override
    public void responseSceneList(SceneBean data) {
        ptrFrameLayout.refreshComplete();
        if (data == null || data.getData().isEmpty()) {
            if (params.page == 1) {
                mStateManager.showEmpty();
            }
            adapter.loadMoreEnd();
            return;
        }
        if (params.page == 1) {
            mStateManager.showContent();
            adapter.setNewData(data.getData());
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
        } else {
            adapter.addData(data.getData());
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void responseStartScene(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
    }

    @Override
    public void responseDeleteScene(BaseBean bean) {
        adapter.remove(params.option);
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddScene(EventRefreshSceneList event) {
        ptrFrameLayout.autoRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchHost(EventSwitchHost event) {
        onRefresh();
    }
}
