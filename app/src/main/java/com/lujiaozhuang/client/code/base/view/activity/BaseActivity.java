package com.lujiaozhuang.client.code.base.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lujiaozhuang.client.R;
import com.lujiaozhuang.client.code.base.view.iview.IActivityAndFragment;
import com.lujiaozhuang.client.mode.httpresponse.BaseHttpResponse;
import com.lujiaozhuang.client.mode.javabean.UserInfo;
import com.lujiaozhuang.client.utils.other.StringUtils;
import com.lujiaozhuang.client.utils.ui.ActivityUtils;
import com.lujiaozhuang.client.utils.ui.SnackbarUtil;
import com.lujiaozhuang.client.utils.ui.SystemBarTintManager;
import com.lujiaozhuang.client.utils.ui.ToastUtils;
import com.lujiaozhuang.client.utils.ui.UiUtils;
import com.lujiaozhuang.client.widget.appbarlayout.SupperToolBar;
import com.lujiaozhuang.client.widget.dialog.LoadDialog;
import com.lujiaozhuang.client.widget.empty_layout.LoadingAndRetryManager;
import com.lujiaozhuang.client.widget.empty_layout.MyOnLoadingAndRetryListener;
import com.lujiaozhuang.client.widget.empty_layout.OnRetryClickListion;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class  BaseActivity extends AppCompatActivity implements IActivityAndFragment {
    /**
     * **
     * 创建时间: 2016/9/22 11:14
     * <p>
     * 方法功能：请求CODE
     */
    public int REQUEST_CODE = Math.abs(this.getClass().getSimpleName().hashCode() % 60000);
    /**
     * **
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：订阅的集合，防止内存泄漏
     */
    private CompositeSubscription mCompositeSubscription;
    /**
     * **
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：布局切换
     */
    protected LoadingAndRetryManager loadingAndRetryManager = null;

    public SystemBarTintManager systemBarTintManager;
    /**
     * **
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：用于显示提示
     */

    private LoadDialog loadDialog;
    public Activity mActivity;
    @Nullable
    protected SupperToolBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        setTranslucentStatus(getStatusBarEnable(), getStatusBarResource());
        //初始化布局文件
        setContentView(getContentView());
        //初始化意图
        parseIntent(getIntent());

        //初始化view
        initView();

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取布局id
     */
    public abstract int getContentView();

    /**
     * **
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：初始化view
     */
    public abstract void initView();

    /**
     * **
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：解析意图
     */
    public abstract void parseIntent(Intent intent);


    /**
     * **
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取状态栏颜色
     */


    public int getStatusBarResource() {
        return R.color.main_color;
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取状态栏高度
     */


    public int getStatusBarPaddingTop() {
        return UiUtils.getStatusHeight() - 2;
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取状态栏是否开启
     */


    public boolean getStatusBarEnable() {
        return true;
    }


    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：显示不同的界面布局 监听器
     */
    public OnRetryClickListion getOnRetryClickListion() {
        if (this instanceof OnRetryClickListion) {
            return (OnRetryClickListion) this;
        } else {
            return null;
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取需要转化为loadingAndRetryManager的控件
     */
    public View getSwitchRoot() {
        return findViewById(R.id.switchRoot);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取页面切换的布局管理器
     */
    public LoadingAndRetryManager getLoadingAndRetryManager() {
        return loadingAndRetryManager;
    }



    private void setTranslucentStatus(boolean on, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏透明 需要在创建SystemBarTintManager 之前调用。
            Window win = getWindow();
            WindowManager
                    .LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
            systemBarTintManager = new SystemBarTintManager(this);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setNavigationBarTintEnabled(true);
            systemBarTintManager.setTintResource(color);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 如果亮色，设置状态栏文字为黑色
            if (isLightColor(color)) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
        //状态栏颜色字体(白底黑字)修改 MIUI6+
        setMiuiStatusBarDarkMode(this, true);
        //状态栏颜色字体(白底黑字)修改 Flyme4+
        setMeizuStatusBarDarkIcon(this, true);
    }
    /**
     * 状态栏颜色字体(白底黑字)修改 MIUI6+
     *
     * @param activity
     * @param darkmode
     * @return
     */
    public boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 状态栏颜色字体(白底黑字)修改 Flyme4+
     *
     * @param activity
     * @param dark
     * @return
     */
    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) <= 0.5;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        UiUtils.applyFont(UiUtils.getRootView(this));
        setRootViewPadding(getStatusBarPaddingTop());
        toolbar = findViewById(R.id.toolbar);
        loadingAndRetryManager = LoadingAndRetryManager.getLoadingAndRetryManager(getSwitchRoot(), new MyOnLoadingAndRetryListener(this, getOnRetryClickListion()));
        if (toolbar != null) {

        }

    }

    public void setRootViewPadding(int top) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && getStatusBarEnable()) {
            UiUtils.getRootView(this).setPadding(0, top, 0, 0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 销毁网络访问的订阅
         */
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        /**
         * 销毁网络访问的订阅
         */
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:09
     * <p>
     * 方法功能：是否登录
     *
     * @param isToLogin : 是否跳转到登录界面
     */

    @Override
    public boolean isLogin(boolean isToLogin) {
        if (!UserInfo.isSLogin()) {
            if (isToLogin) {
                //跳转登录
//                LoginActivity.startUI(this);
            }
            return false;
        } else {
            return true;
        }
    }


    /**
     * **
     * 创建时间: 2016/9/22 11:09
     * <p>
     * 方法功能：RXJava 的订阅集合  用于销毁
     */

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：添加一个订阅
     */
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }


    /**
     * 方法功能：显示对话框，用于网络请求
     *
     * @param msg
     * @param isCancelable
     */
    public void showDialog(String msg, boolean isCancelable) {
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
        // 判断是否加载对话框
        if (!isFinishing() && ActivityUtils.isForeground(this)) {
            if (loadDialog == null) {
                loadDialog = new LoadDialog(this);
            }
            loadDialog.setContent(StringUtils.dataFilter(msg, getResources().getString(R.string.loadding)));
            loadDialog.setCancelable(isCancelable);
            try {
                loadDialog.show();
            } catch (Exception e) {

            }

        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示对话框，用于网络请求
     */
    public void showDialog(String msg) {
        showDialog(msg, false);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示对话框，用于网络请求
     */
    public void showDialog() {
        showDialog(null);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：销毁对话框
     */
    public void dismissDialog() {
        if (loadDialog != null) {
            loadDialog.dismiss();
            loadDialog = null;
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能： Snackbar 显示错误信息
     */
    public void httpError(String errString) {
        SnackbarUtil.showLong(this, errString, SnackbarUtil.Error).show();
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能： Snackbar 显示信息
     */
    public void showMessage(BaseHttpResponse result) {
        if (result != null) {
            SnackbarUtil.showLong(this, result.getMsg(), SnackbarUtil.Error).show();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示错误信息
     */

    public void showErrorMessage(String result) {
        if (result != null) {
            SnackbarUtil.showLong(this, result, SnackbarUtil.Error).show();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningMessage(String result) {
        if (result != null) {
            SnackbarUtil.showLong(this, result, SnackbarUtil.Warning).show();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示信息 并自动班的是否是toast
     */
    public void showMessageFinish(BaseHttpResponse result) {
        if (result.isSucceed()) {
            ToastUtils.showShort(this, result.getMsg());
        } else {
            SnackbarUtil.showLong(this, result.getMsg(), SnackbarUtil.Error).show();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示错误信息
     */
    public void showErrorSnackbar(String result) {
        showSnackbar(result, SnackbarUtil.Error, false);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示错误信息
     */
    public void showErrorSnackbar(String result, boolean isFinish) {
        showSnackbar(result, SnackbarUtil.Error, isFinish);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningSnackbar(String result) {
        showSnackbar(result, SnackbarUtil.Warning, false);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningSnackbar(String result, boolean isFinish) {
        showSnackbar(result, SnackbarUtil.Warning, isFinish);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示提示信息
     */
    public void showInforSnackbar(String result, boolean isFinish) {
        showSnackbar(result, SnackbarUtil.Info, isFinish);
    }


    /**
     * 显示正确提示信息
     *
     * @param result
     */
    public void showInforSnackbar(String result) {
        showInforSnackbar(result, false);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示信息
     */
    public void showSnackbar(String result, int type, boolean isFinish) {
        showSnackbar(UiUtils.getDecorView(this), result, type, isFinish, null);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示信息
     */
    public void showSnackbar(View view, String result, int type, boolean isFinish, final Snackbar.Callback callback) {
        SnackbarUtil.showSnackbar(this, view, result, type, isFinish, callback);
    }
}
