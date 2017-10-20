package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zubrid.scheduler.R;

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

        return rootView;
    }

    @Override
    public void OnItemClickListener(int id) {

        Intent intent = ScheduleItemActivity.getIntent(getContext(), id);
        startActivity(intent);

    }
}
