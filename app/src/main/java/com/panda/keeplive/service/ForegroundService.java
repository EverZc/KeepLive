package com.panda.keeplive.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Zc on 2019/2/22.
 */

public class ForegroundService extends Service {
    /**
     * 前台进程的NotificationId  不可为0
     */
    private final static int SERVICE_ID = 1001;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(SERVICE_ID, new Notification());
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
