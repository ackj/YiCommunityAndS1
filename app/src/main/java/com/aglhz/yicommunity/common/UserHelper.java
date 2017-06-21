package com.aglhz.yicommunity.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.aglhz.abase.log.ALog;
import com.aglhz.yicommunity.BaseApplication;
import com.aglhz.yicommunity.entity.bean.UserBean;
import com.google.gson.Gson;

/**
 * Author：leguang on 2016/5/4 0009 15:49
 * Email：langmanleguang@qq.com
 */

public class UserHelper {
    private static final String DEFAULT = "";
    private static final String TOKEN = "token";
    private static final String COMMUNITY_NAME = "community_name";
    private static final String COMMUNITY_CODE = "community_code";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private static final String USER_INFO = "user_info";
    private static final String DIR = "dir";
    private static final String PROVINCE = "province";
    private static final String CITY = "city";
    private static final String COUNTY = "county";
    private static final String ADDRESS = "address";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String LOCATION_ADDRESS = "location_address";
    private static final String SIP = "sip";
    private static final String ISREMEMBER = "isRemember";


    public static String account = "";//账户、密码、是否记住密码，这三个值是记录在默认SP中的。
    public static String password = "";//同时账户和密码在各自的SP中也有一份。

    public static String FILE_NAME = "default";
    public static String token = "";
    public static String communityName = "";
    public static String communityCode = "";
    public static String province = "";
    public static String city = "";
    public static String county = "";
    public static String address = "";
    public static String dir = "";//默认设置的一键开门的设备路径。
    public static String latitude = "";//纬度
    public static String longitude = "";//经度
    public static String sip = "";
    public static String WXAPPID = "";
    public static String locationAddress = "";//定位的位置地址

    public static UserBean.DataBean.MemberInfoBean userInfo;

    public static boolean setLatitude(String latitude) {
        UserHelper.latitude = latitude;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(LATITUDE, latitude);
        return editor.commit();
    }

    public static boolean setLongitude(String longitude) {
        UserHelper.longitude = longitude;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(LONGITUDE, longitude);
        return editor.commit();
    }

    public static boolean setLocationAddress(String address) {
        UserHelper.locationAddress = address;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(LOCATION_ADDRESS, address);
        return editor.commit();
    }

    //判断是否登录。
    public static boolean isLogined() {
        return !TextUtils.isEmpty(token);
    }

    //判断是否有选择小区。
    public static boolean hasCommunity() {
        return !TextUtils.isEmpty(communityName);
    }

    //退出登录或者token失效清除信息。
    public static void clear() {
        token = "";
        communityName = "";
        communityCode = "";
        account = "";
        password = "";
        dir = "";
        city = "";
        userInfo = null;
        latitude = "";
        longitude = "";
        sip = "";
        province = "";
        city = "";
        county = "";
        address = "";
    }

    //更新Token。
    public static boolean setToken(String token) {
        UserHelper.token = token;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(TOKEN, token);
        return editor.commit();
    }

    //更新社区名称和社区代码。
    public static boolean setCommunity(String communityName, String communityCode) {
        UserHelper.communityName = communityName;
        UserHelper.communityCode = communityCode;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(COMMUNITY_NAME, communityName);
        editor.putString(COMMUNITY_CODE, communityCode);
        return editor.commit();
    }

    //更新用户信息。
    public static boolean setUserInfo(UserBean.DataBean.MemberInfoBean memberInfo) {
        setToken(memberInfo.getToken());
        UserHelper.userInfo = memberInfo;
        SharedPreferences.Editor editor = getEditor();
        String info = new Gson().toJson(memberInfo);
        editor.putString(USER_INFO, info);
        return editor.commit();
    }

    //更新sip账号。
    public static boolean setSip(String sip) {
        UserHelper.sip = sip;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(SIP, sip);
        return editor.commit();
    }

    //获取用户信息。
    public static UserBean.DataBean.MemberInfoBean getUserInfo() {
        if (userInfo != null) {
            return userInfo;
        }
        String userInfo = getSp().getString(USER_INFO, "");
        if (TextUtils.isEmpty(userInfo)) {
            return null;
        }
        UserHelper.userInfo = new Gson().fromJson(userInfo, UserBean.DataBean.MemberInfoBean.class);
        return UserHelper.userInfo;
    }

