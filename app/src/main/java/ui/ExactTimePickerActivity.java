package ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TimePicker;

import com.zubrid.scheduler.R;

public class ExactTimePickerActivity extends ActivityDoneCancelActionBar {

    public static String EXTRA_TIME_HOUR = "ExactTimePickerActivity_EXTRA_TIME_HOUR";
    public static String EXTRA_TIME_MINUTE = "ExactTimePickerActivity_EXTRA_TIME_MINUTE";
    public static String EXTRA_DESCRIPTION = "ExactTimePickerActivity_EXTRA_DESCRIPTION";

    TimePicker mTpExactTime;
    EditText mEtDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBar();

        setContentView(R.layout.activity_exact_time_picker);

        initVariables();
    }

    private void initVariables() {

        mEtDescription = (EditText) findViewById(R.id.dlg_exact_time_picker_te_name);
        mTpExactTime = (TimePicker) findViewById(R.id.dlg_exact_time_picker_tp_time_picker);

    }

    @Override
    void OnDoneClick() {

        Intent data = new Intent();

        data.putExtra(EXTRA_TIME_HOUR,  mTpExactTime.getHour());
        data.putExtra(EXTRA_TIME_MINUTE,  mTpExactTime.getMinute());
        data.putExtra(EXTRA_DESCRIPTION, mEtDescription.getText().toString());

        setResult(Activity.RESULT_OK, data);

        finish();
    }

    @Override
    void OnCancelClick() {

        setResult(Activity.RESULT_CANCELED);
        finish();

    }
}
