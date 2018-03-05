package com.itsite.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.CarportBeam;
import com.itsite.yicommunity.entity.bean.ParkSelectBean.DataBean.ParkPlaceListBean;
import com.itsite.yicommunity.main.parking.contract.CarportContract;
import com.itsite.yicommunity.main.parking.presenter.CarportPresenter;
import com.itsite.yicommunity.main.picker.view.ParkPickerFragment;

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
public class CarportFragment extends BaseFragment<CarportContract.Presenter> implements CarportContract.View {
    public static final String TAG = CarportFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_park_carport_fragment)
    TextView tvPark;
    @BindView(R.id.tv_amount_carport_fragment)
    TextView tvAmount;
    private Unbinder unbinder;
    private Params params = Params.getInstance();

    public static CarportFragment newInstance() {
        return new CarportFragment();
    }

    @NonNull
    @Override
    protected CarportContract.Presenter createPresenter() {
        return new CarportPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carport, container, false);
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
        toolbarTitle.setText("车位查询");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_park_carport_fragment, R.id.tv_amount_carport_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_park_carport_fragment:
                startForResult(ParkPickerFragment.newInstance(), ParkPickerFragment.RESULT_CODE_PARK);
                break;
            case R.id.tv_amount_carport_fragment:
                break;
            default:
        }
    }

    @Override
    public void responseCarports(CarportBeam data) {
        tvAmount.setText(data.getData().getSurplusSpace() + "");
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        ParkPlaceListBean parkBean = (ParkPlaceListBean) data.getSerializable(Constants.KEY_PARK);
        if (parkBean != null) {
            tvPark.setText(parkBean.getName());
            params.parkPlaceFid = parkBean.getFid();
            mPresenter.requestCarports(params);
        }
    }
}
