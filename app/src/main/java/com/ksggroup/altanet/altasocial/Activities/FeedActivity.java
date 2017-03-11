package com.ksggroup.altanet.altasocial.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ksggroup.altanet.altasocial.Adapter.PostAdapter;
import com.ksggroup.altanet.altasocial.Model.GetPostRequest;
import com.ksggroup.altanet.altasocial.Model.InsertPostRequest;
import com.ksggroup.altanet.altasocial.Model.Post;
import com.ksggroup.altanet.altasocial.R;
import com.ksggroup.altanet.altasocial.Model.User;
import com.ksggroup.altanet.altasocial.Soap.AddPostAsync;
import com.ksggroup.altanet.altasocial.Soap.FeedAsync;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);

        final User user = getIntent().getExtras().getParcelable("AuthenticatedUser");
        new FeedAsync(this).execute(new GetPostRequest(user.getUser_id()));

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textPost.getText().toString().isEmpty()) {
                    Toast.makeText(FeedActivity.this, "Nothing to post!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new AddPostAsync(FeedActivity.this).execute(new InsertPostRequest(user.getUser_id(), textPost.getText().toString()));
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new FeedAsync(FeedActivity.this).execute(new GetPostRequest(user.getUser_id()));
                    }
                }
        );
    }





    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
