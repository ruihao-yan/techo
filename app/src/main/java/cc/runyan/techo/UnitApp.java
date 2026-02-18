package cc.runyan.techo;

import android.app.Application;

import cc.runyan.techo.db.DBManager;

public class UnitApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库
        DBManager.initDb(getApplicationContext());
    }
}
