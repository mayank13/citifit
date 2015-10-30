package com.cititmobilechallenge.citifit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.Constants;
import com.cititmobilechallenge.citifit.common.FontHelper;

public class ChosenGoalActivity extends AppCompatActivity {

    private ImageView ivGoalImage = null;
    private TextView tvGoalName = null;
    private TextView tvGoalDays = null;

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

        FontHelper.applyFont(this, findViewById(R.id.chosen_goal_container));
        setFinishOnTouchOutside(true);

        Intent intent = getIntent();

        mGoalToSet = intent.getIntExtra(Constants.GOAL_SELECTED_POSITION_TAG, -1);
        mGoalPrice = intent.getStringExtra(Constants.GOAL_PRICE);
        mGoalPoints = intent.getStringExtra(Constants.GOAL_POINTS);
        mGoalDaysLeft = intent.getStringExtra(Constants.GOAL_DAYS_LEFT);
        mGoalName = intent.getStringExtra(Constants.GOAL_NAME);


        Bitmap bitmap = getGoalImageFromPosition();

        ivGoalImage = (ImageView) findViewById(R.id.chosen_goal_image);
        tvGoalDays = (TextView) findViewById(R.id.text_goal_days);
        tvGoalName = (TextView) findViewById(R.id.text_goal_name);

        ivGoalImage.setImageBitmap(bitmap);
        tvGoalDays.setText(String.format(getString(R.string.goal_days), mGoalDaysLeft));
        tvGoalName.setText(String.format(getString(R.string.goal_name), mGoalName));

    }


    private Bitmap getGoalImageFromPosition() {

        Bitmap goalImage = null;
        switch (mGoalToSet) {
            case Constants.GOAL_KINDLE:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.product1);
                break;
            case Constants.GOAL_NIKE_GIFT_CARD:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.product2);
                break;
            case Constants.GOAL_FITBIT_ONE:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.product3);
                break;
            case Constants.GOAL_NIKE_SHOES:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.product4);
                break;
            case Constants.GOAL_MOVIE_TICKET:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.product5);
                break;
            case Constants.GOAL_CITIBANK_CARD:
                goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.product6);
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
