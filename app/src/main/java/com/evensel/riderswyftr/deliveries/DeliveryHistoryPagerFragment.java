package com.evensel.riderswyftr.deliveries;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evensel.riderswyftr.R;
import com.evensel.riderswyftr.util.AppController;

/**
 * Created by Prishan Maduka on 2/12/2017.
 */
public class DeliveryHistoryPagerFragment extends Fragment {

    private RecyclerView recyclerView;
    private DeliveryHistoryRecycleAdapter deliveryHistoryRecycleAdapter;
    private TextView category;

    public DeliveryHistoryPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_del_history, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        if(this.getArguments().getString("Title").equalsIgnoreCase("This Week")){
            deliveryHistoryRecycleAdapter = new DeliveryHistoryRecycleAdapter(getActivity(), AppController.getThisWeekList());
        }else{
            deliveryHistoryRecycleAdapter = new DeliveryHistoryRecycleAdapter(getActivity(),AppController.getOlderList());
        }

        recyclerView.setAdapter(deliveryHistoryRecycleAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
