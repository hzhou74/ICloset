package com.example.icloset.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.icloset.AddTagActivity;
import com.example.icloset.R;
import com.example.icloset.bean.HistoryBean;
import com.example.icloset.bean.RecordBean;
import com.example.icloset.tools.SharePerferenceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHV> {
    private Context context;
    private List<HistoryBean> data;

    public HistoryAdapter(Context context, List<HistoryBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyHV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHV(LayoutInflater.from(context).inflate(R.layout.item_history,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHV holder, @SuppressLint("RecyclerView") int position) {
        HistoryBean bean = data.get(position);
        if(!TextUtils.isEmpty(bean.getCloth())){
            setImg(bean.getCloth(), holder.mIv1);
        }
        if(!TextUtils.isEmpty(bean.getTrousers())){
            setImg(bean.getTrousers(), holder.mIv2);
        }
        if(!TextUtils.isEmpty(bean.getShoes())){
            setImg(bean.getShoes(), holder.mIv3);
        }
        holder.mLl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDelete(position);
                return false;
            }
        });

        holder.mTvTip.setText(bean.getWeather()+" "+bean.getTemp()+"℃");
    }

    private void showDelete(int position){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Pls Confirm Delete")
                .setNeutralButton("Cancel",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.remove(position);
                        SharePerferenceUtils.putHistoryList(context,data);
                        notifyDataSetChanged();
                    }
                })
                .create();
        dialog.show();
    }

    private void setImg(String path,ImageView iv){
        if(path.contains("icloset_img")){//拍照
            try {
                Uri uri;
                File file = new File(path);
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(context,
                            "com.example.icloset.fileprovider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                Glide.with(context).load(bitmap).into(iv);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Glide.with(context).load(path).into(iv);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHV extends RecyclerView.ViewHolder{
        ImageView mIv1,mIv2,mIv3;
        LinearLayout mLl;
        TextView mTvTip;

        public MyHV(@NonNull View itemView) {
            super(itemView);
            mLl = itemView.findViewById(R.id.mLl);
            mIv1 = itemView.findViewById(R.id.mIv1);
            mIv2 = itemView.findViewById(R.id.mIv2);
            mIv3 = itemView.findViewById(R.id.mIv3);
            mTvTip = itemView.findViewById(R.id.mTvTip);

        }
    }

}
