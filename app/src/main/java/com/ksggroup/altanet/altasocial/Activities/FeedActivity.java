package com.ksggroup.altanet.altasocial.Activities;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedActivity extends AppCompatActivity {

    PostAdapter postAdapter;
    List<Post> posts;

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

        posts = new ArrayList<Post>();
        postAdapter = new PostAdapter(this, posts);
        feed.setAdapter(postAdapter);

        try {
            List<Post> newPost = (new FeedAsync(getBaseContext())).execute(new GetPostRequest(user.getUser_id())).get();
            postAdapter.getPosts().clear();
            postAdapter.getPosts().addAll(newPost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        postAdapter.notifyDataSetChanged();
                    }
                }, 3000);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textPost.getText().toString().isEmpty()) {
                    Toast.makeText(FeedActivity.this, "Nothing to post!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog pd = new ProgressDialog(FeedActivity.this);

                btnPost.setEnabled(false);
                pd.setIndeterminate(true);
                pd.setMessage("Posting...");
                pd.setCancelable(false);
                pd.show();

                AddPostAsync apa = new AddPostAsync(getBaseContext());
                Integer insertedRows = 0;
                try {
                    insertedRows = apa.execute(new InsertPostRequest(user.getUser_id(), textPost.getText().toString())).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                final Integer finalInsertedRows = insertedRows;
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                pd.dismiss();

                                if(finalInsertedRows > 0) {
                                    try {
                                        List<Post> newPost = (new FeedAsync(getBaseContext())).execute(new GetPostRequest(user.getUser_id())).get();
                                        postAdapter.getPosts().clear();
                                        postAdapter.getPosts().addAll(newPost);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }

                                    new android.os.Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    postAdapter.notifyDataSetChanged();
                                                }
                                            }, 3000);
                                } else {
                                    Toast.makeText(FeedActivity.this, "Failed to post!", Toast.LENGTH_SHORT).show();
                                }
                                textPost.setText("");
                                btnPost.setEnabled(true);
                            }
                        }, 3000);
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        try {
                            List<Post> newPost = (new FeedAsync(getBaseContext())).execute(new GetPostRequest(user.getUser_id())).get();
                            postAdapter.getPosts().clear();
                            postAdapter.getPosts().addAll(newPost);

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            postAdapter.notifyDataSetChanged();
                                            mySwipeRefreshLayout.setRefreshing(false);
                                        }
                                    }, 3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
