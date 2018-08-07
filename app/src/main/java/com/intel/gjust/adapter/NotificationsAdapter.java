package com.intel.gjust.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.intel.gjust.R;
import com.intel.gjust.models.Notifications;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public  class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    List<String> notiIDS = new ArrayList<>();
    List<Notifications> notifications = new ArrayList<>();
    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private static String TAG = "ProfilADapter";
    private ProgressDialog progressDialog ;
    public NotificationsAdapter(Context context, DatabaseReference reference){
        this.mContext = context;
        this.mDatabaseReference = reference;
        this.progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Notifications comment = dataSnapshot.getValue(Notifications.class);
                notifications.add(comment);
                notiIDS.add(dataSnapshot.getKey());
                notifyItemInserted(notifications.size() - 1);
                progressDialog.dismiss();
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Notifications newComment = dataSnapshot.getValue(Notifications.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Notifications movedComment = dataSnapshot.getValue(Notifications.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postNotificatiions:onCancelled", databaseError.toException());
                Toast.makeText(mContext, "Failed to load Notification.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        reference.addChildEventListener(mChildEventListener);
    }
    public static class  NotificationViewHolder extends RecyclerView.ViewHolder{
       @BindView(R.id.notification_text)
        TextView notificationMessage;
       @BindView(R.id.notification_time)
       TextView notifyTime;
        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_row_rv,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
      holder.notificationMessage.setText(notifications.get(position).msg);
      holder.notifyTime.setText(notifications.get(position).title);

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void cleanupListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }
    }
}
