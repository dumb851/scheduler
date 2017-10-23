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
import model.TimePoint;

final public class TimePointListAdapter extends RecyclerView.Adapter<TimePointListAdapter.ViewHolder>
        implements DbLab.TimePointListListener {

    public static final String TAG = TimePointListAdapter.class.getSimpleName();

    private ArrayList<TimePoint> mDataSet;
    private Context mContext;
    private static ItemClickListener sItemClickListener;
    private int mScheduleID;

    // constructor
    public TimePointListAdapter(Context context, int scheduleID) {
        mContext = context;
        mScheduleID = scheduleID;
        refreshDataSet();
    }

    public interface ItemClickListener {
        void OnItemClickListener(int id);
    }

    public void setItemClickListener(ItemClickListener listener) {
        sItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.time_point_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TimePoint item = mDataSet.get(position);
        holder.setViewTitle(item.getTitle());
        holder.setID(item.getID());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView viewTitle;
//!        private final TextView scheduleViewID;
//        private final ImageView isRunningImage;
//        private final TextView isRunningText;

        private int id;

        // constructor
        ViewHolder(View v) {
            super(v);

            //Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sItemClickListener != null) {
                        sItemClickListener.OnItemClickListener(id);
                    }
                }
            });

            viewTitle = v.findViewById(R.id.time_point_list_item_tv_title);
//!            scheduleViewID = v.findViewById(R.id.schedulelist_item_id);

        }

        void setViewTitle(String title) {
            viewTitle.setText(title);
        }

        void setID(int timePointID) {
            id = timePointID;
            //scheduleViewID.setText(String.valueOf(scheduleID));
        }

    }

    private void refreshDataSet() {
        //// TODO: 23.10.2017 data set should come from ScheduleItemActivity
        mDataSet = DbLab.getLab(mContext).getTimePointList(mScheduleID);
    }

    @Override
    public void timePointListChanged() {
        refreshDataSet();
        //notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        DbLab.registerTimePointListListener(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        DbLab.unregisterTimePointListListener(this);
    }
}
