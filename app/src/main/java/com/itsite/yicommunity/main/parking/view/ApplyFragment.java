package com.itsite.yicommunity.main.parking.view;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import com.itsite.s1.App;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.common.UserHelper;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.entity.bean.ParkSelectBean.DataBean.ParkPlaceListBean;
import com.itsite.yicommunity.main.parking.contract.ApplyContract;
import com.itsite.yicommunity.main.parking.presenter.ApplyPresenter;
import com.itsite.yicommunity.main.picker.view.ParkPickerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itsite.akeyboard.KeyboardHelper;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块的容器Activity。
 */
public class ApplyFragment extends BaseFragment<ApplyContract.Presenter> implements ApplyContract.View {
    public static final String TAG = ApplyFragment.class.getSimpleName();
    @BindView(R.id.tv_plate_apply_fragment)
    TextView tvPlate;
    @BindView(R.id.tv_park_apply_fragment)
    TextView tvPark;
    @BindView(R.id.tv_name_apply_fragment)
    EditText tvName;
    @BindView(R.id.tv_phone_apply_fragment)
    EditText tvPhone;
    @BindView(R.id.bt_apply_fragment)
    Button btApply;
    @BindView(R.id.view_indicator_0_apply_fragment)
    View indicator0;
    @BindView(R.id.view_indicator_1_apply_fragment)
    View indicator1;
    @BindView(R.id.keyboard)
    KeyboardView keyboard;
    @BindView(R.id.cl_header_apply_fragment)
    ConstraintLayout clHeader;
    @BindView(R.id.tv_title_apply_fragment)
    TextView tvTitle;
    @BindView(R.id.tv_des_apply_fragment)
    TextView tvDes;
    @BindView(R.id.cv_content_apply_fragment)
    CardView cvContent;
    private Unbinder unbinder;
    private Params params = Params.getInstance();
    private KeyboardHelper keyboardHelper;

    public static ApplyFragment newInstance(Bundle bundle) {
        ApplyFragment fragment = new ApplyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected ApplyContract.Presenter createPresenter() {
        return new ApplyPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            params.type = arguments.getInt(Constants.TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
    }

    private void initDate() {
        if (params.type == 1) {
            clHeader.setBackgroundResource(R.drawable.bg_apply_header_1);
            indicator0.setBackgroundColor(ContextCompat.getColor(App.mContext, R.color.default_gray));
            indicator1.setBackgroundColor(ContextCompat.getColor(App.mContext, R.color.base_color));
            tvTitle.setText("办理车位卡");
            tvDes.setText("免费办理小区业主自有车位卡");
        }
        keyboardHelper = new KeyboardHelper(keyboard, tvPlate);
        tvPhone.setText(UserHelper.account);
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
            params.parkPlaceFid = parkBean.getFid();
            ALog.e("parkBean.getFid()--" + parkBean.getFid());

        }
    }

    @Override
    public void responseApplyCard(BaseBean data) {
        dismissLoading();
        DialogHelper.successSnackbar(getView(), data.getOther().getMessage());
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_DES, "申请已提交成功，申请结果将发送至消息中心，请稍后！");
        bundle.putString(Constants.KEY_TITLE, "提交成功");
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof ApplyCardFragment) {
            ((ApplyCardFragment) getParentFragment())
                    .startWithPop(ApplyCarCardResultFragment.newInstance(bundle));
        }
    }

    @OnClick({R.id.cv_content_apply_fragment,
            R.id.tv_plate_apply_fragment,
            R.id.tv_park_apply_fragment,
            R.id.bt_apply_fragment})
    public void onViewClicked(View view) {
        keyboardHelper.hide();
        switch (view.getId()) {
            case R.id.cv_content_apply_fragment:
                keyboardHelper.hide();
                break;
            case R.id.tv_plate_apply_fragment:
                keyboardHelper.show();
                break;
            case R.id.tv_park_apply_fragment:
                Fragment fragment = getParentFragment();
                if (fragment != null && fragment instanceof ApplyCardFragment) {
                    ((ApplyCardFragment) getParentFragment())
                            .startForResult(ParkPickerFragment.newInstance(), params.type);
                }
                break;
            case R.id.bt_apply_fragment:
                if (TextUtils.isEmpty(tvPlate.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "车牌号不能为空！");
                }

                if (TextUtils.isEmpty(params.parkPlaceFid)) {
                    DialogHelper.warningSnackbar(getView(), "停车场不能为空！");
                }

                if (TextUtils.isEmpty(tvName.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "姓名不能为空！");
                }

                if (TextUtils.isEmpty(tvPhone.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "姓名不能为空！");
                }
                params.carNo = tvPlate.getText().toString();
                params.name = tvName.getText().toString();
                params.phoneNo = tvPhone.getText().toString();
                mPresenter.requestApplyCard(params);
                break;
            default:
        }
    }

    public void setPlate(ParkPlaceListBean park) {
        tvPark.setText(park.getName());
        params.parkPlaceFid = park.getFid();
    }
}
