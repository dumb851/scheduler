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

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_timer_name, null);
        mInputText = rootView.findViewById(R.id.dialog_timer_name_et_name);

        // clear button
        ImageView clearButton = rootView.findViewById(R.id.dialog_timer_name_iv_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputText.setText("");
            }
        });

        Bundle bundle = getArguments();
        String name = bundle.getString(EXTRA_NAME);
        if (name != null) {
            mInputText.setText(name);
            int pos = mInputText.getText().length();
            mInputText.setSelection(pos);
        }

        final InputMethodManager imm = (InputMethodManager) getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.timer_name)
                .setView(rootView)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (imm != null) {
                            imm.hideSoftInputFromWindow(mInputText.getWindowToken(), 0);
                        }

                        mListener.onDialogDoneClick(mInputText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (imm != null) {
                            imm.hideSoftInputFromWindow(mInputText.getWindowToken(), 0);
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
            mListener = (TimerNameDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TimerNameDialogListener");
        }

    }
}
