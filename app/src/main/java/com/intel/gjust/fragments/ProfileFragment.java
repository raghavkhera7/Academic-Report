package com.intel.gjust.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intel.gjust.R;
import com.intel.gjust.utils.Constants;
import com.intel.gjust.utils.SharedPref;

import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

@BindView(R.id.profile_name)
    TextView name;
@BindView(R.id.profile_email)
TextView email;
@BindView(R.id.profile_rollno)
TextView rollno;
@BindView(R.id.profile_phone)
TextView phone;
@BindView(R.id.profile_sex)
TextView sex;
@BindView(R.id.profile_mother_name)
TextView mother_name;
@BindView(R.id.father_name)
    TextView father_name;
private SharedPref sharedPref;
    public ProfileFragment() {
        // Required empty public constructor
    }

@Override
public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        sharedPref = new SharedPref(getActivity(),getActivity().getSharedPreferences(Constants.PerfName, Context.MODE_PRIVATE));
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View viwe= inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,viwe);
        setView();
        return  viwe;
    }
    private void setView(){
        name.setText(sharedPref.getStudentName());
        email.setText(sharedPref.getStudentEmail());
        rollno.setText(String.valueOf(sharedPref.getStudentRollNo()));
        sex.setText(sharedPref.getStudentSEx());
        mother_name.setText(sharedPref.getStudentMname());
        father_name.setText(sharedPref.getStudentFname());
        phone.setText(String.valueOf(sharedPref.getStudentPhone()));

    }

}
