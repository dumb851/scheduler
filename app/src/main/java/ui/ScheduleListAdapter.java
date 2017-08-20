package ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zubrid.scheduler.R;

import java.util.ArrayList;

import data.DbLab;
import model.ScheduleItem;


public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>{

    public static final String TAG = ScheduleListAdapter.class.getSimpleName();

    private ArrayList<ScheduleItem> mDataSet;
    private Context mContext;

    // Constructor
    public ScheduleListAdapter(Context context) {
        mContext = context;
        refreshDataSet();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.schedulelist_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ScheduleItem scheduleItem = mDataSet.get(position);

        holder.setScheduleTitle(scheduleItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView scheduleTitle;

        public ViewHolder(View v) {
            super(v);

            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            scheduleTitle = (TextView) v.findViewById(R.id.schedulelist_item_title);
        }

        public void setScheduleTitle(String title) {
            scheduleTitle.setText(title);
        }
    }

    public void refreshDataSet() {

        mDataSet = DbLab.getLab(mContext).getScheduleItemList();

    }

}
