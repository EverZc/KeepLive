package com.panda.keeplive.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.panda.keeplive.KeepServiceManager;

import java.util.Timer;
import java.util.TimerTask;

public class KeepDoubleStartService extends Service {
    public static final int NOTICE_ID = 100;
    private static final String TAG = KeepDoubleStartService.class.getSimpleName();
    private DownloadBinder mDownloadBinder;
    private NotificationCompat.Builder mBuilderProgress;
    private NotificationManager mNotificationManager;
    private Timer mRunTimer;
    private int mTimeSec;
    private int mTimeMin;
    private int mTimeHour;

    private OnTimeChangeListener mOnTimeChangeListener;

    public interface OnTimeChangeListener {
        void showTime(String time);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KeepServiceManager.getInstance().registerReceiver(this);
        mDownloadBinder = new DownloadBinder();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        KeepServiceManager.getInstance().setServiceForeground(this);
        startRunTimer();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mDownloadBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    private void startRunTimer() {
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                mTimeSec++;
                if (mTimeSec == 60||mTimeHour%60==0) {
                    mTimeSec = 0;
                    mTimeMin++;
                }
                if (mTimeMin == 60|mTimeMin%60==0) {
                    mTimeMin = 0;
                    mTimeHour++;
                }

                String time = "运行时间：" + mTimeHour + " : " + mTimeMin + " : " + mTimeSec;
                if (mOnTimeChangeListener != null) {
                    mOnTimeChangeListener.showTime(time);
                }
                Log.e(TAG, time);
            }
        };
        mRunTimer = new Timer();
        // 每隔1s更新一下时间
        mRunTimer.schedule(mTask, 1000, 1000);
    }

    private void stopRunTimer() {
        if (mRunTimer != null) {
            mRunTimer.cancel();
            mRunTimer = null;
        }
        mTimeSec = 0;
        mTimeMin = 0;
        mTimeHour = 0;
    }

    public class DownloadBinder extends Binder {
        public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
            mOnTimeChangeListener = onTimeChangeListener;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeepServiceManager.getInstance().unRegisterReceiver(this);
        NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (mManager == null) {
            return;
        }
        mManager.cancel(NOTICE_ID);
        stopRunTimer();
    }
}
