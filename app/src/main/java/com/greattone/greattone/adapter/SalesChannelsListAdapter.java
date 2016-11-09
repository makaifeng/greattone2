package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.SalesChannels;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.widget.FlowLayout;
/**
 * 销售渠道列表适配器
 * @author makaifeng
 *
 */
@SuppressWarnings("deprecation")
public class SalesChannelsListAdapter extends Adapter<SalesChannels> {

	public SalesChannelsListAdapter(Context context, List<SalesChannels>mList) {
		super(context, mList, 	R.layout.adapter_sales_channels);
	}

	@Override
	public void getview(ViewHolder holder, int position, SalesChannels salesChannels) {
		TextView tv_title= (TextView) holder.getView(R.id.tv_title);
		TextView tv_name= (TextView) holder.getView(R.id.tv_name);
		TextView tv_phone= (TextView) holder.getView(R.id.tv_phone);
		TextView tv_address= (TextView) holder.getView(R.id.tv_address);
//		LinearLayout ll_product= (LinearLayout) holder.getView(R.id.ll_product);
		FlowLayout fl_product= (FlowLayout) holder.getView(R.id.fl_product);
		tv_title.setText(salesChannels.getTitle());
		tv_name.setText(salesChannels.getCharge());
		tv_phone.setText(salesChannels.getPhone());
		tv_address.setText(salesChannels.getAddress()+salesChannels.getAddress1()+salesChannels.getAddress2()+salesChannels.getAddres());
//		addView(ll_product,salesChannels.getProduct());
		addView2(fl_product,salesChannels.getProduct());
	}

/**
 * 添加文字
 * @param ll
 * @param product
 */
private void addView2(FlowLayout ll, String product) {
	ll.removeAllViews();
	String s[]=product.split("\\|");
	for (String string : s) {
		if (TextUtils.isEmpty(string)) {
			continue;
		}
		TextView textView=new TextView(context);
		textView.setBackgroundResource(R.drawable.text_red_bg);
		textView.setTextColor(context.getResources().getColor(R.color.white));
		textView.setText(string);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);  
		lp.setMargins(0, 0,DisplayUtil.dip2px(context, 5), 0); 
		ll.addView(textView,lp);
	}
}
///**
// * 添加文字
// * @param ll
// * @param product
// */
//	private void addView(LinearLayout ll, String product) {
//	int i=	ll.getChildCount();
//	if (i>1) {
//		ll.removeViews(1, i-1);
//	}
//		String s[]=product.split("\\|");
//		for (String string : s) {
//			if (TextUtils.isEmpty(string)) {
//				continue;
//			}
//			TextView textView=new TextView(context);
//			textView.setBackgroundResource(R.drawable.text_red_bg);
//			textView.setTextColor(context.getResources().getColor(R.color.white));
//			textView.setText(string);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
//			lp.setMargins(0, 0,DisplayUtil.dip2px(context, 5), 0); 
//			ll.addView(textView,lp);
//		}
//	}


}
