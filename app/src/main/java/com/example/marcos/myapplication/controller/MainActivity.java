package com.example.marcos.myapplication.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.marcos.myapplication.model.entities.Client;
import com.example.marcos.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Client> clientNames = getClients();

        ListView listViewClients = (ListView) findViewById(R.id.listViewClients);
        //ArrayAdapter<String> clientsAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, clientNames.toArray(new String[]{}));

        ClientListAdapter clientListAdapter = new ClientListAdapter(MainActivity.this, clientNames);

        listViewClients.setAdapter(clientListAdapter);
    }

    private List<Client> getClients() {
        ArrayList<Client> clients = new ArrayList<>();
        Client teste = new Client();
        teste.setName("Teste 1");
        teste.setAge(30);
        Client teste2 = new Client();
        teste2.setName("Teste 2");
        teste2.setAge(25);
        clients.add(teste);
        clients.add(teste2);
        return clients;
    }

}
