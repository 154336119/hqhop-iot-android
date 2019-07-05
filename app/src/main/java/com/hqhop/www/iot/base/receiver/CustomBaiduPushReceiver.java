package com.hqhop.www.iot.base.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.google.gson.Gson;
import com.hqhop.www.iot.BuildConfig;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.bean.PushMessageBean;

import java.util.List;

public class CustomBaiduPushReceiver extends PushMessageReceiver {
    @Override
    public void onBind(Context context, int errorCode, String appId, String userId, String channelId, String requestId) {
        if (BuildConfig.DEBUG) {
            Log.d("Allen", "onBind, errorCode = " + errorCode + ", appId = " + appId + ", userId = " + userId + ", channelId = " + channelId + ", requestId = " + requestId);
        }
    }

    @Override
    public void onUnbind(Context context, int i, String s) {
        if (BuildConfig.DEBUG) {
            Log.d("Allen", "onUnbind");
        }
    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {
        if (BuildConfig.DEBUG) {
            Log.d("Allen", "onSetTags");
        }
    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {
        if (BuildConfig.DEBUG) {
            Log.d("Allen", "onDelTags");
        }
    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {
        if (BuildConfig.DEBUG) {
            Log.d("Allen", "onListTags");
        }
    }

    /**
     * 接收到透传消息后回调
     *
     * @param context             Context
     * @param message             消息内容
     * @param customContentString 自定义内容
     */
    @Override
    public void onMessage(Context context, String message, String customContentString) {
        if (BuildConfig.DEBUG) {
            Log.d("Allen", "onMessage, message = " + message + ", customContentString = " + customContentString);
        }
        Gson gson = new Gson();
        PushMessageBean bean = gson.fromJson(message, PushMessageBean.class);

//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.mipmap.logo)
//                        .setContentTitle(context.getString(R.string.app_name) + "\n" + bean.getTitle())
//                        .setSubText(bean.getSubtitle())
//                        .setAutoCancel(true)
//                        .setContentText(bean.getBody());
//        PendingIntent notifyPIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
//        mBuilder.setContentIntent(notifyPIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.tv_title_notification, bean.getTitle());
        remoteViews.setTextViewText(R.id.tv_subtitle_notification, bean.getSubtitle());
        remoteViews.setTextViewText(R.id.tv_body_notification, bean.getBody());
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle("")
                .setContentText("")
                .setDefaults(Notification.DEFAULT_ALL)
                .setCustomBigContentView(remoteViews)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build(); // 创建通知（每个通知必须要调用这个方法来创建
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        int id = (int) System.currentTimeMillis();
        notificationManager.notify(id, notification);
    }

    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        if (BuildConfig.DEBUG) {
            Log.d("Allen", "onNotificationClicked, title = " + title + ", description = " + description + ", customContentString = " + customContentString);
        }
//        String jsonString = cusomContentString;
//        String jsonString = "{\"open-type\":\"2\",\"content\":\"cccccccccccc\",\"sId\":\"ssssssssssssssiiiid\"}\n";

//        JsonObject obj = new JsonParser().parse(customContentString).getAsJsonObject();
//        String stationId = obj.get("sId").getAsString();
//        if (!TextUtils.isEmpty(stationId)) {
//            try {
//                Intent intent = new Intent(context, DetailStationActivity.class);
//                intent.putExtra("type", 2);
//                intent.putExtra("id", stationId);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("Allen", "exception: " + e.getMessage());
//            }
//        }
    }

    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {
        if (BuildConfig.DEBUG) {
            Log.d("Allen", "onNotificationArrived, title = " + title + ", description = " + description + ", customContentString = " + customContentString);
        }
    }
}
