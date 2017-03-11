package com.ksggroup.altanet.altasocial.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ksggroup.altanet.altasocial.Adapter.PostAdapter;
import com.ksggroup.altanet.altasocial.Model.Post;
import com.ksggroup.altanet.altasocial.Model.RegisterRequest;
import com.ksggroup.altanet.altasocial.R;
import com.ksggroup.altanet.altasocial.Soap.RegisterAsync;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity {
    @BindView(R.id.textFirstname) EditText first_name;
    @BindView(R.id.textMiddlename) EditText middle_name;
    @BindView(R.id.textLastname) EditText last_name;
    @BindView(R.id.textCourse) EditText course;
    @BindView(R.id.textYearLevel) EditText year_level;
    @BindView(R.id.textEmail) EditText email;
    @BindView(R.id.textPassword) EditText password;
    @BindView(R.id.btn_Register) Button registerBtn;
    @BindView(R.id.dob) EditText dob;
    private Activity activity;
    private WeakReference<Activity> mWeakActivity;
    private ProgressDialog pdCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            register(first_name.getText().toString(),
                    middle_name.getText().toString(),
                    last_name.getText().toString(),
                    dob.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString());

            }
        });


    }
    public void register(String fName, String mName, String lName, String dob, String username, String password) {
        if (!validateFields(fName,mName,lName,username,password)) {
            Toast.makeText(getBaseContext(), " \t\t\t\t\t\tRegister failed "+
                    "\n One or more fields is incorrect!", Toast.LENGTH_LONG).show();
            return;
        }

        //
        new RegisterAsync(this).execute(new RegisterRequest(username, password, fName, mName, lName, dob));

    }
    public boolean validateFields(String fName, String mName, String lName, String username, String password) {

        if(fName.isEmpty() || mName.isEmpty()|| fName.isEmpty()||
                username.isEmpty() || password.isEmpty()) {
            return false;
        }

        return true;
    }







}
