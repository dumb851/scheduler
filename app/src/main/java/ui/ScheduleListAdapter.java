package ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zubrid.scheduler.R;


public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>{

    public static final String TAG = ScheduleListAdapter.class.getSimpleName();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.schedulelist_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: pos = " + position);
    }

    @Override
    public int getItemCount() {
        return 10; //!temporarily
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            //textView = (TextView) v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            //!return textView;
            return null;
        }
    }

}
