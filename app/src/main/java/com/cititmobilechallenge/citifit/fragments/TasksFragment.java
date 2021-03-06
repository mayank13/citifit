package com.cititmobilechallenge.citifit.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

    private ImageView ivTaskStatusImage = null;
    private TextView tvTaskStatus = null;

    TextView tvTaskStart = null;
    TextView tvTrackingAnimatedText = null;
    Animation animBlink = null;

    // From Notification
    private String mTask;
    private String mGoalUnit;
    private String mGoalValue;
    private String mPoints;
    private String mMessageType;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment getInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        Bundle bundle = getArguments();

        mTask = bundle.getString(Constants.NOTIFICATION_TASK);
        mGoalUnit = bundle.getString(Constants.NOTIFICATION_GOAL_UNIT);
        mGoalValue = bundle.getString(Constants.NOTIFICATION_GOAL_VALUE);
        mPoints = bundle.getString(Constants.NOTIFICATION_POINTS);
        mMessageType = bundle.getString(Constants.NOTIFICATION_MESSAGE_TYPE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        FontHelper.applyFont(getActivity(), view.findViewById(R.id.task_fragment_container));

        tvTaskStart = (TextView) view.findViewById(R.id.taskStart);
        ivBanner = (ImageView) view.findViewById(R.id.dailyTaskBanner);
        ivTaskIcon = (ImageView) view.findViewById(R.id.taskIcon);
        tvTask = (TextView) view.findViewById(R.id.task_name);
        tvPoints = (TextView) view.findViewById(R.id.text_points);
        tvTaskValue = (TextView) view.findViewById(R.id.text_task_value);
        ivTaskStatusImage = (ImageView) view.findViewById(R.id.taskStatusImage);
        tvTaskStatus = (TextView) view.findViewById(R.id.text_status);

        //Attaching the animation the textView
        tvTrackingAnimatedText = (TextView) view.findViewById(R.id.trackingAnimatedText);

        // load the animation
        animBlink = AnimationUtils.loadAnimation(getActivity(),
                R.anim.blink);

        // set animation listener
        animBlink.setAnimationListener(this);

        tvTaskStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTaskStart.setVisibility(View.INVISIBLE);
                tvTrackingAnimatedText.setVisibility(View.VISIBLE);
                tvTrackingAnimatedText.startAnimation(animBlink);
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
        if (mMessageType != null && mMessageType.equalsIgnoreCase("message")) {
            updateTaskStatus("Completed");
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

    public void updateTaskStatus(String status) {
        tvTaskStatus.setText(status);
        if (status.equalsIgnoreCase("Completed")) {
            tvTaskStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.green));
            ivTaskStatusImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.task_complete));
            tvTaskStart.setText("You won " + mPoints + " points. Sweet!");
            tvTaskStart.setTextColor(ContextCompat.getColor(getActivity(), R.color.green));
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), FontHelper.FONT_PROXIMANOVA_BOLD);
            tvTaskStart.setTypeface(typeface);
            tvTaskStart.setClickable(false);
        } else {
            tvTaskStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            ivTaskStatusImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.task_incomplete));
        }
    }
}
