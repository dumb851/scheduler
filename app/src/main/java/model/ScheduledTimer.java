package model;

public final class ScheduledTimer {

    private String mTimerName;
    private int mID;
    private boolean mIsRunning;
    private boolean mIsUseVibration;
    private float mSortOrder;
    private static float sSortOrder;

    public static final int NO_ID = -1;


    static {
        sSortOrder = 0;
    }

    // constructor
    public ScheduledTimer() {
        mID = NO_ID;
        mTimerName = "";
        mSortOrder = sSortOrder + 100f;
    }

    public float getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(float sortOrder) {
        mSortOrder = sortOrder;

        if (sortOrder > sSortOrder) {
            sSortOrder = sortOrder;
        }
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void setIsRunning(boolean isRunning) {
        mIsRunning = isRunning;
    }

    public String getTimerName() {
        return mTimerName;
    }

    public void setTimerName(String timerName) {
        mTimerName = timerName;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public boolean isUseVibration() {
        return mIsUseVibration;
    }

    public void setUseVibration(boolean useVibration) {
        mIsUseVibration = useVibration;
    }
}
