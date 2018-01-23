package cn.itsite.apayment.payment.pay;

import cn.itsite.apayment.payment.pay.wechat.WeChatH5xPay;

/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2018/1/12 0012 15:18
 * @Description
 */
public class Pay {

    /******************以下为支付宝支付***********************/
    public static IPayable aliAppPay() {
        return null;
    }
    /******************以上为支付宝支付***********************/


    /******************以下为微信支付***********************/
    public static IPayable weChatAppPay() {
        return null;

    }

    public static IPayable weChatH5xPay() {
        return new WeChatH5xPay();
    }
    /******************以上为微信支付***********************/


    /******************以下为银联支付***********************/
    public static IPayable upiAppPay() {
        return null;

    }
    /******************以上为银联支付*************************/

}
