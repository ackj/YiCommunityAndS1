package com.aglhz.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.entity.bean.ParkSelectBean.DataBean.ParkPlaceListBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * [我的车卡]的View层。
 * 打开方式：StartApp-->管家-->智慧管家[办理车卡]
 */
public class ApplyCardFragment extends BaseFragment {
    public static final String TAG = ApplyCardFragment.class.getSimpleName();
    public static final int TYPE_APPLY_CAR_CARD = 0;//申请月卡。
    public static final int TYPE_APPLY_CARPORT = 1;//申请车位卡。
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private Unbinder unbinder;

    public static ApplyCardFragment newInstance() {
        return new ApplyCardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_card, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("办理车卡");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        viewPager.setAdapter(new ApplyCardAdapter(getChildFragmentManager()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        ALog.e("requestCode--" + requestCode);
        ALog.e("resultCode--" + resultCode);
        ALog.e("data--" + data);

        if (data == null) {
            return;
        }
        ParkPlaceListBean parkBean = (ParkPlaceListBean) data.getSerializable(Constants.KEY_PARK);
        if (parkBean != null) {
            ((ApplyFragment) getChildFragmentManager()
                    .getFragments()
                    .get(requestCode))
                    .setPlate(parkBean);
        }
    }
}
