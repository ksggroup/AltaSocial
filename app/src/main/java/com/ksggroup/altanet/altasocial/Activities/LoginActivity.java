package com.ksggroup.altanet.altasocial.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ksggroup.altanet.altasocial.R;
import com.ksggroup.altanet.altasocial.Model.AuthenticateRequest;
import com.ksggroup.altanet.altasocial.Model.User;
import com.ksggroup.altanet.altasocial.Soap.LoginAsync;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_password) EditText passText;
    @BindView(R.id.btn_login) Button loginBtn;
    @BindView (R.id.signUp) TextView signUptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        ButterKnife.bind(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(emailText.getText().toString(), passText.getText().toString());

            }
        });
        signUptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(getBaseContext(), CreateAccountActivity.class);
                startActivity(createAccountIntent);

            }
        });
    }


    public void login(String username, String password) {
        if(!validateFields(username, password)) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            return;
        }

        new LoginAsync(this).execute(new AuthenticateRequest(username, password));
    }

    public boolean validateFields(String username, String password) {

        if(username.isEmpty() || password.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

