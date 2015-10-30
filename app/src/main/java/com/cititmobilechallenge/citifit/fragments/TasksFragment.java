package com.cititmobilechallenge.citifit.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.Constants;
import com.cititmobilechallenge.citifit.common.FontHelper;


public class TasksFragment extends Fragment implements Animation.AnimationListener {

    private ImageView ivBanner = null;
    private ImageView ivTaskIcon = null;
    private TextView tvTask = null;
    private TextView tvPoints = null;
    private TextView tvTaskValue = null;

    TextView taskStart = null;
    TextView trackingAnimatedText = null;
    Animation animBlink = null;

    // From Notification
    private String mTask;
    private String mGoalUnit;
    private String mGoalValue;
    private String mPoints;

    private Bundle mBundle = null;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment getInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBundle = getArguments();

        mTask = mBundle.getString(Constants.NOTIFICATION_TASK);
        mGoalUnit = mBundle.getString(Constants.NOTIFICATION_GOAL_UNIT);
        mGoalValue = mBundle.getString(Constants.NOTIFICATION_GOAL_VALUE);
        mPoints = mBundle.getString(Constants.NOTIFICATION_POINTS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        FontHelper.applyFont(getActivity(), view.findViewById(R.id.task_fragment_container));

        taskStart = (TextView) view.findViewById(R.id.taskStart);
        ivBanner = (ImageView) view.findViewById(R.id.dailyTaskBanner);
        ivTaskIcon = (ImageView) view.findViewById(R.id.taskIcon);
        tvTask = (TextView) view.findViewById(R.id.task_name);
        tvPoints = (TextView) view.findViewById(R.id.text_points);
        tvTaskValue = (TextView) view.findViewById(R.id.text_task_value);


        //Attaching the animation the textView
        trackingAnimatedText = (TextView) view.findViewById(R.id.trackingAnimatedText);

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

        setView();

        return view;
    }

    private void setView() {
        if (mTask != null && !mTask.isEmpty()) {
            ivBanner.setImageBitmap(getBannerByTask());
            ivTaskIcon.setImageBitmap(getIconByTask());
            tvTask.setText(mTask);
            tvPoints.setText(mPoints + " Citi Points");
            tvTaskValue.setText(mGoalValue + " " + mGoalUnit);
        }
    }

    private Bitmap getBannerByTask() {
        Bitmap bannerImage = null;
        if (mTask != null && !mTask.isEmpty()) {
            switch (mTask) {
                case "Run":
                    bannerImage = BitmapFactory.decodeResource(getResources(), R.drawable.run_banner);
                    break;
                case "Walk":
                    bannerImage = BitmapFactory.decodeResource(getResources(), R.drawable.walk_banner);
                    break;
                case "Cycle":
                    bannerImage = BitmapFactory.decodeResource(getResources(), R.drawable.cycle_banner);
                    break;
                default:
                    break;

            }
        }
        return bannerImage;
    }

    private Bitmap getIconByTask() {
        Bitmap taskIcon = null;
        if (mTask != null && !mTask.isEmpty()) {
            switch (mTask) {
                case "Run":
                    taskIcon = BitmapFactory.decodeResource(getResources(), R.drawable.run_icon);
                    break;
                case "Walk":
                    taskIcon = BitmapFactory.decodeResource(getResources(), R.drawable.walk_icon);
                    break;
                case "Cycle":
                    taskIcon = BitmapFactory.decodeResource(getResources(), R.drawable.cycle_icon);
                    break;
                default:
                    break;
            }

        }
        return taskIcon;
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
