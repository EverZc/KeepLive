package com.panda.keeplive.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.panda.keeplive.R;
import com.panda.keeplive.SettingUtils;
import com.panda.keeplive.doubleservice.GuardService;
import com.panda.keeplive.doubleservice.StepService;
import com.panda.keeplive.service.AutoHideNotificationService;
import com.panda.keeplive.service.DownloadService;
import com.panda.keeplive.service.UseJobService;
import com.panda.keeplive.service.ForegroundService;
import com.panda.keeplive.service.HideNotificationService;
import com.panda.keeplive.service.ReceiverService;
import com.panda.keeplive.service.SimpleService;

public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private boolean isDoubleService=false;
    private DownloadService.DownloadBinder mDownloadBinder;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
            mDownloadBinder.setOnTimeChangeListener(new DownloadService.OnTimeChangeListener() {
                @Override
                public void showTime(final String time) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           Log.e("mServiceConnection:",time+"");
                        }
                    });
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Log.e("MainActivity","onCreate");
    }

    private void startDoubleService() {
        isDoubleService=true;
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        //双守护线程，优先级不一样
        startAllServices();
    }
    /**
     * 开启所有守护Service
     */
    private void startAllServices() {
        startService(new Intent(this, StepService.class));
        startService(new Intent(this, GuardService.class));
    }

    private void useJobService() {
        Intent intentJob = new Intent(this, UseJobService.class);
        startService(intentJob);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.e("onContentChanged:","onContentChanged");
    }

    private void initView() {
        btn1 = findViewById(R.id.btn_one);
        btn2 = findViewById(R.id.btn_two);
        btn3 = findViewById(R.id.btn_three);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity","onDestroy");
        if (isDoubleService){
            unbindService(mServiceConnection);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                Toast.makeText(this, "SimpleService", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, SimpleService.class));
                break;
            case  R.id.btn_two:
                Toast.makeText(this, "ForegroundService", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, ForegroundService.class));
                break;
            case R.id.btn_three:
                Toast.makeText(this, "HideNotificationService", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, HideNotificationService.class));
                break;
            case R.id.btn_four:
                Toast.makeText(this, "HideNotificationService", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, AutoHideNotificationService.class));
                break;
            case R.id.btn_five:
                Toast.makeText(this, "ReceiverService", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, ReceiverService.class));
                break;
            case R.id.btn_six:
                Toast.makeText(this, "startDoubleService", Toast.LENGTH_SHORT).show();
                startDoubleService();
                break;
            case R.id.btn_seven:
                Toast.makeText(this, "useJobService", Toast.LENGTH_SHORT).show();
                useJobService();
                break;
            case R.id.btn_white:
                Toast.makeText(this, "进入白名单", Toast.LENGTH_SHORT).show();
                SettingUtils.enterWhiteListSetting(MainActivity.this);
                break;

            default:
                Toast.makeText(this, "default", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}