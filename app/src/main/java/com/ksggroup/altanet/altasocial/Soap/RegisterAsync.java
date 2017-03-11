package com.ksggroup.altanet.altasocial.Soap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.ksggroup.altanet.altasocial.Activities.CreateAccountActivity;
import com.ksggroup.altanet.altasocial.Activities.FeedActivity;
import com.ksggroup.altanet.altasocial.Activities.LoginActivity;
import com.ksggroup.altanet.altasocial.Adapter.PostAdapter;
import com.ksggroup.altanet.altasocial.Model.AuthenticateRequest;

import com.ksggroup.altanet.altasocial.Model.InsertUserRequest;
import com.ksggroup.altanet.altasocial.Model.RegisterRequest;
import com.ksggroup.altanet.altasocial.Model.User;
import com.ksggroup.altanet.altasocial.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.ref.WeakReference;
import java.util.List;



public class RegisterAsync extends AsyncTask<RegisterRequest, Void, Integer> {
    final static int CONST_USERNAME = 0;
    final static int CONST_PASSWORD = 1;
    final static int CONST_FNAME = 2;
    final static int CONST_MNAME = 3;
    final static int CONST_LNAME = 4;
    final static int CONST_DOB = 5;


    private Activity activity;
    private WeakReference<Activity> mWeakActivity;
    private ProgressDialog pdCreateUser;
    private int insertRows = 0;

    public RegisterAsync(Activity activity) {
        this.activity = activity;
        mWeakActivity = new WeakReference<Activity>(activity);

    }

    protected Integer doInBackground(RegisterRequest... params) {

        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
        String URL = activity.getResources().getString(R.string.URL);
        String METHOD_NAME = "insertUser";
        String SOAP_ACTION = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();

        InsertUserRequest iur = new InsertUserRequest(params[0].getProperty(CONST_FNAME).toString(),
                params[0].getProperty(CONST_MNAME).toString(),
                params[0].getProperty(CONST_LNAME).toString(),
                params[0].getProperty(CONST_DOB).toString(),
                params[0].getProperty(CONST_USERNAME).toString(),
                params[0].getProperty(CONST_PASSWORD).toString());

        pi.setName("InsertUserRequest");
        pi.setValue(iur);
        pi.setType(InsertUserRequest.class);

        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        androidHttpTransport.debug = true;
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            System.out.println("Request: " + androidHttpTransport.requestDump);
            System.out.println("Response: " + androidHttpTransport.responseDump);

            insertRows = Integer.valueOf(response.getProperty("insertRows").toString());

            if (insertRows > 0) {
                // insert academic
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return insertRows;
    }

    @Override
    protected void onPreExecute() {
        Activity a = mWeakActivity.get();

        if (a != null) {
            Button registerBtn = (Button) a.findViewById(R.id.btn_Register);
            registerBtn.setEnabled(false);

            pdCreateUser = new ProgressDialog(activity);
            pdCreateUser.setIndeterminate(true);
            pdCreateUser.setMessage("Creating account...");
            pdCreateUser.setCancelable(false);
            pdCreateUser.show();
        }
    }

    @Override
    protected void onPostExecute(Integer insertRows) {
        pdCreateUser.dismiss();
        Toast.makeText(activity, "Registered", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

}


