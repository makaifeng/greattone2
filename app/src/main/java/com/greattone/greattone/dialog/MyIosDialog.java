package com.greattone.greattone.dialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.greattone.greattone.R;

import java.util.List;

/**
 * name：dalong
 * date：2014/12/3
 * QQ：982242260
 */

@SuppressLint("InflateParams")
@SuppressWarnings("deprecation")
public class MyIosDialog {
//	android.content.DialogInterface.OnClickListener onClickListener;
//	android.content.DialogInterface.OnClickListener onClickListener2;
//	String positiveButton;
//	String neutralButton;
	
	private static TextView messageTextView;
	private static Dialog dialog;
	private static int timeCount;//倒计时时间
	private static String timemessage;//倒计时信息
	/**
	 * 带有计时器的单选dialog
	 * @param mContext 上下文
	 * @param titleicon  头部图标
	 * @param title  头部文本
	 * @param message dialog文本
	 * @param positiveButton  按钮名称
	 * @param time  倒计时时间值
	 * @param positiveListener 回调接口
	 * @return
	 */
	public static Dialog showTimeAlertDialog(Context mContext,int titleicon,String title,final String message, String positiveButton,final int time,final OnTimeAlertViewClickListener positiveListener){
		dialog = new Dialog(mContext, R.style.alertView);
		timeCount=time;
		timemessage=message;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = (View) inflater.inflate(R.layout.alertview, null);
		// 标题和内容
		TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
		messageTextView = (TextView) view.findViewById(R.id.messageTextView);
		ImageView icon=(ImageView)view.findViewById(R.id.titileicon);
		if(0!=titleicon){
			icon.setBackgroundResource(titleicon);
		}else{
			icon.setVisibility(View.GONE);
		}
		if (null != title && !title.equalsIgnoreCase("")) {
			titleTextView.setText(title);
		} else {
			titleTextView.setVisibility(View.GONE);
		}
		if (null != message && !message.equalsIgnoreCase("")) {
			messageTextView.setText(time+message);
		} else {
			messageTextView.setVisibility(View.GONE);
		}

		Button pButton = new Button(mContext);
		pButton.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, dip2px(mContext, 44), 1.0f));
		pButton.setTextColor(mContext.getResources().getColor(R.color.dialog_orange));
		pButton.setText(positiveButton);
		pButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != positiveListener) {
					positiveListener.confirm();
				}
				dialog.dismiss();
			}
		});
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				handler.removeMessages(0);
				positiveListener.confirm();
			}
		});
		// 动态添加按钮
		LinearLayout buttonLayout = (LinearLayout) view.findViewById(R.id.buttonLayout);
		// 一个按钮
		buttonLayout.setOrientation(LinearLayout.VERTICAL);
		pButton.setBackgroundResource(R.drawable.alert_time_bottom_button);
		buttonLayout.addView(pButton);
		final int viewWidth = dip2px(mContext, 250);
		view.setMinimumWidth(viewWidth);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(view);
		dialog.show();
		handler.sendEmptyMessage(-1);
		return dialog;
	}
	static Handler handler=new Handler(){@Override
		public void handleMessage(Message msg) {
		int what=msg.what;
		if(what==0){
			if(timeCount>0){
				timeCount--;
				messageTextView.setText(timeCount+timemessage);
				handler.sendEmptyMessageDelayed(0, 1000);
			}else{
				dialog.dismiss();
				handler.removeMessages(0);
			}
		}else{
			handler.sendEmptyMessageDelayed(0, 1000);
		}
		super.handleMessage(msg);
	}};
	
	
	/**
	 * 实现一个按钮，2个按钮
	 * @param mContext 上下文
	 * @param positiveButton  一个默认按钮
	 * @param otherButtons  其他按钮
	 * @param positiveListener 回调监听
	 * @return
	 */
	public static Dialog showButtons(Context mContext,String positiveButton,
			final String[] otherButtons,
			final OnAlertViewClickListener positiveListener) {
		final Dialog dialog = new Dialog(mContext, R.style.alertView);
		
		LinearLayout layout=new LinearLayout(mContext);
		layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		Button pButton = new Button(mContext);
		pButton.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, dip2px(mContext, 44), 1.0f));
		pButton.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
		// 动态添加按钮
		if (null == otherButtons || otherButtons.length == 0) {
			// 一个按钮
			layout.setOrientation(LinearLayout.VERTICAL);
			pButton.setBackgroundResource(R.drawable.alert_bottom_button);
		} else if (null != otherButtons && otherButtons.length == 1) {
			// 两个按钮
			layout.setOrientation(LinearLayout.HORIZONTAL);
			pButton.setBackgroundResource(R.drawable.alert_left_button);
		} else {
			// 三个或三个以上按钮
			layout.setOrientation(LinearLayout.VERTICAL);
			pButton.setBackgroundResource(R.drawable.alert_middle_button);
		}
		
		pButton.setText(positiveButton);
		pButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != positiveListener) {
					positiveListener.cancel();
				}
				dialog.dismiss();
			}
		});
		layout.addView(pButton);
		if (null != otherButtons && otherButtons.length > 0) {
			for (int i = 0; i < otherButtons.length; i++) {
				final int index = i;
				Button otherButton = new Button(mContext);
				otherButton.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, dip2px(mContext, 44), 1.0f));
				otherButton.setText(otherButtons[i]);
				otherButton.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
				if (1 == otherButtons.length) {
					otherButton
					.setBackgroundResource(R.drawable.alert_right_button);
				} else if (i < (otherButtons.length - 1)) {
					otherButton
					.setBackgroundResource(R.drawable.alert_middle_button);
				} else {
					otherButton
					.setBackgroundResource(R.drawable.alert_bottom_button);
				}
				otherButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (null != positiveListener) {
							positiveListener.confirm(otherButtons[index]);
						}
						dialog.dismiss();
					}
				});
				layout.addView(otherButton);
			}
		}
		final int viewWidth = dip2px(mContext, 250);
		layout.setMinimumWidth(viewWidth);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(layout);
		dialog.show();
		return dialog;
	}
	/**
	 * 实现一个按钮，2个按钮
	 * @param mContext 上下文
	 * @param titleicon  头部图标
	 * @param title   头部文字
	 * @param message  内容
	 * @param positiveButton  一个默认按钮
	 * @param otherButtons  其他按钮
	 * @param positiveListener 回调监听
	 * @return
	 */
	public static Dialog showAlertView(Context mContext, int titleicon,String title,
			String message, String positiveButton,
			final String[] otherButtons,
			final OnAlertViewClickListener positiveListener) {
		final Dialog dialog = new Dialog(mContext, R.style.alertView);

		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = (View) inflater.inflate(R.layout.alertview, null);
		// 标题和内容
		TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
		TextView messageTextView = (TextView) view.findViewById(R.id.messageTextView);
		ImageView icon=(ImageView)view.findViewById(R.id.titileicon);
		if(0!=titleicon){
			icon.setBackgroundResource(titleicon);
		}else{
			icon.setVisibility(View.GONE);
		}
		if (null != title && !title.equalsIgnoreCase("")) {
			titleTextView.setText(title);
		} else {
			titleTextView.setVisibility(View.GONE);
		}
		if (null != message && !message.equalsIgnoreCase("")) {
			messageTextView.setText(message);
		} else {
			messageTextView.setVisibility(View.GONE);
		}
		Button pButton = new Button(mContext);
		pButton.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, dip2px(mContext, 44), 1.0f));
		pButton.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
		// 动态添加按钮
		LinearLayout buttonLayout = (LinearLayout) view.findViewById(R.id.buttonLayout);
		if (null == otherButtons || otherButtons.length == 0) {
			// 一个按钮
			buttonLayout.setOrientation(LinearLayout.VERTICAL);
			pButton.setBackgroundResource(R.drawable.alert_bottom_button);
		} else if (null != otherButtons && otherButtons.length == 1) {
			// 两个按钮
			buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
			pButton.setBackgroundResource(R.drawable.alert_left_button);
		} else {
			// 三个或三个以上按钮
			buttonLayout.setOrientation(LinearLayout.VERTICAL);
			pButton.setBackgroundResource(R.drawable.alert_middle_button);
		}

		pButton.setText(positiveButton);
		pButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != positiveListener) {
					positiveListener.cancel();
				}
				dialog.dismiss();
			}
		});
		buttonLayout.addView(pButton);
		if (null != otherButtons && otherButtons.length > 0) {
			for (int i = 0; i < otherButtons.length; i++) {
				final int index = i;
				Button otherButton = new Button(mContext);
				otherButton.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, dip2px(mContext, 44), 1.0f));
				otherButton.setText(otherButtons[i]);
				otherButton.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
				if (1 == otherButtons.length) {
					otherButton
					.setBackgroundResource(R.drawable.alert_right_button);
				} else if (i < (otherButtons.length - 1)) {
					otherButton
					.setBackgroundResource(R.drawable.alert_middle_button);
				} else {
					otherButton
					.setBackgroundResource(R.drawable.alert_bottom_button);
				}
				otherButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (null != positiveListener) {
							positiveListener.confirm(otherButtons[index]);
						}
						dialog.dismiss();
					}
				});
				buttonLayout.addView(otherButton);
			}
		}
		final int viewWidth = dip2px(mContext, 250);
		view.setMinimumWidth(viewWidth);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(view);
		dialog.show();
		return dialog;
	}
	/**
	 * 底部弹出dialog
	 * @param mContext
	 * @param title
	 * @param items
	 * @param dialogClickListener
	 * @return
	 */
	public static Dialog ShowBottomDialog(Context mContext,String title,String[] items,final DialogItemClickListener dialogClickListener){
		final Dialog dialog=new Dialog(mContext, R.style.DialogStyle);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		View view=LayoutInflater.from(mContext).inflate(R.layout.dialog_radio, null);
		dialog.setContentView(view);
		if(title.equals("")||title==null){
			((TextView)view.findViewById(R.id.title)).setVisibility(View.GONE);
			((TextView)view.findViewById(R.id.title_divider)).setVisibility(View.GONE);
		}else{
			((TextView)view.findViewById(R.id.title)).setText(title);
		}
		//根据items动态创建
		LinearLayout parent=(LinearLayout) view.findViewById(R.id.dialogLayout);
		parent.removeAllViews();
		int length=items.length;
		for ( int i = 0; i < items.length; i++) {
			final int position=i;
			LayoutParams params1=new LayoutParams(-1,-2);
			params1.rightMargin=1;
			final TextView tv=new TextView(mContext);
			tv.setLayoutParams(params1);
			tv.setTextSize(18);
			tv.setText(items[i]);
			tv.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
			int pad=mContext.getResources().getDimensionPixelSize(R.dimen.padding10);
			tv.setPadding(pad,pad,pad,pad);
			tv.setSingleLine(true);
			tv.setGravity(Gravity.CENTER);
			if(i!=length-1)
				if((title.equals("")||title==null)&&i==0)
					tv.setBackgroundResource(R.drawable.menudialog_top2_selector);
				else
					tv.setBackgroundResource(R.drawable.menudialog_center_selector);
			else
				if(length==1)
					tv.setBackgroundResource(R.drawable.menudialog_bottom_selector);
				else
					tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);

			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					dialogClickListener.itemClick(tv.getText().toString(),position);
				}
			});
			parent.addView(tv);
			if(i!=length-1){
				TextView divider=new TextView(mContext);
				LayoutParams params=new LayoutParams(-1,(int)1);
				divider.setLayoutParams(params);
				divider.setBackgroundResource(android.R.color.darker_gray);
				parent.addView(divider);
			}
		}
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.width= getScreenWidth(mContext);
		mWindow.setGravity(Gravity.BOTTOM);
		//添加动画
		mWindow.setWindowAnimations(R.style.dialogAnim);
		mWindow.setAttributes(lp);
		dialog.show();
		return dialog;
	}

	/**
	 * 列表框
	 * @param mContext
	 * @param mlist
	 * @param dialogClickListener
     * @return
     */
	public static Dialog ShowListDialog2(final Context mContext,final List<String> mlist, final DialogItemClickListener dialogClickListener){
		final Dialog dialog=new Dialog(mContext, R.style.DialogStyle);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		View view=LayoutInflater.from(mContext).inflate(R.layout.layout_list, null);

		ListView	listView=		((ListView)view.findViewById(R.id.lv_content));
		listView.setAdapter(new BaseAdapter() {
			@Override
			public int getCount() {
				return mlist==null?0:mlist.size();
			}

			@Override
			public Object getItem(int i) {
				return null;
			}

			@Override
			public long getItemId(int i) {
				return 0;
			}

			@Override
			public View getView(int i, View view, ViewGroup viewGroup) {
				LinearLayout layout=new LinearLayout(mContext);
				layout.setOrientation(LinearLayout.VERTICAL);
				final  int position=i;
				LayoutParams params1=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				params1.rightMargin=1;
				final TextView tv=new TextView(mContext);
				tv.setLayoutParams(params1);
				tv.setTextSize(18);
				tv.setText(mlist.get(i));
				tv.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
				int pad=mContext.getResources().getDimensionPixelSize(R.dimen.padding10);
				tv.setPadding(pad,pad,pad,pad);
				tv.setSingleLine(true);
				tv.setGravity(Gravity.CENTER);
				tv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						dialogClickListener.itemClick(tv.getText().toString().trim(),position);
						dialog.cancel();
					}
				});
				layout.addView(tv);
				View view1=new View(mContext);
				LayoutParams params2=new LayoutParams(LayoutParams.MATCH_PARENT,dip2px(mContext,1));
				view1.setLayoutParams(params2);
				view1.setBackgroundColor(mContext.getResources().getColor(R.color.gray_eeeeee));
				layout.addView(view1);
				return layout;
			}
		});
		dialog.setContentView(view);
		final int viewWidth = dip2px(mContext, 250);
		view.setMinimumWidth(viewWidth);
		dialog.setContentView(view);
		dialog.show();
		return dialog;
	}
	/**
	 * 列表框
	 * @param mContext
	 * @param mlist
	 * @param dialogClickListener
     * @return
     */
	public static Dialog ShowListDialog(final Context mContext,String title,final List<String> mlist, final DialogItemClickListener dialogClickListener){
		final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(title);
		builder.setAdapter(new BaseAdapter() {
			@Override
			public int getCount() {
				return mlist == null ? 0 : mlist.size();
			}

			@Override
			public Object getItem(int i) {
				return null;
			}

			@Override
			public long getItemId(int i) {
				return 0;
			}

			@Override
			public View getView(int i, View view, ViewGroup viewGroup) {
				LinearLayout layout = new LinearLayout(mContext);
				layout.setOrientation(LinearLayout.VERTICAL);
				final int position = i;
				LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				params1.rightMargin = 1;
				final TextView tv = new TextView(mContext);
				tv.setLayoutParams(params1);
				tv.setTextSize(18);
				tv.setText(mlist.get(i));
				tv.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
				int pad = mContext.getResources().getDimensionPixelSize(R.dimen.padding10);
				tv.setPadding(pad, pad, pad, pad);
				tv.setSingleLine(true);
				tv.setGravity(Gravity.CENTER);
				layout.addView(tv);
				View view1 = new View(mContext);
				LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, dip2px(mContext, 1));
				view1.setLayoutParams(params2);
				view1.setBackgroundColor(mContext.getResources().getColor(R.color.gray_eeeeee));
				layout.addView(view1);
				return layout;
			}
		}, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogClickListener.itemClick(mlist.get(which),which);
				dialog.dismiss();
			}
		}); //设置适配器注意：mAddAdapter为ListAdapter

