package com.greattone.greattone.SwipeMenu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.greattone.greattone.util.DisplayUtil;

public class MySwipeMenuCreator implements SwipeMenuCreator {
	Context  context;
	List<String> mlist=new ArrayList<String>();
	public MySwipeMenuCreator(Context  context) {
		this.context=context;
	}
	@Override
	public void create(SwipeMenu menu) {
		if (mlist.size()>0) {
			for (int i = 0; i < mlist.size(); i++) {
				   // create "open" item
		        SwipeMenuItem deleteItem = new SwipeMenuItem(
		                context);
		        // set item background
		        deleteItem.setBackground(new ColorDrawable(Color.RED));
		        // set item width
		        deleteItem.setWidth(DisplayUtil.dip2px(context, 90));
		        // set item title
		        deleteItem.setTitle(mlist.get(i));
		        // set item title fontsize
		        deleteItem.setTitleSize(18);
		        // set item title font color
		        deleteItem.setTitleColor(Color.WHITE);
		        // add to menu
		        menu.addMenuItem(deleteItem);
			}
			
		}else{
		
		
		   // create "open" item
        SwipeMenuItem deleteItem = new SwipeMenuItem(
                context);
        // set item background
        deleteItem.setBackground(new ColorDrawable(Color.BLUE));
        // set item width
        deleteItem.setWidth(DisplayUtil.dip2px(context, 90));
        // set item title
        deleteItem.setTitle("编辑");
        // set item title fontsize
        deleteItem.setTitleSize(18);
        // set item title font color
        deleteItem.setTitleColor(Color.WHITE);
        // add to menu
        menu.addMenuItem(deleteItem);
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
	}
		}
/**
 * 设置侧滑按钮的文字
 * @param str 文字的集合
 */
	public void setTexts(String [] str){
		mlist=new ArrayList<String>();
		for (String string : str) {
			mlist.add(string);
		}
	}
	/**
	 * 设置侧滑按钮的文字
	 * @param list 文字的集合
	 */
	public void setTexts(List<String>list){
		mlist=list;
	}
}
