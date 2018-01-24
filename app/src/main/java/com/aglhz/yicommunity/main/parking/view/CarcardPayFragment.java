package com.aglhz.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.CarCardListBean.DataBean.CardListBean;
import com.aglhz.yicommunity.entity.bean.MonthlyPayRulesBean;
import com.aglhz.yicommunity.entity.bean.ParkPayResultBean;
import com.aglhz.yicommunity.main.parking.contract.CarCardPayContract;
import com.aglhz.yicommunity.main.parking.presenter.CarCardPayPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.apayment.payment.PayParams;
import cn.itsite.apayment.payment.Payment;
import cn.itsite.apayment.payment.PaymentListener;
import cn.itsite.apayment.payment.network.NetworkClient;
import cn.itsite.apayment.payment.network.PayService;
import cn.itsite.apayment.payment.pay.IPayable;
import cn.itsite.apayment.payment.pay.Pay;

import static com.aglhz.yicommunity.common.Constants.CARD_TYPE_MONTHLY;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块的容器Activity。
 */
public class CarcardPayFragment extends BaseFragment<CarCardPayContract.Presenter> implements CarCardPayContract.View {
    public static final String TAG = CarcardPayFragment.class.getSimpleName();
    public static final int RESULT_CODE = 0x01;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_park_car_card_pay_fragment)
    TextView tvPark;
    @BindView(R.id.tv_plate_car_card_pay_fragment)
    TextView tvPlate;
    @BindView(R.id.cl_header_car_card_pay_fragment)
    ConstraintLayout clHeader;
    @BindView(R.id.tv_name_car_card_pay_fragment)
    TextView tvName;
    @BindView(R.id.tv_phone_car_card_pay_fragment)
    TextView tvPhone;
    @BindView(R.id.tv_month_car_card_pay_fragment)
    TextView tvMonth;
    @BindView(R.id.tv_indate_car_card_pay_fragment)
    TextView tvIndate;
    @BindView(R.id.tv_amount_car_card_pay_fragment)
    TextView tvAmount;
    @BindView(R.id.tv_alipay_car_card_pay_fragment)
    TextView tvAlipay;
    @BindView(R.id.tv_weixin_car_card_pay_fragment)
    TextView tvWeixin;
    @BindView(R.id.iv_delete_car_card_pay_fragment)
    ImageView ivDelete;
    @BindView(R.id.view_line_0)
    View viewLine0;
    @BindView(R.id.view_line_1)
    View viewLine1;
    @BindView(R.id.view_line_2)
    View viewLine2;
    @BindView(R.id.view_line_3)
    View viewLine3;
    @BindView(R.id.view_line_4)
    View viewLine4;
    @BindView(R.id.view_line_5)
    View viewLine5;
    @BindView(R.id.tv_month)
    TextView tvMonth0;
    @BindView(R.id.tv_indate)
    TextView tvIndate0;
    @BindView(R.id.tv_amount)
    TextView tvAmount0;
    @BindView(R.id.cl_contain_car_card_pay_fragment)
    ConstraintLayout clContain;
    private Unbinder unbinder;
    private Params params = Params.getInstance();
    private CardListBean carCard;
    private BaseDialogFragment selector;

    public static CarcardPayFragment newInstance(Bundle bundle) {
        CarcardPayFragment fragment = new CarcardPayFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected CarCardPayContract.Presenter createPresenter() {
        return new CarCardPayPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            carCard = ((CardListBean) arguments.getSerializable(Constants.KEY_ITEM));
            params.parkCardFid = carCard.getFid();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_card_pay, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initDate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initDate() {
        if (carCard.getApproveState() == 2) {
            new AlertDialog.Builder(_mActivity).setTitle("温馨提示：")
                    .setMessage("您的车卡审核未通过\n" + carCard.getApproveDes())
                    .setPositiveButton("退出", (dialog, which) -> {
                        pop();
                    }).show();
        }

        if (CARD_TYPE_MONTHLY.equals(carCard.getCardType())) {
            mPresenter.requestMonthlyPayRules(params);
            toolbarTitle.setText("月卡充值");
            clHeader.setBackgroundResource(R.drawable.bg_apply_header_0);
            if (carCard.getApproveState() == 1
                    && carCard.getNeedToPayType() == 2
                    && carCard.getSurplusDays() <= 0) {
                //-------------- 已过期 -------------
                new AlertDialog.Builder(_mActivity)
                        .setTitle("温馨提示：")
                        .setMessage("您的月卡已经过期，可充值继续使用！")
                        .setPositiveButton("充值", null)
                        .show();
            }
        } else {
            toolbarTitle.setText("车位卡");
            clHeader.setBackgroundResource(R.drawable.bg_apply_header_1);
            tvIndate.setText("永久有效");
            clContain.setPadding(clContain.getPaddingLeft(), clContain.getPaddingTop(),
                    clContain.getPaddingRight(), clContain.getPaddingBottom() + 30);
            tvMonth.setVisibility(View.GONE);
            tvAmount.setVisibility(View.GONE);
            tvMonth0.setVisibility(View.GONE);
            tvAmount0.setVisibility(View.GONE);
            tvWeixin.setVisibility(View.GONE);
            tvAlipay.setVisibility(View.GONE);
            viewLine1.setVisibility(View.GONE);
            viewLine2.setVisibility(View.GONE);
            viewLine3.setVisibility(View.GONE);
            viewLine4.setVisibility(View.GONE);
            viewLine5.setVisibility(View.GONE);
        }
        tvPhone.setText(carCard.getPhoneNo());
        tvName.setText(carCard.getCustomerName());
        tvPark.setText(carCard.getParkPlace().getName());
        tvPlate.setText(carCard.getCarNo());
    }

    @OnClick({R.id.iv_delete_car_card_pay_fragment,
            R.id.tv_month_car_card_pay_fragment,
            R.id.tv_alipay_car_card_pay_fragment,
            R.id.tv_weixin_car_card_pay_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete_car_card_pay_fragment:
                new AlertDialog.Builder(_mActivity).setTitle("温馨提示")
                        .setMessage("是否删除该停车卡？")
                        .setNegativeButton("否", null)
                        .setPositiveButton("是", (dialog, which) -> {
                            mPresenter.requestDeleteCarCard(params);
                        })
                        .show();
                break;
            case R.id.tv_month_car_card_pay_fragment:
                if (selector != null) {
                    selector.show(getChildFragmentManager());
                }
                break;
            case R.id.tv_alipay_car_card_pay_fragment:
                params.payMethod = Payment.PAYTYPE_ALI_APP;
                pay(Pay.aliAppPay());

                break;
            case R.id.tv_weixin_car_card_pay_fragment:
                params.payMethod = Payment.PAYTYPE_WECHAT_H5X;
                pay(Pay.weChatH5xPay());

                break;
            default:
        }
    }

    @Override
    public void responseDeleteCarCard(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_ITEM, carCard);
        setFragmentResult(RESULT_CODE, bundle);
        pop();
    }

    @Override
    public void responseMonthlyPayRules(MonthlyPayRulesBean bean) {
        List<MonthlyPayRulesBean.DataBean.MonthCardRuleListBean> rules = bean.getData().getMonthCardRuleList();
        selector = new SelectorDialogFragment()
                .setTitle("请选择充值时长")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(rules)
                .setOnItemConvertListener((holder, position, dialog) -> {
                    MonthlyPayRulesBean.DataBean.MonthCardRuleListBean rule = rules.get(position);
                    holder.setText(R.id.tv_item_rv_simple_selector, rule.getName());
                })
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    MonthlyPayRulesBean.DataBean.MonthCardRuleListBean rule = rules.get(position);
                    tvMonth.setText(rule.getName());
                    tvIndate.setText(rule.getStartDate() + "　至　" + rule.getEndDate());
                    tvAmount.setText(rule.getMoney() + "");
                    params.monthName = rule.getName();
                    params.monthCount = rule.getMonthCount();
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM);

        if (rules == null || rules.isEmpty()) {
            return;
        }
        //默认设置第一个。
        MonthlyPayRulesBean.DataBean.MonthCardRuleListBean firstRule = rules.get(0);
        tvMonth.setText(firstRule.getName());
        tvIndate.setText(firstRule.getStartDate() + "　至　" + firstRule.getEndDate());
        tvAmount.setText(firstRule.getMoney() + "");
        params.monthName = firstRule.getName();
        params.monthCount = firstRule.getMonthCount();
    }

