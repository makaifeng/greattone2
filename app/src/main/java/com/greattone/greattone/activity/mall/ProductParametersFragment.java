package com.greattone.greattone.activity.mall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;

/**
 * Created by Administrator on 2017/2/17.
 */

public class ProductParametersFragment extends BaseFragment {

    private View rootView;
    private TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_parameters,
                container, false);// 关联布局文件
        initView();
        return rootView;
    }
    private void initView() {
        tv=(TextView)rootView.findViewById(R.id.tv);

    }
}
