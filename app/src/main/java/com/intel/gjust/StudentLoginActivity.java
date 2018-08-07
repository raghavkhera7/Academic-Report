package com.intel.gjust;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intel.gjust.models.User;
import com.intel.gjust.utils.Constants;
import com.intel.gjust.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentLoginActivity extends AppCompatActivity {
    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.error_text)
    TextView errortext;
    private View mProgressView;
    private View mLoginFormView;
    private static final String TAG = "signInActivity";
    private static final int RC_SIGN_IN = 90010;
    private DatabaseReference databaseReference;
    private SharedPref  sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        sharedPref = new SharedPref(this,getSharedPreferences(Constants.PerfName,MODE_PRIVATE));

    }
    @OnClick({R.id.email_sign_in_button,R.id.email_sign_up_button})
    public void onclick(View view){
        long id = view.getId();
        if (R.id.email_sign_up_button == id){
            startActivity(new Intent(StudentLoginActivity.this,LoginActivity.class));
        }
        else if(R.id.email_sign_in_button == id ){
            attemptLogin();
        }

    }
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        errortext.setText("");

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            signin(email,password);
            //send email and password for auth

        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() == 8;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in

    }
    private void signin(String rollno, final String password){
        DatabaseReference ref = databaseReference.child(rollno);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.getValue() != null){
                for (DataSnapshot ss : dataSnapshot.getChildren()) {
                    System.out.println(ss.getKey());
                    System.out.println(ss.getValue().getClass().getName());
                }

                    User post = dataSnapshot.getValue(User.class);
                    if (post.Password.equals(password)){
                        sharedPref.putStudent(post);
                        sharedPref.setLoggedIn();
                        Toast.makeText(StudentLoginActivity.this, post.Name+" ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StudentLoginActivity.this,MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(StudentLoginActivity.this, "Password is not correct", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(StudentLoginActivity.this, "You Are not Registred", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(StudentLoginActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        ref.addValueEventListener(postListener);
    }

}
