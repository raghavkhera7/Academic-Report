package com.intel.gjust;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppIntroActivity extends AppCompatActivity {
@BindView(R.id.sign_in_btn)
    Button signInButton;
@BindView(R.id.sign_up_btn)
Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);
        ButterKnife.bind(this);

    }
    @OnClick({R.id.sign_up_btn,R.id.sign_in_btn})
    public void onclick(View view){
        if (R.id.sign_in_btn == view.getId()){
            startActivity(new Intent(AppIntroActivity.this,LoginActivity.class));
            finish();
        }
        else if(R.id.sign_up_btn == view.getId()) {
            startActivity(new Intent(AppIntroActivity.this,StudentLoginActivity.class));
        }
    }
}
