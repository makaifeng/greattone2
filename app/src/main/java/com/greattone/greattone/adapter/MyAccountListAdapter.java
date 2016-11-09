package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.Record;

/** 提现 */
public class MyAccountListAdapter extends BaseAdapter {
	private Context context;
	private List<Record> recordList;

	public MyAccountListAdapter(Context context, List<Record> recordList) {
		this.context = context;
		this.recordList = recordList;
		// screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		// return 3;
		return recordList.size();
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
					R.layout.adapter_center_my_account, group, false);
			holder.id = (TextView) convertView.findViewById(R.id.tv_id);//
			holder.status = (TextView) convertView.findViewById(R.id.tv_status);//
			holder.price = (TextView) convertView.findViewById(R.id.tv_price);//
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_content);//
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.title .setVisibility(View.VISIBLE);
			holder.time = (TextView) convertView.findViewById(R.id.tv_time);//
			holder.tv_btn = (TextView) convertView.findViewById(R.id.tv_btn);//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/** 订单id */
		TextView id;
		/** 状态 */
		TextView status;
		/** 价格 */
		TextView price;
		/** 标题 */
		TextView title;
		/** 内容 */
		TextView content;
		/** 时间 */
		TextView time;
		/** 支付 */
		TextView tv_btn;
		Record record;

		public void setPosition(int position) {
			record = recordList.get(position);
			id.setText("订单号：" + record.getTi_ddno());
			String type = null;
			if (record.getTi_type() == 1) {
				type = "支付宝";
			} else if (record.getTi_type() == 2) {
				type = "建行";
			}
			title.setText("提现方式：" + type);
			content.setText("提现账户：" + record.getTi_accounts());

			if (record.getTi_examine() == 1) {
				status.setText("待审核");
			} else if (record.getTi_examine() == 2) {
				status.setText("已审核");
			} else if (record.getTi_examine() == 3) {
				status.setText("拒绝");
			}
			price.setText("￥" + record.getTi_money());
			time.setText(record.getTi_date());
			tv_btn.setVisibility(View.GONE);
			// if (recordList.get(position).getId() == 0) {// 收入
			// price.setText("+￥" + recordList.get(position).getMoney());
			// content.setText("内容：" + recordList.get(position).getDesc());
			//
			// } else {// 提现
			// id.setText("申请编号：" + recordList.get(position).getCode());
			// price.setText("￥" + recordList.get(position).getMoney());
			// content.setText("开户行：" + recordList.get(position).getType());
			//
			// }
			// if (recordList.get(position).getStatus() == 1) {
			// status.setText("待确定");
			//
			// } else if (recordList.get(position).getStatus() == 2) {
			// status.setText("已完成");
			//
			// } else if (recordList.get(position).getStatus() == 3) {
			// status.setText("");
			// }
			// time.setText(TimeUtil.format("yyyy-MM-DD HH:mm",
			// recordList.get(position).getTime()));
		}

		OnClickListener lis = new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		};
	}

}
