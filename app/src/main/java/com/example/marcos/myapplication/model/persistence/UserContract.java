package com.example.marcos.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.marcos.myapplication.model.entities.User;

/**
 * Created by Marcos on 30/07/2015.
 */
public class UserContract {
    public static final String TABLE = "User";
    public static final String ID = "id";
    public static final String USER_NAME = "UserName";
    public static final String PASSWORD = "Password";
    public static final String[] COLUMNS = {ID, USER_NAME, PASSWORD};

    public static String getCreateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(USER_NAME + " TEXT, ");
        sql.append(PASSWORD + " TEXT ");
        sql.append(" ); ");

        return sql.toString();
    }

    public static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserContract.ID, user.getId());
        values.put(UserContract.USER_NAME, user.getUserName());
        values.put(UserContract.PASSWORD, user.getPassword());
        return values;
    }

    public static User bind(Cursor cursor) {
        if (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(UserContract.ID)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserContract.USER_NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.PASSWORD)));
            return user;
        }
        return null;
    }

}
