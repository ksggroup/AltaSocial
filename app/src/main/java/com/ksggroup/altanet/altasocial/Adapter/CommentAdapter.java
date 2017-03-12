package com.ksggroup.altanet.altasocial.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ksggroup.altanet.altasocial.Model.Comment;
import com.ksggroup.altanet.altasocial.R;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Activity activity;
    private List<Comment> comments;
    private LayoutInflater inflater;

    public CommentAdapter(Activity activity, List<Comment> Comments) {
        this.activity = activity;
        this.comments = Comments;
    }
    public List<Comment> getComments() {
        return this.comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return comments.get(position).getComment_id();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.comment_item, null);

        PrettyTime p = new PrettyTime();
        TextView commentName = (TextView) convertView.findViewById(R.id.commentName);
        TextView commentTimestamp = (TextView) convertView.findViewById(R.id.commentTimestamp);
        TextView commentTxt = (TextView) convertView.findViewById(R.id.commentTxt);

        Comment comment = comments.get(position);

        commentName.setText(comment.getFirst_name() + " " + comment.getMiddle_name() + " " + comment.getLast_name());
        try {
            commentTimestamp.setText(p.format(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(comment.getDate_time().toString())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        commentTxt.setText(comment.getContent());

        return convertView;
    }
}
