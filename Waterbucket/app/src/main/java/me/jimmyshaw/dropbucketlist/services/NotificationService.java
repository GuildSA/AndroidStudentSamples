package me.jimmyshaw.dropbucketlist.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.util.Log;

import br.com.goncalves.pugnotification.notification.PugNotification;
import io.realm.Realm;
import io.realm.RealmResults;
import me.jimmyshaw.dropbucketlist.ActivityMain;
import me.jimmyshaw.dropbucketlist.R;
import me.jimmyshaw.dropbucketlist.models.Drop;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class NotificationService extends IntentService {

    public static final String TAG = "Jim";

    public NotificationService() {
        super("NotificationService");
        Log.d(TAG, "NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            // Query realm and find all our incomplete goals. We don't need to use the findAllAsync
            // method because we're already in a background thread with this service.
            RealmResults<Drop> results = realm.where(Drop.class).equalTo("completed", false).findAll();

            for (Drop currentGoal : results) {
                if (isNotificationNeeded(currentGoal.getDateAdded(), currentGoal.getDateDue())) {
                    fireNotification(currentGoal);
                }
            }
        }
        finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private boolean isNotificationNeeded(long dateAdded, long dateDue) {
        long currentTime = System.currentTimeMillis();

        if (currentTime > dateDue) {
            // If we're already passed a goal's due date then it doesn't make sense send notification.
            return false;
        }
        else {
            // We'd like to notify the user when 90% of the time to reach the due date has passed.
            long ninetyPercentDifference = (long) (0.90 * (dateDue - dateAdded));

            // If the current time has surpassed the time when the goal was added plus 90% of the
            // due date then return true, otherwise return false.
            return (currentTime > (dateAdded + ninetyPercentDifference)) ? true : false;
        }

    }

    private void fireNotification(Drop goal) {
        String message = getString(R.string.notification_message_small_device)
                            + " \"" + goal.getGoal() + "\"";

        PugNotification.with(this)
                .load()
                .title(R.string.app_name)
                .message(message)
                .bigTextStyle(message)
                .smallIcon(R.drawable.ic_logo)
                .largeIcon(R.drawable.ic_logo)
                .flags(Notification.DEFAULT_ALL) // The defaults are lights, sound and vibrate.
                .autoCancel(true) // Clears the message if the user clicks on it.
                .click(ActivityMain.class) // Clicking on the message will take the user to this app's main activity.
                .simple()
                .build();
    }

}
