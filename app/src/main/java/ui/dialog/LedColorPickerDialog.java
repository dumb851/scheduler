package ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
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
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {

                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_LED_COLOR, getColorById(radioButtonId));

                mListener.onLedColorPickerDialogChanged(bundle);

                dismiss();

            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.led_color).setView(rootView);

        return builder.create();

    }

    private int getColorById(int radioButtonId) {

        switch (radioButtonId) {

            case R.id.dialog_color_picker_rb_red:
                return ContextCompat.getColor(getContext(), R.color.color_LED_red);

            case R.id.dialog_color_picker_rb_orange:
                return ContextCompat.getColor(getContext(), R.color.color_LED_orange);

            case R.id.dialog_color_picker_rb_yellow:
                return ContextCompat.getColor(getContext(), R.color.color_LED_yellow);

            case R.id.dialog_color_picker_rb_green:
                return ContextCompat.getColor(getContext(), R.color.color_LED_green);

            case R.id.dialog_color_picker_rb_blue:
                return ContextCompat.getColor(getContext(), R.color.color_LED_blue);

            case R.id.dialog_color_picker_rb_cyan:
                return ContextCompat.getColor(getContext(), R.color.color_LED_cyan);

            case R.id.dialog_color_picker_rb_white:
                return ContextCompat.getColor(getContext(), R.color.color_LED_white);

            default:
                return 0;
        }

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
