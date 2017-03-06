package com.swpuiot.mydrawerlayout2.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.swpuiot.mydrawerlayout2.view.services.TimeKeepService;

/**
 * Created by 羊荣毅_L on 2017/3/6.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, TimeKeepService.class);
        context.startService(i);
    }
}
