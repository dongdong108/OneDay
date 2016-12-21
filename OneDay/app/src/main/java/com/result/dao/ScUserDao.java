package com.result.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * autour: 刘东东
 * date: 2016/12/19 18:50
 * update: 2016/12/19
 */
public class ScUserDao {
    // 找到数据库
    private ScUserHelper helper;

    public ScUserDao(Context context) {
        helper = new ScUserHelper(context);
    }

    // 数据库的添加方法
    public boolean insert(String data, String title, String image,String e_id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("data", data);
        values.put("title", title);
        values.put("image", image);
        values.put("e_id", e_id);
        long insert = db.insert("ldd", null, values);
        if (insert != -1) {
            return true;
        } else {
            return false;
        }
    }

    // 数据库的查看方法
    public List<ScUser> select() {
        List<ScUser> list = new ArrayList<ScUser>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor rs = db.query("ldd", null, null, null, null, null, null);
        while (rs.moveToNext()) {
            ScUser user = new ScUser();
            int id = rs.getInt(rs.getColumnIndex("id"));
            String data = rs.getString(rs.getColumnIndex("data"));
            String title = rs.getString(rs.getColumnIndex("title"));
            String image = rs.getString(rs.getColumnIndex("image"));
            String e_id = rs.getString(rs.getColumnIndex("e_id"));
            user.setId(id);
            user.setData(data);
            user.setTitle(title);
            user.setImage(image);
            user.setE_id(e_id);
            list.add(user);
        }
        return list;
    }

    // 数据库的删除方法
    public boolean delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = db.delete("ldd", "id = ?", new String[]{id + ""});
        if (delete > 0) {
            return true;
        } else {
            return false;
        }
    }
    // 数据的删除方法
    public boolean delete(String e_id) {
        SQLiteDatabase db= helper.getWritableDatabase();
        int delete = db.delete("ldd", "e_id = ?", new String[]{e_id });
        if (delete > 0) {
            return true;
        } else {
            return false;
        }
    }
    // 数据库的查看方法
    public boolean isHaveid(String t_id) {
        List<ScUser> list = new ArrayList<ScUser>();
        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor rs = db.rawQuery("scusers", null, "e_id=?", new String[]{t_id}, null, null, null);
        if(t_id==null)
            return  false;
        Cursor rs = db.rawQuery("select * from ldd where e_id=?", new String[]{t_id});
        ScUser user=null;
        while (rs.moveToNext()) {
            user = new ScUser();
            int id = rs.getInt(rs.getColumnIndex("id"));
            String data = rs.getString(rs.getColumnIndex("data"));
            String title = rs.getString(rs.getColumnIndex("title"));
            String image = rs.getString(rs.getColumnIndex("image"));
            String e_id = rs.getString(rs.getColumnIndex("e_id"));
            user.setId(id);
            user.setData(data);
            user.setTitle(title);
            user.setImage(image);
            user.setE_id(e_id);
            list.add(user);
        }
        db.close();
        if(list.size()==0){
            return false;
        }
        return true;
    }

    // 数据库的修改方法
    public boolean update(String data, String title, String image,
                          int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("data", data);
        values.put("title", title);
        values.put("image", image);
        int update = db.update("ldd", values, "id = ?", new String[]{id + ""});
        if (update > 0) {
            return true;
        } else {
            return false;
        }
    }
}
