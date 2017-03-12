package com.ksggroup.altanet.altasocial.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ksggroup.altanet.altasocial.Adapter.CommentAdapter;
import com.ksggroup.altanet.altasocial.Adapter.PostAdapter;
import com.ksggroup.altanet.altasocial.Adapter.SearchAdapter;
import com.ksggroup.altanet.altasocial.Model.Comment;
import com.ksggroup.altanet.altasocial.Model.DeletePostRequest;
import com.ksggroup.altanet.altasocial.Model.GetCommentsRequest;
import com.ksggroup.altanet.altasocial.Model.GetPostRequest;
import com.ksggroup.altanet.altasocial.Model.InsertPostRequest;
import com.ksggroup.altanet.altasocial.Model.Post;
import com.ksggroup.altanet.altasocial.Model.SearchUserReq;
import com.ksggroup.altanet.altasocial.R;
import com.ksggroup.altanet.altasocial.Model.User;
import com.ksggroup.altanet.altasocial.Soap.AddPostAsync;
import com.ksggroup.altanet.altasocial.Soap.DownloadImageTask;
import com.ksggroup.altanet.altasocial.Soap.FeedAsync;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedActivity extends AppCompatActivity {
    private ProgressDialog pdCreate;
    private WeakReference<Activity> mWeakActivity;
    private Activity activity;
    @BindView(R.id.list) ListView feed;
    @BindView(R.id.text_post) EditText textPost;
    @BindView(R.id.btn_post) Button btnPost;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout mySwipeRefreshLayout;
    @BindView(R.id.userId) TextView userId;
    @BindView(R.id.empty) TextView emptyTxt;
    @BindView(R.id.profile_picture) ImageView profilePic;
    @BindView(R.id.addBtn) ImageView addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);

        final User user = getIntent().getExtras().getParcelable("AuthenticatedUser");
        userId.setText(user.getUser_id().toString());
        if(user.getProfilePic() != null) {
            new DownloadImageTask(profilePic).execute(user.getProfilePic().toString());
        }

        feed.setEmptyView(emptyTxt);

        new FeedAsync(this, user).execute(new GetPostRequest(user.getUser_id()));

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textPost.getText().toString().isEmpty()) {
                    Toast.makeText(FeedActivity.this, "Nothing to post!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new AddPostAsync(FeedActivity.this, user).execute(new InsertPostRequest(user.getUser_id(), textPost.getText().toString()));
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new FeedAsync(FeedActivity.this, user).execute(new GetPostRequest(user.getUser_id()));
                    }
                }
        );


        final Activity activity = this;
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);

                dialog.setContentView(R.layout.search_popup);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                final TextView searchBox = (TextView) dialog.findViewById(R.id.searchBox);
                final ListView searchList = (ListView) dialog.findViewById(R.id.searchList);
                Button searchBtn = (Button) dialog. findViewById(R.id.searchBtn);


                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new GetResultAsync(activity,searchList, user).execute(searchBox.getText().toString());

                    }
                });

                dialog.show();


            }

        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private class GetResultAsync extends AsyncTask<String, Void, List<User>> {

        private ProgressDialog pd;
        private List<User> users;
        private Activity activity;
        private ListView searchList;
        private User user;

        public GetResultAsync(Activity activity, ListView searchList, User user) {
            this.activity = activity;
            this.searchList = searchList;
            this.user = user;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
            pd.setIndeterminate(true);
            pd.setMessage("Searching...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected List<User> doInBackground(String... params) {
            String NAMESPACE = getResources().getString(R.string.NAMESPACE);
            String URL = getResources().getString(R.string.URL);
            String METHOD_NAME = "searchUser";   //Method
            String SOAP_ACTION = "";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo pi = new PropertyInfo();

            pi.setName("SearchUserRequest");
            pi.setValue(new SearchUserReq(params[0].toString()));
            pi.setType(SearchUserReq.class);

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
                users = new ArrayList<User>();

                int propertyCount = response.getPropertyCount();
                System.out.println("Property Count: " + propertyCount);

                for(int ctr = 0; ctr < propertyCount; ctr++) {

                    System.out.println("Inside loop...");
                    SoapObject soapPost = (SoapObject)response.getProperty(ctr);
                    User user = new User();

                    user.setUser_id(Long.valueOf(soapPost.getProperty("user_id").toString()));
                    user.setFirst_name(soapPost.getProperty("first_name").toString());
                    user.setMiddle_name(soapPost.getProperty("middle_name").toString());
                    user.setLast_name(soapPost.getProperty("last_name").toString());
                    user.setProfilePic(soapPost.getProperty("profile_pic").toString());

                    users.add(user);
                }
                System.out.println("after loop...");

            } catch(Exception e) {
                e.printStackTrace();
            }

            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            pd.dismiss();
            //searchList
            System.out.println("onPostExecute...");

            final SearchAdapter searchAdapter = new SearchAdapter(activity, users, user);
            searchList.setAdapter(searchAdapter);
            searchAdapter.notifyDataSetChanged();


        }
    }//.execute(searchBox.getText().toString());

}
