package com.example.happylearning.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.happylearning.Bean.User;

/**
 *
 * 存储登录的用户信息
 */

public class SharedPreferenceUtil {

    private static final String PATH = "user" ;
    private String uid ;
    private String pwd ;

    public SharedPreferenceUtil(){}

    //存储用户信息
    public void saveUser(Context context , String uid , String pwd ){

        //获取SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences(PATH, Activity.MODE_PRIVATE) ;
        //获取Editor对象
        SharedPreferences.Editor editor = sp.edit() ;
        //写入数据
        editor.putString("uid" , uid ) ;
        editor.putString("pwd" , pwd ) ;
        editor.apply() ;

    }

    //返回用户信息
    public User sendUser(Context context){

        SharedPreferences sp = context.getSharedPreferences(PATH , Activity.MODE_PRIVATE) ;
        User user = new User() ;
        user.setUid( sp.getString("uid" , "")) ;
        user.setPwd( sp.getString("pwd" , "")) ;
        return user ;

    }

    //用户退出，清空数据
    public void clearSharedPrf( Context context ){

        SharedPreferences sp = context.getSharedPreferences(PATH , Activity.MODE_PRIVATE ) ;
        SharedPreferences.Editor editor = sp.edit() ;
        editor.clear() ;
        editor.apply() ;

    }
}
