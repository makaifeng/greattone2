package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.Course;
import com.greattone.greattone.util.ImageLoaderUtil;

public class RentKotofusaAdapter extends BaseAdapter {
	private Context context;
	private List<Course> courseList;
	private int screenWidth;

	public RentKotofusaAdapter(Context context, List<Course> courseList) {
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
					R.layout.adapter_course_center, group, false);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					screenWidth * 2 / 5, screenWidth * 2 * 3 / 5 / 5);
			holder.icon.setLayoutParams(params);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//

			holder.price = (TextView) convertView.findViewById(R.id.tv_price);//
			holder.old_price = (TextView) convertView
					.findViewById(R.id.tv_old_price);//
			holder.period = (TextView) convertView.findViewById(R.id.tv_period);//
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
		/** 原价格 */
		TextView old_price;
		/** 课时 */
		TextView period;
		/** 图片 */
		ImageView icon;

		public void setPosition(int position) {
			title.setText(courseList.get(position).getTitle());
			price.setText("￥" + courseList.get(position).getPrice());
			if (! courseList.get(position).getTprice().equals("0")&& courseList.get(position).getTprice()!=null) {
				SpannableString string = new SpannableString("原价格：￥"
						+ courseList.get(position).getTprice());
				string.setSpan(new StrikethroughSpan(), 4, string.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				old_price.setText(string);
			}
			if (courseList.get(position).getSmalltext()!=null) {
				period.setLines(2);
				period.setText( courseList.get(position).getSmalltext());
			}
			ImageLoaderUtil.getInstance().setImagebyurl(
					courseList.get(position).getTitlepic(), icon);
		}
	}

}
