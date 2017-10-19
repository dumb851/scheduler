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

//// TODO: 19.10.2017 do i need a recycler view? may be something else?
//RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
//        recycler.setNestedScrollingEnabled(false);


final public class TimePointListAdapter extends RecyclerView.Adapter<TimePointListAdapter.ViewHolder>
    implements DbLab.ScheduleItemListListener{

    public static final String TAG = TimePointListAdapter.class.getSimpleName();

    private ArrayList<TimePoint> mDataSet;
    private Context mContext;
    //private static ItemClickListener sItemClickListener;

    // Constructor
    public TimePointListAdapter(Context context) {
        mContext = context;
        refreshDataSet();
       //! DbLab.registerScheduleItemListListener(this);
    }

    public interface ItemClickListener {
        void OnItemClickListener(int ID);
    }

//    public void setItemClickListener(ItemClickListener listener) {
//        sItemClickListener = listener;
//    }

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

        private int ID;

        // constructor
        ViewHolder(View v) {
            super(v);

            // Define click listener for the ViewHolder's View.
//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (sItemClickListener != null) {
//                        sItemClickListener.OnItemClickListener(ID);
//                    }
//                }
//            });

//            scheduleViewTitle = v.findViewById(R.id.schedulelist_item_title);
//            scheduleViewID = v.findViewById(R.id.schedulelist_item_id);
//            isRunningImage = v.findViewById(R.id.schedulelist_item_iv_running);
//            isRunningText = v.findViewById(R.id.schedulelist_item_tv_running);
//
//            isRunningImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DbLab.changeScheduleRunningState(ID);
//                }
//            });
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
        //// TODO: 19.10.2017  0-temp
        mDataSet = DbLab.getLab(mContext).getTimePointList(0);
    }

    @Override
    public void scheduleItemListChanged() {
        refreshDataSet();
        //notifyDataSetChanged();
    }
}
