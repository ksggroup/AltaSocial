package com.ksggroup.altanet.altasocial.Soap;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ksggroup.altanet.altasocial.Model.AuthenticateRequest;
import com.ksggroup.altanet.altasocial.Model.GetPostRequest;
import com.ksggroup.altanet.altasocial.Model.InsertCommentsReq;
import com.ksggroup.altanet.altasocial.Model.InsertPostRequest;
import com.ksggroup.altanet.altasocial.Model.Post;
import com.ksggroup.altanet.altasocial.Model.User;
import com.ksggroup.altanet.altasocial.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.ref.WeakReference;
import java.util.List;

public class AddCommentAsync extends AsyncTask<InsertCommentsReq, Dialog, Integer> {

    private Activity activity;
    private WeakReference<Activity> mWeakActivity;
    private ProgressDialog pd;
    private Long postId;
    private ListView listView;
    private Button commentBtn;
    private User user;

    public AddCommentAsync(Activity activity, ListView listView, Button commentBtn, User user) {
        this.activity = activity;
        mWeakActivity = new WeakReference<Activity>(activity);
        pd = new ProgressDialog(activity);
        this.listView = listView;
        this.commentBtn = commentBtn;
        this.user = user;
    }

    @Override
    protected Integer doInBackground(InsertCommentsReq ... params) {
        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
        String URL = activity.getResources().getString(R.string.URL);
        String METHOD_NAME = "insertComments";   //Method
        String SOAP_ACTION = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();

        postId = params[0].getPost_id();
        pi.setName("InsertCommentsReq");
        pi.setValue(params[0]);
        pi.setType(InsertCommentsReq.class);

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
            commentBtn.setEnabled(false);
            pd.setIndeterminate(true);
            pd.setMessage("Commenting...");
            pd.setCancelable(false);
            pd.show();
        }
    }

    @Override
    protected void onPostExecute(final Integer insertRows) {

        Activity a = mWeakActivity.get();

        if(a != null) {
            pd.dismiss();

            TextView t = (TextView)a.findViewById(R.id.userId);

            if(insertRows > 0) {
                Toast.makeText(a, "Comment posted.", Toast.LENGTH_SHORT).show();
                Long userId = Long.valueOf(t.getText().toString());
                new FeedAsync(a, user).execute(new GetPostRequest(userId));
                new GetCommentAsync(activity, listView).execute(postId);
            } else {
                Toast.makeText(a, "Failed to comment!", Toast.LENGTH_SHORT).show();
            }

            commentBtn.setEnabled(true);
        }
    }
 }

