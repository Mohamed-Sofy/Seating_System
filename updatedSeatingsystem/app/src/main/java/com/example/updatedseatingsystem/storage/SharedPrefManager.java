package com.example.updatedseatingsystem.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {


    private static final String MY_SHARED_PREF = "my_shared_pref";
    private static SharedPrefManager mInstance;
    private Context mContext;

    private SharedPrefManager(Context context)
    {
        this.mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){

        if(mInstance==null)
        {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public void saveManagerId(String manager_id){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("manager_id",manager_id);
        editor.apply();
    }

    public void saveManagerName(String manager_name){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("manager_name",manager_name);
        editor.apply();
    }

    public void saveAdminId(int id){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("admin_id",id);
        editor.apply();
    }

    public boolean isLoggedInManager(){

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREF,Context.MODE_PRIVATE);

        return sharedPreferences.getString("manager_id",null)!=null;

    }

    public boolean isLoggedInAdmin(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("admin_id",-1)!=-1;

    }

    public String getManagerId(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREF,Context.MODE_PRIVATE);

               return sharedPreferences.getString("manager_id",null);

    }

    public String getManagerName(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREF,Context.MODE_PRIVATE);

        return sharedPreferences.getString("manager_name",null);

    }


    // to Log out
    public void clear()
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
