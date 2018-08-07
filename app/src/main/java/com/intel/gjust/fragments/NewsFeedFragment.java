package com.intel.gjust.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.intel.gjust.R;
import com.intel.gjust.adapter.NotificationsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {

    @BindView(R.id.notification_rv)
    RecyclerView notificationRv;
    private DatabaseReference databaseReference;
    private static String TAG = "notifications";
    private NotificationsAdapter adapter;
private ProgressDialog progressDialog;
    public NewsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("notifications");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        ButterKnife.bind(this,view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        notificationRv.setLayoutManager(layoutManager);
        adapter = new NotificationsAdapter(getActivity(),databaseReference);
        notificationRv.setAdapter(adapter);
        return view;
    }

}
