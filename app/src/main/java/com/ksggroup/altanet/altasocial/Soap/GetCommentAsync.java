package com.ksggroup.altanet.altasocial.Soap;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ksggroup.altanet.altasocial.Adapter.CommentAdapter;
import com.ksggroup.altanet.altasocial.Model.Comment;
import com.ksggroup.altanet.altasocial.Model.GetCommentsRequest;
import com.ksggroup.altanet.altasocial.Model.GetPostRequest;
import com.ksggroup.altanet.altasocial.Model.InsertCommentsReq;
import com.ksggroup.altanet.altasocial.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GetCommentAsync extends AsyncTask<Long, Void, Void> {
    private Activity activity;
    private WeakReference<Activity> mWeakActivity;
    private ProgressDialog pd;
    private List<Comment> comments;
    private ListView listView;

    public GetCommentAsync(Activity activity, ListView listView) {
        this.activity = activity;
        mWeakActivity = new WeakReference<Activity>(activity);
        this.listView = listView;
    }

    @Override
    protected Void doInBackground(Long... params) {

        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
        String URL = activity.getResources().getString(R.string.URL);
        String METHOD_NAME = "getComments";   //Method
        String SOAP_ACTION = "";


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();

        pi.setName("GetCommentsRequest");
        pi.setValue(new GetCommentsRequest(params[0].longValue()));
        pi.setType(GetCommentsRequest.class);

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
            comments = new ArrayList<Comment>();

            int propertyCount = response.getPropertyCount();
            System.out.println("Property Count: " + propertyCount);

            for(int ctr = 0; ctr < propertyCount; ctr++) {

                SoapObject soapPost = (SoapObject)response.getProperty(ctr);
                Comment comment = new Comment();

                comment.setComment_id(Long.valueOf(soapPost.getProperty("comment_id").toString()));
                comment.setContent(soapPost.getProperty("content").toString());
                comment.setDate_time(soapPost.getProperty("date_time").toString());
                comment.setPost_id(Long.valueOf(soapPost.getProperty("post_id").toString()));
                comment.setUser_id(Long.valueOf(soapPost.getProperty("user_id").toString()));
                comment.setFirst_name(soapPost.getProperty("first_name").toString());
                comment.setMiddle_name(soapPost.getProperty("middle_name").toString());
                comment.setLast_name(soapPost.getProperty("last_name").toString());

                comments.add(comment);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Void v) {

        Activity a = mWeakActivity.get();
        if(a != null) {
            //ListView commentList = (ListView)a.getWindow().findViewById(R.id.commentList);

            final CommentAdapter commentAdapter = new CommentAdapter(activity, comments);
            this.listView.setAdapter(commentAdapter);
            //commentAdapter.getComments().addAll(comments);
            commentAdapter.notifyDataSetChanged();

        }
    }

 }

