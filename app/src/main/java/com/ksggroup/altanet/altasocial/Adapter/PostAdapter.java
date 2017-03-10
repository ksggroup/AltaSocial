package com.ksggroup.altanet.altasocial.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ksggroup.altanet.altasocial.Model.Post;
import com.ksggroup.altanet.altasocial.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by vfiguracion on 3/10/17.
 */

public class PostAdapter extends BaseAdapter {
    private Activity activity;
    private List<Post> posts;
    private LayoutInflater inflater;

    public PostAdapter(Activity activity, List<Post> posts) {
        this.activity = activity;
        this.posts = posts;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);

        TextView content = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        TextView name = (TextView) convertView.findViewById(R.id.profile_name);

        Post post = posts.get(position);

        content.setText(post.getContent());
        name.setText(post.getFirst_name() + " " + post.getMiddle_name() + " " + post.getLast_name());

        return convertView;
    }
}
