package com.greattone.greattone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.greattone.greattone.activity.BaseActivity;

public class EditTextActivity extends BaseActivity {

    private EditText edittext;

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
        edittext.setText(getIntent().getStringExtra("text"));
    }
}
