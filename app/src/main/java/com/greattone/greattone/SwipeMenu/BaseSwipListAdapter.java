package com.greattone.greattone.SwipeMenu;


import android.widget.BaseAdapter;

/**
 * Created by Abner on 15/11/20.
 * Email nimengbo@gmail.com
 * github https://github.com/nimengbo
 */
public abstract class BaseSwipListAdapter extends BaseAdapter {

    public boolean getSwipEnableByPosition(int position){
        return true;
    }



}
