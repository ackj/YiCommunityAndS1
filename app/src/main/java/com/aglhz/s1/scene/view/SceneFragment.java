package com.aglhz.s1.scene.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.s1.linkage.view.LinkageEditFragment;
import com.aglhz.s1.linkage.view.LinkageListFragment;
import com.aglhz.yicommunity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class SceneFragment extends BaseFragment {
    public static final String TAG = SceneFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.tablayout_scene)
    TabLayout tabLayout;
    Unbinder unbinder;
    private int selectedPos = 0;
    private SupportFragment[] fragments = new SupportFragment[2];

    public static SceneFragment newInstance() {
        return new SceneFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scene, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            fragments[0] = SceneListFragment.newInstance();
            fragments[1] = LinkageListFragment.newInstance();
            loadMultipleRootFragment(R.id.framelayout_scene, 0, fragments[0], fragments[1]);
        } else {
            fragments[0] = findChildFragment(SceneListFragment.class);
            fragments[1] = findChildFragment(LinkageListFragment.class);
        }
        initToolbar();
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("场景联动");
        toolbarMenu.setText("添加");
    }

    @OnClick(R.id.toolbar_menu)
    public void onViewClicked() {
        if (selectedPos == 0) {
            _mActivity.start(AddSceneFragment.newInstance());
        } else {
            _mActivity.start(LinkageEditFragment.newInstance());
        }
    }

    private void initData() {
        tabLayout.addTab(tabLayout.newTab().setText("智能场景"));
        tabLayout.addTab(tabLayout.newTab().setText("智能联动"));
    }

    private void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int hidePosition = tab.getPosition() == 0 ? 1 : 0;
                selectedPos = tab.getPosition();
                Log.d("onTabSelected", "hidePosition:" + hidePosition);
                showHideFragment(fragments[tab.getPosition()], fragments[hidePosition]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
