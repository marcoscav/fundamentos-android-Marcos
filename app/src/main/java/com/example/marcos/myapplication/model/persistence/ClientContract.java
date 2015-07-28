package com.example.marcos.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.marcos.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 23/07/2015.
 */
public class ClientContract {

    public static final String TABLE = "Client";
    public static final String ID = "id";
    public static final String NAME = "Name";
    public static final String AGE = "Age";
    public static final String ADDRESS = "Address";
    public static final String PHONE = "Phone";
    public static final String[] COLUMNS = {ID, NAME, AGE, ADDRESS, PHONE};

    public static String getCreateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(ADDRESS + " TEXT, ");
        sql.append(PHONE + " TEXT ");

        sql.append(" ) ");

        return sql.toString();
    }

    public static ContentValues getContentValues(Client client) {
        ContentValues values = new ContentValues();
        values.put(ClientContract.ID, client.getId());
        values.put(ClientContract.NAME, client.getName());
        values.put(ClientContract.AGE, client.getAge());
        values.put(ClientContract.ADDRESS, client.getAddress());
        values.put(ClientContract.PHONE, client.getPhone());
        return values;
    }

    public static Client bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));
            client.setAddress(cursor.getString(cursor.getColumnIndex(ClientContract.ADDRESS)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(ClientContract.PHONE)));
            return client;
        }
        return null;
    }

    public static List<Client> clientList(Cursor cursor) {
        List<Client> clients = new ArrayList<>();
        while (cursor.moveToNext()) {
            clients.add(bind(cursor));
        }
        return clients;
    }

}
