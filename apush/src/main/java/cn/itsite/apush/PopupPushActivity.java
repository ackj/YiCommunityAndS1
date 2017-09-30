package cn.itsite.apush;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.sdk.android.push.AndroidPopupActivity;

import java.util.Map;

/**
 * Created by leguang on 2017/9/25 0025.
 * Email：langmanleguang@qq.com
 */

public class PopupPushActivity extends AndroidPopupActivity {
    static final String TAG = "PopupPushActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 实现通知打开回调方法，获取通知相关信息
     *
     * @param title   标题
     * @param summary 内容
     * @param extMap  额外参数
     */
    @Override
    protected void onSysNoticeOpened(String title, String summary, Map<String, String> extMap) {
        Log.d("onSysNoticeOpened", " title: " + title + ", content: " + summary + ", extMap: " + extMap);
    }
}
