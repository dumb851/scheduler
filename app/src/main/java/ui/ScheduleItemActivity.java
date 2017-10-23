package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zubrid.scheduler.R;

import java.util.ArrayList;

import data.DbLab;
import model.ScheduleItem;
import model.TimePoint;

public final class ScheduleItemActivity extends ActivityDoneCancelActionBar
        implements TimePointListAdapter.ItemClickListener {

    private DbLab mDbLab;
    private ScheduleItem mScheduleItem;
    private TextView mTvTitle;
    private Button mBtnAddPoint;
    private RecyclerView mRvTimePointList;
    private ArrayList<TimePoint> mTimePointArrayList;
    private int mClickedTimePointPos;
    private static int EXACT_TIME_PICKER_REQUEST = 27;
    private static String EXTRA_ID = "ScheduleItemActivity_EXTRA_ID";
    private TimePointListAdapter mListAdapter;

    public static Intent getIntent(Context context, int scheduleID) {

        Intent intent = new Intent(context, ScheduleItemActivity.class);
        intent.putExtra(EXTRA_ID, scheduleID);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBar();

        setContentView(R.layout.schedule_item);

        initVariables();

        fillUI();

        mBtnAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                showExactTimePicker(-1);
            }
        });

    }

    private void initVariables() {

        mDbLab = DbLab.getLab(this);

        mScheduleItem = getScheduleItem();

        mTvTitle = (TextView) findViewById(R.id.schedule_item_title);
        mBtnAddPoint = (Button) findViewById(R.id.schedule_item_btn_add_point);
        mRvTimePointList = (RecyclerView) findViewById(R.id.schedule_item_rv_time_point_list);

        mTimePointArrayList = mDbLab.getTimePointList(mScheduleItem.getID());

    }

    private ScheduleItem getScheduleItem() {

        int schedule_ID = getIntent().getIntExtra(EXTRA_ID, -1);

        if (schedule_ID != -1) {
            return mDbLab.getScheduleItem(schedule_ID);
        } else {
            return new ScheduleItem();
        }
    }

    private void fillUI() {

        mTvTitle.setText(mScheduleItem.getTitle());

        mListAdapter = new TimePointListAdapter(mTimePointArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mListAdapter.setItemClickListener(this);

        mRvTimePointList.setLayoutManager(layoutManager);
        mRvTimePointList.setAdapter(mListAdapter);
        mRvTimePointList.setNestedScrollingEnabled(false);

    }

    @Override
    void OnDoneClick() {

        mScheduleItem.setTitle(mTvTitle.getText().toString());
        int resultID = mDbLab.saveSchedule(mScheduleItem);

        for (TimePoint point : mTimePointArrayList) {
            point.setScheduleID(resultID);
            mDbLab.saveTimePoint(point);
        }

        finish();

    }

    @Override
    void OnCancelClick() {
        finish();
    }

    private void showExactTimePicker(int pos) {

        Bundle bundle = new Bundle();

        mClickedTimePointPos = pos;

        if (pos != -1) {

            TimePoint clickedTimePoint = findTimePointByPos(mClickedTimePointPos);

            bundle.putString(ExactTimePickerActivity.EXTRA_TITLE, clickedTimePoint.getTitle());
            bundle.putInt(ExactTimePickerActivity.EXTRA_TIME_HOUR, clickedTimePoint.getHour());
            bundle.putInt(ExactTimePickerActivity.EXTRA_TIME_MINUTE, clickedTimePoint.getMinute());
        }

        Intent intent = ExactTimePickerActivity.getIntent(this, bundle);

        startActivityForResult(intent, EXACT_TIME_PICKER_REQUEST);

    }

    @Override
    public void OnItemClickListener(int pos) {
        showExactTimePicker(pos);
    }

    //!    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EXACT_TIME_PICKER_REQUEST) {

            if (resultCode != Activity.RESULT_OK) {
                return;
            }

            TimePoint clickedTimePoint = findTimePointByPos(mClickedTimePointPos);

            clickedTimePoint.setTitle(
                    data.getStringExtra(ExactTimePickerActivity.EXTRA_TITLE));
            clickedTimePoint.setHour(
                    data.getIntExtra(ExactTimePickerActivity.EXTRA_TIME_HOUR, 0));
            clickedTimePoint.setMinute(
                    data.getIntExtra(ExactTimePickerActivity.EXTRA_TIME_MINUTE, 0));

            mListAdapter.refreshDataSet(mTimePointArrayList);

        }
    }

    TimePoint findTimePointByPos(int pos) {

        if (pos == -1) {
            TimePoint clickedTimePoint = new TimePoint();
            mTimePointArrayList.add(clickedTimePoint);

            return clickedTimePoint;
        } else {

            return mTimePointArrayList.get(pos);

        }


    }

}
