package com.cititmobilechallenge.citifit.helper;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.application.AppConfig;
import com.cititmobilechallenge.citifit.common.Constants;

import java.util.List;


/**
 * Created by Mayank Jain
 */
public class NotificationUtils {

    private String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;

    public NotificationUtils() {
    }

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(Intent intent) {

        String message = intent.getStringExtra(Constants.NOTIFICATION_MESSAGE);
        String task = intent.getStringExtra(Constants.NOTIFICATION_TASK);
        String points = intent.getStringExtra(Constants.NOTIFICATION_POINTS);
        String goalUnit = intent.getStringExtra(Constants.NOTIFICATION_GOAL_UNIT);
        String goalValue = intent.getStringExtra(Constants.NOTIFICATION_GOAL_VALUE);
        String title = intent.getStringExtra(Constants.NOTIFICATION_TITLE);
        String message_type = intent.getStringExtra(Constants.NOTIFICATION_MESSAGE_TYPE);

        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        int smallIcon = R.drawable.points_big;

        intent.putExtra(Constants.NOTIFICATION_TASK, task);
        intent.putExtra(Constants.NOTIFICATION_GOAL_UNIT, goalUnit);
        intent.putExtra(Constants.NOTIFICATION_GOAL_VALUE, goalValue);
        intent.putExtra(Constants.NOTIFICATION_POINTS, points);

        if (message_type.equalsIgnoreCase("Data")) {
            smallIcon = getTaskIconByType(task);
        }
        // notification icon
        int icon = R.mipmap.ic_launcher;

        int mNotificationId = AppConfig.NOTIFICATION_ID;

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                        | PendingIntent.FLAG_ONE_SHOT);

        // shows a big content text notification
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(title);
        bigTextStyle.bigText(message);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        Notification notification = builder.setSmallIcon(smallIcon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon)).setStyle(bigTextStyle).build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);

    }

    private int getTaskIconByType(String type) {
        int taskIconId = R.drawable.run_icon;
        switch (type) {
            case "Run":
                taskIconId = R.drawable.run_icon;
                break;
            case "Walk":
                taskIconId = R.drawable.walk_icon;
                break;
            case "Cycle":
                taskIconId = R.drawable.cycle_icon;
                break;
            default:
                break;

        }
        return taskIconId;
    }

    /**
     * Method checks if the app is in background or not
     *
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        //return isInBackground;
        return true;
    }
}
