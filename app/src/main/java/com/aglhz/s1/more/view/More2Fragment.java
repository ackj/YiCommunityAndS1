package com.aglhz.s1.more.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.event.EventAddDevice;
import com.aglhz.s1.event.EventDeviceNameChanged;
import com.aglhz.s1.host.view.HostListFragment;
import com.aglhz.s1.host.view.HostSettingsFragment;
import com.aglhz.s1.net.view.SetWifiFragment;
import com.aglhz.s1.room.view.AddDeviceFragment;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.UserHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author: LiuJia on 2017/9/25 0025 18:24.
 * Email: liujia95me@126.com
 */

public class More2Fragment extends BaseFragment {

    public static final String TAG = More2Fragment.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.tv_name)
    TextView tvName;
    private Params params = Params.getInstance();
    private List<String> addHostTypes;

    public static SupportFragment newInstance() {
        return new More2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_2, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        tvName.setText(UserHelper.deviceName);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventAddDevice(EventDeviceNameChanged event) {
        tvName.setText(UserHelper.deviceName);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.ll_wifi_setting, R.id.ll_host_manager, R.id.ll_room_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wifi_setting:
                _mActivity.start(SetWifiFragment.newInstance());
                break;
            case R.id.ll_host_manager:
                _mActivity.start(HostSettingsFragment.newInstance());
                break;
            case R.id.ll_room_manager:
                _mActivity.start(RoomManagerFragment.newInstance());
                break;
        }
    }
}