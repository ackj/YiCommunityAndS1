package com.aglhz.yicommunity.message.view;

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

import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.message.contract.CompainsReplyContract;
import com.aglhz.yicommunity.message.presenter.CompainReplyPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author: LiuJia on 2017/5/18 0018 17:36.
 * Email: liujia95me@126.com
 */

public class CompainsReplyFragment extends BaseFragment<CompainReplyPresenter> implements CompainsReplyContract.View {
    private static final String TAG = CompainsReplyFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ptrFrameLayout)
    PtrFrameLayout ptrFrameLayout;

    private Unbinder unbinder;
    private CompainsReplyRVAdapter adapter;

    public static CompainsReplyFragment newInstance() {
        return new CompainsReplyFragment();
    }

    @NonNull
    @Override
    protected CompainReplyPresenter createPresenter() {
        return new CompainReplyPresenter(this);
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
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("物业投诉回复");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new CompainsReplyRVAdapter();
        recyclerView.setAdapter(adapter);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("张三" + i);
        }
        adapter.setNewData(datas);
        adapter.setOnItemChildClickListener((adapter, view, position) -> ALog.e(position));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void start(Object response) {

    }

    @Override
    public void error(String errorMessage) {

    }
}
