package com.ksggroup.altanet.altasocial.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ksggroup.altanet.altasocial.Activities.FeedActivity;
import com.ksggroup.altanet.altasocial.Model.DeletePostRequest;
import com.ksggroup.altanet.altasocial.Model.GetPostRequest;
import com.ksggroup.altanet.altasocial.Model.GetReactionRequest;
import com.ksggroup.altanet.altasocial.Model.InsertCommentsReq;
import com.ksggroup.altanet.altasocial.Model.InsertReactionReq;
import com.ksggroup.altanet.altasocial.Model.Post;
import com.ksggroup.altanet.altasocial.Model.User;
import com.ksggroup.altanet.altasocial.R;
import com.ksggroup.altanet.altasocial.Soap.AddCommentAsync;
import com.ksggroup.altanet.altasocial.Soap.FeedAsync;
import com.ksggroup.altanet.altasocial.Soap.GetCommentAsync;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PostAdapter extends BaseAdapter {
    private Activity activity;
    private List<Post> posts;
    private LayoutInflater inflater;
    private User user;

    public PostAdapter(Activity activity, List<Post> posts, User user) {
        this.activity = activity;
        this.posts = posts;
        this.user = user;
    }
    public List<Post> getPosts() {
        return this.posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return posts.get(position).getPost_id();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);

        PrettyTime p = new PrettyTime();
        final TextView postId = (TextView) convertView.findViewById(R.id.postId);
        TextView content = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        TextView name = (TextView) convertView.findViewById(R.id.profile_name);
        TextView timestamp = (TextView) convertView.findViewById(R.id.profile_timestamp);
        final TextView comment = (TextView) convertView.findViewById(R.id.txtComment);
        final TextView reaction = (TextView) convertView.findViewById(R.id.txtReactions);
        TextView deleteTxt =  (TextView) convertView.findViewById(R.id.txtDelete);

        final Post post = posts.get(position);

        if(post.getUser_id() != user.getUser_id()) {
            deleteTxt.setVisibility(View.INVISIBLE);
        }

        postId.setText(String.valueOf(post.getPost_id()));
        content.setText(post.getContent());
        name.setText(post.getFirst_name() + " " + post.getMiddle_name() + " " + post.getLast_name());
        try {
            timestamp.setText(p.format(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(post.getDatetime().toString())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        reaction.setText(post.getReactionCount() + " Likes");

        reaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Long, Void, Integer>() {

                    ProgressDialog pd;
                    @Override
                    protected void onPreExecute() {
                        reaction.setEnabled(false);
                    }

                    @Override
                    protected Integer doInBackground(Long... params) {

                        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
                        String URL = activity.getResources().getString(R.string.URL);
                        String METHOD_NAME = "insertReaction";   //Method
                        String SOAP_ACTION = "";


                        List<Post> posts = null;
                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                        PropertyInfo pi = new PropertyInfo();

                        InsertReactionReq irr = new InsertReactionReq(params[0].longValue(), user.getUser_id(), 0L);
                        pi.setName("InsertReactionRequest");
                        pi.setValue(irr);
                        pi.setType(InsertReactionReq.class);

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
                    protected void onPostExecute(final Integer insertRows) {
                        reaction.setText((Integer.valueOf(reaction.getText().toString().split(" ")[0])
                                + insertRows) + " Likes");
                        reaction.setEnabled(true);
                    }
                }.execute(post.getPost_id());
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);

                dialog.setContentView(R.layout.comment_popup);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                PrettyTime p = new PrettyTime();
                TextView content = (TextView) dialog.findViewById(R.id.txtStatusMsg);
                TextView name = (TextView) dialog.findViewById(R.id.profile_name);
                TextView timestamp = (TextView) dialog.findViewById(R.id.profile_timestamp);
                TextView emptyComment = (TextView) dialog.findViewById(R.id.empty);
                final EditText commentBox = (EditText) dialog.findViewById(R.id.commentBox);
                final Button commentBtn = (Button) dialog.findViewById(R.id.commentBtn);
                final ListView commentList = (ListView)  dialog.findViewById(R.id.commentList);

                commentList.setEmptyView(emptyComment);

                Post post = posts.get(position);
                content.setText(post.getContent());
                name.setText(post.getFirst_name() + " " + post.getMiddle_name() + " " + post.getLast_name());

                try {
                    timestamp.setText(p.format(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(post.getDatetime().toString())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                commentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(commentBox.getText().toString().isEmpty()) {
                            Toast.makeText(activity, "Comment is empty!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        TextView t = (TextView) activity.findViewById(R.id.userId);
                        new AddCommentAsync(activity, commentList, commentBtn, user)
                                .execute(new InsertCommentsReq(Long.valueOf(t.getText().toString()),
                                        Long.valueOf(postId.getText().toString()),
                                        commentBox.getText().toString(),
                                        user.getFirst_name(),
                                        user.getMiddle_name(),
                                        user.getLast_name()
                                        ));
                        commentBox.setText("");
                    }
                });

                new GetCommentAsync(activity, commentList).execute(Long.valueOf(postId.getText().toString()));

                dialog.show();
            }
        });

        deleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete post?")
                        .setMessage("Do you want to delete this post?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                new AsyncTask<Long, Void, Integer>() {

                                    ProgressDialog pd;
                                    @Override
                                    protected void onPreExecute() {
                                        pd = new ProgressDialog(activity);
                                        pd.setIndeterminate(true);
                                        pd.setMessage("Deleting...");
                                        pd.setCancelable(false);
                                        pd.show();
                                    }

                                    @Override
                                    protected Integer doInBackground(Long... params) {

                                        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
                                        String URL = activity.getResources().getString(R.string.URL);
                                        String METHOD_NAME = "deletePosts";   //Method
                                        String SOAP_ACTION = "";


                                        List<Post> posts = null;
                                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                                        PropertyInfo pi = new PropertyInfo();

                                        DeletePostRequest dpr = new DeletePostRequest(params[0].longValue());
                                        pi.setName("DeletePostRequest");
                                        pi.setValue(dpr);
                                        pi.setType(DeletePostRequest.class);

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
                                    protected void onPostExecute(final Integer insertRows) {
                                        pd.dismiss();
                                        new FeedAsync(activity, user).execute(new GetPostRequest(user.getUser_id()));
                                    }
                                }.execute(post.getPost_id());

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return convertView;
    }
}