//    @Override
//    public void responseCarCardBill(JSONObject jsonObject) {
//        switch (params.payMethod) {
//            case Constants.TYPE_ALIPAY:
//                //支付宝
//                new ALiPayHelper().pay(_mActivity, jsonObject.optString("body"));
//                break;
//            case Constants.TYPE_WXPAY:
//                //微信
//                WxPayHelper.pay(jsonObject.toString());
//                break;
//            default:
//        }
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(EventPay event) {
//        if (event.code == 0) {
//            Bundle bundle = new Bundle();
//            ParkPayResultBean result = new ParkPayResultBean();
//            result.order = event.extra;
//            result.park = carCard.getParkPlace().getName();
//            result.plate = carCard.getCarNo();
//            result.time = carCard.getCreateTime();
//            result.amount = tvAmount.getText().toString();
//            bundle.putSerializable(Constants.KEY_PAR_KPAY_RESULT, result);
//            start(ParkPayResultFragment.newInstance(bundle));
//        } else {
//            DialogHelper.warningSnackbar(getView(), "很遗憾，支付失败,请重试");
//        }
//    }

    private void pay(IPayable iPayable) {
        //拼参数。
        Map<String, String> params = new HashMap<>();
        params.put("token", this.params.token);
        params.put("parkCardFid", this.params.parkCardFid);
        params.put("monthName", this.params.monthName);
        params.put("monthCount", this.params.monthCount + "");
        params.put("payMethod", this.params.payMethod + "");

        final PayParams[] payParams = new PayParams[1];
        //构建支付入口对象。
        Payment.builder()
                .setParams(params)
                .setHttpType(Payment.HTTP_POST)
                .setUrl(PayService.requestCarCardOrder)
                .setActivity(_mActivity)
                .setClient(NetworkClient.okhttp())
                .setPay(iPayable)
                .setOnRequestListener(new PaymentListener.OnRequestListener() {
                    @Override
                    public void onStart() {
                        ALog.e("1.请求 开始-------->");
                        showLoading("请求订单中");
                    }

                    @Override
                    public void onSuccess(String result) {
                        ALog.e("1.请求 成功-------->" + result);
                        showLoading("订单请求成功，等待解析");
                    }

                    @Override
                    public void onError(int errorCode) {
                        ALog.e("1.请求 失败-------->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "订单请求失败");

                    }
                })
                .setOnParseListener(new PaymentListener.OnParseListener() {
                    @Override
                    public void onStart(String result) {
                        ALog.e("2.解析 开始-------->" + result);
                        showLoading("正在解析");
                    }

                    @Override
                    public void onSuccess(PayParams params) {
                        payParams[0] = params;
                        ALog.e("2.解析 成功-------->");
                        showLoading("解析成功");

                    }

                    @Override
                    public void onError(int errorCode) {
                        ALog.e("2.解析 失败------->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "解析异常");
                    }
                })
                .setOnPayListener(new PaymentListener.OnPayListener() {
                    @Override
                    public void onStart(@Payment.PayType int payType) {
                        ALog.e("3.支付 开始-------->" + payType);
                        showLoading("正在支付");
                    }

                    @Override
                    public void onSuccess(@Payment.PayType int payType) {
                        ALog.e("3.支付 成功-------->" + payType);
                        dismissLoading();
//                        DialogHelper.successSnackbar(getView(), "支付成功");

                        Bundle bundle = new Bundle();
                        ParkPayResultBean result = new ParkPayResultBean();
                        result.order = payParams[0].getOutTradeNo();
                        result.park = carCard.getParkPlace().getName();
                        result.plate = carCard.getCarNo();
                        result.time = carCard.getCreateTime();
                        result.amount = tvAmount.getText().toString();
                        bundle.putSerializable(Constants.KEY_PAR_KPAY_RESULT, result);
                        start(ParkPayResultFragment.newInstance(bundle));
                    }

                    @Override
                    public void onFailure(@Payment.PayType int payType, int errorCode) {
                        ALog.e("3.支付 失败-------->" + payType + "----------errorCode-->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "支付失败，请重试");
                    }
                })
                .setOnVerifyListener(new PaymentListener.OnVerifyListener() {

                    @Override
                    public void onStart() {
                        ALog.e("4.检验 开始--------");
                        showLoading("正在确认");
                    }

                    @Override
                    public void onSuccess() {
                        ALog.e("4.检验 成功--------");
                        dismissLoading();
                    }

                    @Override
                    public void onFailure(int errorCode) {
                        ALog.e("4.检验 失败--------" + "errorCode-->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "确认失败，请稍后再查看");
                    }
                })
                .start();
    }
}
