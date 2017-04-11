package com.evensel.riderswyftr.deliveries;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evensel.riderswyftr.R;
import com.evensel.riderswyftr.util.AppController;
import com.evensel.riderswyftr.util.Constants;
import com.evensel.riderswyftr.util.Datum;
import com.evensel.riderswyftr.util.JsonRequestManager;
import com.evensel.riderswyftr.util.Notifications;
import com.evensel.riderswyftr.util.OrderHistoryResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Prishan Maduka on 2/12/2017.
 */
public class DeliveryHistoryFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressDialog progress;
    private SharedPreferences sharedPref;
    private View layout;
    private LayoutInflater inflate;
    private String token;
    private DeliveryHistoryPagerAdapter deliveryHistoryPagerAdapter;
    private ArrayList<Datum> thisWeekList = new ArrayList<>();
    private ArrayList<Datum> olderList = new ArrayList<>();

    public DeliveryHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_delivery_history, container, false);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        sharedPref = getActivity().getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        token = sharedPref.getString(Constants.LOGIN_ACCESS_TOKEN, "");
        inflate = inflater;
        layout = inflate.inflate(R.layout.custom_toast_layout,(ViewGroup) rootView.findViewById(R.id.toast_layout_root));

        addFiles();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void setupViewPager() {

        ArrayList<String> titles = new ArrayList<>();
        titles.add("This Week");
        titles.add("Last Week");

        deliveryHistoryPagerAdapter =
                new DeliveryHistoryPagerAdapter(getChildFragmentManager(),titles);
        viewPager.setAdapter(deliveryHistoryPagerAdapter);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void addFiles() {

        /*progress = ProgressDialog.show(getActivity(), null,
                "Loading...", true);
        Log.d("xxxxxxxxxx",token);
        JsonRequestManager.getInstance(getActivity()).getOrderHistoryRequest(AppURL.APPLICATION_BASE_URL+AppURL.GET_ORDER_HISTORY__URL+token,token, getOrderHistoryCallback);*/

        Datum datum = new Datum();
        datum.setOrderId((long) 1);
        datum.setDeliveredLocation("295/2 Stanley Thilakarathna Mawatha Nugegoda");
        datum.setOrderDeliveredTime("2016-10-10 10:31:00");
        thisWeekList.add(datum);

        Datum datum2 = new Datum();
        datum2.setOrderId((long) 2);
        datum2.setDeliveredLocation("295/2 Stanley Thilakarathna Mawatha Nugegoda");
        datum2.setOrderDeliveredTime("2016-10-10 10:31:00");
        thisWeekList.add(datum2);

        Datum datum3 = new Datum();
        datum3.setOrderId((long) 3);
        datum3.setDeliveredLocation("295/2 Stanley Thilakarathna Mawatha Nugegoda");
        datum3.setOrderDeliveredTime("2016-10-10 10:31:00");
        thisWeekList.add(datum3);



        Datum datum4 = new Datum();
        datum4.setOrderId((long) 4);
        datum4.setDeliveredLocation("295/2 Stanley Thilakarathna Mawatha Nugegoda");
        datum4.setOrderDeliveredTime("2016-10-10 10:31:00");
        olderList.add(datum4);

        Datum datum5 = new Datum();
        datum5.setOrderId((long) 5);
        datum5.setDeliveredLocation("295/2 Stanley Thilakarathna Mawatha Nugegoda");
        datum5.setOrderDeliveredTime("2016-10-10 10:31:00");
        olderList.add(datum5);

        AppController.setOlderList(olderList);
        AppController.setThisWeekList(thisWeekList);

        setupViewPager();

    }

    //Response callback for "Get Delivery History"
    private final JsonRequestManager.getOrderHistory getOrderHistoryCallback = new JsonRequestManager.getOrderHistory() {

        @Override
        public void onSuccess(OrderHistoryResponse model) {

            if(progress!=null)
                progress.dismiss();

            if(model.getStatus().equalsIgnoreCase("success")){
                /*for (Datum datum:model.getDetails().getData()){
                    if(isThisWeek(datum.getOrderDeliveredTime())){
                        thisWeekList.add(datum);
                    }else{
                        olderList.add(datum);
                    }
                }*/
            }else{
                Notifications.showToastMessage(layout,getActivity(),model.getMessage()).show();
            }


        }

        @Override
        public void onError(String status) {
            if(progress!=null)
                progress.dismiss();
            Notifications.showToastMessage(layout,getActivity(),status).show();
        }

        @Override
        public void onError(OrderHistoryResponse model) {
            if(progress!=null)
                progress.dismiss();

            Notifications.showToastMessage(layout,getActivity(),model.getMessage()).show();
        }
    };

    private boolean isThisWeek(String dateTime){
        Date date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date monday = c.getTime();

        Date nextMonday= new Date(monday.getTime()+7*24*60*60*1000);

        return date.after(monday) && date.before(nextMonday);
    }
}
