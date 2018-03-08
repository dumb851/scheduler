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
import android.widget.Button;

import com.zubrid.scheduletimer.R;

import java.util.Collections;

import utils.NotificationHelper;
import utils.NotificationProperties;

/**
 * A placeholder fragment containing a simple view.
 */
final public class MainActivityFragment extends Fragment implements ScheduleListAdapter.ItemClickListener {

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    protected RecyclerView mRecyclerViewScheduleList;
    protected ScheduleListAdapter mScheduleListAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

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

        //! test button
        Button btn_test_notify = rootView.findViewById(R.id.btn_test_notify);
        btn_test_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotificationProperties properties = new NotificationProperties();

                NotificationHelper notificationHelper = new NotificationHelper(getActivity(), properties);

                notificationHelper.show();
            }
        });

        return rootView;
    }

    private void setItemTouchHelper() {

        ItemTouchHelper.Callback touchHelperCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);

            }

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {

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

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);

                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    mScheduleListAdapter.selectItem(viewHolder);
                } else {

                    mScheduleListAdapter.unselectItems();
                }

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
