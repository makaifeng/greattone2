package com.greattone.greattone.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.WeekEntity;
import com.greattone.greattone.util.DisplayUtil;

public class WeekAdapter extends BaseAdapter {
	private Context context;
	private List<WeekEntity> mlist=new ArrayList<WeekEntity>();

	public WeekAdapter(Context context, String week) {
		this.context = context;
		String s[] = null;
		if (week != null && !week.isEmpty()) {
			s = week.split("\\|");
		}
		WeekEntity[] arrayOfWeekEntity = new WeekEntity[8];
		arrayOfWeekEntity[0] = new WeekEntity("上课时间");
		arrayOfWeekEntity[1] = new WeekEntity("周一");
		arrayOfWeekEntity[2] = new WeekEntity("周二");
		arrayOfWeekEntity[3] = new WeekEntity("周三");
		arrayOfWeekEntity[4] = new WeekEntity("周四");
		arrayOfWeekEntity[5] = new WeekEntity("周五");
		arrayOfWeekEntity[6] = new WeekEntity("周六");
		arrayOfWeekEntity[7] = new WeekEntity("周日");
		this.mlist = Arrays.asList(arrayOfWeekEntity);
		if (s != null) {
			for (int i = 0; i < mlist.size(); i++) {
				for (int j = 0; j < s.length; j++) {
					if (mlist.get(i).getName().equals(s[j])) {
						mlist.get(i).setSelect(true);
					}
				}
			}
		}
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup group) {
		CheckBox checkBox = new CheckBox(context);
		checkBox.setButtonDrawable(R.drawable.register_checkbox_style);
		checkBox.setTextSize(14);
		checkBox.setPadding(10, 0, 0, 0);
		checkBox.setLayoutParams(new GridView.LayoutParams(
				GridView.LayoutParams.WRAP_CONTENT, DisplayUtil.dip2px(context,
						20)));
		if (position == 0)
			checkBox.setButtonDrawable(new ColorDrawable(0));
		checkBox.setText(mlist.get(position).getName());
		if (mlist.get(position).isSelect()) {
			checkBox.setChecked(true);
		} else {
			checkBox.setChecked(false);
		}
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mlist.get(position).setSelect(isChecked);
			}
		});
		return checkBox;
	}

	public List<WeekEntity> getList() {
		return mlist;
	}
	public String getWeek() {
		String week="";
		for (WeekEntity weekEntity : mlist) {
			if (weekEntity.isSelect()) {
				week+=weekEntity.getName()+"|";
			}
		}
		return week.length()>0?week.substring(0, week.length()-1):week;
	}
}
