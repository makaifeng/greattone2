package com.greattone.greattone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.MusicalProduct;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;
import com.greattone.greattone.widget.MyTextView;

import java.util.List;

import static com.greattone.greattone.R.id.ll_label;

@SuppressWarnings("deprecation")
public class InstrumentMallAdapter extends BaseAdapter {
	private Context context;
	private List<MusicalProduct> productList;
	private int screenWidth;

	public InstrumentMallAdapter(Context context,
								 List<MusicalProduct> productList) {
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
					R.layout.adapter_instrument_mall, group, false);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.address = (TextView) convertView
					.findViewById(R.id.tv_address);//
			holder.ll_label = (LinearLayout) convertView
					.findViewById(ll_label);//
			holder.price = (TextView) convertView
					.findViewById(R.id.tv_price);//
//			holder.pay_num = (TextView) convertView
//					.findViewById(R.id.tv_pay_num);//
			holder.icon = (MyRoundImageView) convertView.findViewById(R.id.iv_pic);//
			holder.icon.setRadius(DisplayUtil.dip2px(context, 5));
//			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//					screenWidth * 2 , screenWidth * 2* 4 / 5);
//			LayoutParams params =(LayoutParams) holder.icon.getLayoutParams();
//			params.width=screenWidth /2-DisplayUtil.dip2px(context, 5);
//			params.height=(screenWidth /2-DisplayUtil.dip2px(context, 5));
//			holder.icon.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/** 名字*/
		TextView title;
		/** 地址 */
		TextView address;
		/**标签*/
		LinearLayout ll_label;
		/** 价格 */
		TextView price;
		/** 头像 */
		MyRoundImageView icon;
//		/** 付款人数*/
//		TextView pay_num;
		int position;
		MusicalProduct musicalProduct;
		public void setPosition(int position) {
			this.position = position;
			musicalProduct=productList.get(position);
			title.setText(musicalProduct.getTitle()+" "+musicalProduct.getModel());
			address.setText(musicalProduct.getCity());
			price.setText("￥"+musicalProduct.getMoney());
			String titlepic[]=musicalProduct.getTitlepic().split("\\::::::");
			ImageLoaderUtil.getInstance().setImagebyurl(titlepic[0],icon);
			if (musicalProduct.getFreight()==0){
					addLabelView("包邮");
			}

		}

		private void addLabelView(String name) {
			MyTextView textview=new MyTextView(context,null);
			textview.setTextColor(context.getResources().getColor(R.color.white));
			textview.setText(name);
			textview.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.mall_label_background));
			ll_label.addView(textview);
		}

	}

}
