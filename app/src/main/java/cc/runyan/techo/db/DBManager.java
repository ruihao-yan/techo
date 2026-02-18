package cc.runyan.techo.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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


}
