package com.panda.keeplive.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.panda.keeplive.KeepServiceManager;


/**
 * Created by Zc on 2019/2/22.
 */

public class PixelActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width =1;
        window.setAttributes(params);
        KeepServiceManager.getInstance().setKeepLiveActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("PixelActivity","onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("PixelActivity","onDestroy");
    }

}
