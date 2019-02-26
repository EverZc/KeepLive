package com.panda.keeplive;

import android.app.Application;
import android.content.Context;

/**
 * Created by Zc on 2019/2/22.
 */
public class MyApplicaiton extends Application {
    private static MyApplicaiton instance;
    public static MyApplicaiton getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
