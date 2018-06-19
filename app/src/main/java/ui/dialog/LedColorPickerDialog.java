package ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zubrid.scheduletimer.R;

public class LedColorPickerDialog extends DialogFragment {

    public static final String EXTRA_LED_COLOR = "EXTRA_LED_COLOR";

    private LedColorPickerDialog.LedColorPickerDialogListener mListener;
    private int mColor;

    public interface LedColorPickerDialogListener {
        void onLedColorPickerDialogChanged(Bundle bundle);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_color_picker, null);

        RadioGroup radioGroup = rootView.findViewById(R.id.dialog_color_picker_radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_LED_COLOR, i);

                mListener.onLedColorPickerDialogChanged(bundle);

                dismiss();

            }
        });

//        mTimeSpinner = rootView.findViewById(R.id.dialog_time_picker_tp_spinner);
//        mTimeSpinner.setIs24HourView(true);
//
//        mMessage = rootView.findViewById(R.id.dialog_time_picker_et_message);
//
//        // clear button
//        ImageView clearButton = rootView.findViewById(R.id.dialog_time_picker_iv_clear);
//        clearButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mMessage.setText("");
//            }
//        });
//
//        // Args
//        Bundle bundle = getArguments();
//
//        String message = bundle.getString(EXTRA_MESSAGE);
//        if (message != null) {
//            mMessage.setText(message);
//            int pos = mMessage.getText().length();
//            mMessage.setSelection(pos);
//        }
//
//        int hour = bundle.getInt(EXTRA_HOUR, -1);
//        if (hour != -1) {
//            mTimeSpinner.setHour(hour);
//        }
//
//        int minute = bundle.getInt(EXTRA_MINUTE, -1);
//        if (minute != -1) {
//            mTimeSpinner.setMinute(minute);
//        }
//
//        final InputMethodManager imm = (InputMethodManager) getContext().
//                getSystemService(Context.INPUT_METHOD_SERVICE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.led_color).setView(rootView);

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            mListener = (LedColorPickerDialog.LedColorPickerDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LedColorPickerDialogListener");
        }
    }
}
