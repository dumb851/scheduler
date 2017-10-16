package ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zubrid.scheduler.R;

public class ExactTimePickerActivity extends ActivityDoneCancelActionBar {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBar();

        setContentView(R.layout.activity_exact_time_picker);

    }

    @Override
    void OnDoneClick() {
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    void OnCancelClick() {
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        finish();
    }
}
