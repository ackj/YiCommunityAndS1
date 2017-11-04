package com.aglhz.yicommunity.main.parking.view;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.ParkingChargeBean;
import com.aglhz.yicommunity.main.guide.GuideHelper;
import com.aglhz.yicommunity.main.parking.contract.TempParkContract;
import com.aglhz.yicommunity.main.parking.presenter.TempParkPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.akeyboard.KeyboardHelper;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块的容器Activity。
 */
public class ParkChargeFragment extends BaseFragment<TempParkContract.Presenter> implements TempParkContract.View {
    private static final String TAG = ParkChargeFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_park_parking_charge_fragment)
    TextView tvPark;
    @BindView(R.id.tv_plate_parking_charge_fragment)
    TextView tvPlate;
    @BindView(R.id.bt_search_parking_charge_fragment)
    Button btSearch;
    @BindView(R.id.keyboard)
    KeyboardView keyboard;
    private Unbinder unbinder;
    private KeyboardHelper keyboardHelper;
    private Params params = Params.getInstance();

    public static ParkChargeFragment newInstance(Bundle bundle) {
        ParkChargeFragment fragment = new ParkChargeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            params.parkPlaceFid = arguments.getString(Constants.PARAM_PARKPLACEFID);
            params.name = arguments.getString(Constants.PARAM_PARKNAME);
        }
    }

    @NonNull
    @Override
    protected TempParkContract.Presenter createPresenter() {
        return new TempParkPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parking_charge, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        showParkingCharge();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("临停缴费");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
        //显示引导页
        GuideHelper.showTempporaryCardPayGuide(_mActivity);
    }

    private void initData() {
        keyboardHelper = new KeyboardHelper(keyboard, tvPlate);
        tvPark.setText(params.name);
        tvPark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ALog.e("count--" + count);
                ALog.e("CharSequence--" + s);
                btSearch.setEnabled(count != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_plate_parking_charge_fragment, R.id.bt_search_parking_charge_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_plate_parking_charge_fragment:
                keyboardHelper.show();
                break;
            case R.id.bt_search_parking_charge_fragment:
                params.carNo = tvPlate.getText().toString();
                mPresenter.requestParkingCharge(params);
                break;
            default:
        }
    }


    @Override
    public void responseParkingCharge(ParkingChargeBean data) {
        ALog.e("data--" + data.getData().getCarNo());
        showParkingCharge();
    }

    private void showParkingCharge() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_parking_charge)
                .setConvertListener((holder, dialog) -> {
                    holder.setOnClickListener(R.id.iv_cancel_parking_charge_dialog, v -> {
                        dialog.dismiss();
                    }).setOnClickListener(R.id.tv_alipay_parking_charge_dialog, v -> {
                        params.payType = 1;
                        mPresenter.requestTempParkBill(params);
                        dialog.dismiss();
                    }).setOnClickListener(R.id.tv_weixin_parking_charge_dialog, v -> {
                        params.payType = 2;
                        mPresenter.requestTempParkBill(params);
                        dialog.dismiss();
                    }).setText(R.id.tv_carport_search, "2222")
                            .setText(R.id.tv_plate_parking_charge_dialog, "2222")
                            .setText(R.id.tv_in_time_parking_charge_dialog, "2222")
                            .setText(R.id.tv_pay_time_parking_charge_dialog, "2222")
                            .setText(R.id.tv_duration_parking_charge_dialog, "2222")
                            .setText(R.id.tv_charge_parking_charge_dialog, "2222");
                })
                .setMargin(20)
                .setDimAmount(0.3f)
                .setGravity(Gravity.CENTER)
                .show(getFragmentManager())
                .setCancelable(false);
    }

    @Override
    public void responseTempParkBill(BaseBean baseBean) {

    }
}
