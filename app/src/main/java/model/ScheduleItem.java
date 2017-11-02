package model;

public final class ScheduleItem {

    private String mTitle;
    private int mID;
    private boolean mIsRunning;
    private long mSortOrder;

    public static final int NO_ID = -1;

    // constructor
    public ScheduleItem() {
        mID = NO_ID;
    }

    public long getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(long sortOrder) {
        mSortOrder = sortOrder;
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
