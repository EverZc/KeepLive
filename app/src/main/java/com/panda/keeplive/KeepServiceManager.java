package com.panda.keeplive;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.panda.keeplive.activity.PixelActivity;

/**
 * Created by Zc on 2019/2/22.
 */

public class KeepServiceManager {

    /**
     * Service ID
     */
    private final static int KEEP_SERVICE_ID = 998;

    private static KeepServiceManager instance = new KeepServiceManager();

    public static KeepServiceManager getInstance(){
        return instance;
    }

    /**
     * 设置服务为前台服务
     * @param service
     */
    public void setServiceForeground(Service service){
        if (Build.VERSION.SDK_INT < 18) {
            //Android4.3以下 ，此方法能有效隐藏Notification上的图标
            service.startForeground(KEEP_SERVICE_ID, new Notification());
        } else if(Build.VERSION.SDK_INT>18 && Build.VERSION.SDK_INT<25){
            //Android4.3 - Android7.0，此方法能有效隐藏Notification上的图标
            Intent innerIntent = new Intent(service, SubsidiaryService.class);
            service.startService(innerIntent);
            service.startForeground(KEEP_SERVICE_ID, new Notification());
        }else{
            //Android7.1 google修复了此漏洞，暂无解决方法（现状：
            // Android7.1以上app启动后通知栏会出现一条"正在运行"的通知消息）
            service.startForeground(KEEP_SERVICE_ID, new Notification());
        }
    }

    /**
     * 辅助Service
     */
    public static class SubsidiaryService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(KEEP_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.e("SubsidiaryService","SubsidiaryService onDestroy");
        }
    }

    /**
     * 1像素的透明Activity
     */
    private PixelActivity activity;

    /**
     * 监听锁屏/解锁的广播（必须动态注册）
     */
    private LockReceiver lockReceiver;

    /**
     * 传入1像素的透明Activity实例
     * @param activity
     */
    public void setKeepLiveActivity(PixelActivity activity){
        this.activity = activity;
        Log.e("setKeepLiveActivity","传入1像素的透明Activity实例");
    }
    /**
     * 注册锁屏/解锁广播
     * @param context
     */
    public void registerReceiver(Context context){
        Log.e("KeepServiceManager","registerReceiver");
        lockReceiver = new LockReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(lockReceiver,filter);

    }

    /**
     * 注销锁屏/解锁广播
     * @param context
     */
    public void unRegisterReceiver(Context context){
        Log.e("KeepServiceManager","unRegisterReceiver");
        if(lockReceiver!=null){
            context.unregisterReceiver(lockReceiver);
        }
    }

    class LockReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Intent.ACTION_SCREEN_OFF:
                    startLiveActivity(context);//关闭屏幕则开启1像素的Activity
                    break;
                case Intent.ACTION_USER_PRESENT://开启屏幕则关闭1像素的Activity
                    destroyLiveActivity();
                    break;
            }
        }
    }

    private void startLiveActivity(Context context){
        Log.e("KeepServiceManager","接到关闭广播");
        Intent intent = new Intent(context, PixelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    private void destroyLiveActivity(){
        Log.e("KeepServiceManager","接到开启屏幕广播");
        if(activity!=null){
            activity.finish();
        }

    }
}
