package com.greattone.greattone.dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.Listener.TimePickerDismissCallback;

public class MyTimePickerDialog extends Dialog implements OnDateChangedListener {
	private TimePickerDismissCallback timePickerDismissCallback;
	private View view;
	private DatePicker datePicker;
	private TextView tv_sure;
	private String dateTime;
	private String time;

	public MyTimePickerDialog(Context context, @Nullable String time,
			TimePickerDismissCallback timePickerDismissCallback2) {
		super(context, R.style.ActionSheetDialogStyle1);
		this.time = time;
		this.timePickerDismissCallback = timePickerDismissCallback2;
	}

	private void callback(String dateTime) {
		if (this.timePickerDismissCallback != null)
			this.timePickerDismissCallback.finish(dateTime);
	}

	@SuppressLint("InflateParams")
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		this.view = getLayoutInflater().inflate(R.layout.time_picker, null);
		tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		datePicker = (DatePicker) view.findViewById(R.id.datepicker);

		init(datePicker);
		tv_sure.setOnClickListener(lis);
		setContentView(view);

		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
			
			}
		});
		setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
		
			}
		});
	}

	@Override
	public void onWindowAttributesChanged(LayoutParams params) {
		params.gravity = Gravity.BOTTOM;
		params.width = LayoutParams.MATCH_PARENT;
		super.onWindowAttributesChanged(params);
	}

	private void init(DatePicker datePicker) {
		int year;
		int monthOfYear;
		int dayOfMonth;
		if (time == null || time.equals("")) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			monthOfYear = calendar.get(Calendar.MONTH)+1;
			dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			dateTime = year + "-" + monthOfYear + "-" + dayOfMonth;
		} else {
			String str[] = time.split("\\-");
			year = Integer.valueOf(str[0]);
			monthOfYear = Integer.valueOf(str[1]);
			dayOfMonth = Integer.valueOf(str[2]);
			dateTime = time;
		}
		datePicker.init(year, monthOfYear-1, dayOfMonth, this);
	}

	View.OnClickListener lis = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			callback(dateTime);
			cancel();
		}
	};


	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// 获得日历实例
		Calendar calendar = Calendar.getInstance();
		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		dateTime = sdf.format(calendar.getTime());
	}

}
