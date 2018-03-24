package utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.zubrid.scheduletimer.R;

import data.DbLab;
import model.ScheduleItem;
import model.TimePoint;
import ui.activity.Main;

public class NotificationHelper {

    private Context mContext;

    private boolean mEnableVibration;
    private String mContentText;
    private String mContentTitle;
    private int mTimePointID;

    // constructor
    public NotificationHelper(Context context, int timePointID) {

        mContext = context;
        mTimePointID = timePointID;

        DbLab dbLab = DbLab.getLab(mContext);
        TimePoint timePoint = dbLab.getTimePoint(timePointID);

        mContentText = timePoint.getTitle();

        ScheduleItem scheduleItem = dbLab.getScheduleItem(timePoint.getScheduleID());

        mContentTitle = scheduleItem.getTitle();

    }

    public void show() {

        // TODO: 19.03.2018 here fill notification properties

        Notification notification = new Notification();

        if (mEnableVibration) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        String CHANNEL_ID = "my_channel_01";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(mContentTitle)
                        .setContentText(mContentText)
                        .setDefaults(notification.defaults);
                //.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                //;


// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, Main.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your app to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(Main.class);
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

        mNotificationManager.notify(mTimePointID, mBuilder.build());

    }


}
