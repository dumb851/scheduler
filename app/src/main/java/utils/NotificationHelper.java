package utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.zubrid.scheduletimer.R;

import data.DbLab;
import model.ScheduledTimer;
import model.TimePoint;
import ui.activity.MainActivity;

public class NotificationHelper {

    private Context mContext;

    private boolean mUseVibration;
    private String mContentText;
    private String mContentTitle;
    private int mTimePointID;
    private PendingIntent contentIntent;
    private int mColor;

    // constructor
    public NotificationHelper(Context context, int timePointID) {

        mContext = context;
        mTimePointID = timePointID;

        DbLab dbLab = DbLab.getLab(mContext);
        TimePoint timePoint = dbLab.getTimePoint(timePointID);

        mContentText = timePoint.getTitle();

        ScheduledTimer scheduledTimer = dbLab.getScheduleItem(timePoint.getScheduleID());

        mContentTitle = scheduledTimer.getTimerName();
        mUseVibration = scheduledTimer.isUseVibration();
        mColor = scheduledTimer.getColor();
    }

    public void show() {

        // TODO: 19.03.2018 here fill notification properties

        Notification notification = new Notification();

        if (mUseVibration) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        //!
        //if (mColor != -1) {
            //notification.defaults |= Notification.DEFAULT_LIGHTS;
            notification.defaults |= Notification.FLAG_SHOW_LIGHTS;
        //}

        Intent notificationIntent = new Intent();

        PendingIntent intentButtonStop = PendingIntent.getActivity(
                mContext,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        String CHANNEL_ID = "my_channel_01";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(mContentTitle)
                        .setContentText(mContentText)
                        .setDefaults(notification.defaults)
                        .addAction(R.drawable.ic_action_done, mContext.getString(R.string.stop_timer), intentButtonStop)
                        .addAction(R.drawable.ic_action_done, mContext.getString(R.string.ok), intentButtonStop)
                        .setLights(Color.BLUE, 500, 2000)//!

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

        mNotificationManager.notify(mTimePointID, mBuilder.build());

    }


}
