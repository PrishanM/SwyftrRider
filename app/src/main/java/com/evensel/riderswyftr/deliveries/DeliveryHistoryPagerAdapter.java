package com.evensel.riderswyftr.deliveries;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Prishan Maduka on 3/6/2017.
 */
public class DeliveryHistoryPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> titles;

    public DeliveryHistoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public DeliveryHistoryPagerAdapter(FragmentManager fm, ArrayList<String> titles) {
        super(fm);
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new DeliveryHistoryPagerFragment();
        Bundle args = new Bundle();
        args.putString("Title",titles.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
