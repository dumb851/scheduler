package model;

public final class ScheduleItem {

    private String mTitle;
    private int mID;
    private boolean mIsRunning;
    public static final int NO_ID = -1;

    // constructor
    public ScheduleItem() {
        mID = NO_ID;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void setIsRunning(boolean isRunning) {
        mIsRunning = isRunning;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

}
