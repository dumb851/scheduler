package ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toolbar;

import com.zubrid.scheduletimer.R;

import java.util.ArrayList;

import data.DbLab;
import model.ScheduleItem;
import model.TimePoint;
import ui.TimePointListAdapter;
import ui.dialog.TimePickerDialog;
import ui.dialog.TimerNameDialog;

public final class ScheduleItemActivity extends AppCompatActivity
        implements TimePointListAdapter.ItemClickListener,
        TimerNameDialog.TimerNameDialogListener, TimePickerDialog.TimePickerDialogListener {

    private static String EXTRA_ID = "ScheduleItemActivity_EXTRA_ID";

    private DbLab mDbLab;
    private ScheduleItem mScheduleItem;
    private TextView mTvTimerName;
    private AppCompatButton mBtnAddPoint;
    private RecyclerView mRvTimePointList;
    private ArrayList<TimePoint> mTimePointArrayList;
    private int mClickedTimePointPos;
    private TimePointListAdapter mListAdapter;
    private CardView mCvTimerName;
    private CardView mCvNotificationSettings;


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
                args.putString(TimerNameDialog.EXTRA_NAME, mScheduleItem.getTitle());
                nameDialog.setArguments(args);

                nameDialog.show(getSupportFragmentManager(), "timerName");

            }
        });

        mCvNotificationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = NotificationSettingsActivity.getIntent(getApplicationContext(),
                        mScheduleItem.getID());
                startActivity(intent);

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

        mTvTimerName = findViewById(R.id.schedule_item_timer_name);
        mBtnAddPoint = findViewById(R.id.schedule_item_btn_add_point);
        mRvTimePointList = findViewById(R.id.schedule_item_rv_time_point_list);
        mCvTimerName = findViewById(R.id.schedule_item_cv_title);
        mCvNotificationSettings = findViewById(R.id.schedule_item_notification_settings);
        mTimePointArrayList = mDbLab.getTimePointList(mScheduleItem.getID());

    }

    private void fillUI() {

        setTvTimerName();

        mListAdapter = new TimePointListAdapter(mTimePointArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mListAdapter.setItemClickListener(this);

        mRvTimePointList.setLayoutManager(layoutManager);
        mRvTimePointList.setAdapter(mListAdapter);
        mRvTimePointList.setNestedScrollingEnabled(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private model.ScheduleItem getScheduleItem() {

        int schedule_ID = getIntent().getIntExtra(EXTRA_ID, -1);

        if (schedule_ID != -1) {
            return mDbLab.getScheduleItem(schedule_ID);
        } else {
            return new model.ScheduleItem();
        }
    }

    private void deleteSchedule() {

        mDbLab.deleteSchedule(mScheduleItem);
    }

    private void saveSchedule() {

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

        if (mClickedTimePointPos == -1) {

            args.putString(TimePickerDialog.EXTRA_MESSAGE, "");
            args.putInt(TimePickerDialog.EXTRA_HOUR, 0);
            args.putInt(TimePickerDialog.EXTRA_MINUTE, 0);

        } else {

            TimePoint clickedTimePoint = findTimePointByPos(mClickedTimePointPos);
            args.putString(TimePickerDialog.EXTRA_MESSAGE, clickedTimePoint.getTitle());
            args.putInt(TimePickerDialog.EXTRA_HOUR, clickedTimePoint.getHour());
            args.putInt(TimePickerDialog.EXTRA_MINUTE, clickedTimePoint.getMinute());
        }

        timePickerDialog.setArguments(args);

        timePickerDialog.show(getSupportFragmentManager(), "timePickerDialog");

    }

    @Override
    public void OnItemClickListener(int pos) {
        showExactTimePicker(pos);
    }



    private TimePoint findTimePointByPos(int pos) {

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

        mScheduleItem.setTitle(timerName);
        setTvTimerName();

    }

    void setTvTimerName() {

        if (mScheduleItem.getTitle().isEmpty()) {
            mTvTimerName.setText(R.string.timer_name);
        } else {
            mTvTimerName.setText(mScheduleItem.getTitle());
        }
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