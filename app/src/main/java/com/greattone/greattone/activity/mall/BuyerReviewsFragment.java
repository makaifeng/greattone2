package com.greattone.greattone.activity.mall;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.adapter.PlazaMusicDetailsListAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Comment;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.widget.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 买家评论
 * Created by Administrator on 2017/2/17.
 */

public class BuyerReviewsFragment extends BaseFragment {
    List<Comment> commList = new ArrayList<>();
    private View rootView;
    private ListView lv_content;
    private String classid;
    private String id;
    int page=1;
    int pageSize=20;
    private PullToRefreshView pull_to_refresh;
    private PlazaMusicDetailsListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCommentList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_music_discuss,
                container, false);// 关联布局文件
        initView();
        return rootView;
    }
    private void initView() {
        pull_to_refresh=(PullToRefreshView)rootView.findViewById(R.id.pull_to_refresh);
        lv_content=(ListView)rootView.findViewById(R.id.lv_content);
        adapter = new PlazaMusicDetailsListAdapter(context, commList);
        lv_content.setAdapter(adapter);
    }

    /** 获取评论信息 */
    protected void getCommentList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("api", "comment/list");
        map.put("classid", classid+"");
        map.put("id", id+"");
        map.put("pageSize", pageSize+"");
        map.put("pageIndex", page+"");
        addRequest(HttpUtil.httpConnectionByPost(context, map,
                new HttpUtil.ResponseListener() {

                    @Override
                    public void setResponseHandle(Message2 message) {
                        if (message.getData().startsWith("[")) {
                            List<Comment> mlist= JSON.parseArray(message.getData(), Comment.class);
                            commList.addAll(mlist);
                        }
                        pull_to_refresh.onHeaderRefreshComplete();
                        pull_to_refresh.onFooterRefreshComplete();
                        initContentAdapter();
                        MyProgressDialog.Cancel();
                    }
                }, null));
    }
    /**
     * 加载ContentAdapter数据
     */
    protected void initContentAdapter() {
        Parcelable listState = lv_content.onSaveInstanceState();
        adapter.notifyDataSetChanged();
        lv_content.onRestoreInstanceState(listState);

    }
}
