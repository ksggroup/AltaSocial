package com.ksggroup.altanet.altasocial.Soap;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.ksggroup.altanet.altasocial.Model.AuthenticateRequest;
import com.ksggroup.altanet.altasocial.Model.User;
import com.ksggroup.altanet.altasocial.R;

import butterknife.BindString;

/**
 * Created by vfiguracion on 3/7/17.
 */

public class LoginAsync extends AsyncTask<AuthenticateRequest, String, User> {

    private Context activity;

    ProgressDialog pd;

    public LoginAsync(Context activity) {
        this.activity = activity;
    }

    @Override
    protected User doInBackground(AuthenticateRequest... params) {

        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
        String URL = activity.getResources().getString(R.string.URL);
        String METHOD_NAME = "auth";
        String SOAP_ACTION = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();
        User user = null;

        pi.setName("AuthenticateRequest");
        pi.setValue(params[0]);
        pi.setType(AuthenticateRequest.class);

        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        androidHttpTransport.debug = true;
        try
        {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject)envelope.getResponse();

            if(response.getProperty("return_code").toString().equals("0")) {
                System.out.println("response: " + androidHttpTransport.responseDump);

                SoapObject soapUser = (SoapObject)response.getProperty("user");
                user = new User();
                user.setFirst_name(soapUser.getProperty("first_name").toString());
                user.setLast_name(soapUser.getProperty("last_name").toString());
                user.setMiddle_name(soapUser.getProperty("middle_name").toString());
                user.setDob(soapUser.getProperty("dob").toString());
                user.setUser_id(Long.valueOf(soapUser.getProperty("user_id").toString()));

                System.out.println("First Name: " + user.getFirst_name());
                System.out.println("Middle Name: " + user.getMiddle_name());
                System.out.println("Last Name: " + user.getLast_name());
                System.out.println("DOB: " + user.getDob().toString());
                System.out.println("user_id: " + user.getUser_id().toString());
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(User user) {

    }
}



