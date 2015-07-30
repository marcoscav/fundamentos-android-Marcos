package com.example.marcos.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcos.myapplication.R;
import com.example.marcos.myapplication.model.entities.User;
import com.example.marcos.myapplication.util.FormHelper;

/**
 * Created by Marcos on 20/07/2015.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button buttonLogin;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindLoginButton();
    }

    private void bindLoginButton() {
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FormHelper.requireValidate(LoginActivity.this, editTextUserName, editTextPassword)) {
                    bindUser();
                    if (user.authenticate(user)) {
                        Intent goToMainActivity = new Intent(LoginActivity.this, ClientListActivity.class);
                        startActivity(goToMainActivity);
                    } else {
                        Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.userInvalid), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void bindUser() {
        user = new User();
        user.setUserName(editTextUserName.getText().toString());
        user.setPassword(editTextPassword.getText().toString());
    }

}
