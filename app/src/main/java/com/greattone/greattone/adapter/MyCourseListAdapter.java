package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.course.PostCourseActivity;
import com.greattone.greattone.entity.Course;
import com.greattone.greattone.util.ImageLoaderUtil;

public class MyCourseListAdapter extends BaseAdapter {
	private Context context;
	private List<Course> courseList;
	private int screenWidth;
	OnBtnItemClickListener btnItemClickListener;

	public MyCourseListAdapter(Context context, List<Course> courseList) {
		this.context = context;
		this.courseList = courseList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return courseList.size();
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
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_my_course, group, false);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_pic);//
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					screenWidth * 2 / 5, screenWidth * 2 * 3 / 5 / 5);
			holder.icon.setLayoutParams(params);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//

			holder.price = (TextView) convertView.findViewById(R.id.tv_price);//
			holder.del = (TextView) convertView.findViewById(R.id.tv_del);//
			holder.edit = (TextView) convertView.findViewById(R.id.tv_edit);//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/** 标题 */
		TextView title;
		/** 价格 */
		TextView price;
		/** 编辑 */
		TextView edit;
		/** 删除 */
		TextView del;
		/** 图片 */
		ImageView icon;
		int position;

		public void setPosition(final int position) {
			this.position = position;
			title.setText(courseList.get(position).getTitle());
			price.setText("￥" + courseList.get(position).getPrice());
			ImageLoaderUtil.getInstance().setImagebyurl(
					courseList.get(position).getTitlepic(), icon);
			edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							PostCourseActivity.class);
					intent.putExtra("id", courseList.get(position).getId());
					intent.putExtra("classid", courseList.get(position).getClassid());
					intent.putExtra("type", "edit");
					context.startActivity(intent);
				}
			});
			del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					btnItemClickListener.onItemClick(v, position);
				

				}
			});
		}
	}
	
   public void setOnBtnClick(OnBtnItemClickListener btnItemClickListener){
	   this.btnItemClickListener=btnItemClickListener;
   }
}
