package cc.runyan.techo.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cc.runyan.techo.constant.Constants;
import cc.runyan.techo.dto.AmountCome;
import cc.runyan.techo.po.RecordBean;
import cc.runyan.techo.po.TypeBean;

/**
 * 对数据库中内容进行操作
 */
public class DBManager {

    private static SQLiteDatabase db;

    public static void initDb(Context context) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
    }

    public static List<TypeBean> getListByKind(int kind) {
        List<TypeBean> kindList = new ArrayList<>();
        String sql = "SELECT * FROM type Where kind = ?";
        try (Cursor cursor = db.rawQuery(sql, new String[]{Integer.toString(kind)})) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndexOrThrow("id");
                int typeNameIndex = cursor.getColumnIndexOrThrow("type_name");
                int imageIdIndex = cursor.getColumnIndexOrThrow("image_id");
                int sImageIdIndex = cursor.getColumnIndexOrThrow("s_image_id");
                int kindIndex = cursor.getColumnIndexOrThrow("kind");

                TypeBean bean = new TypeBean();
                bean.setId(cursor.getInt(idIndex));
                bean.setTypename(cursor.getString(typeNameIndex));
                bean.setImageId(cursor.getInt(imageIdIndex));
                bean.setSImageId(cursor.getInt(sImageIdIndex));
                bean.setKind(cursor.getInt(kindIndex));
                kindList.add(bean);
            }
        }
        return kindList;
    }

    private static String getFormatTime(Integer year, Integer month, Integer day) {
        if (year == null && month == null && day == null) {
            return null;
        }
        if (day == null) { // 精确到某年某月
            return String.format("%d年%02d月", year, month);
        } else if (month == null) { // 精确到某年
            return String.format("%d年", year);
        } else {
            return String.format("%d年%02d月%02d日", year, month, day);
        }

    }

    public static List<RecordBean> getRecords(Integer year, Integer month, Integer day) {
        List<RecordBean> list = new ArrayList<>();
        String time = getFormatTime(year, month, day);
        String sql;
        String[] params;
        if (time == null) {
            sql = "select * from record order by id desc";
            params = null;
        } else {
            sql = "select * from record where time like ? order by id desc";
            params = new String[]{time + "%"};
        }
        Cursor cursor = db.rawQuery(sql, params);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String typeName = cursor.getString(cursor.getColumnIndexOrThrow("type_name"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("s_image_id"));
            String remark = cursor.getString(cursor.getColumnIndexOrThrow("remark"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            String queryTime = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            RecordBean recordBean = new RecordBean(id, typeName, remark, sImageId, money, queryTime, kind);
            list.add(recordBean);
        }
        cursor.close();
        return list;
    }

    public static void insertIntoRecord(RecordBean recordBean) {
        String sql = "INSERT INTO record(type_name, s_image_id, remark, money, time, kind) values(?, ?, ?, ?, ?, ?)";
        db.execSQL(sql, new Object[]{recordBean.getTypename(), recordBean.getsImageId(), recordBean.getRemark(), recordBean.getMoney(), recordBean.getTime(), recordBean.getKind()});
    }

    public static AmountCome getAllAmountByTime(Integer year, Integer month, Integer day) {
        AmountCome amountCome = new AmountCome();
        List<RecordBean> records = getRecords(year, month, day);
        for (RecordBean record : records) {
            if (record.getKind() == Constants.INCOME_KIND) { // 收入
                amountCome.setInCome(amountCome.getInCome() + record.getMoney());
            } else {
                amountCome.setOutCome(amountCome.getOutCome() + record.getMoney());
            }
        }
        amountCome.setRecordBeanList(records);
        return amountCome;
    }


}
