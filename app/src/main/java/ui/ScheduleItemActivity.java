package ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zubrid.scheduler.R;

import data.DbLab;
import model.ScheduleItem;

public final class ScheduleItemActivity extends ActivityDoneCancelActionBar {

    private DbLab mDbLab;
    private ScheduleItem mScheduleItem;
    private TextView mTvTitle;
    private Intent mIntent;
    private Button mBtnAddPoint;
    private static int EXACT_TIME_PICKER_REQUEST = 27;

    public static String EXTRA_ID = "ScheduleItemActivity_EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBar();

        setContentView(R.layout.activity_schedule_item);

        initVariables();

        fillUI();

        mBtnAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExactTimePicker();
            }
        });

    }

    private void initVariables() {

        mDbLab = DbLab.getLab(this);

        mIntent = getIntent();

        mScheduleItem = getScheduleItem();

        mTvTitle = (TextView) findViewById(R.id.schedule_item_title);
        mBtnAddPoint = (Button) findViewById(R.id.schedule_item_btn_add_point);

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

    private void showExactTimePicker() {

        Intent intent = new Intent(ScheduleItemActivity.this, ExactTimePickerActivity.class);
        startActivityForResult(intent, EXACT_TIME_PICKER_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //// TODO: 15.10.2017
        if (requestCode == EXACT_TIME_PICKER_REQUEST) {

            if (resultCode != Activity.RESULT_OK) {

                Toast.makeText(this, "NOT_RESULT_OK", Toast.LENGTH_SHORT).show();
                return;
            }

            String description = data.getStringExtra(ExactTimePickerActivity.EXTRA_DESCRIPTION);
            int hour = data.getIntExtra(ExactTimePickerActivity.EXTRA_TIME_HOUR, -1);
            int minute = data.getIntExtra(ExactTimePickerActivity.EXTRA_TIME_MINUTE, -1);

            Toast.makeText(this, "RESULT_OK", Toast.LENGTH_SHORT).show();
        }

    }
}
