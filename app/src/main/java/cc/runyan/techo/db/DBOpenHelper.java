package cc.runyan.techo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import cc.runyan.techo.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    // 创建techo数据库
    public DBOpenHelper(@Nullable Context context) {
        // name 是数据库的名称
        super(context, "techo", null, 1);
    }

    // 只有第一次创建数据库的时候才会执行
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 收支类型表
        String sql = "CREATE TABLE type(id integer primary key autoincrement, type_name varchar(10), image_id integer, s_image_id integer, kind integer)";
        db.execSQL(sql);
        insertType(db);
        // 记账表
        sql = "CREATE TABLE record(id integer primary key autoincrement, type_name varchar(10), s_image_id integer, remark varchar(100), money float, time varchar(60), kind integer)";
        db.execSQL(sql);
    }

    private void insertType(SQLiteDatabase db) {
        String sql = "INSERT INTO type(type_name, image_id, s_image_id, kind) values(?, ?, ?, ?)";
        // 支出
        db.execSQL(sql, new Object[]{"其他", R.mipmap.ic_qita, R.mipmap.ic_qita_fs, 0});
        db.execSQL(sql, new Object[]{"餐饮", R.mipmap.ic_canyin, R.mipmap.ic_canyin_fs, 0});
        db.execSQL(sql, new Object[]{"交通", R.mipmap.ic_jiaotong, R.mipmap.ic_jiaotong_fs, 0});
        db.execSQL(sql, new Object[]{"购物", R.mipmap.ic_gouwu, R.mipmap.ic_gouwu_fs, 0});
        db.execSQL(sql, new Object[]{"服饰", R.mipmap.ic_fushi, R.mipmap.ic_fushi_fs, 0});
        db.execSQL(sql, new Object[]{"日用品", R.mipmap.ic_riyongpin, R.mipmap.ic_riyongpin_fs, 0});
        db.execSQL(sql, new Object[]{"娱乐", R.mipmap.ic_yule, R.mipmap.ic_yule_fs, 0});
        db.execSQL(sql, new Object[]{"零食", R.mipmap.ic_lingshi, R.mipmap.ic_lingshi_fs, 0});
        db.execSQL(sql, new Object[]{"烟酒茶", R.mipmap.ic_yanjiu, R.mipmap.ic_yanjiu_fs, 0});
        db.execSQL(sql, new Object[]{"学习", R.mipmap.ic_xuexi, R.mipmap.ic_xuexi_fs, 0});
        db.execSQL(sql, new Object[]{"医疗", R.mipmap.ic_yiliao, R.mipmap.ic_yiliao_fs, 0});
        db.execSQL(sql, new Object[]{"住宅", R.mipmap.ic_zhufang, R.mipmap.ic_zhufang_fs, 0});
        db.execSQL(sql, new Object[]{"水电煤", R.mipmap.ic_shuidianfei, R.mipmap.ic_shuidianfei_fs, 0});
        db.execSQL(sql, new Object[]{"通讯", R.mipmap.ic_tongxun, R.mipmap.ic_tongxun_fs, 0});
        db.execSQL(sql, new Object[]{"人情往来", R.mipmap.ic_renqingwanglai, R.mipmap.ic_renqingwanglai_fs, 0});

        // 收入
        db.execSQL(sql, new Object[]{"其他", R.mipmap.in_qt, R.mipmap.in_qt_fs, 1});
        db.execSQL(sql, new Object[]{"薪资", R.mipmap.in_xinzi, R.mipmap.in_xinzi_fs, 1});
        db.execSQL(sql, new Object[]{"奖金", R.mipmap.in_jiangjin, R.mipmap.in_jiangjin_fs, 1});
        db.execSQL(sql, new Object[]{"借入", R.mipmap.in_jieru, R.mipmap.in_jieru_fs, 1});
        db.execSQL(sql, new Object[]{"收债", R.mipmap.in_shouzhai, R.mipmap.in_shouzhai_fs, 1});
        db.execSQL(sql, new Object[]{"利息收入", R.mipmap.in_lixifuji, R.mipmap.in_lixifuji_fs, 1});
        db.execSQL(sql, new Object[]{"投资回报", R.mipmap.in_touzi, R.mipmap.in_touzi_fs, 1});
        db.execSQL(sql, new Object[]{"二手交易", R.mipmap.in_ershoushebei, R.mipmap.in_ershoushebei_fs, 1});
        db.execSQL(sql, new Object[]{"意外所得", R.mipmap.in_yiwai, R.mipmap.in_yiwai_fs, 1});

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
