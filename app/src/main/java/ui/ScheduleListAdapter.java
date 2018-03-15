package ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zubrid.scheduletimer.R;

import java.util.ArrayList;
import java.util.Random;

import data.DbLab;
import model.ScheduleItem;
import utils.AlarmHelper;


final public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>
        implements DbLab.ScheduleItemListListener {

    public static final String TAG = ScheduleListAdapter.class.getSimpleName();

    private ArrayList<ScheduleItem> mDataSet;
    private Context mContext;
    private static ItemClickListener sItemClickListener;
    private static RecyclerView.ViewHolder sSelectedItem;
    private static int sSelectedItemPos;
    private static DbLab mDbLab;

    static {
        sSelectedItem = null;
        sSelectedItemPos = -1;
    }

    // Constructor
    public ScheduleListAdapter(Context context) {

        mContext = context;
        mDbLab = DbLab.getLab(mContext);
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
        holder.setSortOrder(scheduleItem.getSortOrder());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvScheduleTitle;
        private final TextView mTvScheduleID;
        private final ImageView mIvIsRunning;
        private final TextView mTvIsRunning;
        private final TextView mTvSortOrder;

        private int ID;

        // constructor
        ViewHolder(View v) {
            super(v);

            View containerView = v.findViewById(R.id.schedulelist_item_container);
            // Define click listener for the ViewHolder's View.
            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sItemClickListener != null) {
                        sItemClickListener.OnItemClickListener(ID);
                    }
                }
            });

            mTvScheduleTitle = v.findViewById(R.id.schedulelist_item_title);
            mTvScheduleID = v.findViewById(R.id.schedulelist_item_id);
            mIvIsRunning = v.findViewById(R.id.schedulelist_item_iv_running);
            mTvIsRunning = v.findViewById(R.id.schedulelist_item_tv_running);
            mTvSortOrder = v.findViewById(R.id.schedulelist_item_tv_sort_order);

            mIvIsRunning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScheduleItem scheduleItem = DbLab.changeScheduleRunningState(ID);

                    if (scheduleItem.isRunning()) {

                        AlarmHelper.runSchedule(view.getContext(), scheduleItem);
                    } else {
                        AlarmHelper.stopSchedule(view.getContext(), scheduleItem);
                    }

                }
            });
        }

        void setScheduleViewTitle(String title) {
            mTvScheduleTitle.setText(title);
        }

        void setScheduleViewID(int scheduleID) {
            ID = scheduleID;
            mTvScheduleID.setText(String.valueOf(scheduleID));
        }

        void setIsRunningView(boolean isRunning) {

            if (isRunning) {
                int[] state = new int[]{android.R.attr.state_checked};
                mIvIsRunning.setImageState(state, false);

                mTvIsRunning.setText(R.string.running);

            } else {
                int[] state = new int[]{};
                mIvIsRunning.setImageState(state, false);

                mTvIsRunning.setText(R.string.not_running);
            }
        }

        void setSortOrder(float sortOrder) {
            mTvSortOrder.setText(String.valueOf(sortOrder));
        }

    }

    void selectItem(RecyclerView.ViewHolder holder) {

        holder.itemView.setSelected(true);

        sSelectedItem = holder;
        sSelectedItemPos = holder.getLayoutPosition();

    }

    void unselectItems() {

        if (sSelectedItem != null) {

            sSelectedItem.itemView.setSelected(false);
            reorderScheduleItem(sSelectedItem);

        }

        sSelectedItem = null;
        sSelectedItemPos = -1;

    }

    private void reorderScheduleItem(RecyclerView.ViewHolder holder) {

        int pos = holder.getLayoutPosition();

        if (pos == sSelectedItemPos) {
            return;
        }

        ScheduleItem currentItem = mDataSet.get(pos);

        float newSortOrder;
        float min;
        float max;

        Random random = new Random();

        if (pos == 0) {

            ScheduleItem nextItem = mDataSet.get(pos + 1);

            min = 0f;
            max = nextItem.getSortOrder();

        } else if (pos == mDataSet.size() - 1) {

            ScheduleItem prevItem = mDataSet.get(pos - 1);

            min = prevItem.getSortOrder();
            max = min + 100f;

        } else {

            ScheduleItem nextItem = mDataSet.get(pos + 1);
            ScheduleItem prevItem = mDataSet.get(pos - 1);

            min = prevItem.getSortOrder();
            max = nextItem.getSortOrder();
        }

        newSortOrder = min + random.nextFloat() * (max - min);

        currentItem.setSortOrder(newSortOrder);
        mDbLab.saveSchedule(currentItem);

    }

    private void refreshDataSet() {
        mDataSet = mDbLab.getScheduleItemList();
    }

    public ArrayList<ScheduleItem> getDataSet() {
        return mDataSet;
    }

    @Override
    public void onScheduleItemListChanged() {
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
