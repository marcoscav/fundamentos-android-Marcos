package com.example.marcos.myapplication.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marcos.myapplication.model.entities.User;

/**
 * Created by Marcos on 23/07/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String BANCO_DADOS = "MY_DATABASE";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.BANCO_DADOS, null, DatabaseHelper.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClientContract.getCreateSql());
        db.execSQL(UserContract.getCreateSql());
        User user = new User();
        user.setUserName("admin");
        user.setPassword("admin");
        db.insert(UserContract.TABLE, null, UserContract.getContentValues(user));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
