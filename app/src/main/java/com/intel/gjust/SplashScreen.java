package com.intel.gjust;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.intel.gjust.teacher.TeacherMain;
import com.intel.gjust.utils.SharedPref;

import static com.intel.gjust.utils.Constants.PerfName;


/**
 * Created by nikhil on 09-01-2018.
 */

public class SplashScreen extends AppCompatActivity {

    SharedPref sh ;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        sh = new SharedPref(this,getSharedPreferences(PerfName, Context.MODE_PRIVATE));
        sh.setFirstRun();
        if(sh.isLoggedin()){
            //true then mainactivity
            if (sh.isTeacher())
                startActivity(new Intent(this,TeacherMain.class));
            else
                startActivity(new Intent(this,MainActivity.class));

            finish();
        }
        else {
            startActivity(new Intent (this,AppIntroActivity.class));
            finish();

        }
    }
}
