package com.intel.gjust.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseUser;
import com.intel.gjust.models.User;

/**
 * Created by nikhil on 11-01-2018.
 */

public class SharedPref  {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor ;
    public SharedPref(Context context,SharedPreferences s) {
        this.context = context;
        this.sharedPreferences = s;
        this.editor = sharedPreferences.edit();
    }
    public void setFirstRun() {
         editor = sharedPreferences.edit();
        editor.putBoolean("isFirstRun",false);
        editor.apply();

    }
    public void setLoggedIn(){
        editor.putBoolean("isLoggedIn",true);
        editor.apply();
    }
    public void putUser(FirebaseUser userInfo) {
            editor.putString("name",userInfo.getDisplayName());
            editor.putString("email",userInfo.getEmail());
            editor.putString("providerID",userInfo.getProviderId());
            editor.putString("phone",userInfo.getPhoneNumber());
            editor.putString("avatar", String.valueOf(userInfo.getPhotoUrl()));
            editor.putString("id",userInfo.getUid());
            editor.apply();
    }
    public void putStudent(User ur){
        editor.putString("student_name",ur.User_name);
        editor.putString("student_email",ur.Email_id);
       // editor.putString("student_providerID",userInfo.getProviderId());
        editor.putLong("student_phone",ur.Phone_number);
        editor.putString("mother_name",ur.Mother_name);
        editor.putString("father_name",ur.Father_name);
        editor.putString("student_sex",ur.Sex);
        editor.putString("student_password",ur.Password);
        //editor.putString("student_avatar", String.valueOf(userInfo.getPhotoUrl()));
        editor.putLong("student_rollno",ur.Roll);
        editor.apply();
    }
    public String getPassword(){
        return sharedPreferences.getString("student_password","1234");
    }
    public String getStudentName(){
        return sharedPreferences.getString("student_name","Raghav");

    }
    public String getStudentEmail(){
        return sharedPreferences.getString("student_email","raghav@gmail.com");
    }
    public Long getStudentRollNo(){
        return sharedPreferences.getLong("student_rollno",14013068);
    }
    public String getStudentSEx(){
        return sharedPreferences.getString("student_sex","Male");

    }
    public String getStudentFname(){return sharedPreferences.getString("father_name","papa");}
    public String getStudentMname(){
        return sharedPreferences.getString("mother_name","mummy");
    }
    public Long getStudentPhone(){
        return sharedPreferences.getLong("student_phone",99224);
    }
    public void setLastGroupKey(String key){
        editor.putString("lastGroupKey",key);
        editor.commit();

    }
    public void setTeacher(){
        editor.putBoolean("isTeacher",true);
        editor.apply();
    }
    public Boolean isTeacher(){
      return   sharedPreferences.getBoolean("isTeacher",false);
    }
    public void setLastGroupPosition(int position){
        editor.putInt("lastGroupPosition",position);
        editor.commit();
    }
    public String getLastGroupKey(){
        return sharedPreferences.getString("lastGroupKey","all-posts");
    }
    public int getLastGroupPosition(){
        return sharedPreferences.getInt("lastGroupPosition",0);
    }
    public void setAvatar(String a){
        editor.putString("avatar",a);
        editor.commit();
    }
    public void setlogOut(){
        editor.putBoolean("isLoggedIn",false);
        editor.clear();
        editor.apply();
    }
    public void setName(String name){
        editor.putString("name",name);
        editor.commit();
    }
   public void setCustomText(String text){
        editor.putString("custom_text",text);
        editor.commit();
   }
   public void setCurLoc(String text){
       editor.putString("current_location",text);
       editor.commit();
   }
   public void setLat(String lat){
       editor.putString("latitude",lat);
       editor.commit();
   }
   public void setLong(String lng){
       editor.putString("longitude",lng
       );
       editor.commit();
   }
   public void setLocHelp() {
       editor.putBoolean("setLocHelpFirst",false);
       editor.commit();
   }
   public Boolean getSetLocHelp(){
       return sharedPreferences.getBoolean("setLocHelpFirst",true);
   }
    public String getEmail(){return sharedPreferences.getString("email","email not Found");}
    public String getName() {return sharedPreferences.getString("name","Name not found");}
    public String getAvatar() {return sharedPreferences.getString("avatar", Constants.DefaultAvatar);}
    public Boolean getFirstRun() {
        return sharedPreferences.getBoolean("isFirstRun",true);
    }
    public boolean isLoggedin(){
        return sharedPreferences.getBoolean("isLoggedIn",false);
    }
    public String getClientID(){return sharedPreferences.getString("id","0");}
    public String getPhone() {return sharedPreferences.getString("phone","0");}
    public String getCustomText() {return sharedPreferences.getString("custom_text","");}
    public String getCurLoc(){return sharedPreferences.getString("current_location","Not Found");}
    public String getLat() {return sharedPreferences.getString("latitude",null);}
    public String getLon(){return sharedPreferences.getString("longitude",null);}
}
