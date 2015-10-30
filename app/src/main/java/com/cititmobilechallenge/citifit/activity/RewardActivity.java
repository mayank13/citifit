package com.cititmobilechallenge.citifit.activity;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.adaptors.RewardListViewAdaptor;
import com.cititmobilechallenge.citifit.common.Constants;
import com.cititmobilechallenge.citifit.common.FontHelper;
import com.cititmobilechallenge.citifit.modal.RewardHolder;

import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;

/**
 * Created by ashwiask on 10/25/2015.
 */

public class RewardActivity extends AppCompatActivity implements RewardListViewAdaptor.RewardListItemClickListener {

    private TwoWayView mRewardListView = null;

    private RewardListViewAdaptor adaptor = null;

    private ArrayList<RewardHolder> mRewardList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reward);

        FontHelper.applyFont(this, findViewById(R.id.rl_reward_container));

        mRewardListView = (TwoWayView) findViewById(R.id.rewardListView);

        initList();

        adaptor = new RewardListViewAdaptor(mRewardList, this, this);

        mRewardListView.setAdapter(adaptor);


    }

    public void onSkip(View view) {
        if (view.getId() == R.id.btnSkip) {
            Intent intent = new Intent(this, CitiFitDashboardActivity.class);
            startActivity(intent);
        }
    }

    private void initList() {
        mRewardList = new ArrayList<>(5);
        //TODO - Fetch the data dynamically using Async Task, once the API is ready

        //Populating dummy data

        Bitmap scaledImage1 = decodeSampledBitmapFromResource(getResources(), R.drawable.goal1, 400, 400);
        RewardHolder reward1 = new RewardHolder("Kindle Paperwhite", "4020", "5630", "30", scaledImage1);
        mRewardList.add(reward1);

        Bitmap scaledImage2 = decodeSampledBitmapFromResource(getResources(), R.drawable.goal2, 400, 400);
        RewardHolder reward2 = new RewardHolder("Nike Gift Card", "700", "1400", "20", scaledImage2);
        mRewardList.add(reward2);

        Bitmap scaledImage3 = decodeSampledBitmapFromResource(getResources(), R.drawable.goal3, 400, 400);
        RewardHolder reward3 = new RewardHolder("Fitbit One", "6990", "2796", "14", scaledImage3);
        mRewardList.add(reward3);

        Bitmap scaledImage4 = decodeSampledBitmapFromResource(getResources(), R.drawable.goal4, 400, 400);
        RewardHolder reward4 = new RewardHolder("Nike Running Shoes", "2200", "4400", "20", scaledImage4);
        mRewardList.add(reward4);

        Bitmap scaledImage5 = decodeSampledBitmapFromResource(getResources(), R.drawable.goal5, 400, 400);
        RewardHolder reward5 = new RewardHolder("2 Movie Tickets", "500", "900", "7", scaledImage5);
        mRewardList.add(reward5);

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ChosenGoalActivity.class);

        RewardHolder reward = mRewardList.get(position);

        intent.putExtra(Constants.GOAL_SELECTED_POSITION_TAG, position);
        intent.putExtra(Constants.GOAL_PRICE, reward.getPrice());
        intent.putExtra(Constants.GOAL_POINTS, reward.getPointsNeeded());
        intent.putExtra(Constants.GOAL_DAYS_LEFT, reward.getDays());
        intent.putExtra(Constants.GOAL_NAME, reward.getName());
        startActivity(intent);
    }
}
