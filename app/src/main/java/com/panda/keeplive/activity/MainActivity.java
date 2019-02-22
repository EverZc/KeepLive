package com.panda.keeplive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.panda.keeplive.R;
import com.panda.keeplive.SettingUtils;
import com.panda.keeplive.service.AutoHideNotificationService;
import com.panda.keeplive.service.ForegroundService;
import com.panda.keeplive.service.HideNotificationService;
import com.panda.keeplive.service.ReceiverService;
import com.panda.keeplive.service.SimpleService;

public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn_one);
        btn2 = findViewById(R.id.btn_two);
        btn3 = findViewById(R.id.btn_three);
        Log.e("MainActivity","onCreate");
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