package com.cititmobilechallenge.citifit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.Constants;

public class ChosenGoalActivity extends AppCompatActivity {

    private ImageView mGoalImageView = null;

    //TODO - Cleaning to be done once API's are ready


    private int mGoalToSet = -1;
    private String mGoalPrice;
    private String mGoalPoints;
    private String mGoalDaysLeft;
    private String mGoalName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_goal);

        setFinishOnTouchOutside(true);

        Intent intent = getIntent();

        mGoalToSet = intent.getIntExtra(Constants.GOAL_SELECTED_POSITION_TAG, -1);
        mGoalPrice = intent.getStringExtra(Constants.GOAL_PRICE);
        mGoalPoints = intent.getStringExtra(Constants.GOAL_POINTS);
        mGoalDaysLeft = intent.getStringExtra(Constants.GOAL_DAYS_LEFT);
        mGoalName = intent.getStringExtra(Constants.GOAL_NAME);


        Bitmap bitmap = getGoalImageFromPosition();

        mGoalImageView = (ImageView) findViewById(R.id.chosen_goal_image);

        mGoalImageView.setImageBitmap(bitmap);

    }


    private Bitmap getGoalImageFromPosition() {

        Bitmap goalImage = null;
        switch (mGoalToSet) {
            case Constants.GOAL_KINDLE:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.goal1);
                break;
            case Constants.GOAL_NIKE_GIFT_CARD:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.goal2);
                break;
            case Constants.GOAL_FITBIT_ONE:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.goal3);
                break;
            case Constants.GOAL_NIKE_SHOES:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.goal4);
                break;
            case Constants.GOAL_MOVIE_TICKET:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.goal5);
                break;
            default:
                break;
        }
        return goalImage;
    }

    public void onBeginBtnClicked(View view) {

        Intent intent = new Intent(this, CitiFitDashboardActivity.class);

        intent.putExtra(Constants.GOAL_SELECTED_POSITION_TAG, mGoalToSet);
        intent.putExtra(Constants.GOAL_NAME, mGoalName);
        intent.putExtra(Constants.GOAL_POINTS, mGoalPoints);
        intent.putExtra(Constants.GOAL_DAYS_LEFT, mGoalDaysLeft);
        intent.putExtra(Constants.GOAL_PRICE, mGoalPrice);

        startActivity(intent);
    }
}
