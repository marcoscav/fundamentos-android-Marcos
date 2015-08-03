package com.example.marcos.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marcos.myapplication.model.entities.Client;
import com.example.marcos.myapplication.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 23/07/2015.
 */
public class SQLiteClientRepository implements ClientRepository {

    private static SQLiteClientRepository singletonInstance;

    private static final String PERCENT = "%";

    private SQLiteClientRepository() {
        super();
    }

    public static SQLiteClientRepository getInstance() {
        if (SQLiteClientRepository.singletonInstance == null) {
            SQLiteClientRepository.singletonInstance = new SQLiteClientRepository();
        }
        return singletonInstance;
    }

    @Override
    public void save(Client client) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = ClientContract.getContentValues(client);
        if (client.getId() == null) {
            db.insert(ClientContract.TABLE, null, values);
        } else {
            String where = ClientContract.ID + " = ? ";
            String[] args = {client.getId().toString()};
            db.update(ClientContract.TABLE, values, where, args);
        }
        db.close();
        helper.close();
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(ClientContract.TABLE, ClientContract.COLUMNS, null, null, null, null, ClientContract.NAME);
        clients = ClientContract.clientList(cursor);
        db.close();
        helper.close();
        return clients;
    }

    public List<Client> search(String parameter) {
        List<Client> clients = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        String where = ClientContract.NAME + " LIKE ? OR " + ClientContract.AGE + " LIKE ? ";
        String[] args = new String[]{PERCENT + parameter + PERCENT, PERCENT + parameter + PERCENT};
        Cursor cursor = db.query(ClientContract.TABLE, ClientContract.COLUMNS, where, args, null, null, ClientContract.NAME);
        clients = ClientContract.clientList(cursor);
        db.close();
        helper.close();
        return clients;
    }

    @Override
    public void delete(Client client) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = ClientContract.ID + " = ?";
        String[] args = {client.getId().toString()};
        db.delete(ClientContract.TABLE, where, args);
        db.close();
        helper.close();
    }
}
