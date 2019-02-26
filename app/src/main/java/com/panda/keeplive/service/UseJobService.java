package com.panda.keeplive.service;

import android.app.NotificationManager;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class UseJobService extends Service {
    public static final int NOTICE_ID = 100;
    private static final String TAG = UseJobService.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        useJobServiceForKeepAlive();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 使用JobScheduler进行保活
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void useJobServiceForKeepAlive() {
        Log.e(TAG, "使用JobScheduler进行保活");
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler == null) {
            return;
        }
        jobScheduler.cancelAll();
        JobInfo.Builder builder =
                new JobInfo.Builder(1024, new ComponentName(getPackageName(),
                        JobSchedulerService.class.getName()));
        //周期设置为了2s
        builder.setPeriodic(1000 * 5);
        builder.setPersisted(true);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        int schedule = jobScheduler.schedule(builder.build());
        if (schedule <= 0) {
            Log.e(TAG, "schedule error！");
        }
    }
}