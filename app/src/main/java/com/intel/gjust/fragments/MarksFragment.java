package com.intel.gjust.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intel.gjust.MainActivity;
import com.intel.gjust.R;
import com.intel.gjust.StudentLoginActivity;
import com.intel.gjust.models.User;
import com.intel.gjust.utils.Constants;
import com.intel.gjust.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarksFragment extends Fragment {

@BindView(R.id.spinner_choose_sem)
    Spinner semChooser;
@BindView(R.id.minor_marks_view)
    TextView minormarksText;
private List<String> spinnerEntry = new ArrayList<>();
private List<String> spinerkeyEntry = new ArrayList<>();
private int pos = 0;
private SharedPref sharedPref;
private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
            public MarksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        sharedPref = new SharedPref(getActivity(),getActivity().getSharedPreferences(Constants.PerfName, Context.MODE_PRIVATE));
    databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(String.valueOf(sharedPref.getStudentRollNo()))
            .child("marks");
    progressDialog = new ProgressDialog(getContext());
    progressDialog.setMessage("Fetching...");
            }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_marks, container, false);
        ButterKnife.bind(this,view);
        addData();
        setHome();
       return view;
    }
@OnClick(R.id.button_major)
public void onclick(){
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.MAJOR_MARKS_URL));
                startActivity(i);
}
    private void setHome(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_expandable_list_item_1,spinnerEntry);
        semChooser.setAdapter(arrayAdapter);
        semChooser.setSelection(2);
        semChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             pos =   position;
             fetchData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
    }
    private void fetchData(){
                progressDialog.show();
        DatabaseReference ref = databaseReference.child(spinerkeyEntry.get(pos));
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.getValue() != null){
                    StringBuilder sb = new StringBuilder();
                    for (DataSnapshot ss : dataSnapshot.getChildren()) {
                        sb.append(ss.getKey());
                        sb.append(" : ");
                        sb.append(ss.getValue());
                        sb.append(" \n");
                    }
minormarksText.setText(sb.toString());
progressDialog.dismiss();
                }
                else {
                    Toast.makeText(getActivity(), "You Are not Registred", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Mai", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        ref.addValueEventListener(postListener);
    }
    private void addData(){
                spinnerEntry.add("1st Semester");
                spinerkeyEntry.add("sem1");
                spinnerEntry.add("2nd Semester");
                spinerkeyEntry.add("sem2");
                spinerkeyEntry.add("sem8");
                spinnerEntry.add("8th Semester");
    }
}
