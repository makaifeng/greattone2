package com.greattone.greattone.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.util.DisplayUtil;

public class NewsTitleGridAdapter1 extends BaseAdapter {
	Context context;
	int[] resIds;
	String texts[];

	public NewsTitleGridAdapter1(Context context, int[] resIds, String[] texts) {
		this.context = context;
		this.resIds = resIds;
		this.texts = texts;
		
	}

	@Override
	public int getCount() {
		return texts.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View v, ViewGroup group) {
		TextView textView = new TextView(context);
		textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		textView.setText(texts[position]);
		
		textView.setBackgroundColor(context.getResources().getColor(resIds[position]));
		textView.setPadding(0, DisplayUtil.dip2px(context, 10), 0,
				DisplayUtil.dip2px(context, 10));
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(context.getResources().getColor(R.color.white));
		// ImageView imageView=new ImageView(context);
		// imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// DisplayUtil.dip2px(context, 50)));
		// imageView.setImageResource(resIds[position]);
		return textView;
	}

}
