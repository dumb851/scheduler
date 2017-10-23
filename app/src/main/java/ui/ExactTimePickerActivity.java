package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TimePicker;

import com.zubrid.scheduler.R;

final public class ExactTimePickerActivity extends ActivityDoneCancelActionBar {

    private static String EXTRA_BUNDLE = "ExactTimePickerActivity_EXTRA_ID";

    public static String EXTRA_TIME_HOUR = "ExactTimePickerActivity_EXTRA_TIME_HOUR";
    public static String EXTRA_TIME_MINUTE = "ExactTimePickerActivity_EXTRA_TIME_MINUTE";
    public static String EXTRA_TITLE = "ExactTimePickerActivity_EXTRA_TITLE";

    TimePicker mTpExactTime;
    EditText mEtTitle;

    public static Intent getIntent(Context context, Bundle bundle) {

        Intent intent = new Intent(context, ExactTimePickerActivity.class);
        intent.putExtra(EXTRA_BUNDLE, bundle);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBar();

        setContentView(R.layout.activity_exact_time_picker);

        initVariables();

        //// TODO: 23.10.2017  fill UI EXTRA_BUNDLE
    }

    private void initVariables() {

        mEtTitle = (EditText) findViewById(R.id.acv_exact_time_picker_te_title);
        mTpExactTime = (TimePicker) findViewById(R.id.acv_exact_time_picker_tp_time_picker);
    }



    @Override
    void OnDoneClick() {

        Intent data = new Intent();

        data.putExtra(EXTRA_TIME_HOUR,  mTpExactTime.getHour());
        data.putExtra(EXTRA_TIME_MINUTE,  mTpExactTime.getMinute());
        data.putExtra(EXTRA_TITLE, mEtTitle.getText().toString());

        setResult(Activity.RESULT_OK, data);

        finish();

    }

    @Override
    void OnCancelClick() {
        finish();
    }
}
