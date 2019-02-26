package com.panda.keeplive;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceAliveUtils {
   public static boolean isServiceAlice(String serviceName) {
        String servicePackageName="com.panda.keeplive.service."+serviceName;
        boolean isServiceRunning = false;
        ActivityManager manager =
                (ActivityManager) MyApplicaiton.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return true;
        }
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (servicePackageName.equals(service.service.getClassName())) {
                isServiceRunning = true;
            }
        }
        return isServiceRunning;
    }
}