//   builder.setInverseBackgroundForced(true);

		AlertDialog mAlertDialog = builder.create();
		mAlertDialog.setCanceledOnTouchOutside(true);
		mAlertDialog.setCancelable(true);
//		mAlertDialog.setOnCancelListener(n);

		mAlertDialog.show();
		return mAlertDialog;
	}
	/**
	 * 回调接口
	 * @author Administrator
	 *
	 */
	public interface OnAlertViewClickListener {
		public abstract void confirm(String result);
		public abstract void cancel();
	}
	/**
	 * 时间dialog回调接口
	 * @author Administrator
	 *
	 */
	public interface OnTimeAlertViewClickListener {
		public abstract void confirm();
	}

	public interface DialogItemClickListener{
		public abstract void itemClick(String result, int position);
	}
//	public void setPositiveButton(String positiveButton,android.content.DialogInterface.OnClickListener onClickListener){
//		this.positiveButton=positiveButton;
//		this.onClickListener=onClickListener;
//	}
//	public void setNeutralButton(String neutralButton,android.content.DialogInterface.OnClickListener onClickListener){
//		this.neutralButton=neutralButton;
//		this.onClickListener2=onClickListener;
//	}
	/**获取屏幕分辨率宽*/
	public static int getScreenWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
	}
	/**获取屏幕分辨率高*/
	public static int getScreenHeight(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
	}
	/**获取屏幕分辨率宽计算dialog的宽度*/
	private static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
