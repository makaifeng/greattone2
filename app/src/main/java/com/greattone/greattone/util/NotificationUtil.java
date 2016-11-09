package com.greattone.greattone.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.chat.MyChatActivity;

public class NotificationUtil {
	public static void showMessageNotification(Context context, String title, String message, String channel){
		Intent intent = new Intent(context,
				MyChatActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(
		context, 0, intent,
		PendingIntent.FLAG_CANCEL_CURRENT);
		Notification noti = new NotificationCompat.Builder(
context)
		.setSmallIcon(R.drawable.haoqinsheng_logo)
//		.setLargeIcon(v)
		.setNumber(13)
		.setContentIntent(pendingIntent)
		.setStyle(
		new NotificationCompat.InboxStyle()
		.addLine(
		"M.Twain (Google+) Haiku is more than a cert...")
		.addLine("M.Twain Reminder")
		.addLine("M.Twain Lunch?")
		.addLine("M.Twain Revised Specs")
		.addLine("M.Twain ")
		.addLine(
		"Google Play Celebrate 25 billion apps with Goo..")
		.addLine(
		"Stack Exchange StackOverflow weekly Newsl...")
		.setBigContentTitle("6 new message")
		.setSummaryText("mtwain@android.com"))
		.build();
		NotificationManager mNotificationManager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, noti);
	}
}
