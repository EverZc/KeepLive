package com.panda.keeplive.service;

import android.app.NotificationManager;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.panda.keeplive.KeepServiceManager;

import java.util.Timer;
import java.util.TimerTask;

public class DownloadService extends Service {
    public static final int NOTICE_ID = 100;
    private static final String TAG = DownloadService.class.getSimpleName();
    private DownloadBinder mDownloadBinder;
    private NotificationCompat.Builder mBuilderProgress;
    private NotificationManager mNotificationManager;

    private Timer mRunTimer;

    private int mTimeSec;
    private int mTimeMin;
    private int mTimeHour;


    private OnTimeChangeListener mOnTimeChangeListener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
      //  useJobServiceForKeepAlive();
        KeepServiceManager.getInstance().registerReceiver(this);
        mDownloadBinder = new DownloadBinder();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
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
//        mScreenListener.stopScreenReceiverListener();
    }



    private void startRunTimer() {
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                mTimeSec++;
                if (mTimeSec == 60) {
                    mTimeSec = 0;
                    mTimeMin++;
                }
                if (mTimeMin == 60) {
                    mTimeMin = 0;
                    mTimeHour++;
                }
                if (mTimeHour == 24) {
                    mTimeSec = 0;
                    mTimeMin = 0;
                    mTimeHour = 0;
                }
                String time = "时间为：" + mTimeHour + " : " + mTimeMin + " : " + mTimeSec;
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
        Log.e(TAG, "时间为：" + mTimeHour + " : " + mTimeMin + " : " + mTimeSec);
    }

    public interface OnTimeChangeListener {
        void showTime(String time);
    }

    public class DownloadBinder extends Binder {
        public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
            mOnTimeChangeListener = onTimeChangeListener;
        }
    }

    /**
     * 使用JobScheduler进行保活
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void useJobServiceForKeepAlive() {
        Log.e(TAG, "useJobServiceForKeepAlive");
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler == null) {
            return;
        }

        jobScheduler.cancelAll();
        JobInfo.Builder builder =
                new JobInfo.Builder(1024, new ComponentName(getPackageName(),
                        JobSchedulerService.class.getName()));
        //周期设置为了2s
        builder.setPeriodic(1000 * 18);
        builder.setPersisted(true);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        int schedule = jobScheduler.schedule(builder.build());
        if (schedule <= 0) {
            Log.e(TAG, "schedule error！");
        }
    }
}