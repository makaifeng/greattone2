package com.greattone.greattone.dialog;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.greattone.greattone.R;
import com.greattone.greattone.adapter.PopuWindowsAdapter;
import com.greattone.greattone.util.DisplayUtil;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2015-8-19 上午10:13:10 
 * @version 1.0 * @parameter  
 * @since  * @return  
 */
public class PostDeletePopu {
	private Context context;
	private View view;
	private PopupWindow popupWindow;
	private onDeleteBack listener;
	
	public PostDeletePopu(Context context,View view,onDeleteBack listener){
		this.context=context;
		this.view=view;
		this.listener=listener;
		OnCreate();
	}
	
	public void show(){
		if(popupWindow!=null){
			popupWindow.showAsDropDown(view);
		}else{
			popupWindow.dismiss();
		}
	}
	
	
	@SuppressLint("InflateParams")
	public void OnCreate(){
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.pop_window, null);
		
		ListView popuList=(ListView)contentView.findViewById(R.id.popuwindow_lv);
		List<String> list=new ArrayList<String>();
		list.add("删除");
//		list.add("编辑");
		list.add("取消");
		final PopuWindowsAdapter  adapter=new PopuWindowsAdapter(context, list, R.layout.adapter_popuwindows);
		popuList.setAdapter(adapter);
		
		int hight=0;
		for (int i=0;i<adapter.getCount();i++) {
			View item=adapter.getView(i, null,popuList);
			item.measure(0, 0);
			hight+=item.getMeasuredHeight();
		}
		//设置ListView的高度
		  ViewGroup.LayoutParams params = popuList.getLayoutParams();

		  params.height = hight
		    + (popuList.getDividerHeight() * (adapter.getCount() - 1));
		  popuList.setLayoutParams(params);
		
		//设置线性布局的高度
		hight+=DisplayUtil.dip2px(context,15);
		hight=hight+ (popuList.getDividerHeight() * (adapter.getCount() - 1))+10;
		LinearLayout ll=(LinearLayout)contentView.findViewById(R.id.ll_ycsx);
		LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) ll.getLayoutParams();
		lp.height=hight;
		ll.setPadding(DisplayUtil.dip2px(context, 8), 16, DisplayUtil.dip2px(context, 8), 0);
		ll.setLayoutParams(lp);
		 popupWindow = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, hight, true);

		popuList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				// TODO Auto-generated method stub
				adapter.setselector(paramInt);
				adapter.notifyDataSetChanged();
				switch(paramInt){
				case 0:
					listener.OnDelete();
//					mactivity.toast("删除");
					break;
//				case 1:
//					listener.OnEdit();
//					break;
				case 2:
					
					break;
			
				}
				popupWindow.dismiss();
				
				
			}
		});
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new View.OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("mengdd", "onTouch : ");
				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
//		popupWindow.setBackgroundDrawable(getResources().getDrawable(
//				R.drawable.icon_blackbg));
		// 设置好参数之后再show
		popupWindow.setBackgroundDrawable(new ColorDrawable(android.R.color.transparent)); 
		popupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
		
	}

	public interface onDeleteBack{
		public void OnDelete();
		public void OnEdit();
	}

}
