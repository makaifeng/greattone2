package com.greattone.greattone.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.greattone.greattone.R;
import com.greattone.greattone.adapter.NormalPopuWindowsAdapter;

import java.util.List;

public class NormalPopuWindow {
	private NormalPopuWindowsAdapter adapter;
	private List<String> list;
	private OnItemClickBack listener;
	private PopupWindow popu;
	private View view;

	public NormalPopuWindow(Context context, List<String> paramList,
			View paramView) {
		this(context, paramList, paramView, false);
	}

	@SuppressLint("InflateParams")
	public NormalPopuWindow(Context context, final List<String> mlist,
			View paramView, boolean paramBoolean) {
		
		this.list = mlist;
		this.view = paramView;
		View localView1 = LayoutInflater.from(context).inflate(
				R.layout.pop_list, null, false);
		ListView localListView = (ListView) localView1
				.findViewById(R.id.pop_list);
		localListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView, View v,
							int position, long paramAnonymousLong) {
						if (NormalPopuWindow.this.listener != null)
							NormalPopuWindow.this.listener.OnClick(position,
									list.get(position));
						popu.dismiss();
					}
				});
		this.adapter = new NormalPopuWindowsAdapter(context, list,
				R.layout.adapter_normal_popu);
		localListView.setAdapter(this.adapter);
		// int i = 0;
		// if (!paramBoolean)
		// i = 0;
		// for (int j = 0; j < this.adapter.getCount(); j++) {
		// View localView2 = this.adapter.getView(j, null, localListView);
		// localView2.measure(0, 0);
		// i += localView2.getMeasuredHeight();
		// }
		// int k = localListView.getDividerHeight()
		// * (-1 + localListView.getCount());
		// ViewGroup.LayoutParams localLayoutParams = localListView
		// .getLayoutParams();
		// localLayoutParams.height = (i + k);
		// localListView.setLayoutParams(localLayoutParams);
		this.popu = new PopupWindow(localView1, paramView.getWidth(),-2);
		this.popu.setFocusable(true);
		this.popu.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(
				android.R.color.transparent)));
		this.popu.setOutsideTouchable(true);

	}

	public void dismisss() {
		this.popu.dismiss();
	}

	public List<String> getList() {
		return this.list;
	}

	public PopupWindow getPopu() {
		return this.popu;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public void setOnItemClickBack(OnItemClickBack paramOnItemClickBack) {
		this.listener = paramOnItemClickBack;
	}

	public void setPopu(PopupWindow paramPopupWindow) {
		this.popu = paramPopupWindow;
	}

	public void show() {
		if (this.popu != null) {
			if (this.popu.isShowing())
				this.popu.dismiss();
			} else
				return;
		this.popu.showAsDropDown(this.view);
	}

	public void update(List<String> paramList) {
		this.list = paramList;
		this.adapter.notifyDataSetChanged();
	}

	public static abstract interface OnItemClickBack {
		public abstract void OnClick(int position, String text);
	}
}
