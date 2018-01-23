package cn.itsite.apayment.payment;


/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2018/1/12 0012 11:15
 * Description: 支付相关参数.
 */

public class PayParams {
    private String appID;
    private String url;
    private String token;
    private String billFids;
    private String jumpUrl;
    private String quitUrl;
    private String payUrl;
    private String code;
    private String partnerId;
    private String prePayId;
    private String packageValue;
    private String nonceStr;
    private String timeStamp;
    private String sign;
    private String outTradeNo;
    private String orderInfo;
    private String webUrl;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBillFids() {
        return billFids;
    }

    public void setBillFids(String billFids) {
        this.billFids = billFids;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getQuitUrl() {
        return quitUrl;
    }

    public void setQuitUrl(String quitUrl) {
        this.quitUrl = quitUrl;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public static class Builder {
        String appID;
        String url;
        String token;
        String billFids;
        String jumpUrl;
        String quitUrl;
        String payUrl;
        String code;
        String partnerId;
        String prePayId;
        String packageValue;
        String nonceStr;
        String timeStamp;
        String sign;
        String outTradeNo;
        String orderInfo;
        String webUrl;

        public Builder appID(String appid) {
            this.appID = appid;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder billFids(String billFids) {
            this.billFids = billFids;
            return this;
        }

        public Builder jumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
            return this;
        }

        public Builder quitUrl(String quitUrl) {
            this.quitUrl = quitUrl;
            return this;
        }

        public Builder payUrl(String payUrl) {
            this.payUrl = payUrl;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder partnerId(String partnerId) {
            this.partnerId = partnerId;
            return this;
        }

        public Builder prePayId(String prePayId) {
            this.prePayId = prePayId;
            return this;
        }

        public Builder packageValue(String packageValue) {
            this.packageValue = packageValue;
            return this;
        }

        public Builder nonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public Builder timeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder sign(String sign) {
            this.sign = sign;
            return this;
        }

        public Builder outTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
            return this;
        }

        public Builder orderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
            return this;
        }

        public Builder webUrl(String webUrl) {
            this.webUrl = webUrl;
            return this;
        }

        public PayParams build() {
            PayParams params = new PayParams();
            params.setAppID(appID);
            params.setUrl(url);
            params.setToken(token);
            params.setBillFids(billFids);
            params.setJumpUrl(jumpUrl);
            params.setQuitUrl(quitUrl);
            params.setPayUrl(payUrl);
            params.setCode(code);
            params.setPartnerId(partnerId);
            params.setPrePayId(prePayId);
            params.setPackageValue(packageValue);
            params.setNonceStr(nonceStr);
            params.setTimeStamp(timeStamp);
            params.setSign(sign);
            params.setOutTradeNo(outTradeNo);
            params.setOrderInfo(orderInfo);
            params.setWebUrl(webUrl);
            return params;
        }
    }
}
