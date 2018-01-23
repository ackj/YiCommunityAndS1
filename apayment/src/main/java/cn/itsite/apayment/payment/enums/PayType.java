package cn.itsite.apayment.payment.enums;

/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2018/1/12 0012 11:15
 */

public enum PayType {
    WechatPay(0),
    WechatH5xPay(1),
    ALiPay(2),
    UPPay(3);

    int payway;

    PayType(int way) {
        payway = way;
    }

    @Override
    public String toString() {
        return String.valueOf(payway);
    }
}
