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

    // From Notification
    private String mTask;
    private String mGoalUnit;
    private String mGoalValue;
    private String mPoints;

    public PagerAdapter(FragmentManager fm, int noOfTabs, int goalSelectedPos, String goalPrice, String goalPoints, String goalDays, String goalName, String task, String goalUnit, String goalValue, String points) {
        super(fm);
        mNoOfTabs = noOfTabs;
        mGoalSelectedPostion = goalSelectedPos;
        mGoalPrice = goalPrice;
        mGoalDaysLeft = goalDays;
        mGoalPoints = goalPoints;
        mGoalName = goalName;
        mTask = task;
        mGoalUnit = goalUnit;
        mGoalValue = goalValue;
        mPoints = points;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = TasksFragment.getInstance();
                Bundle taskBundle = new Bundle();
                taskBundle.putString(Constants.NOTIFICATION_TASK, mTask);
                taskBundle.putString(Constants.NOTIFICATION_GOAL_UNIT, mGoalUnit);
                taskBundle.putString(Constants.NOTIFICATION_GOAL_VALUE, mGoalValue);
                taskBundle.putString(Constants.NOTIFICATION_POINTS, mPoints);
                fragment.setArguments(taskBundle);
                break;
            case 1:
                fragment = GoalsFragment.getInstance();
                Bundle goalsBundle = new Bundle();
                goalsBundle.putInt(Constants.GOAL_SELECTED_POSITION_TAG, mGoalSelectedPostion);
                goalsBundle.putString(Constants.GOAL_PRICE, mGoalPrice);
                goalsBundle.putString(Constants.GOAL_POINTS, mGoalPoints);
                goalsBundle.putString(Constants.GOAL_DAYS_LEFT, mGoalDaysLeft);
                goalsBundle.putString(Constants.GOAL_NAME, mGoalName);
                fragment.setArguments(goalsBundle);
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
