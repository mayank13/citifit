package com.cititmobilechallenge.citifit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.adaptors.PagerAdapter;
import com.cititmobilechallenge.citifit.common.Constants;

public class CitiFitDashboardActivity extends AppCompatActivity {

    private static final String TAG = CitiFitDashboardActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private PagerAdapter mAdapter = null;

    private static final int NO_OF_TABS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citifit_dashboard);

        Intent intent = getIntent();

        int goalToSet = intent.getIntExtra(Constants.GOAL_SELECTED_POSITION_TAG, -1);
        String goalPrice = intent.getStringExtra(Constants.GOAL_PRICE);
        String goalPoints = intent.getStringExtra(Constants.GOAL_POINTS);
        String goalDays = intent.getStringExtra(Constants.GOAL_DAYS_LEFT);
        String goalName = intent.getStringExtra(Constants.GOAL_NAME);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));
        mToolbar.setTitle(getString(R.string.home));

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.black));
        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.black));

        mTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tasks)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.goal)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.profile)));

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mAdapter = new PagerAdapter(getSupportFragmentManager(), NO_OF_TABS, goalToSet, goalPrice, goalPoints, goalDays, goalName);

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mToolbar.setTitle(getString(R.string.home));
    }
}
