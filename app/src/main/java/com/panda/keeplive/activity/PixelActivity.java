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
        params.height = 100;
        params.width =100;
        window.setAttributes(params);
        KeepServiceManager.getInstance().setKeepLiveActivity(this);
        Log.e("PixelActivity","onCreate");


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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("PixelActivity","ACTION_DOWN");
                finish();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("PixelActivity","ACTION_MOVE");

                break;
        }
        return super.onTouchEvent(event);
    }
}
