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

    public static void setTeachersToken(Context context,String token){
        SharedPreferences preferences = context.getSharedPreferences("teacherToken",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public static String getTeachersToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences("teacherToken",0);
        return  preferences.getString("token",null);
    }

    public static void teachersLogout(Context context){
        SharedPreferences preferences =context.getSharedPreferences("teacherToken",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }


    public static void setPracticeDate(Context context,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences("practiceDate",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
        editor.apply();

    }

    public static String getPracticeDate(Context context,String key){
        SharedPreferences preferences = context.getSharedPreferences("practiceDate",0);
        return  preferences.getString(key,null);
    }

    public static void writeExamTest(Context context,String fileName,String key,int value){
        SharedPreferences preferences = context.getSharedPreferences(fileName,0);

        int examResult = getExamResult(context,fileName,key);
        if (examResult<=0){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key,value);
            editor.commit();
        }else {
            int result = examResult += value;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key,result);
            editor.commit();
        }

    }

    public static int getExamResult(Context context,String fileName,String key){
        SharedPreferences preferences = context.getSharedPreferences(fileName,0);
        return  preferences.getInt(key,0);
    }

    public static void clearExamResult(Context context,String fileName,String key){
        SharedPreferences preferences = context.getSharedPreferences(fileName,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,0);
        editor.commit();
    }

}
