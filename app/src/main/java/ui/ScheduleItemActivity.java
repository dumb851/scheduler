package ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zubrid.scheduler.R;

import data.DbLab;
import model.ScheduleItem;

public class ScheduleItemActivity extends AppCompatActivity {

    private DbLab mDbLab;
    private ScheduleItem mScheduleItem;
    private TextView mTitle;
    private Intent mIntent;
    private ImageView mIsRunningView;
    private boolean mIsRunning;

    public static String EXTRA_ID = "ScheduleItemActivity_EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBar();

        setContentView(R.layout.activity_schedule_item);

        initVariables();

        fillUI();

        mIsRunningView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIsRunning = !mIsRunning;
                setIsRunningView();

            }
        });
    }

    private void initVariables() {

        mDbLab = DbLab.getLab(this);

        mIntent = getIntent();

        mScheduleItem = getScheduleItem();
        mIsRunning = mScheduleItem.isRunning();

        mTitle = (TextView) findViewById(R.id.schedule_title);
        mIsRunningView = (ImageView) findViewById(R.id.schedule_iv_running);
    }

    private ScheduleItem getScheduleItem() {

        int schedule_ID = mIntent.getIntExtra(EXTRA_ID, -1);

        if (schedule_ID != -1) {
            return mDbLab.getScheduleItem(schedule_ID);
        } else {
            return new ScheduleItem();
        }
    }

    private void setActionBar() {

        final ActionBar actionBar = getSupportActionBar();

        final LayoutInflater inflater = (LayoutInflater) actionBar.getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_done_cancel, null);

        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mScheduleItem.setTitle(mTitle.getText().toString());
                        mScheduleItem.setIsRunning(mIsRunning);

                        mDbLab.saveSchedule(mScheduleItem);
                        finish();
                    }
                });
        customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // "Cancel"
                        finish();
                    }
                });

        // Show the custom action bar view and hide the normal Home icon and title.

        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);

        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void fillUI() {
        mTitle.setText(mScheduleItem.getTitle());

        setIsRunningView();
    }

    void setIsRunningView() {

        if (mIsRunning) {
            int[] state = new int[] {android.R.attr.state_checked};
            mIsRunningView.setImageState(state, false);
        } else {
            int[] state = new int[] {};
            mIsRunningView.setImageState(state, false);
        }
    }
}
