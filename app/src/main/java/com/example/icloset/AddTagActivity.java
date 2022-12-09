package com.example.icloset;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.icloset.base.BaseActivity;
import com.example.icloset.bean.RecordBean;
import com.example.icloset.tools.SharePerferenceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class AddTagActivity extends BaseActivity {
    ImageView mIvImg;
    TextView mWTag1,mWTag2,mWTag3,mWTag4;
    TextView mTTag1,mTTag2,mTTag3,mTTag4;
    TextView mTypeTag1,mTypeTag2,mTypeTag3;

    private int position;
    private String path;
    List<RecordBean> list;
    RecordBean recordBean;
    String wTag;
    String tTag;
    String typeTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtag);

        mIvImg = findViewById(R.id.mIvImg);
        mWTag1 = findViewById(R.id.mWTag1);
        mWTag2 = findViewById(R.id.mWTag2);
        mWTag3 = findViewById(R.id.mWTag3);
        mWTag4 = findViewById(R.id.mWTag4);
        mTTag1 = findViewById(R.id.mTTag1);
        mTTag2 = findViewById(R.id.mTTag2);
        mTTag3 = findViewById(R.id.mTTag3);
        mTTag4 = findViewById(R.id.mTTag4);
        mTypeTag1 = findViewById(R.id.mTypeTag1);
        mTypeTag2 = findViewById(R.id.mTypeTag2);
        mTypeTag3 = findViewById(R.id.mTypeTag3);

        mWTag1.setOnClickListener(view -> setWTvBg(mWTag1));
        mWTag2.setOnClickListener(view -> setWTvBg(mWTag2));
        mWTag3.setOnClickListener(view -> setWTvBg(mWTag3));
        mWTag4.setOnClickListener(view -> setWTvBg(mWTag4));

        mTTag1.setOnClickListener(view -> setTTvBg(mTTag1));
        mTTag2.setOnClickListener(view -> setTTvBg(mTTag2));
        mTTag3.setOnClickListener(view -> setTTvBg(mTTag3));
        mTTag4.setOnClickListener(view -> setTTvBg(mTTag4));

        mTypeTag1.setOnClickListener(view -> setTypeTvBg(mTypeTag1));
        mTypeTag2.setOnClickListener(view -> setTypeTvBg(mTypeTag2));
        mTypeTag3.setOnClickListener(view -> setTypeTvBg(mTypeTag3));

        path = getIntent().getStringExtra("path");
//        position = getIntent().getIntExtra("position",0);
        list = SharePerferenceUtils.getRecordList(this);
        for(int i=0;i<list.size();i++){
            if(TextUtils.equals(path,list.get(i).getPath())){
                position = i;
            }
        }
        recordBean = list.get(position);
        wTag = recordBean.getWTag();
        tTag = recordBean.getTTag();
        typeTag = recordBean.getTypeTag();

        if("1".equals(recordBean.getType())){
            try {
                Uri uri;
                File file = new File(recordBean.getPath());
                if (Build.VERSION.SDK_INT >= 27) {
                    uri = FileProvider.getUriForFile(this,
                            "com.example.icloset.fileprovider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
                Glide.with(this).load(bitmap).into(mIvImg);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Glide.with(this).load(recordBean.getPath()).into(mIvImg);
        }

        switch (wTag){
            case "Windy":
                setWTvBg(mWTag1);
                break;
            case "Rainy":
                setWTvBg(mWTag2);
                break;
            case "Snowy":
                setWTvBg(mWTag3);
                break;
            case "Sunny":
                setWTvBg(mWTag4);
                break;
        }

        switch (tTag){
            case "Hot":
                setTTvBg(mTTag1);
                break;
            case "Warm":
                setTTvBg(mTTag2);
                break;
            case "Cold":
                setTTvBg(mTTag3);
                break;
            case "Freeze":
                setTTvBg(mTTag4);
                break;
        }

        switch (typeTag+""){
            case "Top":
                setTypeTvBg(mTypeTag1);
                break;
            case "Bottom":
                setTypeTvBg(mTypeTag2);
                break;
            case "Shoes":
                setTypeTvBg(mTypeTag3);
                break;
        }
    }

    public void complete(View view){
        recordBean.setTTag(tTag);
        recordBean.setWTag(wTag);
        recordBean.setTypeTag(typeTag);
        list.remove(position);
        list.add(position,recordBean);
        SharePerferenceUtils.putRecordList(this,list);
        finish();
    }

    public void cancel(View view){
        finish();
    }

    private void setWTvBg(TextView tv){
        mWTag1.setBackgroundResource(R.drawable.bg_item);
        mWTag2.setBackgroundResource(R.drawable.bg_item);
        mWTag3.setBackgroundResource(R.drawable.bg_item);
        mWTag4.setBackgroundResource(R.drawable.bg_item);
        tv.setBackgroundResource(R.drawable.bg_item_checked);
        wTag = tv.getText().toString();
    }

    private void setTTvBg(TextView tv){
        mTTag1.setBackgroundResource(R.drawable.bg_item);
        mTTag2.setBackgroundResource(R.drawable.bg_item);
        mTTag3.setBackgroundResource(R.drawable.bg_item);
        mTTag4.setBackgroundResource(R.drawable.bg_item);
        tv.setBackgroundResource(R.drawable.bg_item_checked);
        tTag = tv.getText().toString();
    }

    private void setTypeTvBg(TextView tv){
        mTypeTag1.setBackgroundResource(R.drawable.bg_item);
        mTypeTag2.setBackgroundResource(R.drawable.bg_item);
        mTypeTag3.setBackgroundResource(R.drawable.bg_item);
        tv.setBackgroundResource(R.drawable.bg_item_checked);
        typeTag = tv.getText().toString();
    }
}
