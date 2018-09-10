package com.lujiaozhuang.client.utils.db;


import android.app.Application;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.lujiaozhuang.client.BuildConfig;
import com.lujiaozhuang.client.R;


public class LiteOrmUtil {


    private static LiteOrm liteOrm;

    private LiteOrmUtil() {
    }

    public static void init(Application application) {
        if (liteOrm == null) {
            DataBaseConfig config = new DataBaseConfig(application, application.getString(R.string.app_name));
            config.debugged = true; // open the log
            config.dbVersion = BuildConfig.VERSION_CODE; // set database version
            config.onUpdateListener = null; // set database update listener
            liteOrm = LiteOrm.newSingleInstance(config);
        }
    }

    public static LiteOrm getLiteOrm() {
        return liteOrm;
    }
}
