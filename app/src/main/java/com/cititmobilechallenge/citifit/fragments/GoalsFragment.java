package com.cititmobilechallenge.citifit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.FontHelper;
import com.dlazaro66.wheelindicatorview.WheelIndicatorItem;
import com.dlazaro66.wheelindicatorview.WheelIndicatorView;
import com.github.mikephil.charting.charts.LineChart;


public class GoalsFragment extends Fragment {

    private WheelIndicatorView mWheelIndicatorView = null;
    private LineChart mChart = null;

    // TODO - UIs to be populated dynamically, once APIs are made
    public GoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        initView(view);

        FontHelper.applyFont(getActivity(), view.findViewById(R.id.rl_goal_container));

        setUpWheelView();

        return view;
    }

    private void initView(View view) {

        mWheelIndicatorView = (WheelIndicatorView) view.findViewById(R.id.wheel_indicator_view);

        mChart = (LineChart) view.findViewById(R.id.chart);
    }

    private void setUpWheelView() {
        //TODO - The percentage to be calculated based on data being processed and fetched dynamically
        // dummy data for kindle
        float totalPointsTarget = 7719.0f; // Points target
        float currentPoints = 4110.0f; // User has achieved current points
        int percentageOfExerciseDone = (int) (totalPointsTarget / currentPoints * 100);

        mWheelIndicatorView.setFilledPercent(percentageOfExerciseDone);

        WheelIndicatorItem runningActivityIndicatorItem = new WheelIndicatorItem(1.0f, ContextCompat.getColor(getActivity(), R.color.purple));

        mWheelIndicatorView.addWheelIndicatorItem(runningActivityIndicatorItem);
        mWheelIndicatorView.startItemsAnimation(); // Animate!
    }

}
