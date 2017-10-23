package ui;

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
    private static ItemClickListener sItemClickListener;

    // constructor
    public TimePointListAdapter(ArrayList<TimePoint> timePointArrayList) {
        mDataSet = timePointArrayList;
    }

    public interface ItemClickListener {
        void OnItemClickListener(int pos);
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
        holder.setPos(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView viewTitle;

        private int pos;

        // constructor
        ViewHolder(View v) {
            super(v);

            //Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sItemClickListener != null) {
                        sItemClickListener.OnItemClickListener(pos);
                    }
                }
            });

            viewTitle = v.findViewById(R.id.time_point_list_item_tv_title);

        }

        void setViewTitle(String title) {
            viewTitle.setText(title);
        }

        void setPos(int pos) {
            this.pos = pos;
        }

    }

    void refreshDataSet(ArrayList<TimePoint> timePointArrayList) {
        mDataSet = timePointArrayList;
        notifyDataSetChanged();
    }

    @Override
    public void timePointListChanged() {
        //!refreshDataSet();
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
