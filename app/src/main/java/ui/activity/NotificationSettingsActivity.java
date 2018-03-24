package ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.zubrid.scheduletimer.R;

public class NotificationSettingsActivity extends AppCompatActivity {

    private static String EXTRA_ID = "NotificationSettings_EXTRA_ID";

    public static Intent getIntent(Context context, int scheduleID) {

        Intent intent = new Intent(context, NotificationSettingsActivity.class);
        intent.putExtra(EXTRA_ID, scheduleID);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification_settings);

        setToolbar();

//        initVariables();
//
//        fillUI();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
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
            actionBar.setTitle(R.string.notification_settings);
        }
    }
}
