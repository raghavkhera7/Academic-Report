package com.intel.gjust;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.intel.gjust.fragments.AssignmentFragment;
import com.intel.gjust.fragments.ChangePasswordFragment;
import com.intel.gjust.fragments.MarksFragment;
import com.intel.gjust.fragments.NewsFeedFragment;
import com.intel.gjust.fragments.ProfileFragment;
import com.intel.gjust.fragments.TimeTableFragment;
import com.intel.gjust.utils.Constants;
import com.intel.gjust.utils.SharedPref;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
sharedPref = new SharedPref(this,getSharedPreferences(Constants.PerfName,MODE_PRIVATE));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView studentName = (TextView)header.findViewById(R.id.student_name);
        TextView studentRollno = (TextView)header.findViewById(R.id.student_rollno);
        studentName.setText(sharedPref.getStudentName());
        studentRollno.setText(String.valueOf(sharedPref.getStudentRollNo()));
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fragment = new ProfileFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_frame_layout,fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = new ProfileFragment();
if (id == R.id.nav_home){
    fragment = new ProfileFragment();
}
else if(id == R.id.nav_assignment){
fragment = new AssignmentFragment();
}
else if(id == R.id.nav_news_feed){
    fragment = new NewsFeedFragment();
}
else if(id == R.id.nav_faculty_mess){
fragment = new NewsFeedFragment();
}
else if(id == R.id.nav_time_table){
fragment = new TimeTableFragment();
}
else if(id == R.id.nav_marks){
fragment = new MarksFragment();
}
else if(id == R.id.nav_change_password){
fragment = new ChangePasswordFragment();
}
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_frame_layout,fragment);
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
