package com.panda.keeplive.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.panda.keeplive.ServiceAliveUtils;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final String TAG = JobSchedulerService.class.getSimpleName();
    @Override
    public boolean onStartJob(JobParameters params) {
        boolean isServiceRunning = ServiceAliveUtils.isServiceAlice();
        if (!isServiceRunning) {
            Intent i = new Intent(this, DownloadService.class);
            startService(i);
            Log.e(TAG, "ScheduleService启动了DownloadService");
        }
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
