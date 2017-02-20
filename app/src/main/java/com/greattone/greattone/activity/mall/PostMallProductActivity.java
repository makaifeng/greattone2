package com.greattone.greattone.activity.mall;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.volley.VolleyError;
import com.greattone.greattone.Listener.UpdateListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;
import com.kf_test.picselect.GalleryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 琴行发布商城的产品
 */
public class PostMallProductActivity extends BaseActivity {
    EditText et_name,et_other_name,et_model,et_color,et_city,et_telephone,et_price,et_freight,et_text;
    TextView tv_type;
    GridView gv_pic;
    Button btn_send;
    View ll_type;
    PostGridAdapter adapter;
    private ArrayList<Picture> pictureFileList=new ArrayList<>();
    View.OnClickListener lis =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()){
                    case R.id.tv_type://类型
                        List<String> list=new ArrayList<>();
                        list.add("sss");
                        NormalPopuWindow pop=   new NormalPopuWindow(context,list,ll_type);
                        pop.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
                            @Override
                            public void OnClick(int position, String text) {
                                tv_type.setText(text);
                            }
                        });
                        pop.show();
                        break;
                    case R.id.btn_send://发送
                        commit();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_post_mall_product);
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        setHead("发布产品", true, true);//发布产品
//        setOtherText(getResources().getString(R.string.添加), new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context, AddNewFriendActivity.class));
//            }
//        });
        et_name = (EditText) findViewById(R.id.et_name);
        et_other_name = (EditText) findViewById(R.id.et_other_name);
        tv_type = (TextView) findViewById(R.id.tv_type);
        ll_type = findViewById(R.id.ll_type);
        et_model = (EditText) findViewById(R.id.et_model);
        et_color = (EditText) findViewById(R.id.et_color);
        et_city = (EditText) findViewById(R.id.et_city);
        et_telephone = (EditText) findViewById(R.id.et_telephone);
        et_price = (EditText) findViewById(R.id.et_price);
        et_freight = (EditText) findViewById(R.id.et_freight);
        et_text = (EditText) findViewById(R.id.et_text);
        gv_pic = (GridView) findViewById(R.id.gv_pic);
        adapter=new PostGridAdapter(context, GalleryActivity.TYPE_PICTURE, Constants.MAXPAGE);
        gv_pic.setAdapter(adapter);
        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(lis);
        tv_type.setOnClickListener(lis);

    }
    private  String photos="";
    int num=0;
    UpdateObjectToOSSUtil updateObjectToOSSUtil;
    private ProgressDialog pd;
    HashMap<String, String> map = new HashMap<>();
    /**
     * 提交
     */
    private void commit() {
        String title=et_name.getText().toString().trim();
        String subtitle=et_other_name.getText().toString().trim();
        String type=tv_type.getText().toString().trim();
        String model=et_model.getText().toString().trim();
        String newstext=et_text.getText().toString().trim();
        String city=et_city.getText().toString().trim();
        String colour=et_color.getText().toString().trim();
        String money=et_price.getText().toString().trim();
        String telephone=et_telephone.getText().toString().trim();
        String freight=et_freight.getText().toString().trim();


        if (TextUtils.isEmpty(title)){
            toast("请输入产品名称");
            return;
        }
        if (TextUtils.isEmpty(type)){
            toast("请选择产品分类");
            return;
        }
        if (TextUtils.isEmpty(colour)){
            toast("请输入颜色分类");
            return;
        }
        if (TextUtils.isEmpty(city)){
            toast("请输入发货城市");
            return;
        }
        if (TextUtils.isEmpty(money)){
            toast("请输入价格");
            return;
        }
        if (TextUtils.isEmpty(freight)){
            toast("请输入运费");
            return;
        }
        pictureFileList=adapter.getList();
        if (pictureFileList.size()==0) {
            toast(getResources().getString(R.string.请选择图片));
            return;
        }

        map.put("title", title); //产品标题
        map.put("subtitle", subtitle);//产品副标题
        map.put("type", type);// 产品类型
        map.put("model", model);///产品型号
        map.put("newstext", newstext); //产品详细介绍
        map.put("city", city);//产品发货城市
        map.put("colour", colour);// 产品颜色
        map.put("money", money);//产品价格
        map.put("telephone", telephone);//咨询电话
        map.put("freight", freight);//运费
        num=0;
//		MyProgressDialog.show(context);
        updateObjectToOSSUtil= UpdateObjectToOSSUtil.getInstance();
        pd=new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("上传中...");
        pd.setCancelable(false);
        pd.show();
        updatePic2();
    }
    protected void updatePic2() {
        try {
            pd.setMessage("上传第"+(num+1)+"张");
            String path= pictureFileList.get(num).getPicUrl();
            updateObjectToOSSUtil.uploadImage_folder(context,path, new UpdateListener() {
                @Override
                public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                    pd.setMax((int)totalSize);
                    pd.setProgress((int)currentSize);
                }

                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    num++;
                    photos=photos+updateObjectToOSSUtil.getUrl(request.getBucketName(),request.getObjectKey())+"::::::";
                    if (num==pictureFileList.size()) {
                        map.put("titlepic", photos);  //产品多图
                        postProduct();
                    }else {
                        Message.obtain(handler,0).sendToTarget();
                    }
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    MyProgressDialog.Cancel();
                    pd.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what){
                case 0:
                    updatePic2();
                    break;
                case 1:
                    pd.setMessage((CharSequence) message.obj);
                    break;
                case 2:
                    pd.dismiss();
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    };
    private void postProduct() {
        HttpProxyUtil.postProduct(context, map, new HttpUtil.ResponseListener() {
                    @Override
                    public void setResponseHandle(Message2 message) {
                        toast(message.getInfo());
                        Message.obtain(handler, 2).sendToTarget();
                        MyProgressDialog.Cancel();
                        finish();
                    }
                },
                new HttpUtil.ErrorResponseListener() {
                    @Override
                    public void setServerErrorResponseHandle(Message2 message) {
                        Message.obtain(handler, 2).sendToTarget();
                    }

                    @Override
                    public void setErrorResponseHandle(VolleyError error) {
                        Message.obtain(handler, 2).sendToTarget();
                    }
                });
    }

}
