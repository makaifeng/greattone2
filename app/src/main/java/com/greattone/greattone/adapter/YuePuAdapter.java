package com.greattone.greattone.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.Yuepu;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.List;

/**
 * 添加朋友
 * @author yff
 * 2015-9-2
 */ 
public class YuePuAdapter extends Adapter<Yuepu>{

	private int screenWidth;
	private int rightMargin;

	public YuePuAdapter(Context context,
			List<Yuepu> list) {
		super(context, list, R.layout.adapter_yue_pu);
		screenWidth = ((BaseActivity) context).screenWidth;
		rightMargin=DisplayUtil.dip2px(context, 10);
	}

	@Override
	public void getview(ViewHolder vh,  int position,  Yuepu T) {
		ImageView iv_icon = (ImageView) vh.getView(R.id.iv_icon);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				screenWidth * 2 / 7, screenWidth * 2 / 7);
		params.rightMargin=rightMargin;
		iv_icon.setLayoutParams(params);
		TextView tv_title = (TextView) vh.getView(R.id.tv_title);
		TextView tv_name = (TextView) vh.getView(R.id.tv_name);
		TextView tv_content = (TextView) vh.getView(R.id.tv_content);
		final TextView tv_time = (TextView) vh.getView(R.id.tv_time);
		
		if (TextUtils.isEmpty(T.getTitlepic())) {
			String[] s=T.getPhoto().split("\\::::::");
			if (s.length>1) {
				ImageLoaderUtil.getInstance().setImagebyurl(s[0], iv_icon);
			}else{
				ImageLoaderUtil.getInstance().setImagebyurl(T.getTitlepic(), iv_icon);
			}
		}else{
			ImageLoaderUtil.getInstance().setImagebyurl(T.getTitlepic(), iv_icon);
		}
		tv_name.setText(T.getUsername());
		tv_title.setText(T.getTitle());
		tv_time.setText(T.getNewstime());
		tv_content.setText(T.getSmalltext());
	}

}
