package com.greattone.greattone.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.activity.LoginActivity;
import com.greattone.greattone.activity.MainActivity;
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.activity.personal.DirectoryActivity2;
import com.greattone.greattone.activity.plaza.PlazaMusicDetailsActivity;
import com.greattone.greattone.activity.qa.MyQAActivity;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.entity.Chat;
import com.greattone.greattone.entity.JPushMessage;
import com.greattone.greattone.util.SQLManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyJPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction()
				+ ", extras: " + printBundle(bundle));
		// 接收Registration Id
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			// String regId =
			// bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			// Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			// send the Registration Id to your server...
			// 接收到推送下来的自定义消息
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			// Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " +
			// bundle.getString(JPushInterface.EXTRA_MESSAGE));

			processCustomMessage(context, bundle);
			// 接收到推送下来的通知
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			// Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			// int notifactionId =
			// bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			// Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			processNotification(context, bundle);
			// 用户点击打开了通知
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
//			 Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
//			Log.d(TAG, bundle.getString(JPushInterface.EXTRA_EXTRA));
			if (bundle.getString(JPushInterface.EXTRA_EXTRA).startsWith("{")) {
				try {
					JPushMessage message2 = JSON.parseObject(
                            bundle.getString(JPushInterface.EXTRA_EXTRA),
                            JPushMessage.class);
					if (islogin(context,message2)) {//是否登录
                        if (message2.getType().equals("chating")) {// 聊天信息
                            toChat(context, message2);
                        } else if (message2.getType().equals("QAAsk")
                                || message2.getType().equals("payend")) {// qa问答
                            toQA(context, 1);
                        } else if (message2.getType().equals("QABiaojia")) {// qa问答
                            toQA(context, 2);
                        } else if (message2.getType().equals("QAHd")) {// qa问答
                            toQA(context, 3);
                        } else if (message2.getType().equals("feed")) {// 关注
                            Intent i = new Intent(context, DirectoryActivity2.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(i);
                        } else if (message2.getType().equals("pinglun")) {// 评论
                            Intent i = new Intent(context, PlazaMusicDetailsActivity.class);
							i.putExtra("id",Integer.valueOf(message2.getId()));
							i.putExtra("classid",Integer.valueOf(message2.getClassid()));
//							intent.putExtra("videourl", blogsList.get(position).getShipin());
							i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(i);
                        }
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
			Log.d(TAG,
					"[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
							+ bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction())) {
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction()
					+ " connected state change to " + connected);
		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}


	private boolean islogin(Context context, JPushMessage message2) {
		if (Data.myinfo.getUsername()==null) {
			Intent i = new Intent(context, LoginActivity.class);
			i.putExtra("type",message2.getType());
			i.putExtra("id",Integer.valueOf(message2.getId()));
			i.putExtra("classid",Integer.valueOf(message2.getClassid()));
			i.putExtra("username",message2.getUsername());
//			i.putExtra("class", "");
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
			return false;
		}
		return true;
	}

	/**
	 * qa问答
	 * 
	 * @param context
	 * @param num
	 */
	private void toQA(Context context, int num) {
		SharedPreferences preferences= context.getSharedPreferences(Constants.PREFERENCES_NAME_USER,
				Context.MODE_PRIVATE);
		if (preferences.getInt("cked", 0)==1) {
			
		Intent i = new Intent(context, MyQAActivity.class);
		i.putExtra("num", num);// 我的问题
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);
		}else
		Toast.makeText(context.getApplicationContext(), "非会员无法使用该功能", Toast.LENGTH_LONG).show();
	}

	/**
	 * 聊天信息
	 */
	private void toChat(Context context, JPushMessage message) {
		// 打开自定义的Activity
		Intent i = new Intent(context, MyChatActivity.class);
		i.putExtra("name", message.getUsername());
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(
							bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it = json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" + myKey + " - "
								+ json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	// send msg to MainActivity
	/**
	 * 处理自定义消息
	 * @param context
	 * @param bundle
	 */
	private void processCustomMessage(final Context context, Bundle bundle) {
		if (bundle.getString(JPushInterface.EXTRA_EXTRA).startsWith("{")) {
			JPushMessage message2 = JSON.parseObject(
					bundle.getString(JPushInterface.EXTRA_EXTRA),
					JPushMessage.class);
			if (message2.getType().equals("chating")) {// 聊天信息
				if (MyChatActivity.isForeground) {
					   Intent mIntent=new Intent(MyChatActivity.ACTION_INTENT_RECEIVER);  
		                mIntent.putExtra("message",message2);  
		                context.sendBroadcast(mIntent); 
//					((MyChatActivity) Data.activityErrorList
//							.get(Data.activityErrorList.size() - 1))
//							.addMessage();
				} else {
					// 写入数据库
					SQLManager sql = SQLManager.bulid(context);
					ContentValues values = new ContentValues();
					values.put("username", message2.getUsername());
					values.put("last_text",
							bundle.getString(JPushInterface.EXTRA_MESSAGE));
					values.put("userid", message2.getId());
					values.put("userpic", message2.getPic());
					values.put("saytime", System.currentTimeMillis());
					sql.addChat(context, values, message2.getUsername());
					values = new ContentValues();
					values.put("username", "all");
					sql.addChat(context, values, "all");
					List<Chat> mlList = sql.queryChat(context, "all");
					if (mlList.size() > 0) {
						MainActivity.setNum(mlList.get(0).getIssys());
					}
					sql.closeDB();
					// 推送到信息栏
					JPushLocalNotification notification = new JPushLocalNotification();
					notification.setTitle(bundle
							.getString(JPushInterface.EXTRA_TITLE));
					notification.setContent(bundle
							.getString(JPushInterface.EXTRA_MESSAGE));
					notification.setExtras(JSON.toJSONString(message2));
					notification.setNotificationId(Long.valueOf(bundle
							.getString(JPushInterface.EXTRA_MSG_ID)));
					notification
							.setBroadcastTime(System.currentTimeMillis() + 1000);
					JPushInterface.addLocalNotification(context, notification);
				}
			} else if (message2.getType().equals("QAAsk")) {// qa问答

			}
		}
	}
	/**
	 * 处理通知消息
	 * @param context
	 * @param bundle
	 */
	private void processNotification(Context context, Bundle bundle) {
		if (bundle.getString(JPushInterface.EXTRA_EXTRA).startsWith("{")) {
			JPushMessage message2 = JSON.parseObject(
					bundle.getString(JPushInterface.EXTRA_EXTRA),
					JPushMessage.class);
			 if (message2.getType().equals("feed")) {// 关注
				 initFeedNotification(context,bundle,message2);
			} else if (message2.getType().equals("QAAsk")) {// qa问答

			}
		}
	}

/**
 * 处理关注的通知信息
 */
	private void initFeedNotification(Context context,Bundle bundle, JPushMessage message) {
			// 写入数据库
//			SQLManager sql = SQLManager.bulid(context);
//			ContentValues values = new ContentValues();
//			values.put("username", message.getUsername());
//			values.put("last_text",
//					bundle.getString(JPushInterface.EXTRA_MESSAGE));
//			values.put("userid", message.getId());
//			values.put("userpic", message.getPic());
//			values.put("saytime", System.currentTimeMillis());
//			sql.addChat(context, values, message.getUsername());
//			values = new ContentValues();
//			values.put("username", "all");
//			sql.addChat(context, values, "all");
//			List<Chat> mlList = sql.queryChat(context, "all");
//			if (mlList.size() > 0) {
//				MainActivity.setNum(mlList.get(0).getIssys());
//			}
//			sql.closeDB();
	}

	// private void processCustomMessage(Context context, Bundle bundle) {
	// String title = bundle.getString(JPushInterface.EXTRA_TITLE);
	// String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
	// if (StringUtils.isEmpty(title)) {
	// Logger.w(TAG, "Unexpected: empty title (friend). Give up");
	// return;
	// }
	//
	// boolean needIncreaseUnread = true;
	//
	// if (title.equalsIgnoreCase(Config.myName)) {
	// Logger.d(TAG, "Message from myself. Give up");
	// needIncreaseUnread = false;
	// if (!Config.IS_TEST_MODE) {
	// return;
	// }
	// }
	//
	// String channel = null;
	// String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
	// try {
	// JSONObject extrasJson = new JSONObject(extras);
	// channel = extrasJson.optString(Constants.KEY_CHANNEL);
	// } catch (Exception e) {
	// Logger.w(TAG, "Unexpected: extras is not a valid json", e);
	// }
	//
	// // Send message to UI (Webview) only when UI is up
	// if (!Config.isBackground) {
	// Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
	// msgIntent.putExtra(Constants.KEY_MESSAGE, message);
	// msgIntent.putExtra(Constants.KEY_TITLE, title);
	// if (null != channel) {
	// msgIntent.putExtra(Constants.KEY_CHANNEL, channel);
	// }
	//
	// JSONObject all = new JSONObject();
	// try {
	// all.put(Constants.KEY_TITLE, title);
	// all.put(Constants.KEY_MESSAGE, message);
	// all.put(Constants.KEY_EXTRAS, new JSONObject(extras));
	// } catch (JSONException e) {
	// }
	// msgIntent.putExtra("all", all.toString());
	//
	// context.sendBroadcast(msgIntent);
	// }
	//
	// String chatting = title;
	// if (!StringUtils.isEmpty(channel)) {
	// chatting = channel;
	// }
	//
	// String currentChatting =
	// MyPreferenceManager.getString(Constants.PREF_CURRENT_CHATTING, null);
	// if (chatting.equalsIgnoreCase(currentChatting)) {
	// Logger.d(TAG, "Is now chatting with - " + chatting +
	// ". Dont show notificaiton.");
	// needIncreaseUnread = false;
	// if (!Config.IS_TEST_MODE) {
	// return;
	// }
	// }
	//
	// if (needIncreaseUnread) {
	// unreadMessage(title, channel);
	// }
	//
	// NotificationUtil.showMessageNotification(context, title, message,
	// channel);
	// }
	
	
}
