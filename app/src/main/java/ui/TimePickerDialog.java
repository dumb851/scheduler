package ui;


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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.zubrid.scheduletimer.R;

final public class TimePickerDialog extends DialogFragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE_BUNDLE";
    public static final String EXTRA_HOUR = "EXTRA_HOUR_BUNDLE";
    public static final String EXTRA_MINUTE = "EXTRA_MINUTE_BUNDLE";

    private TimePickerDialogListener mListener;
    private EditText mMessage;
    private TimePicker mTimeSpinner;

   public interface TimePickerDialogListener {
        void onTimePickerDialogDoneClick(Bundle bundle);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_time_picker, null);

        mTimeSpinner = rootView.findViewById(R.id.dialog_time_picker_tp_spinner);
        mTimeSpinner.setIs24HourView(true);

        mMessage = rootView.findViewById(R.id.dialog_time_picker_et_message);

        // clear button
        ImageView clearButton = rootView.findViewById(R.id.dialog_time_picker_iv_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMessage.setText("");
            }
        });

        // Args
        Bundle bundle = getArguments();

        String message = bundle.getString(EXTRA_MESSAGE);
        if (message != null) {
            mMessage.setText(message);
            int pos = mMessage.getText().length();
            mMessage.setSelection(pos);
        }

        int hour = bundle.getInt(EXTRA_HOUR, -1);
        if (hour != -1) {
           mTimeSpinner.setHour(hour);
        }

        int minute = bundle.getInt(EXTRA_MINUTE, -1);
        if (minute != -1) {
            mTimeSpinner.setMinute(minute);
        }

        final InputMethodManager imm = (InputMethodManager) getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.hint_name)
                .setView(rootView)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (imm != null) {
                            imm.hideSoftInputFromWindow(mMessage.getWindowToken(), 0);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString(EXTRA_MESSAGE, mMessage.getText().toString());
                        bundle.putInt(EXTRA_HOUR, mTimeSpinner.getHour());
                        bundle.putInt(EXTRA_MINUTE, mTimeSpinner.getMinute());

                        mListener.onTimePickerDialogDoneClick(bundle);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (imm != null) {
                            imm.hideSoftInputFromWindow(mMessage.getWindowToken(), 0);
                        }
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            mListener = (TimePickerDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TimePickerDialogListener");
        }

    }
}
