package com.cititmobilechallenge.citifit.adaptors;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cititmobilechallenge.citifit.common.Constants;
import com.cititmobilechallenge.citifit.fragments.GoalsFragment;
import com.cititmobilechallenge.citifit.fragments.ProfileFragment;
import com.cititmobilechallenge.citifit.fragments.TasksFragment;

/**
 * Created by ashwiask on 10/28/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNoOfTabs;
    private int mGoalSelectedPostion;
    private String mGoalPrice;
    private String mGoalPoints;
    private String mGoalDaysLeft;
    private String mGoalName;

    public PagerAdapter(FragmentManager fm, int noOfTabs, int goalSelectedPos, String goalPrice, String goalPoints, String goalDays, String goalName) {
        super(fm);
        mNoOfTabs = noOfTabs;
        mGoalSelectedPostion = goalSelectedPos;
        mGoalPrice = goalPrice;
        mGoalDaysLeft = goalDays;
        mGoalPoints = goalPoints;
        mGoalName = goalName;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = TasksFragment.getInstance();
                break;
            case 1:
                fragment = GoalsFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.GOAL_SELECTED_POSITION_TAG, mGoalSelectedPostion);
                bundle.putString(Constants.GOAL_PRICE, mGoalPrice);
                bundle.putString(Constants.GOAL_POINTS, mGoalPoints);
                bundle.putString(Constants.GOAL_DAYS_LEFT, mGoalDaysLeft);
                bundle.putString(Constants.GOAL_NAME, mGoalName);
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = ProfileFragment.getInstance();
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
