package com.example.icloset.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.icloset.R;
import com.example.icloset.bean.RecordBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private List<RecordBean> recordBeanList;
    private ArrayList<View> viewLists = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public MyPagerAdapter(Context context,LayoutInflater inflater, List<RecordBean> recordBeanList) {
        super();
        this.context = context;
        this.inflater = inflater;
        this.recordBeanList = recordBeanList;
        for(RecordBean bean:recordBeanList){
            View view = inflater.inflate(R.layout.item_page_img,null);
            ImageView mImg = view.findViewById(R.id.mImg);
            setImg(bean,mImg);
            viewLists.add(view);
        }
    }

    private void setImg(RecordBean recordBean,ImageView mIvImg){
        if("1".equals(recordBean.getType())){
            try {
                Uri uri;
                File file = new File(recordBean.getPath());
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(context,
                            "com.example.icloset.fileprovider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                Glide.with(context).load(bitmap).into(mIvImg);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Glide.with(context).load(recordBean.getPath()).into(mIvImg);
        }
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
}
