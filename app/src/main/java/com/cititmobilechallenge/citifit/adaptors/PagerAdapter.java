package com.cititmobilechallenge.citifit.adaptors;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cititmobilechallenge.citifit.fragments.GoalsFragment;
import com.cititmobilechallenge.citifit.fragments.ProfileFragment;
import com.cititmobilechallenge.citifit.fragments.TasksFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by ashwiask on 10/28/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int noOfTabs) {
        super(fm);
        mNoOfTabs = noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;
        switch (position) {
            case 0:
                TasksFragment tasksFragment = new TasksFragment();
                fragment = tasksFragment;
                break;
            case 1:
                GoalsFragment goalsFragment = GoalsFragment.getInstance();

                fragment = goalsFragment;
                break;
            case 2:
                ProfileFragment profileFragment = new ProfileFragment();
                fragment = profileFragment;
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
