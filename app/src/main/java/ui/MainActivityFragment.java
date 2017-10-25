package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zubrid.scheduler.R;

import java.util.Collections;

/**
 * A placeholder fragment containing a simple view.
 */
final public class MainActivityFragment extends Fragment implements ScheduleListAdapter.ItemClickListener{

    protected RecyclerView mRecyclerViewScheduleList;
    protected ScheduleListAdapter mScheduleListAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerViewScheduleList = rootView.findViewById(R.id.rv_schedule_list);

        mScheduleListAdapter = new ScheduleListAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewScheduleList.setLayoutManager(mLayoutManager);

        mRecyclerViewScheduleList.setAdapter(mScheduleListAdapter);
        mScheduleListAdapter.setItemClickListener(this);

        setItemTouchHelper();

        return rootView;
    }

    private void setItemTouchHelper() {

        ItemTouchHelper.Callback touchHelperCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);

                //!return 0;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                //!return false;

                Collections.swap(mScheduleListAdapter.getDataSet(),
                        viewHolder.getAdapterPosition(),
                        target.getAdapterPosition()
                );

                mScheduleListAdapter.notifyItemMoved(viewHolder.getAdapterPosition(),
                        target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);

        touchHelper.attachToRecyclerView(mRecyclerViewScheduleList);

    }

    @Override
    public void OnItemClickListener(int id) {

        Intent intent = ScheduleItemActivity.getIntent(getContext(), id);
        startActivity(intent);

    }
}