    //更新账号密码，同时更改了SP文件名，作为用户数据的初始化入口。
    public static void setAccount(String account, String password) {
        ALog.e("account-->" + account);
        ALog.e("password-->" + password);

        //存到默认SP文件中，用于程序入口处获取默认账户。
        getDefaultEditor()
                .putString(ACCOUNT, account)
                .putString(PASSWORD, password)
                .commit();

        FILE_NAME = account;//此处更改SP文件名。
        getEditor()
                .putString(ACCOUNT, account)
                .putString(PASSWORD, password);
        initData();
    }

    public static String getAccount() {
        return getDefaultSp().getString(ACCOUNT, "");
    }

    public static String getPassword() {
        return getDefaultSp().getString(PASSWORD, "");
    }

    //用户数据的初始化入口。
    public static void init() {
        FILE_NAME = getDefaultSp().getString(ACCOUNT, "");
        initData();
    }

    private static void initData() {
        SharedPreferences sp = getSp();
        token = sp.getString(TOKEN, "");
        account = sp.getString(ACCOUNT, "");
        password = sp.getString(PASSWORD, "");
        communityName = sp.getString(COMMUNITY_NAME, "");
        communityCode = sp.getString(COMMUNITY_CODE, "");
        dir = sp.getString(DIR, "");
        city = sp.getString(CITY, "");
        latitude = sp.getString(LATITUDE, "");
        longitude = sp.getString(LONGITUDE, "");
        sip = sp.getString(SIP, "");
        province = sp.getString(PROVINCE, "");
        city = sp.getString(CITY, "");
        county = sp.getString(COUNTY, "");
        address = sp.getString(ADDRESS, "");
        getUserInfo();
    }

    //更新是否记住密码。
    public static boolean setRemember(boolean isRemember) {
        return getDefaultEditor().putBoolean(ISREMEMBER, isRemember).commit();
    }

    //更新门禁机的路径。
    public static boolean setDir(String dir) {
        UserHelper.dir = dir;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(DIR, dir);
        return editor.commit();
    }

    //更新省份名称。
    public static boolean setProvince(String province) {
        UserHelper.province = province;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(PROVINCE, province);
        return editor.commit();
    }

    //更新城市名称。
    public static boolean setCity(String city) {
        UserHelper.city = city;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(CITY, city);
        return editor.commit();
    }

    //更新区名。
    public static boolean setCounty(String county) {
        UserHelper.county = county;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(COUNTY, county);
        return editor.commit();
    }

    //更新完整地址。
    public static boolean setAddress(String address) {
        UserHelper.address = address;
        SharedPreferences.Editor editor = getEditor();
        editor.putString(ADDRESS, address);
        return editor.commit();
    }

    //更新位置信息。
    public static boolean setPosition(String province, String city, String county, String address) {
        UserHelper.province = province;
        UserHelper.city = city;
        UserHelper.county = county;
        UserHelper.address = address;

        SharedPreferences.Editor editor = getEditor();
        editor.putString(PROVINCE, province);
        editor.putString(CITY, city);
        editor.putString(COUNTY, county);
        editor.putString(ADDRESS, address);

        return editor.commit();
    }

    public static boolean isRemember() {
        return getDefaultSp().getBoolean(ISREMEMBER, false);
    }

    private static SharedPreferences getSp() {
        return BaseApplication.mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        return getSp().edit();
    }

    private static SharedPreferences getDefaultSp() {
        return BaseApplication.mContext.getSharedPreferences(DEFAULT, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getDefaultEditor() {
        return getDefaultSp().edit();
    }

    public static String string() {
        return "UserHelper{" +
                "token=" + token +
                ", sip='" + sip + '\'' +
                ", dir='" + communityName + '\'' +
                ", dir='" + account + '\'' +
                ", dir='" + address + '\'' +
                ", dir='" + dir + '\'' +
                '}';
    }
}
