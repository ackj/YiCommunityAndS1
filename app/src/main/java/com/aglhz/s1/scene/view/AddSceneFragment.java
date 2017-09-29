package com.aglhz.s1.scene.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.abase.mvp.view.base.Decoration;
import com.aglhz.abase.utils.KeyBoardUtils;
import com.aglhz.s1.App;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.CommandBean;
import com.aglhz.s1.entity.bean.DeviceListBean;
import com.aglhz.s1.event.EventRefreshSceneList;
import com.aglhz.s1.scene.contract.AddSceneContract;
import com.aglhz.s1.scene.presenter.AddScenePresenter;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.widget.PtrHTFrameLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.statemanager.StateManager;
import me.yokeyword.fragmentation.SupportFragment;

import static com.aglhz.abase.mvp.view.base.Decoration.VERTICAL_LIST;
import static com.aglhz.s1.scene.view.AddSceneRVAdapter.TYPE_SWITCH;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class AddSceneFragment extends BaseFragment<AddSceneContract.Presenter> implements AddSceneContract.View {
    public static final String TAG = AddSceneFragment.class.getSimpleName();
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
    @BindView(R.id.et_name_add_scene_fragment)
    EditText etName;
    @BindView(R.id.ll_add_device_add_scene_fragment)
    LinearLayout llAddDevice;
    private Unbinder unbinder;
    private Params params = Params.getInstance();
    private AddSceneRVAdapter adapter;
    private StateManager mStateManager;
    private List<CommandBean> commandList = new ArrayList<>();

    public static AddSceneFragment newInstance() {
        return new AddSceneFragment();
    }

    @NonNull
    @Override
    protected AddSceneContract.Presenter createPresenter() {
        return new AddScenePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_scene, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initStateManager();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("场景编辑");
        toolbarMenu.setText("确定");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new AddSceneRVAdapter();
        adapter.setEnableLoadMore(true);
        recyclerView.addItemDecoration(new Decoration(_mActivity, VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.layout_state_empty)
                .setEmptyText("还没有设备，请点击添加设备！")
                .setEmptyOnClickListener(v -> startForResult(DeviceListFragment.newInstance(), SupportFragment.RESULT_OK))
                .setConvertListener((holder, stateLayout) ->
                        holder.setOnClickListener(R.id.bt_empty_state,
                                v -> startForResult(DeviceListFragment.newInstance(), SupportFragment.RESULT_OK))
                                .setText(R.id.bt_empty_state, "点击添加"))
                .build();
        mStateManager.showEmpty();
    }

    private void initListener() {
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            int viewType = adapter1.getItemViewType(position);
            switch (viewType) {
                case TYPE_SWITCH:
                    switch (view.getId()) {
                        case R.id.tv_node_item_rv_add_scene_fragment:
                            showNodeSelecotr(position, ((TextView) view));
                            break;
                        case R.id.tv_action_item_rv_add_scene_fragment:
                            showActionSelecotr(position, ((TextView) view));
                            break;
                        case R.id.iv_delete_item_rv_add_scene_fragment:
                            commandList.remove(position);
                            adapter.remove(position);
                            if (adapter.getData().isEmpty()) {
                                mStateManager.showEmpty();
                            }
                            break;
                    }
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(etName, App.mContext);
        unbinder.unbind();
    }

    @OnClick({R.id.toolbar_menu, R.id.ll_add_device_add_scene_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_menu:
                params.name = etName.getText().toString().trim();
                if (TextUtils.isEmpty(params.name)) {
                    DialogHelper.warningSnackbar(getView(), "场景名称不能为空！");
                    return;
                }
                if (adapter.getData().isEmpty()) {
                    DialogHelper.warningSnackbar(getView(), "设备不能为空！");
                    return;
                }
                params.paramJson = new Gson().toJson(commandList);
                ALog.e("params.paramJson-->" + params.paramJson);
                mPresenter.requestAddScene(params);
                break;
            case R.id.ll_add_device_add_scene_fragment:
                KeyBoardUtils.hideKeybord(etName, App.mContext);
                startForResult(DeviceListFragment.newInstance(), SupportFragment.RESULT_OK);
                break;
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == SupportFragment.RESULT_OK && data != null) {
            ArrayList<DeviceListBean.DataBean.SubDevicesBean> selector = data.getParcelableArrayList(Constants.KEY_SELECTOR);
            if (selector != null) {
                for (DeviceListBean.DataBean.SubDevicesBean subDevice : selector) {
                    commandList.add(new CommandBean(subDevice.getIndex(), 0, 1));
                }
            }
            if (adapter.getData().isEmpty()) {
                adapter.getData().addAll(selector);
                adapter.setNewData(adapter.getData());
            } else {
                adapter.addData(selector);
            }
            mStateManager.showContent();
        }
    }

    private void showActionSelecotr(int position, TextView textView) {
        List<String> list = new ArrayList<>();
        list.add(0, "关闭");
        list.add(1, "打开");
        new SelectorDialogFragment()
                .setTitle("请选择开关")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(list)
                .setOnItemConvertListener((holder, which, dialog) ->
                        holder.setText(R.id.tv_item_rv_simple_selector, list.get(which)))
                .setOnItemClickListener((view, baseViewHolder, which, dialog) -> {
                    dialog.dismiss();
                    commandList.get(position).setAction1(which);
                    textView.setText(which == 1 ? "打开" : "关闭");
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    private void showNodeSelecotr(int position, TextView textView) {
        List<String> list = new ArrayList<>();
        int node = adapter.getData().get(position).getExtInfo().getNode();
        for (int i = 0; i < node; i++) {
            list.add("第　" + (i + 1) + "　路");
        }

        new SelectorDialogFragment()
                .setTitle("请选择")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(list)
                .setOnItemConvertListener((holder, which, dialog) ->
                        holder.setText(R.id.tv_item_rv_simple_selector, list.get(which)))
                .setOnItemClickListener((view, baseViewHolder, which, dialog) -> {
                    dialog.dismiss();
                    commandList.get(position).setNodeId(which);
                    textView.setText("第　" + (which + 1) + "　路");
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    @Override
    public void responseAddScene(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        EventBus.getDefault().post(new EventRefreshSceneList());
        pop();
    }
}
