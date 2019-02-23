package com.panda.keeplive;

import android.app.Application;
import android.content.Context;

/**
 * Created by Zc on 2018/5/22.
 */
public class MyApplicaiton extends Application {
    private static Context context;
    private double longitude=0;
    private double latitude=0;


    private static MyApplicaiton instance;

    public static MyApplicaiton getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        instance=this;
    }

}
