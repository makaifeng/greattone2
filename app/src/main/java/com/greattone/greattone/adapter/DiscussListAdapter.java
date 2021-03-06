package com.greattone.greattone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.entity.Discuss;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.LanguageUtil;
import com.greattone.greattone.util.Textutil;

import java.util.List;

public class DiscussListAdapter extends BaseAdapter {
	private Context context;
	private List<Discuss> list;

	public DiscussListAdapter(Context context, List<Discuss> list) {
		this.list = list;
		this.context = context;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return this.list.get(position);
	}

	public long getItemId(int paramInt) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_discuss_list, group, false);
			holder.content = ((TextView) convertView
					.findViewById(R.id.tv_content));
			holder.name = ((TextView) convertView
					.findViewById(R.id.tv_name));
			holder.level = ((TextView) convertView
					.findViewById(R.id.tv_level));
			holder.time = ((TextView) convertView
					.findViewById(R.id.tv_time));
			holder.onclick = ((TextView) convertView
					.findViewById(R.id.tv_onclick));
			holder.icon = ((ImageView) convertView
					.findViewById(R.id.iv_icon));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.setPosition(position);
		return convertView;
	}

	private class ViewHolder {
		private TextView content;
		private TextView name;
		private TextView level;
		private TextView time;
		private TextView onclick;
		private ImageView icon;
		UserInfo userInfo;
   private  void setPosition(int position ){
	   content.setText(list.get(position).getTitle());
	   if (list.get(position).getIsmember()==0){
		   name.setVisibility(View.GONE);
		   level.setVisibility(View.GONE);
		   time.setVisibility(View.GONE);
		   onclick.setVisibility(View.GONE);
		   return;
	   }
	   if (list.get(position).getUserinfo()!=null&&list.get(position).getUserinfo().startsWith("{")){
		    userInfo= JSON.parseObject(list.get(position).getUserinfo(),UserInfo.class);
		   name.setText(getLanguageText("作者：")+userInfo.getUsername());
		   level.setText(userInfo.getLevel().getName());
		   time.setText(getLanguageText("发帖时间：")+list.get(position).getNewstime());
		   onclick.setText(getLanguageText("点击量：")+list.get(position).getOnclick());
		   ImageLoaderUtil.getInstance().setImagebyurl(userInfo.getUserpic(),icon);
	   }else {
		   name.setVisibility(View.GONE);
		   level.setVisibility(View.GONE);
		   time.setVisibility(View.GONE);
		   onclick.setVisibility(View.GONE);
	   }

	   //		if (list.get(position).getContent()==null||list.get(position).getContent().equals("")) {
//			holder.content.setVisibility(View.GONE);
//		}else {
//			holder.content.setVisibility(View.VISIBLE);
//			holder.content.setText(list.get(position).getContent());
//		}
//	   if (position != 0) {
//		   content.setVisibility(View.GONE);
//	   }


   }
		private CharSequence getLanguageText(CharSequence text){
			try {
				if (LanguageUtil.getLanguage().equals("TW")) {
                    if (text!=null&&text!="") {
                        text= Textutil.Sim2Tra(text);
                    }
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
			return text;
		}
	}
}