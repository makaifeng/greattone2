package com.greattone.greattone;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.greattone.greattone.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.greattone.greattone.util.DisplayUtil.dip2px;

public class EditTextActivity extends BaseActivity {

    private EditText edittext;
    private ListView listView;
List<String > mlist=new ArrayList<>();
List<String > intentlist=new ArrayList<>();
    boolean isShowList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        init();
    }

    private void init() {
        setHead(getIntent().getStringExtra("title"),true,true);
        setOtherText("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK,new Intent().putExtra("text",edittext.getText().toString().trim()));
                finish();
            }
        });
        edittext=(EditText)findViewById(R.id.edit);
        listView= (ListView) findViewById(R.id.listView);
        edittext.setText(getIntent().getStringExtra("text"));
        edittext.addTextChangedListener(watcher);
        isShowList=getIntent().getBooleanExtra("isShowList",false);
        if (isShowList){
            listView.setVisibility(View.VISIBLE);
            intentlist=getIntent().getStringArrayListExtra("list");
            listView.setAdapter(new MyAdapter());
        }else {
            listView.setVisibility(View.GONE);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edittext.setText(mlist.get(position));
            }
        });
    }
    TextWatcher watcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mlist.clear();
            if (TextUtils.isEmpty(s)){
                return;
            }
            for (String str:intentlist){
                if (str.contains(s.toString())){
                    mlist.add(str);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    class  MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mlist == null ? 0 : mlist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view==null) {
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                final int position = i;
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params1.rightMargin = 1;
                final TextView tv = new TextView(context);
                tv.setLayoutParams(params1);
                tv.setTextSize(18);
                tv.setText(mlist.get(i));
                tv.setTextColor(context.getResources().getColor(R.color.dialogTxtColor));
                int pad = context.getResources().getDimensionPixelSize(R.dimen.padding10);
                tv.setPadding(pad, pad, pad, pad);
                tv.setSingleLine(true);
                tv.setGravity(Gravity.CENTER);
                layout.addView(tv);
                View view1 = new View(context);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dip2px(context, 1));
                view1.setLayoutParams(params2);
                view1.setBackgroundColor(context.getResources().getColor(R.color.gray_eeeeee));
                layout.addView(view1);
                view=layout;
                view.setTag(tv);
            }else {
                final TextView tv= (TextView) view.getTag();
                tv.setText(mlist.get(i));
            }
            return view;
        }
    }
}
