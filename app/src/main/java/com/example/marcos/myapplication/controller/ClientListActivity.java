package com.example.marcos.myapplication.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcos.myapplication.R;
import com.example.marcos.myapplication.model.entities.Client;

import java.util.List;


public class ClientListActivity extends AppCompatActivity {

    private static final String TAG = ClientListActivity.class.getSimpleName();

    private ListView listViewClients;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindClientList();
    }

    private void bindClientList() {
        List<Client> clientNames = getClients();
        listViewClients = (ListView) findViewById(R.id.listViewClients);
        //ArrayAdapter<String> clientsAdapter = new ArrayAdapter<String>(ClientListActivity.this, android.R.layout.simple_list_item_1, clientNames.toArray(new String[]{}));
        ClientListAdapter clientListAdapter = new ClientListAdapter(ClientListActivity.this, clientNames);
        listViewClients.setAdapter(clientListAdapter);
        listViewClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client) parent.getItemAtPosition(position);
                return false;
            }
        });
        registerForContextMenu(listViewClients);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        this.getMenuInflater().inflate(R.menu.menu_client_list_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuConsult) {


        }else if (item.getItemId() == R.id.menuEdit) {
            Intent intent = new Intent(ClientListActivity.this, ClientPersistActivity.class);
            intent.putExtra(ClientPersistActivity.CLIENT_PARAM, (Parcelable) client);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuDelete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ClientListActivity.this)
                    .setMessage(R.string.confirmDelete).setTitle(R.string.confirm)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            client.delete();
                            refreshClientList();
                            Toast.makeText(ClientListActivity.this, ClientListActivity.this.getString(R.string.success), Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.create();
            builder.show();
        }
        return super.onContextItemSelected(item);
    }

    private List<Client> getClients() {
        return Client.getAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshClientList();
    }

    private void refreshClientList() {
        ClientListAdapter adapter = (ClientListAdapter) listViewClients.getAdapter();
        adapter.setClients(getClients());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuCreateNew) {
            Intent goToPersistActivity = new Intent(ClientListActivity.this, ClientPersistActivity.class);
            startActivity(goToPersistActivity);
        }
        return super.onOptionsItemSelected(item);
    }
}
