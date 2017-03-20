package com.pooja.mediplusapp.AlarmReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.pooja.mediplusapp.Model.Alarm_Information;
import com.pooja.mediplusapp.R;

/**
 * Created by Pooja on 3/2/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Uri sound = Uri.parse("android.resource://" + context.getPackageName() +"/raw/sound_notification");
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intentgoto=new Intent(context, Alarm_Information.class);
        intentgoto.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,intentgoto,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Take your medicine")
                .setContentText("medicines")
                .setSound(sound)
                .setAutoCancel(true);

        notificationManager.notify(0,builder.build());

    }
}
