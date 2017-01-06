package com.greattone.greattone.adapter;


import android.content.Context;
import android.widget.TextView;

import com.greattone.greattone.R;

import java.util.List;

public class PopuWindowsAdapter extends Adapter<String> {
	private int selector;

	public void setselector(int i){
		this.selector=i;
	}
	public PopuWindowsAdapter(Context context, List<String> list,
			int layout) {
		super(context, list, layout);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void getview(ViewHolder vh, int position, String T) {
		TextView tv=(TextView) vh.getView(R.id.adapter_popuwindows_tv);
		tv.setText(T);
		if(selector==position){
			tv.setBackgroundResource(R.color.popu_on);
			tv.setTextColor(context.getResources().getColor(R.color.red_c30000));
//			tv.setSelected(true);
//			tv.setPressed(true);
		}else{
			tv.setBackgroundResource(R.color.popu_);
			tv.setTextColor(context.getResources().getColor(R.color.white));
//			tv.setSelected(true);
//			tv.setPressed(true);
		}
		
	}

}
