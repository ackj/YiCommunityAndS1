package cn.itsite.apayment.payment;

import cn.itsite.apayment.payment.enums.PayType;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2018/1/12 0012 9:00
 */
public interface PaymentListener {
    interface OnRequestListener {
        void onStart();

        void onSuccess(String result);

        void onError(int errorCode);
    }

    interface OnParseListener {
        void onStart(String result);

        void onSuccess(PayParams params);

        void onError(int errorCode);
    }

    interface OnPayListener {
        void onStart(PayType payType);

        void onSuccess(PayType payType);

        void onFailure(PayType payType, int errorCode);
    }

    interface OnVerifyListener {
        void onStart();

        void onSuccess();

        void onFailure(int errorCode);
    }
}
