package com.cititmobilechallenge.citifit.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cititmobilechallenge.citifit.activity.CitiFitDashboardActivity;
import com.cititmobilechallenge.citifit.common.Constants;
import com.cititmobilechallenge.citifit.helper.NotificationUtils;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Mayank Jain
 */
public class CustomPushReceiver extends ParsePushBroadcastReceiver {
    private final String TAG = CustomPushReceiver.class.getSimpleName();

    private NotificationUtils notificationUtils;

    public CustomPushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.e(TAG, "Push received: " + json);
            parsePushJson(context, json);

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    private void parsePushJson(Context context, JSONObject json) {
        try {
            boolean isBackground = json.getBoolean("is_background");
            JSONObject data = json.getJSONObject("data");
            String type = data.getString("type");
            String message = data.getString("message");
            String goal_unit = data.getString("goal_unit");
            String goal_value = data.getString("goal_value");
            String points = data.getString("points");
            String title = data.getString("title");

            if (!isBackground) {
                Intent resultIntent = new Intent(context, CitiFitDashboardActivity.class);

                resultIntent.putExtra(Constants.NOTIFICATION_MESSAGE, message);
                resultIntent.putExtra(Constants.NOTIFICATION_TASK, type);
                resultIntent.putExtra(Constants.NOTIFICATION_GOAL_UNIT, goal_unit);
                resultIntent.putExtra(Constants.NOTIFICATION_GOAL_VALUE, goal_value);
                resultIntent.putExtra(Constants.NOTIFICATION_POINTS, points);
                resultIntent.putExtra(Constants.NOTIFICATION_TITLE, title);
                showNotificationMessage(context, resultIntent);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }


    /**
     * Shows the notification message in the notification bar
     * If the app is in background, launches the app
     *
     * @param context
     * @param intent
     */
    private void showNotificationMessage(Context context, Intent intent) {

        notificationUtils = new NotificationUtils(context);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationUtils.showNotificationMessage(intent);
    }
}