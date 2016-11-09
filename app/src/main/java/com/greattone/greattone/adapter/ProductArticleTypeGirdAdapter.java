package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.Product;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;
@SuppressWarnings("deprecation")
public class ProductArticleTypeGirdAdapter extends BaseAdapter {
	private Context context;
	private List<Product> productList;
	private int screenWidth;

	public ProductArticleTypeGirdAdapter(Context context,
			List<Product> productList) {
		this.context = context;
		this.productList = productList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return productList.size();
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
					R.layout.adapter_brand_type, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.address = (TextView) convertView
					.findViewById(R.id.tv_address);//
			holder.address.setSingleLine();
			holder.telphone = (TextView) convertView
					.findViewById(R.id.tv_telphone);//
			holder.telphone .setVisibility(View.GONE);
			holder.distance = (TextView) convertView
					.findViewById(R.id.tv_distance);//
			holder.distance .setVisibility(View.GONE);
			Drawable drawable = context.getResources().getDrawable(
					R.drawable.icon_jl);
			drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 10),
					DisplayUtil.dip2px(context, 15));
			holder.distance.setCompoundDrawables(drawable, null, null, null);
			holder.distance.setCompoundDrawablePadding(DisplayUtil.dip2px(
					context, 5));
			holder.vip = (ImageView) convertView.findViewById(R.id.iv_vip);//
			holder.icon = (MyRoundImageView) convertView.findViewById(R.id.iv_icon);//
			holder.icon.setRadius(DisplayUtil.dip2px(context, 15));
//			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//					screenWidth * 2 , screenWidth * 2* 4 / 5);
			LayoutParams params =(LayoutParams) holder.icon.getLayoutParams();
			params.width=screenWidth /2-DisplayUtil.dip2px(context, 5);
			params.height=(screenWidth /2-DisplayUtil.dip2px(context, 5));
			holder.icon.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/** 教室姓名 */
		TextView name;
		/** 地址 */
		TextView address;
		/** 电话 */
		TextView telphone;
		/** 距离 */
		TextView distance;
		/** 头像 */
		MyRoundImageView icon;
		/** v符号 */
		ImageView vip;
		int position;
		Product product = new Product();

		public void setPosition(int position) {
			this.position = position;
			if (productList.get(position) != null) {
				product = productList.get(position);
				ImageLoaderUtil.getInstance().setImagebyurl(
						product.getThumbnail(), icon);
				name.setText(product.getTitle());
				if (product.getChan_money() != null) {
					address.setText("价格："+product.getChan_money()+"元");
				}else{
					address.setText("创立国家：");
				}
			}
		}
	}

}
