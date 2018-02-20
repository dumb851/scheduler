package ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.zubrid.scheduletimer.R;

import java.util.ArrayList;

import data.DbLab;
import model.ScheduleItem;
import model.TimePoint;

public final class ScheduleItemActivity extends AppCompatActivity
        implements TimePointListAdapter.ItemClickListener,
        TimerNameDialog.TimerNameDialogListener, TimePickerDialog.TimePickerDialogListener{

    private static int EXACT_TIME_PICKER_REQUEST = 27;
    private static String EXTRA_ID = "ScheduleItemActivity_EXTRA_ID";

    private DbLab mDbLab;
    private ScheduleItem mScheduleItem;
    private TextView mTvTitle;
    private Button mBtnAddPoint;
    private RecyclerView mRvTimePointList;
    private ArrayList<TimePoint> mTimePointArrayList;
    private int mClickedTimePointPos;
    private TimePointListAdapter mListAdapter;
    private CardView mCvTimerName;


    public static Intent getIntent(Context context, int scheduleID) {

        Intent intent = new Intent(context, ScheduleItemActivity.class);
        intent.putExtra(EXTRA_ID, scheduleID);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.schedule_item);

        setToolbar();

        initVariables();

        fillUI();

        mBtnAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExactTimePicker(-1);
            }
        });

        mCvTimerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimerNameDialog nameDialog = new TimerNameDialog();

                Bundle args = new Bundle();
                args.putString(TimerNameDialog.EXTRA_NAME, mTvTitle.getText().toString());
                nameDialog.setArguments(args);

                nameDialog.show(getSupportFragmentManager(), "timerName");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_schedule_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_schedule_item_done:
                saveSchedule();
                break;

            case R.id.action_schedule_item_delete:

                deleteSchedule();
                finish();
                break;

            case R.id.action_schedule_item_duplicate:
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void setToolbar() {
        Toolbar toolbar = new Toolbar(this);
        setActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setTitle(R.string.timer);
        }
    }

    private void initVariables() {

        mDbLab = DbLab.getLab(this);

        mScheduleItem = getScheduleItem();

        mTvTitle = findViewById(R.id.schedule_item_title);
        mBtnAddPoint = findViewById(R.id.schedule_item_btn_add_point);
        mRvTimePointList = findViewById(R.id.schedule_item_rv_time_point_list);
        mCvTimerName = findViewById(R.id.schedule_item_cv_title);
        mTimePointArrayList = mDbLab.getTimePointList(mScheduleItem.getID());

    }

    private void fillUI() {

        mTvTitle.setText(mScheduleItem.getTitle());

        mListAdapter = new TimePointListAdapter(mTimePointArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mListAdapter.setItemClickListener(this);

        mRvTimePointList.setLayoutManager(layoutManager);
        mRvTimePointList.setAdapter(mListAdapter);
        mRvTimePointList.setNestedScrollingEnabled(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private ScheduleItem getScheduleItem() {

        int schedule_ID = getIntent().getIntExtra(EXTRA_ID, -1);

        if (schedule_ID != -1) {
            return mDbLab.getScheduleItem(schedule_ID);
        } else {
            return new ScheduleItem();
        }
    }

    private void deleteSchedule() {

        mDbLab.deleteSchedule(mScheduleItem);
    }

    private void saveSchedule() {

        mScheduleItem.setTitle(mTvTitle.getText().toString());

        int resultID = mDbLab.saveSchedule(mScheduleItem);

        for (TimePoint point : mTimePointArrayList) {
            point.setScheduleID(resultID);
            mDbLab.saveTimePoint(point);
        }

        finish();

    }

    private void showExactTimePicker(int pos) {

        mClickedTimePointPos = pos;

        TimePickerDialog timePickerDialog = new TimePickerDialog();

        Bundle args = new Bundle();

        if (pos != -1) {
            TimePoint clickedTimePoint = findTimePointByPos(mClickedTimePointPos);
            args.putString(TimePickerDialog.EXTRA_MESSAGE, clickedTimePoint.getTitle());
            args.putInt(TimePickerDialog.EXTRA_HOUR, clickedTimePoint.getHour());
            args.putInt(TimePickerDialog.EXTRA_MINUTE, clickedTimePoint.getMinute());

        }

        timePickerDialog.setArguments(args);

        timePickerDialog.show(getSupportFragmentManager(), "timePickerDialog");



//!        mClickedTimePointPos = pos;
//
//        Intent intent;
//
//        if (pos != -1) {
//
//            TimePoint clickedTimePoint = findTimePointByPos(mClickedTimePointPos);
//
//            intent = ExactTimePickerActivity.getIntent(this,
//                    clickedTimePoint.getTitle(),
//                    clickedTimePoint.getHour(),
//                    clickedTimePoint.getMinute()
//            );
//
//        } else {
//            intent = ExactTimePickerActivity.getIntent(this);
//        }
//
//        startActivityForResult(intent, EXACT_TIME_PICKER_REQUEST);

    }

    @Override
    public void OnItemClickListener(int pos) {
        showExactTimePicker(pos);
    }


    //! do not need this any more
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == EXACT_TIME_PICKER_REQUEST) {
//
//            if (resultCode != Activity.RESULT_OK) {
//                return;
//            }
//
//            TimePoint clickedTimePoint = findTimePointByPos(mClickedTimePointPos);
//
//            clickedTimePoint.setTitle(
//                    data.getStringExtra(ExactTimePickerActivity.EXTRA_TITLE));
//            clickedTimePoint.setHour(
//                    data.getIntExtra(ExactTimePickerActivity.EXTRA_TIME_HOUR, 0));
//            clickedTimePoint.setMinute(
//                    data.getIntExtra(ExactTimePickerActivity.EXTRA_TIME_MINUTE, 0));
//
//            mListAdapter.refreshDataSet(mTimePointArrayList);
//
//        }
//    }

    TimePoint findTimePointByPos(int pos) {

        if (pos == -1) {
            TimePoint clickedTimePoint = new TimePoint();
            mTimePointArrayList.add(clickedTimePoint);

            return clickedTimePoint;
        } else {

            return mTimePointArrayList.get(pos);

        }


    }

    //TimerNameDialogListener

    @Override
    public void onDialogDoneClick(String timerName) {

        mTvTitle.setText(timerName);
    }

    //TimerDialogListener
    @Override
    public void onTimePickerDialogDoneClick(Bundle bundle) {

        TimePoint clickedTimePoint = findTimePointByPos(mClickedTimePointPos);

        clickedTimePoint.setTitle(
                bundle.getString(TimePickerDialog.EXTRA_MESSAGE));
        clickedTimePoint.setHour(
                bundle.getInt(TimePickerDialog.EXTRA_HOUR, 0));
        clickedTimePoint.setMinute(
                bundle.getInt(TimePickerDialog.EXTRA_MINUTE, 0));

        mListAdapter.refreshDataSet(mTimePointArrayList);

    }

}
