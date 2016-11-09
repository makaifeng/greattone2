package com.greattone.greattone.adapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import com.greattone.greattone.R;

public class NormalPopuWindowsAdapter extends Adapter<String> {
	public NormalPopuWindowsAdapter(Context context, List<String> paramList,
			int paramInt) {
		super(context, paramList, paramInt);
	}

	public void getview(ViewHolder paramViewHolder, int paramInt,
			String paramString) {
		((TextView) paramViewHolder.getView(R.id.adapter_normal_popu_tv)).setText(paramString);
	}
}
