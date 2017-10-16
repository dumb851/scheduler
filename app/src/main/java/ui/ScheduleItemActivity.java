package ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zubrid.scheduler.R;

import data.DbLab;
import model.ScheduleItem;

public class ScheduleItemActivity extends ActivityDoneCancelActionBar {

    private DbLab mDbLab;
    private ScheduleItem mScheduleItem;
    private TextView mTvTitle;
    private Intent mIntent;
    private Button mBtnAddPoint;

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

    private void showExactTimePicker() {
        //// TODO: 15.10.2017

        Intent intent = new Intent(ScheduleItemActivity.this, ExactTimePickerActivity.class);
        startActivity(intent);

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
}
