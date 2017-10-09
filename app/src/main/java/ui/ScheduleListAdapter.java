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


public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {

    public static final String TAG = ScheduleListAdapter.class.getSimpleName();

    private ArrayList<ScheduleItem> mDataSet;
    private Context mContext;
    private static ItemClickListener sItemClickListener;

    // Constructor
    public ScheduleListAdapter(Context context) {
        mContext = context;
        refreshDataSet();
    }

    public interface ItemClickListener {
        void OnItemClickListener(int ID);
    }

    public void setItemClickListener(ItemClickListener listener) {
        sItemClickListener = listener;
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
        holder.setScheduleViewTitle(scheduleItem.getTitle());
        holder.setScheduleViewID(scheduleItem.getID());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView scheduleViewTitle;
        private final TextView scheduleViewID;
        private int ID;

        // constructor
        ViewHolder(View v) {
            super(v);

            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sItemClickListener != null) {
                        // TODO: 05.10.2017
                        //should it be an id of schedule? to open it later
                        sItemClickListener.OnItemClickListener(ID);
                    }
                }
            });

            scheduleViewTitle = v.findViewById(R.id.schedulelist_item_title);
            scheduleViewID = v.findViewById(R.id.schedulelist_item_id);
        }

        void setScheduleViewTitle(String title) {
            scheduleViewTitle.setText(title);
        }

        void setScheduleViewID(int scheduleID) {
            ID = scheduleID;
            scheduleViewID.setText(String.valueOf(scheduleID));
        }
    }

    public void refreshDataSet() {
        mDataSet = DbLab.getLab(mContext).getScheduleItemList();
    }

}
