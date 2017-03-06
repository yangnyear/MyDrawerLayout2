package com.swpuiot.mydrawerlayout2.view.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.swpuiot.mydrawerlayout2.view.receiver.AlarmReceiver;

/**
 * Created by 羊荣毅_L on 2017/3/6.
 * 定时提醒服务
 */
public class TimeKeepService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int annour = 60 * 60 * 1000;// TODO: 2017/3/6
        long triggerAtTime = SystemClock.elapsedRealtime() + annour;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pt = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pt);
        return super.onStartCommand(intent, flags, startId);
    }
}
