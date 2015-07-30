package com.example.marcos.myapplication.model.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marcos.myapplication.model.entities.User;
import com.example.marcos.myapplication.util.AppUtil;

/**
 * Created by Marcos on 30/07/2015.
 */
public class SQLiteUserRepository implements UserRepository {

    private static SQLiteUserRepository singletonInstance;


    private SQLiteUserRepository() {
        super();
    }

    public static SQLiteUserRepository getInstance() {
        if (SQLiteUserRepository.singletonInstance == null) {
            SQLiteUserRepository.singletonInstance = new SQLiteUserRepository();
        }
        return singletonInstance;
    }

    @Override
    public User authenticate(User user) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        String where = UserContract.USER_NAME + " = ? AND " + UserContract.PASSWORD + " = ? ";
        String[] args = {user.getUserName().toString(), user.getPassword().toString()};
        //String[] args = {user.getUserName().toString()};
        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUMNS, where, args, null, null, null);
        user = UserContract.bind(cursor);
        db.close();
        helper.close();
        return user;
    }
}
