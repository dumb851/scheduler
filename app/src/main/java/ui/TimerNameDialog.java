package ui;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.zubrid.scheduletimer.R;

final public class TimerNameDialog extends DialogFragment {


    public static final String EXTRA_NAME = "EXTRA_NAME_BUNDLE";
    private TimerNameDialogListener mListener;
    private EditText mInputText;

    public interface TimerNameDialogListener {
        void onDialogDoneClick(String name);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int margin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);

        mInputText = new EditText(getActivity());

        Bundle bundle = getArguments();
        String name = bundle.getString(EXTRA_NAME);
        if (name != null) {
            mInputText.setText(name);
        }

        FrameLayout container = new FrameLayout(getActivity());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(margin, margin, margin, margin);

        mInputText.setLayoutParams(layoutParams);
        mInputText.setSingleLine();
        int pos = mInputText.getText().length();
        mInputText.setSelection(pos);

        container.addView(mInputText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.hint_name)
                .setView(container)
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogDoneClick(mInputText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //!mListener.onDialogCancelClick(TimerNameDialog.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            mListener = (TimerNameDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TimerNameDialogListener");
        }

    }
}
