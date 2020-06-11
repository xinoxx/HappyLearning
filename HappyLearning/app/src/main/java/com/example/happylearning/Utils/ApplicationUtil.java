package com.example.happylearning.Utils;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * ApplicationUtil维护所有的activity
 * 目的：退出功能的实现
 */

public class ApplicationUtil extends Application {

    private List<Activity> aList = new LinkedList<Activity>() ;
    private static ApplicationUtil instance ;

    public ApplicationUtil(){}

    public synchronized static ApplicationUtil getInstance(){
        if( instance == null ){
            instance = new ApplicationUtil();
        }
        return instance ;
    }

    //添加Activity到列表中
    public void addActivity(Activity activity){
        aList.add(activity) ;
    }

    //退出
    public void exit(){
        try{
            for( Activity activity: aList){
                if( activity != null ){
                    activity.finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.exit(0);
        }
    }

}
