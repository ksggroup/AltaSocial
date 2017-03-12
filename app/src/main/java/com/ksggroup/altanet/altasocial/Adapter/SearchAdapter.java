package com.ksggroup.altanet.altasocial.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksggroup.altanet.altasocial.Model.Comment;
import com.ksggroup.altanet.altasocial.Model.DeletePostRequest;
import com.ksggroup.altanet.altasocial.Model.GetPostRequest;
import com.ksggroup.altanet.altasocial.Model.InsertConnectionsReq;
import com.ksggroup.altanet.altasocial.Model.Post;
import com.ksggroup.altanet.altasocial.Model.User;
import com.ksggroup.altanet.altasocial.R;
import com.ksggroup.altanet.altasocial.Soap.DownloadImageTask;
import com.ksggroup.altanet.altasocial.Soap.FeedAsync;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private Activity activity;
    private List<User> users;
    private LayoutInflater inflater;
    private User currentUser;

    public SearchAdapter(Activity activity, List<User> users, User currentUser) {
        this.activity = activity;
        this.users = users;
        this.currentUser = currentUser;
    }
    public List<User> getUsers() {
        return this.users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getUser_id();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.search_item, null);

        ImageView profilePic = (ImageView) convertView.findViewById(R.id.searchPicture);
        TextView searchName = (TextView) convertView.findViewById(R.id.searchName);
        Button searchAddBtn = (Button) convertView.findViewById(R.id.searchAddBtn);
        final TextView searchUserId = (TextView) convertView.findViewById(R.id.searchUserId);

        searchUserId.setText(users.get(position).getUser_id().toString());
        searchName.setText(users.get(position).getFirst_name() + " " +
                users.get(position).getMiddle_name()  + " " +
                users.get(position).getLast_name());

        if(users.get(position).getProfilePic() != null) {
            new DownloadImageTask(profilePic).execute(users.get(position).getProfilePic());
        }

        searchAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<InsertConnectionsReq, Void, Integer>() {

                    ProgressDialog pd;
                    @Override
                    protected void onPreExecute() {
                        pd = new ProgressDialog(activity);
                        pd.setIndeterminate(true);
                        pd.setMessage("Connecting...");
                        pd.setCancelable(false);
                        pd.show();
                    }

                    @Override
                    protected Integer doInBackground(InsertConnectionsReq... params) {

                        String NAMESPACE = activity.getResources().getString(R.string.NAMESPACE);
                        String URL = activity.getResources().getString(R.string.URL);
                        String METHOD_NAME = "insertConnections";   //Method
                        String SOAP_ACTION = "";

                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                        PropertyInfo pi = new PropertyInfo();

                        InsertConnectionsReq icr = new InsertConnectionsReq(params[0].getProfile_id(), params[0].getUser_id());
                        pi.setName("InsertConnectionsRequest");
                        pi.setValue(icr);
                        pi.setType(InsertConnectionsReq.class);

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
                    }
                }.execute(new InsertConnectionsReq(Long.valueOf(searchUserId.getText().toString()),
                        currentUser.getUser_id()));
            }
        });

        return convertView;
    }
}
