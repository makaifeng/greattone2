package com.greattone.greattone.SwipeMenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.greattone.greattone.util.DisplayUtil;

public class ConnectWayCreator implements SwipeMenuCreator {
	Context  context;
	public ConnectWayCreator(Context  context) {
		this.context=context;
	}
	@Override
	public void create(SwipeMenu menu) {
		switch (menu.getViewType()) {
		case 0:
			break;

		default:
	        // create "open" item
	        SwipeMenuItem deleteItem2 = new SwipeMenuItem(
	        		context);
	        // set item background
	        deleteItem2.setBackground(new ColorDrawable(Color.RED));
	        // set item width
	        deleteItem2.setWidth(DisplayUtil.dip2px(context, 90));
	        // set item title
	        deleteItem2.setTitle("删除");
	        // set item title fontsize
	        deleteItem2.setTitleSize(18);
	        // set item title font color
	        deleteItem2.setTitleColor(Color.WHITE);
	        // add to menu
	        menu.addMenuItem(deleteItem2);
			break;
		}

	}

}
