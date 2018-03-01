package utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.zubrid.scheduletimer.R;

import ui.MainActivity;

public class NotificationHelper {

    interface Properties {
        boolean enableVibration();
        String getContentText();
        String getContentTitle();
    }

    private Properties mProperties;
    private Context mContext;

    // constructor
    private NotificationHelper() {
    }

    public NotificationHelper(Context context, Properties properties) {
        mProperties = properties;
        mContext = context;
    }

    public void show() {

        boolean enableVibration = mProperties.enableVibration();

        //!
        // The id of the channel.

        Notification notification = new Notification();
        if (enableVibration) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        String CHANNEL_ID = "my_channel_01";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(mProperties.getContentTitle())
                        .setContentText(mProperties.getContentText())
                        .setDefaults(notification.defaults)
                        //.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                ;


// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your app to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

// mNotificationId is a unique integer your app uses to identify the
// notification. For example, to cancel the notification, you can pass its ID
// number to NotificationManager.cancel().

        //!
        int mNotificationId = 111;

        mNotificationManager.notify(mNotificationId, mBuilder.build());

    }


}
