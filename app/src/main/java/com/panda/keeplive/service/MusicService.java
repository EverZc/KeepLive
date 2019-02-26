package com.panda.keeplive.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.panda.keeplive.R;

public class MusicService extends Service {
    private final static String TAG = MusicService.class.getSimpleName();
    private MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "MusicService启动服务");
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.canon);
        mMediaPlayer.setLooping(true);//无线循环
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer != null) {
                    Log.e(TAG, "启动播放无声音乐");
                    mMediaPlayer.start();
                }
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            Log.e(TAG, "关闭播放无声音乐");
            mMediaPlayer.stop();
        }
        Log.e(TAG, "MusicService停止服务");
        // 重启自己
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        startService(intent);
    }
}
