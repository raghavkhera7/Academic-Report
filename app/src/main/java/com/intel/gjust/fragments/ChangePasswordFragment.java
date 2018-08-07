package com.intel.gjust.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.intel.gjust.R;
import com.intel.gjust.utils.Constants;
import com.intel.gjust.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {
@BindView(R.id.old_password)
    EditText oldPassword;
@BindView(R.id.new_password)
EditText newPassword;
@BindView(R.id.confirm_password)
EditText confirmPassword;
@BindView(R.id.confirm)
    Button changeConfirm;
    private SharedPref sharedPref;

    public ChangePasswordFragment() {
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
       View view =  inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this,view);

        return view;
    }
    @OnClick(R.id.confirm)
    public void seonclick(){
        setHome();
    }
    private void setHome(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users")
                .child(String.valueOf(sharedPref.getStudentRollNo()))
                .child("Password");
        String oldpassword = oldPassword.getText().toString();
        String newpasword= newPassword.getText().toString();
        if (oldpassword.equals(sharedPref.getPassword())){
            mDatabase.setValue(newpasword);
            Toast.makeText(getContext(), "Changed Succesfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Old password doest bnot match", Toast.LENGTH_SHORT).show();
        }
    }


}
