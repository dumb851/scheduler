package ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zubrid.scheduler.R;

import java.util.ArrayList;

import data.DbLab;
import model.TimePoint;

final public class TimePointListAdapter extends RecyclerView.Adapter<TimePointListAdapter.ViewHolder>
    implements DbLab.TimePointListListener{

    public static final String TAG = TimePointListAdapter.class.getSimpleName();

    private ArrayList<TimePoint> mDataSet;
    private Context mContext;
    private static ItemClickListener sItemClickListener;
    private int mScheduleID;

    // Constructor
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
//        holder.setScheduleViewTitle(item.getTitle());
//        holder.setScheduleViewID(item.getID());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

//        private final TextView scheduleViewTitle;
//        private final TextView scheduleViewID;
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

//            scheduleViewTitle = v.findViewById(R.id.schedulelist_item_title);
//            scheduleViewID = v.findViewById(R.id.schedulelist_item_id);
//            isRunningImage = v.findViewById(R.id.schedulelist_item_iv_running);
//            isRunningText = v.findViewById(R.id.schedulelist_item_tv_running);
//

        }

//        void setScheduleViewTitle(String title) {
//            scheduleViewTitle.setText(title);
//        }
//
//        void setScheduleViewID(int scheduleID) {
//            ID = scheduleID;
//            scheduleViewID.setText(String.valueOf(scheduleID));
//        }

    }

    private void refreshDataSet() {
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
