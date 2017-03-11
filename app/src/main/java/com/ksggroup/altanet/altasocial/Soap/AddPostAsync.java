package com.ksggroup.altanet.altasocial.Soap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ksggroup.altanet.altasocial.Activities.FeedActivity;
import com.ksggroup.altanet.altasocial.Model.AuthenticateRequest;
import com.ksggroup.altanet.altasocial.Model.GetPostRequest;
import com.ksggroup.altanet.altasocial.Model.InsertPostRequest;
import com.ksggroup.altanet.altasocial.Model.Post;
import com.ksggroup.altanet.altasocial.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.List;

public class AddPostAsync extends AsyncTask<InsertPostRequest, Void, Integer> {

    private Activity activity;
    private WeakReference<Activity> mWeakActivity;
    private ProgressDialog pd;
    private Long userId;

    public AddPostAsync(Activity activity) {
        this.activity = activity;
        mWeakActivity = new WeakReference<Activity>(activity);
        pd = new ProgressDialog(activity);
    }

    @Override
    protected Integer doInBackground(InsertPostRequest ... params) {
        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
        String URL = activity.getResources().getString(R.string.URL);
        String METHOD_NAME = "insertPosts";   //Method
        String SOAP_ACTION = "";

        userId = Long.valueOf(params[0].getProperty(0).toString());
        List<Post> posts = null;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();

        pi.setName("InsertPostRequest");
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
            System.out.println("request: " + androidHttpTransport.requestDump);
            System.out.println("response: " + androidHttpTransport.responseDump);

            SoapObject response = (SoapObject)envelope.getResponse();

            return Integer.valueOf(response.getProperty("insertRows").toString());

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    protected void onPreExecute() {

        Activity a = mWeakActivity.get();

        if(a != null) {
            Button btnPost = (Button) a.findViewById(R.id.btn_post);
            btnPost.setEnabled(false);
            pd.setIndeterminate(true);
            pd.setMessage("Posting...");
            pd.setCancelable(false);
            pd.show();
        }
    }

    @Override
    protected void onPostExecute(final Integer insertRows) {

        Activity a = mWeakActivity.get();

        if(a != null) {
            final Button btnPost = (Button) a.findViewById(R.id.btn_post);
            final TextView textPost = (TextView) a.findViewById(R.id.text_post);

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            pd.dismiss();

                            if(insertRows > 0) {
                                new FeedAsync(activity).execute(new GetPostRequest(userId));
                            } else {
                                Toast.makeText(activity, "Failed to post!", Toast.LENGTH_SHORT).show();
                            }
                            textPost.setText("");
                            btnPost.setEnabled(true);
                        }
                    }, 3000);
        }

    }
}
