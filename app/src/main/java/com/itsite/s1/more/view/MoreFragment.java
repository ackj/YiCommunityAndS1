package com.itsite.s1.more.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.LogInterceptor;
import com.itsite.s1.about.AboutActivity;
import com.itsite.s1.common.Params;
import com.itsite.s1.event.EventLogin;
import com.itsite.s1.host.view.AddHostFragment;
import com.itsite.s1.host.view.HostListFragment;
import com.itsite.s1.more.contract.MoreContract;
import com.itsite.s1.more.presenter.MorePresenter;
import com.itsite.s1.net.view.SetWifiFragment;
import com.itsite.s1.qrcode.ScanQRCodeFragment;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.common.UserHelper;
import com.itsite.yicommunity.login.LoginActivity;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author: LiuJia on 2017/4/27 0027 17:27.
 * Email: liujia95me@126.com
 */

public class MoreFragment extends BaseFragment<MoreContract.Presenter> implements MoreContract.View {
    public static final String TAG = MoreFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.sv_more_fragment)
    ScrollView sv;
    Unbinder unbinder;
    private Params params = Params.getInstance();
    private List<String> addHostTypes;

    public static SupportFragment newInstance() {
        return new MoreFragment();
    }

    @NonNull
    @Override
    protected MoreContract.Presenter createPresenter() {
        return new MorePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("更多");
    }

    @OnClick({R.id.rl_head,
            R.id.ll_room_manager,
            R.id.ll_host_manager,
            R.id.ll_wifi_setting,
            R.id.ll_add_host,
            R.id.ll_about,
            R.id.ll_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_head:
                if (isLogined()) {
                }
                break;
            case R.id.ll_room_manager:
                _mActivity.start(RoomManagerFragment.newInstance());
                break;
            case R.id.ll_host_manager:
                _mActivity.start(HostListFragment.newInstance());
                break;
            case R.id.ll_wifi_setting:
                _mActivity.start(SetWifiFragment.newInstance());
                break;
            case R.id.ll_add_host:
                showAddHostSelecotr();
                break;
            case R.id.ll_about:
                _mActivity.startActivity(new Intent(_mActivity, AboutActivity.class));
                break;
            case R.id.ll_logout:
                new AlertDialog.Builder(_mActivity)
                        .setTitle("提示")
                        .setMessage("确定退出登录？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            mPresenter.requestLogout(params);//请求服务器注销。
                            onLoginoutEvent(null);
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }

    private void initData() {
        updataView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void showAddHostSelecotr() {
        if (addHostTypes == null) {
            addHostTypes = new ArrayList<>();
            addHostTypes.add(0, "扫码添加");
            addHostTypes.add(1, "手动输入添加");
        }
        new SelectorDialogFragment()
                .setTitle("请选择添加方式")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(addHostTypes)
                .setOnItemConvertListener((holder, position, dialog) ->
                        holder.setText(R.id.tv_item_rv_simple_selector, addHostTypes.get(position)))
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    switch (position) {
                        case 0:
                            _mActivity.start(ScanQRCodeFragment.newInstance());
                            break;
                        case 1:
                            _mActivity.start(AddHostFragment.newInstance("", null));
                            break;
                    }
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(EventLogin event) {
        updataView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginoutEvent(LogInterceptor event) {
        tvNickname.setText("访客");
        tvPhoneNumber.setText("");
        ivAvatar.setImageResource(R.drawable.ic_more_avatar_black_180px);
        sv.post(() -> sv.fullScroll(ScrollView.FOCUS_UP));//滑动到顶部，提高用户体验，方便用户点击头像登录。
//        PushAgent.getInstance(App.mContext)
//                .removeAlias(UserHelper.account, "userType", (b, s) -> {
//                    ALog.e("b-->" + b);
//                    ALog.e("s-->" + s);
//                });
        UserHelper.clear();//要放在最后清除，不然上面用到UserHelper.account也为空了
    }

    private void updataView() {
        if (isLogined()) {
            Glide.with(this)
                    .load(UserHelper.userInfo.getFace())
                    .placeholder(R.drawable.ic_more_avatar_black_180px)
                    .error(R.drawable.ic_more_avatar_black_180px)
                    .bitmapTransform(new CropCircleTransformation(_mActivity))
                    .into(ivAvatar);

            tvNickname.setText(UserHelper.userInfo.getNickName());
            tvPhoneNumber.setText(UserHelper.userInfo.getMobile());
        }
    }

    private boolean isLogined() {
        if (UserHelper.isLogined()) {
            return true;
        } else {
            startActivity(new Intent(_mActivity, LoginActivity.class));
            _mActivity.overridePendingTransition(0, 0);
            return false;
        }
    }

    @Override
    public void responseLogout(String message) {
        DialogHelper.successSnackbar(getView(), message);
    }
}
