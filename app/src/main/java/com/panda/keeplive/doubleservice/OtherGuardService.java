package com.panda.keeplive.doubleservice;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.panda.keeplive.ServiceAliveUtils;
import com.panda.keeplive.service.KeepDoubleStartService;

public class OtherGuardService extends Service {

    private final static String TAG = OtherGuardService.class.getSimpleName();
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "OtherGuardService:建立链接");
            boolean isServiceRunning = ServiceAliveUtils.isServiceAlice("KeepDoubleStartService");
            if (!isServiceRunning) {
                Intent i = new Intent(OtherGuardService.this, KeepDoubleStartService.class);
                startService(i);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // 断开链接
            startService(new Intent(OtherGuardService.this, GuardService.class));
            // 重新绑定
            bindService(new Intent(OtherGuardService.this, GuardService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IKeepAliveConnection.Stub() {

        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1111, new Notification());
        // 绑定建立链接
        bindService(new Intent(this, GuardService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

}
