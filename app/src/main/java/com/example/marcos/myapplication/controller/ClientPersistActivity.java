package com.example.marcos.myapplication.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcos.myapplication.R;
import com.example.marcos.myapplication.model.entities.Client;
import com.example.marcos.myapplication.model.entities.ClientAddress;
import com.example.marcos.myapplication.model.services.CepService;
import com.example.marcos.myapplication.util.FormHelper;

/**
 * Created by Marcos on 21/07/2015.
 */
public class ClientPersistActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private EditText editTextCep;
    private Button buttonFindCep;

    public static final String CLIENT_PARAM = "CLIENT_PARAM";

    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_client);
        bindFields();
        getParameters();
    }

    private void bindFields() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextCep = (EditText) findViewById(R.id.editTextCep);
        bindButtonFindCep();
    }

    private void bindButtonFindCep() {
        buttonFindCep = (Button) findViewById(R.id.buttonFind);
        buttonFindCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CepService.getAddressBy(editTextCep.getText().toString());
                new GetAddressByCep().execute(editTextCep.getText().toString());
            }
        });
    }

    private void getParameters() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            client = (Client) extras.getParcelable(CLIENT_PARAM);
            if (client == null) {
                throw new IllegalArgumentException();
            }
            bindForm(client);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void bindClient() {
        if (client == null) {
            client = new Client();
        }
        client.setName(editTextName.getText().toString());
        client.setAge((editTextAge.getText().toString() != null && !editTextAge.getText().toString().isEmpty()) ? Integer.valueOf(editTextAge.getText().toString()) : null);
        client.setAddress(editTextAddress.getText().toString());
        client.setPhone(editTextPhone.getText().toString());
    }

    private void bindForm(Client client) {
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextAddress.setText(client.getAddress());
        editTextPhone.setText(client.getPhone());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSave) {
            if (FormHelper.requireValidate(ClientPersistActivity.this, editTextName, editTextAge, editTextAddress, editTextPhone)) {
                bindClient();
                client.save();
                //Intent goToPersistActivity = new Intent(ClientPersistActivity.this, ClientListActivity.class);
                //startActivity(goToPersistActivity);
                ClientPersistActivity.this.finish();
                Toast.makeText(ClientPersistActivity.this, ClientPersistActivity.this.getString(R.string.success), Toast.LENGTH_LONG).show();
                finish();
            }
            //Toast.makeText(ClientPersistActivity.this, Client.getAll().toString(), Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);

    }

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress> {

        //@Override
        //protected void onPreExecute() {
        //    super.onPreExecute();
        //}

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
        }

        //@Override
        //protected void onPostExecute(Void aVoid) {
        //    super.onPostExecute(aVoid);
        //}
    }
}
