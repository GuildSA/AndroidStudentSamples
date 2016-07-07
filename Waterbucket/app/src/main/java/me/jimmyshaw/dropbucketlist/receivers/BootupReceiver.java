package me.jimmyshaw.dropbucketlist.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import me.jimmyshaw.dropbucketlist.utilities.Util;

// Our app's Notification Service will be disabled whenever the user's device restarts. The service
// needs to be triggered again in the background, without ever forcing the user to re-open our app.
// This broadcast receiver listens for device restarts and upon bootup will trigger the service.
public class BootupReceiver extends BroadcastReceiver {

    public static final String TAG = "Jim";

    public BootupReceiver() {
        Log.d(TAG, "BootupReceiver");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Util.scheduleAlarm(context);
    }
}
