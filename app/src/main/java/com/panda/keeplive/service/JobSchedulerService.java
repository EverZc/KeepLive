package com.panda.keeplive.service;

import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.panda.keeplive.MyApplicaiton;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final String TAG = JobSchedulerService.class.getSimpleName();
    private String keepPackageName="com.panda.keeplive.service."+"ForegroundService";
    @Override
    public boolean onStartJob(JobParameters params) {
        boolean isServiceRunning = isServiceAlice(keepPackageName);
        if (!isServiceRunning) {
            Intent i = new Intent(this, ForegroundService.class);
            startService(i);
        }else {
        }
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private   boolean isServiceAlice(String keepPackageName) {
        boolean isServiceRunning = false;
        ActivityManager manager =
                (ActivityManager) MyApplicaiton.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return true;
        }
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (keepPackageName.equals(service.service.getClassName())) {
                isServiceRunning = true;
            }
        }
        return isServiceRunning;
    }
}
