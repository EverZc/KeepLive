package com.panda.keeplive.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.panda.keeplive.KeepServiceManager;


/**
 * Created by Zc on 2019/2/22.
 */

public class AutoHideNotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KeepServiceManager.getInstance().setServiceForeground(this);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
