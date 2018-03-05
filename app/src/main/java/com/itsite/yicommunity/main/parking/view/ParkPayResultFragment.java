package com.itsite.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.entity.bean.ParkPayResultBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块的容器Activity。
 */
public class ParkPayResultFragment extends BaseFragment {
    private static final String TAG = ParkPayResultFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_park_park_pay_result_fragment)
    TextView tvPark;
    @BindView(R.id.tv_plate_park_pay_result_fragment)
    TextView tvPlate;
    @BindView(R.id.tv_order_park_pay_result_fragment)
    TextView tvOrder;
    @BindView(R.id.tv_pay_time_park_pay_result_fragment)
    TextView tvPayTime;
    @BindView(R.id.tv_charge_park_pay_result_fragment)
    TextView tvCharge;
    @BindView(R.id.bt_back_park_pay_result_fragment)
    Button btBack;
    private Unbinder unbinder;
    private ParkPayResultBean result;

    public static ParkPayResultFragment newInstance(Bundle bundle) {
        ParkPayResultFragment fragment = new ParkPayResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            result = ((ParkPayResultBean) bundle.getSerializable(Constants.KEY_PAR_KPAY_RESULT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_park_pay_result, container, false);
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
        toolbarTitle.setText("缴费成功");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        tvPark.setText(result.park);
        tvPlate.setText(result.plate);
        tvOrder.setText(result.order);
        tvPayTime.setText(result.time);
        tvCharge.setText(result.amount + " 元");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_back_park_pay_result_fragment)
    public void onViewClicked() {
        _mActivity.onBackPressedSupport();
    }
}
