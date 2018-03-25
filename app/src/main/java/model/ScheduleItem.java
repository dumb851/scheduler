package model;

public final class ScheduleItem {

    private String mTitle;
    private int mID;
    private boolean mIsRunning;
    private float mSortOrder;
    private static float sSortOrder;

    public static final int NO_ID = -1;


    static {
        sSortOrder = 0;
    }

    // constructor
    public ScheduleItem() {
        mID = NO_ID;
        mTitle = "";
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
