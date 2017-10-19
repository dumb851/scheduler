package model;

public final class TimePoint {

    private String mTitle;
    private int mID;
    private int mScheduleID;
    private int mHour;
    private int mMinute;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getID() {
        return mID;
    }

    public void setID(int id) {
        mID = id;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }

    public int getScheduleID() {
        return mScheduleID;
    }

    public void setScheduleID(int scheduleID) {
        mScheduleID = scheduleID;
    }
}
