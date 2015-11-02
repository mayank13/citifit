package com.cititmobilechallenge.citifit.fragments;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.Constants;
import com.cititmobilechallenge.citifit.common.FontHelper;
import com.cititmobilechallenge.citifit.common.Utils;
import com.cititmobilechallenge.citifit.logger.Log;
import com.dlazaro66.wheelindicatorview.WheelIndicatorItem;
import com.dlazaro66.wheelindicatorview.WheelIndicatorView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class GoalsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = GoalsFragment.class.getSimpleName();

    private WheelIndicatorView mWheelIndicatorView = null;
    private LineChart mChart = null;

    private ImageView ivGoalImage = null;
    private TextView tvGoalPrice = null;
    private TextView tvGoalPoints = null;
    private TextView tvGoalDaysLeft = null;
    private TextView tvGoalName = null;
    /**
     * Track whether an authorization activity is stacking over the current activity, i.e. when
     * a known auth error is being resolved, such as showing the account chooser or presenting a
     * consent dialog. This avoids common duplications as might happen on screen rotations, etc.
     */
    private static final String AUTH_PENDING = "auth_state_pending";

    private static final String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";

    private static final int REQUEST_OAUTH = 1;

    private boolean isAuthInProgress = false;

    private GoogleApiClient mClient = null;

    private ArrayList<String> mXVals = null;
    private ArrayList<Entry> mYVals = null; // StepCount
    private int mCounter = 0; // Counter for no of entries

    private int mGoalSelectedPos = -1;
    private String mGoalPrice;
    private String mGoalPoints;
    private String mGoalDaysLeft;
    private String mGoalName;

    // TODO - UIs to be populated dynamically, once APIs are made

    public static GoalsFragment getInstance() {

        return new GoalsFragment();
    }


    public GoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        mGoalSelectedPos = bundle.getInt(Constants.GOAL_SELECTED_POSITION_TAG);
        mGoalPrice = bundle.getString(Constants.GOAL_PRICE);
        mGoalDaysLeft = bundle.getString(Constants.GOAL_DAYS_LEFT);
        mGoalPoints = bundle.getString(Constants.GOAL_POINTS);
        mGoalName = bundle.getString(Constants.GOAL_NAME);

        //Initialise the GoogleApiClient
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Fitness.HISTORY_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        initView(view);

        FontHelper.applyFont(getActivity(), view.findViewById(R.id.rl_goal_container));

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChart.getAxisLeft().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        mXVals = new ArrayList<>(7);

        // Create XAxis of week days
        mXVals.add("Sun");
        mXVals.add("Mon");
        mXVals.add("Tue");
        mXVals.add("Wed");
        mXVals.add("Thu");
        mXVals.add("Fri");
        mXVals.add("Sat");

        mYVals = new ArrayList<>(7);

        setGoalView();

        return view;
    }


    private void initView(View view) {

        mWheelIndicatorView = (WheelIndicatorView) view.findViewById(R.id.wheel_indicator_view);

        mChart = (LineChart) view.findViewById(R.id.chart);

        ivGoalImage = (ImageView) view.findViewById(R.id.image_reward);
        tvGoalPrice = (TextView) view.findViewById(R.id.text_mrp_goal);
        tvGoalPoints = (TextView) view.findViewById(R.id.text_citipoints_goal);
        tvGoalDaysLeft = (TextView) view.findViewById(R.id.text_days_left);
        tvGoalName = (TextView) view.findViewById(R.id.text_reward_name);
    }

    private Bitmap getGoalImageByGoalSelection() {
        Bitmap image = null;
        switch (mGoalSelectedPos) {
            case Constants.GOAL_KINDLE:
                image = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.product1, 100, 100);
                break;
            case Constants.GOAL_NIKE_GIFT_CARD:
                image = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.product2, 100, 100);
                break;
            case Constants.GOAL_FITBIT_ONE:
                image = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.product3, 100, 100);
                break;
            case Constants.GOAL_NIKE_SHOES:
                image = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.product4, 100, 100);
                break;
            case Constants.GOAL_MOVIE_TICKET:
                image = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.product5, 100, 100);
                break;
            case Constants.GOAL_CITIBANK_CARD:
                image = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.product6, 100, 100);
                break;
            case Constants.GOAL_PLATINUM_CARD:
                image = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.product7, 100, 100);
                break;
            case Constants.GOAL_JACKS_PLACE:
                image = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.product8, 100, 100);
                break;

            default:
                break;
        }
        return image;
    }

    private void setGoalView() {
        if (mGoalPrice != null && !mGoalPrice.isEmpty()) {
            tvGoalPrice.setText(mGoalPrice);
            tvGoalDaysLeft.setText(mGoalDaysLeft);
            tvGoalPoints.setText(mGoalPoints);
            tvGoalName.setText(mGoalName);
            Bitmap goalImage = getGoalImageByGoalSelection();
            ivGoalImage.setImageBitmap(goalImage);
        }

        setUpWheelView();
    }

    private void setUpWheelView() {
        //TODO - The percentage to be calculated based on data being processed and fetched dynamically
        // dummy data for kindle
        float totalPointsTarget = 7719; // Points target
        float currentPoints = 4110; // User has achieved current points
        float div = currentPoints / totalPointsTarget;
        int percentageOfExerciseDone = (int) (div * 100);

        mWheelIndicatorView.setFilledPercent(percentageOfExerciseDone);

        WheelIndicatorItem runningActivityIndicatorItem = new WheelIndicatorItem(1.0f, ContextCompat.getColor(getActivity(), R.color.purple));

        mWheelIndicatorView.addWheelIndicatorItem(runningActivityIndicatorItem);
        mWheelIndicatorView.startItemsAnimation(); // Animate!
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mClient != null && !mClient.isConnected()) {
            Log.i(TAG, "Connecting client...");
            mClient.connect();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mClient != null && !mClient.isConnected()) {
            setDummyData();
        }
    }


    // For dummy data
    private void setDummyData() {

        ArrayList<String> xVals = new ArrayList<String>();

        Log.i(TAG, "Setting dummy data...");
        // Create XAxis of week days
        xVals.add("Sun");
        xVals.add("Mon");
        xVals.add("Tue");
        xVals.add("Wed");
        xVals.add("Thu");
        xVals.add("Fri");
        xVals.add("Sat");

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < 7; i++) {

            float mult = (100 + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)

            yVals.add(new Entry(val, i));
        }

        LineDataSet dummyData = new LineDataSet(yVals, "WeeklyData");

        setThemeForLineData(dummyData);
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(dummyData); // add the datasets

        // create a data object with the datasets
        LineData lineData = new LineData(xVals, dataSets);
        mChart.setData(lineData);
        mChart.invalidate();
    }

    private void setThemeForLineData(LineDataSet set) {

        int[] colors = {R.color.red, R.color.purple, R.color.purple, R.color.purple, R.color.purple, R.color.purple, R.color.red};

        set.enableDashedLine(10f, 5f, 0f);
        set.enableDashedHighlightLine(10f, 5f, 0f);
        set.setColor(ContextCompat.getColor(getActivity(), R.color.purple));
        set.setCircleColors(colors, getActivity());
        set.setLineWidth(1f);
        set.setCircleSize(5f);
        set.setDrawCircleHole(false);
        set.setValueTextSize(8f);
        set.setFillAlpha(65);
        set.setFillColor(ContextCompat.getColor(getActivity(), R.color.purple));
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Connected!!!");
        // Now you can make calls to the Fitness APIs.  What to do?
        // Look at some data!!
        new GoogleFitDataFetcherTask().execute();
    }


    @Override
    public void onConnectionSuspended(int i) {
        // If your connection to the sensor gets lost at some point,
        // you'll be able to determine the reason and react to it here.
        if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
            Log.i(TAG, "Connection lost.  Cause: Network Lost.");
        } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
            Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed. Cause: " + result.toString());
        if (!result.hasResolution()) {
            // Show the localized error dialog
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
                    getActivity(), 0).show();
            return;
        }
        // The failure has a resolution. Resolve it.
        // Called typically when the app is not yet authorized, and an
        // authorization dialog is displayed to the user.
        if (!isAuthInProgress) {
            try {
                Log.i(TAG, "Attempting to resolve failed connection");
                isAuthInProgress = true;
                result.startResolutionForResult(getActivity(),
                        REQUEST_OAUTH);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG,
                        "Exception while starting resolution activity", e);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OAUTH) {
            isAuthInProgress = false;
            if (resultCode == 1) {
                // Make sure the app is not already connected or attempting to connect
                if (!mClient.isConnecting() && !mClient.isConnected()) {
                    mClient.connect();
                }
            }
        }
    }

    /**
     * Create a {@link DataSet} to insert data into the History API, and
     * then create and execute a {@link DataReadRequest} to verify the insertion succeeded.
     * By using an {@link AsyncTask}, we can schedule synchronous calls, so that we can query for
     * data after confirming that our insert was successful. Using asynchronous calls and callbacks
     * would not guarantee that the insertion had concluded before the read request was made.
     * An example of an asynchronous call using a callback can be found in the example
     * on deleting data below.
     */
    private class GoogleFitDataFetcherTask extends AsyncTask<Void, Void, DataReadResult> {

        protected DataReadResult doInBackground(Void... params) {
            Log.i(TAG, "Processing Google Fit Data");

            // Begin by creating the query.
            DataReadRequest readRequest = queryFitnessData();
            // [END insert_dataset]
            DataReadResult dataReadResult =
                    Fitness.HistoryApi.readData(mClient, readRequest).await(1, TimeUnit.MINUTES);

            return dataReadResult;
        }

        @Override
        protected void onPostExecute(DataReadResult result) {
            super.onPostExecute(result);

            processDataReadResult(result);

            if (mYVals != null && mYVals.size() > 0) {
                LineDataSet weeklyData = new LineDataSet(mYVals, "Weekly Data");

                setThemeForLineData(weeklyData);

                ArrayList<LineDataSet> dataSets = new ArrayList<>();
                dataSets.add(weeklyData);

                LineData lineData = new LineData(mXVals, dataSets);

                mChart.setData(lineData);
                mChart.invalidate();

            }
        }
    }

    /**
     * Return a {@link DataReadRequest} for all step count changes in the past week.
     */
    private DataReadRequest queryFitnessData() {
        // [START build_read_data_request]
        // Setting a start and end date using a range of 1 week before this moment.
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
        Log.i(TAG, "Range End: " + dateFormat.format(endTime));

        DataReadRequest readRequest = new DataReadRequest.Builder()
                // The data request can specify multiple data types to return, effectively
                // combining multiple data queries into one call.
                // In this example, it's very unlikely that the request is for several hundred
                // datapoints each consisting of a few steps and a timestamp.  The more likely
                // scenario is wanting to see how many steps were walked per day, for 7 days.
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                        // Analogous to a "Group By" in SQL, defines how data should be aggregated.
                        // bucketByTime allows for a time span, whereas bucketBySession would allow
                        // bucketing by "sessions", which would need to be defined in code.
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        // [END build_read_data_request]

        return readRequest;
    }

    /**
     * Log a record of the query result. It's possible to get more constrained data sets by
     * specifying a data source or data type, but for demonstrative purposes here's how one would
     * dump all the data. In this sample, logging also prints to the device screen, so we can see
     * what the query returns, but your app should not log fitness information as a privacy
     * consideration. A better option would be to dump the data you receive to a local data
     * directory to avoid exposing it to other applications.
     */
    private void processDataReadResult(DataReadResult dataReadResult) {
        // [START parse_read_data_result]
        // If the DataReadRequest object specified aggregated data, dataReadResult will be returned
        // as buckets containing DataSets, instead of just DataSets.
        if (dataReadResult.getBuckets().size() > 0) {
            Log.i(TAG, "Number of returned buckets of DataSets is: "
                    + dataReadResult.getBuckets().size());
            for (Bucket bucket : dataReadResult.getBuckets()) {
                List<DataSet> dataSets = bucket.getDataSets();
                for (DataSet dataSet : dataSets) {
                    populateStepsValues(dataSet);
                }
            }
        } else if (dataReadResult.getDataSets().size() > 0) {
            Log.i(TAG, "Number of returned DataSets is: "
                    + dataReadResult.getDataSets().size());
            for (DataSet dataSet : dataReadResult.getDataSets()) {
                populateStepsValues(dataSet);
            }
        }
        // [END parse_read_data_result]
    }

    // [START parse_dataset]
    private void populateStepsValues(DataSet dataSet) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        for (DataPoint dp : dataSet.getDataPoints()) {

            Log.i(TAG, "Data point:");
            Log.i(TAG, "\tType: " + dp.getDataType().getName());
            Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));

            // Resets the counter
            if (mCounter > 7) {
                mCounter = 0;
            }
            for (Field field : dp.getDataType().getFields()) {

                String steps = dp.getValue(field).toString();
                Log.i(TAG, "\tField: " + field.getName() +
                        " Value: " + steps);

                //Generating the entries for the Y axis of chart
                if (field.getName().equalsIgnoreCase("steps")) {
                    mYVals.add(new Entry(Integer.parseInt(steps), mCounter));
                }
                mCounter++;
            }
        }

    }

}
