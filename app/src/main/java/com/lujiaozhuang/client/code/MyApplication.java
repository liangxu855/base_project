package com.lujiaozhuang.client.code;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;

import com.lujiaozhuang.client.R;
import com.lujiaozhuang.client.utils.db.LiteOrmUtil;
import com.lujiaozhuang.client.widget.empty_layout.LoadingAndRetryManager;


public class MyApplication extends Application {
    public static Application myApp;
    public NetWorkType netWorkType = NetWorkType.NULL;
    public static String appCachePath;
    public static String appFilePath;
    public static String appSDCachePath;
    public static String appSDFilePath;

    private static Context context;

    public enum NetWorkType {
        WIFI, ETHERNET, MOBILE, NetWorkType, NULL
    }

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context=getApplicationContext();

        LiteOrmUtil.init(this);
        myApp = this;
//        GlideUtils.init(myApp);
//        new NetWorkBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //typeface = Typeface.createFromAsset(getAssets(), "fzlt.ttf");
        appCachePath = getCacheDir().getPath();
        appFilePath = getFilesDir().getPath();
        appSDCachePath = Environment.getExternalStorageDirectory().getPath() + "/" + getResources().getString(R.string.app_name_letter) + "/cache";
        appSDFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + getResources().getString(R.string.app_name_letter) + "/file";

        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_load_empty;
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_load_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_load_loading;

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);



    }

    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            ActivityManager.getInstance().pushActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityManager.getInstance().exitActivity(activity);
        }
    };

}
