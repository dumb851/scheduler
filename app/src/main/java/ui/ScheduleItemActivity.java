package ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zubrid.scheduler.R;

import data.DbLab;
import model.ScheduleItem;

public final class ScheduleItemActivity extends ActivityDoneCancelActionBar {

    private DbLab mDbLab;
    private ScheduleItem mScheduleItem;
    private TextView mTvTitle;
    private Intent mIntent;
    private Button mBtnAddPoint;
    private RecyclerView mRvTimePointList;

    //!private static int EXACT_TIME_PICKER_REQUEST = 27;

    private static String EXTRA_ID = "ScheduleItemActivity_EXTRA_ID";

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
                showExactTimePicker(0);
            }
        });

    }

    private void initVariables() {

        mDbLab = DbLab.getLab(this);

        mIntent = getIntent();

        mScheduleItem = getScheduleItem();

        mTvTitle = (TextView) findViewById(R.id.schedule_item_title);
        mBtnAddPoint = (Button) findViewById(R.id.schedule_item_btn_add_point);
        mRvTimePointList = (RecyclerView) findViewById(R.id.schedule_item_rv_time_point_list);

    }

    private ScheduleItem getScheduleItem() {

        int schedule_ID = mIntent.getIntExtra(EXTRA_ID, -1);

        if (schedule_ID != -1) {
            return mDbLab.getScheduleItem(schedule_ID);
        } else {
            return new ScheduleItem();
        }
    }

    private void fillUI() {

        mTvTitle.setText(mScheduleItem.getTitle());

        TimePointListAdapter listAdapter = new TimePointListAdapter(this, mScheduleItem.getID());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mRvTimePointList.setLayoutManager(layoutManager);
        mRvTimePointList.setAdapter(listAdapter);
        mRvTimePointList.setNestedScrollingEnabled(false);

    }

    @Override
    void OnDoneClick() {

        mScheduleItem.setTitle(mTvTitle.getText().toString());
        mDbLab.saveSchedule(mScheduleItem);
        finish();

    }

    @Override
    void OnCancelClick() {
        finish();
    }

    private void showExactTimePicker(int id) {

        Intent intent = ExactTimePickerActivity.getIntent(this, id);
        startActivity(intent);

    }

//!    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        //// TODO: 15.10.2017
//        if (requestCode == EXACT_TIME_PICKER_REQUEST) {
//
//            if (resultCode != Activity.RESULT_OK) {
//
//                Toast.makeText(this, "NOT_RESULT_OK", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            String title = data.getStringExtra(ExactTimePickerActivity.EXTRA_TITLE);
//            int hour = data.getIntExtra(ExactTimePickerActivity.EXTRA_TIME_HOUR, -1);
//            int minute = data.getIntExtra(ExactTimePickerActivity.EXTRA_TIME_MINUTE, -1);
//
//            Toast.makeText(this, "RESULT_OK", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}
