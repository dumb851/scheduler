package ui.notifacation;

public class NotificationProperties implements NotificationHelper.Properties{

    private boolean mVibrationEnabled;
    private String mContentText;
    private String mContentTitle;

    public NotificationProperties() {
        mVibrationEnabled = false;
        mContentText = "mContentText";
        mContentTitle = "mContentTitle";
    }

    @Override
    public boolean enableVibration() {
        return mVibrationEnabled;
    }

    @Override
    public String getContentText() {
        return mContentText;
    }

    @Override
    public String getContentTitle() {
        return mContentTitle;
    }
}
