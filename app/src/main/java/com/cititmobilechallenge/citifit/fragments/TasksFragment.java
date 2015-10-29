package com.cititmobilechallenge.citifit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.FontHelper;


public class TasksFragment extends Fragment implements Animation.AnimationListener {

    TextView taskStart = null;
    TextView trackingAnimatedText = null;
    Animation animBlink = null;

    public TasksFragment() {
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
        View inflatedView = inflater.inflate(R.layout.fragment_tasks, container, false);
        taskStart = (TextView) inflatedView.findViewById(R.id.taskStart);

        //Attaching the animation the textView
        trackingAnimatedText = (TextView) inflatedView.findViewById(R.id.trackingAnimatedText);

        // load the animation
        animBlink = AnimationUtils.loadAnimation(getActivity(),
                R.anim.blink);

        // set animation listener
        animBlink.setAnimationListener(this);

        taskStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskStart.setVisibility(View.INVISIBLE);
                trackingAnimatedText.setVisibility(View.VISIBLE);
                trackingAnimatedText.startAnimation(animBlink);
            }
        });

        FontHelper.applyFont(getActivity(), inflatedView.findViewById(R.id.task_fragment_container));

        return inflatedView;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
