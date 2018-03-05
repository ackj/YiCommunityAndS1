package cn.itsite.abase;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.itsite.abase.BuildConfig;

import cn.itsite.abase.exception.AppExceptionHandler;
import cn.itsite.abase.log.ALog;

import me.yokeyword.fragmentation.Fragmentation;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BaseApp extends MultiDexApplication {
    private static final String TAG = BaseApp.class.getSimpleName();
    public static Context mContext;
    //    private RefWatcher mRefWatcher;
    public static String PUSH_TYPE = "";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initData();//根据是不是Debug版本来设置。
    }

    private void initData() {
        if (BuildConfig.DEBUG) {
            Fragmentation.builder()
                    .stackViewMode(Fragmentation.BUBBLE)
                    .install();
            //初始化内存泄露监听
//        mRefWatcher = LeakCanary.install(this);

            // 初始化卡顿监听
//        BlockCanary.install(this, new AppContext()).start();
            ALog.init(true, "ysq");
        } else {
            ALog.init(false, "ysq");//在release版中禁止打印log。
            Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));//在release版中处理全局异常。
        }
    }
}
