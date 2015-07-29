package com.example.marcos.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.marcos.myapplication.model.entities.Client;
import com.example.marcos.myapplication.model.entities.ClientAddress;

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
    public static final String PHONE = "Phone";
    public static final String TIPO_LOGRADOURO = "Tipo_Logradouro";
    public static final String LOGRADOURO = "Logradouro";
    public static final String BAIRRO = "Bairro";
    public static final String CIDADE = "Cidade";
    public static final String ESTADO = "Estado";
    public static final String[] COLUMNS = {ID, NAME, AGE, PHONE, TIPO_LOGRADOURO, LOGRADOURO, BAIRRO, CIDADE, ESTADO};

    public static String getCreateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(PHONE + " TEXT, ");
        sql.append(TIPO_LOGRADOURO + " TEXT, ");
        sql.append(LOGRADOURO + " TEXT, ");
        sql.append(BAIRRO + " TEXT, ");
        sql.append(CIDADE + " TEXT, ");
        sql.append(ESTADO + " TEXT ");
        sql.append(" ); ");

        return sql.toString();
    }

    public static ContentValues getContentValues(Client client) {
        ContentValues values = new ContentValues();
        values.put(ClientContract.ID, client.getId());
        values.put(ClientContract.NAME, client.getName());
        values.put(ClientContract.AGE, client.getAge());
        values.put(ClientContract.PHONE, client.getPhone());
        values.put(ClientContract.TIPO_LOGRADOURO, client.getAddress().getTipoDeLogradouro());
        values.put(ClientContract.LOGRADOURO, client.getAddress().getLogradouro());
        values.put(ClientContract.BAIRRO, client.getAddress().getBairro());
        values.put(ClientContract.CIDADE, client.getAddress().getCidade());
        values.put(ClientContract.ESTADO, client.getAddress().getEstado());
        return values;
    }

    public static Client bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(ClientContract.PHONE)));
            ClientAddress address = new ClientAddress();
            address.setTipoDeLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.TIPO_LOGRADOURO)));
            address.setLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.LOGRADOURO)));
            address.setBairro(cursor.getString(cursor.getColumnIndex(ClientContract.BAIRRO)));
            address.setCidade(cursor.getString(cursor.getColumnIndex(ClientContract.CIDADE)));
            address.setEstado(cursor.getString(cursor.getColumnIndex(ClientContract.ESTADO)));
            client.setAddress(address);
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
