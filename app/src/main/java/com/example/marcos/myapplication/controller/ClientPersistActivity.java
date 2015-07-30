package com.example.marcos.myapplication.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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
    private EditText editTextPhone;
    private EditText editTextCep;
    private EditText editTextTipoLogradouro;
    private EditText editTextLogradouro;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;

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
        editTextName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_edittext_client, 0);
        editTextName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextName.getRight() - editTextName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);
                    }
                }
                return false;
            }
        });

        editTextCep = (EditText) findViewById(R.id.editTextCep);
        editTextCep.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_search, 0);
        editTextCep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (editTextCep.getText().toString() != null && !editTextCep.getText().toString().equals("")) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (editTextCep.getRight() - editTextCep.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            new GetAddressByCep().execute(editTextCep.getText().toString());
                        }
                    }
                } else {
                    Toast.makeText(ClientPersistActivity.this, ClientPersistActivity.this.getString(R.string.cepRequired), Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextTipoLogradouro = (EditText) findViewById(R.id.editTextTipoLogradouro);
        editTextLogradouro = (EditText) findViewById(R.id.editTextLogradouro);
        editTextBairro = (EditText) findViewById(R.id.editTextBairro);
        editTextCidade = (EditText) findViewById(R.id.editTextCidade);
        editTextEstado = (EditText) findViewById(R.id.editTextEstado);
    }

    /**
     * @see <a href="http://developer.android.com/training/basics/intents/result.html">Getting a Result from an Activity</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    editTextName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    editTextPhone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    cursor.close();
                } catch (Exception e) {
                    Log.d("TAG", "Unexpected error");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        address.setCep(editTextCep.getText().toString());
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
        editTextCep.setText(client.getAddress().getCep());
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
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
            
        }

        @Override
        protected void onPostExecute(ClientAddress address) {
            if (address != null) {
                editTextTipoLogradouro.setText(address.getTipoDeLogradouro());
                editTextLogradouro.setText(address.getLogradouro());
                editTextBairro.setText(address.getBairro());
                editTextCidade.setText(address.getCidade());
                editTextEstado.setText(address.getEstado());
            } else {
                Toast.makeText(ClientPersistActivity.this, getString(R.string.addressNotFound), Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }
    }
}
