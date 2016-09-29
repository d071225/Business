package com.yisheng.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DY on 2016/9/24.
 */
public class ActivityUitls {
    private static List<Activity> list= new ArrayList<>();
    public static void addActivity(Activity activity){
        list.add(activity);
    }
    public static void RemoveActivity(Activity activity){
        list.remove(activity);
    }
    public static void RemoveAllActivity(){
        for (Activity a:list) {
            if (!a.isFinishing()){
                a.finish();
            }
        }
    }

}
