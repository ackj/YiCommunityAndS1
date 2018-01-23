package cn.itsite.apayment.payment.temp;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/5/20 0020 10:56
 * 支付返回结果Bean。
 */

public class EventPay {
    public int code;
    public String description;
    public String extra;

    public EventPay(int code) {
        this.code = code;
    }

    public EventPay(int code, String description, String extra) {
        this.code = code;
        this.description = description;
        this.extra = extra;
    }
}
