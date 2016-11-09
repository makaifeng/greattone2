package com.greattone.greattone.activity2;

import java.util.Arrays;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.BaseData;
/**推荐视频*/
public class RecommendToActivity extends BaseActivity {

	private TextView cname;
//	private EditText key;
	BaseData data;
	int cid = 1;
	private NormalPopuWindow pop;
	private View ll_cidll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend_to);
		data = (BaseData) getIntent().getSerializableExtra("info");
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.推荐到), true, true);
		cname = (TextView) findViewById(R.id.tv_cid);
//		key = (EditText) findViewById(R.id.et_key);
		ll_cidll=	findViewById(R.id.ll_cidll);
		ll_cidll.setOnClickListener(lis);

		findViewById(R.id.btn_button).setOnClickListener(lis);
	}

	private void initPop() {
		final String[] arrayOfString = getResources().getStringArray(R.array.video_type);
		this.pop = new NormalPopuWindow(context, Arrays.asList(arrayOfString),
				ll_cidll);
		this.pop.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
			public void OnClick(int paramAnonymousInt,String text) {
				RecommendToActivity.this.pop.dismisss();
				RecommendToActivity.this.cid = (paramAnonymousInt + 1);
				RecommendToActivity.this.cname
						.setText(arrayOfString[paramAnonymousInt]);
			}
		});
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_cidll:
				if (pop == null) {
					initPop();
				}
				pop.show();
				break;
			case R.id.btn_button:
				recvideo();
				break;

			default:
				break;
			}
		}
	};

	/** 推荐视频 */
	protected void recvideo() {
		MyProgressDialog.show(context);
//		String msg = "uid=" + Data.userData.getUid() + Data.userData.getToken()
//				+ "&id=" + data.getId() + "&cid=" + cid + "&videoid="
//				+ data.getVideoid() + "&key=" + key.getText().toString().trim()
//				+ "&desc=" + data.getDesc() + "&url=" + data.getVideo()
//				+ "&isnet=" + data.getIsIdent();
//		((BaseActivity) context).addRequest(HttpUtil.sendStringToServerByGet(
//				context, HttpConstants.CENTRE_ISRECVIDEO_URL, msg,
//				new MyStringResponseHandle() {
//
//					@Override
//					public void setServerErrorResponseHandle(
//							com.greattone.greattone.entity.Message message) {
//
//					}
//
//					@Override
//					public void setResponseHandle(
//							com.greattone.greattone.entity.Message message) {
//						toast("推荐成功！");
//						MyProgressDialog.Cancel();
//						finish();
//					}
//
//					@Override
//					public void setErrorResponseHandle(VolleyError error) {
//
//					}
//				}));
	}
}
