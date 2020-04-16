package com.students.preparation.matric.exam;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenService {

    public static void setAdminToken(Context context,String token){
        SharedPreferences preferences = context.getSharedPreferences("adminToken",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public static String getAdminToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences("adminToken",0);
        return  preferences.getString("token",null);
    }

    public static void adminLogout(Context context){
        SharedPreferences preferences =context.getSharedPreferences("adminToken",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
