package com.example.marcos.myapplication.controller;

import android.app.ProgressDialog;
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

import java.util.concurrent.ExecutionException;

/**
 * Created by Marcos on 21/07/2015.
 */
public class ClientPersistActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextPhone;
    private EditText editTextCep;
    private EditText editTextTipoLogradouro;
    private EditText editTextLogradouro;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;
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
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextCep = (EditText) findViewById(R.id.editTextCep);
        editTextTipoLogradouro = (EditText) findViewById(R.id.editTextTipoLogradouro);
        editTextLogradouro = (EditText) findViewById(R.id.editTextLogradouro);
        editTextBairro = (EditText) findViewById(R.id.editTextBairro);
        editTextCidade = (EditText) findViewById(R.id.editTextCidade);
        editTextEstado = (EditText) findViewById(R.id.editTextEstado);
        bindButtonFindCep();
    }

    private void bindButtonFindCep() {
        buttonFindCep = (Button) findViewById(R.id.buttonFind);
        buttonFindCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CepService.getAddressBy(editTextCep.getText().toString());
                AsyncTask<String, Void, ClientAddress> execute = new GetAddressByCep().execute(editTextCep.getText().toString());
                try {
                    ClientAddress address = execute.get();
                    editTextTipoLogradouro.setText(address.getTipoDeLogradouro());
                    editTextLogradouro.setText(address.getLogradouro());
                    editTextBairro.setText(address.getBairro());
                    editTextCidade.setText(address.getCidade());
                    editTextEstado.setText(address.getEstado());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
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
        client.setPhone(editTextPhone.getText().toString());
        ClientAddress address = new ClientAddress();
        address.setTipoDeLogradouro(editTextTipoLogradouro.getText().toString());
        address.setLogradouro(editTextLogradouro.getText().toString());
        address.setBairro(editTextBairro.getText().toString());
        address.setCidade(editTextCidade.getText().toString());
        address.setEstado(editTextEstado.getText().toString());
        client.setAddress(address);
    }

    private void bindForm(Client client) {
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextPhone.setText(client.getPhone());
        editTextBairro.setText(client.getAddress().getBairro());
        editTextEstado.setText(client.getAddress().getEstado());
        editTextCidade.setText(client.getAddress().getCidade());
        editTextLogradouro.setText(client.getAddress().getLogradouro());
        editTextTipoLogradouro.setText(client.getAddress().getTipoDeLogradouro());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSave) {
            if (FormHelper.requireValidate(ClientPersistActivity.this, editTextName, editTextAge, editTextPhone)) {
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
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ClientPersistActivity.this);
            super.onPreExecute();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            progressDialog.dismiss();
        }
    }
}
