package ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zubrid.scheduler.R;

import java.util.ArrayList;

import data.DbLab;
import model.ScheduleItem;


final public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>
    implements DbLab.ScheduleItemListListener{

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

        holder.setIsRunningView(scheduleItem.isRunning());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mScheduleViewTitle;
        private final TextView mScheduleViewID;
        private final ImageView mIsRunningImage;
        private final TextView mIsRunningText;
        //!private final ConstraintLayout mContainer;

        private int ID;

        // constructor
        ViewHolder(View v) {
            super(v);

            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sItemClickListener != null) {
                        sItemClickListener.OnItemClickListener(ID);
                    }
                }
            });

            mScheduleViewTitle = v.findViewById(R.id.schedulelist_item_title);
            mScheduleViewID = v.findViewById(R.id.schedulelist_item_id);
            mIsRunningImage = v.findViewById(R.id.schedulelist_item_iv_running);
            mIsRunningText = v.findViewById(R.id.schedulelist_item_tv_running);

//!           Drawable drawable = v.getBackground();
//            int[] state = new int[] {android.R.attr.state_selected};
//            drawable.setState(state);

//!            mContainer = v.findViewById(R.id.schedulelist_item_container);
//            mContainer.setSelected(true);

            mIsRunningImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbLab.changeScheduleRunningState(ID);
                }
            });
        }

        void setScheduleViewTitle(String title) {
            mScheduleViewTitle.setText(title);
        }

        void setScheduleViewID(int scheduleID) {
            ID = scheduleID;
            mScheduleViewID.setText(String.valueOf(scheduleID));
        }

        void setIsRunningView(boolean isRunning) {

            if (isRunning) {
                int[] state = new int[] {android.R.attr.state_checked};
                mIsRunningImage.setImageState(state, false);

                mIsRunningText.setText(R.string.running);

            } else {
                int[] state = new int[] {};
                mIsRunningImage.setImageState(state, false);

                mIsRunningText.setText(R.string.not_running);
            }
        }



//!        public void setDragDropState(boolean isDraging) {
//            mContainer.setSelected(isDraging);
//        }
    }

    private void refreshDataSet() {
        mDataSet = DbLab.getLab(mContext).getScheduleItemList();
    }

    public ArrayList<ScheduleItem> getDataSet() {
        return mDataSet;
    }

    @Override
    public void scheduleItemListChanged() {
        refreshDataSet();
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        DbLab.registerScheduleItemListListener(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        DbLab.unregisterScheduleItemListListener(this);
    }
}
