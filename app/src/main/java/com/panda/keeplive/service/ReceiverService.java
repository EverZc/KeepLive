package com.panda.keeplive.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.panda.keeplive.KeepServiceManager;

/**
 * Created by Zc on 2019/2/22.
 */

public class ReceiverService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        KeepServiceManager.getInstance().registerReceiver(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        KeepServiceManager.getInstance().unRegisterReceiver(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KeepServiceManager.getInstance().setServiceForeground(this);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
