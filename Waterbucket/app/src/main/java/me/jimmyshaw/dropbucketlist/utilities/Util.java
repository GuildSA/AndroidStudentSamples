package me.jimmyshaw.dropbucketlist.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.util.List;

import me.jimmyshaw.dropbucketlist.services.NotificationService;

public class Util {

    public static void showViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static boolean apiLevelGreaterThanJellyBean() {
        return Build.VERSION.SDK_INT > 15;
    }

    public static void setBackground(View view, Drawable drawable) {
        if (apiLevelGreaterThanJellyBean()) {
            view.setBackground(drawable);
        }
        else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static void scheduleAlarm(Context context) {
        // The vision for using an alarm manager is that we'll be combining it with a service so that
        // whenever a goal is 90%, 95% or whatever other % finished approaching its due date time then
        // we'll notify the user. We want our alarm service to check if any goals are approaching their
        // due date time once every hour.
        // The reason we're putting our intent inside a pending intent is because we want the alarm
        // service to be checking due dates regardless if our app is running. We'd even like it to check
        // when the actual phone is asleep. A service inside a pending intent will run no matter if its
        // hosting app process is destroyed.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationService.class);
        // The requestCode int, which we arbitrarily put as 100, is an int used to differentiate
        // this pending intent from other pending intents. The final parameter, FLAG_UPDATE_CURRENT
        // means that if the described PendingIntent already exists, then keep it but replace its
        // extra data with what is in this new Intent.
        PendingIntent pendingIntent = PendingIntent.getService(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // ELAPSED_REALTIME_WAKEUP will use real time plus will wake up the phone to run whatever is in
        // the pending intent. Second parameter is the trigger time, after wakeup how long before the
        // pending intent triggers. Third parameter is the interval time, how often do we want the
        // pending intent to fire. Note that the interval time is recommended to be an hour or more.
        // If not then the device's battery will drain quickly.
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, 3600000, pendingIntent);
    }
}
