package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.MyQA;

public class QAOrderAdapter extends Adapter<MyQA> {

	public QAOrderAdapter(Context context, List<MyQA> list, int resId) {
		super(context, list, resId);
	}

	@Override
	public void getview(ViewHolder holder, final int position, final MyQA myQA) {

		TextView tv_num = (TextView) holder.getView(R.id.tv_num);
		TextView tv_content = (TextView) holder.getView(R.id.tv_content);
		TextView tv_type = (TextView) holder.getView(R.id.tv_type);
		TextView tv_time = (TextView) holder.getView(R.id.tv_time);
		TextView tv_status = (TextView) holder.getView(R.id.tv_status);
		TextView tv_price = (TextView) holder.getView(R.id.tv_price);
		LinearLayout ll_del = (LinearLayout) holder.getView(R.id.ll_del);
		tv_num.setText(myQA.getQa_id());
		tv_type.setText(myQA.getQa_dingjia());
		tv_content.setText("提问问题：" + myQA.getTitle());
		tv_time.setText(myQA.getNewspath());
			tv_status.setText(myQA.getQa_dingjia());
		tv_price.setText("￥" + myQA.getTitle());
		ll_del.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				MyProgressDialog.show(context);
//				String msg = "uid=" + Data.userData.getUid() + "&id="
//						+ myQA.getId() + "&token=" + Data.userData.getToken();
//				((BaseActivity) context).addRequest(HttpUtil
//						.sendStringToServerByGet(context,
//								HttpConstants.CENTRE_DEL_QA_URL, msg,
//								new MyStringResponseHandle() {
//
//									@Override
//									public void setServerErrorResponseHandle(
//											com.greattone.greattone.entity.Message message) {
//
//									}
//
//									@Override
//									public void setResponseHandle(
//											com.greattone.greattone.entity.Message message) {
//										mlist.remove(position);
//										notifyDataSetChanged();
//										MyProgressDialog.Cancel();
//									}
//
//									@Override
//									public void setErrorResponseHandle(
//											VolleyError error) {
//
//									}
//								}));
			}
		});
	}

}
