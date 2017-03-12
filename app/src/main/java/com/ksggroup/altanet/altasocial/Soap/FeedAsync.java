package com.ksggroup.altanet.altasocial.Soap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ksggroup.altanet.altasocial.Adapter.PostAdapter;
import com.ksggroup.altanet.altasocial.Model.AuthenticateRequest;
import com.ksggroup.altanet.altasocial.Model.GetPostRequest;
import com.ksggroup.altanet.altasocial.Model.GetReactionRequest;
import com.ksggroup.altanet.altasocial.Model.Post;
import com.ksggroup.altanet.altasocial.Model.User;
import com.ksggroup.altanet.altasocial.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;

/**
 * Created by vfiguracion on 3/10/17.
 */

public class FeedAsync extends AsyncTask<GetPostRequest, Void, List<Post>> {

    private Activity activity;
    private WeakReference<Activity> mWeakActivity;
    private ProgressDialog pd;
    private User user;

    public FeedAsync(Activity activity, User user) {
        this.activity = activity;
        mWeakActivity = new WeakReference<Activity>(activity);
        pd = new ProgressDialog(activity);
        this.user = user;
    }

    @Override
    protected List<Post> doInBackground(GetPostRequest[] params) {
        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
        String URL = activity.getResources().getString(R.string.URL);
        String METHOD_NAME = "getFeeds";   //Method
        String SOAP_ACTION = "";


        List<Post> posts = null;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();

        pi.setName("GetFeedsRequest");
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
            posts = new ArrayList<Post>();

            int propertyCount = response.getPropertyCount();
            System.out.println("Property Count: " + propertyCount);

            for(int ctr = 0; ctr < propertyCount; ctr++) {

                SoapObject soapPost = (SoapObject)response.getProperty(ctr);
                Post post = new Post();

                post.setContent(soapPost.getProperty("content").toString());
                post.setDatetime(soapPost.getProperty("datetime").toString());
                post.setPost_id(Long.valueOf(soapPost.getProperty("post_id").toString()));
                post.setUser_id(Long.valueOf(soapPost.getProperty("user_id").toString()));
                post.setFirst_name(soapPost.getProperty("first_name").toString());
                post.setMiddle_name(soapPost.getProperty("middle_name").toString());
                post.setLast_name(soapPost.getProperty("last_name").toString());
                post.setReactionCount(getReactionCount(Long.valueOf(soapPost.getProperty("post_id").toString())));

                posts.add(post);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return posts;
    }
    @Override
    protected void onPreExecute() {
        Activity a = mWeakActivity.get();
        if(a != null) {
            pd.setIndeterminate(true);
            pd.setMessage("Loading post...");
            pd.setCancelable(false);
            pd.show();
        }
    }

    @Override
    protected void onPostExecute(List<Post> posts) {
        Activity a = mWeakActivity.get();

        if(a != null) {
            System.out.println("onPostExecute Feed: " + posts.size());

            final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) a.findViewById(R.id.swiperefresh);
            ListView feed = (ListView) a.findViewById(R.id.list);

            final PostAdapter postAdapter = new PostAdapter(a, posts, this.user);
            feed.setAdapter(postAdapter);

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            postAdapter.notifyDataSetChanged();

                            if(mySwipeRefreshLayout.isRefreshing()) {
                                mySwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }, 3000);
        }
        pd.dismiss();
    }

    private Integer getReactionCount(Long postId) {
        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
        String URL = activity.getResources().getString(R.string.URL);
        String METHOD_NAME = "getReactions";   //Method
        String SOAP_ACTION = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();

        GetReactionRequest grr = new GetReactionRequest(postId);
        pi.setName("GetReactionRequest");
        pi.setValue(grr);
        pi.setType(GetReactionRequest.class);

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
            int propertyCount = response.getPropertyCount();
            System.out.println("Property Count: " + propertyCount);
            return propertyCount;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


}
