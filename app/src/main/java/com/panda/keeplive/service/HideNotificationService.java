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

public class HideNotificationService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1001, new Notification());
        stopForeground(true);
        stopSelf();
        Log.e("HideNotificationService","HideNotficationService = " + android.os.Process.myPid());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
