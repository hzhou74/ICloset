package com.example.icloset.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.icloset.AddTagActivity;
import com.example.icloset.R;
import com.example.icloset.bean.RecordBean;
import com.example.icloset.tools.SharePerferenceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyHV> {
    private Context context;
    private List<RecordBean> data;

    public GridAdapter(Context context, List<RecordBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyHV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHV(LayoutInflater.from(context).inflate(R.layout.item_img,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHV holder, @SuppressLint("RecyclerView") int position) {
        if("1".equals(data.get(position).getType())){
            try {
                Uri uri;
                File file = new File(data.get(position).getPath());
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(context,
                            "com.example.icloset.fileprovider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                Glide.with(context).load(bitmap).into(holder.mImg);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Glide.with(context).load(data.get(position).getPath()).into(holder.mImg);
        }
        holder.mTvWTag.setText(data.get(position).getWTag());
        holder.mTvTTag.setText(data.get(position).getTTag());
        holder.mTvTypeTag.setText(data.get(position).getTypeTag());
        holder.mImg.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddTagActivity.class);
            intent.putExtra("position",position);
            context.startActivity(intent);
        });
        holder.mImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDelete(position);
                return false;
            }
        });
    }

    private void showDelete(int position){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Pls Confirm Delete")
                .setNeutralButton("Cancel",null)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.remove(position);
                        SharePerferenceUtils.putRecordList(context,data);
                        notifyDataSetChanged();
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHV extends RecyclerView.ViewHolder{
        ImageView mImg;
        TextView mTvWTag,mTvTTag,mTvTypeTag;

        public MyHV(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.mImg);
            mTvWTag = itemView.findViewById(R.id.mTvWTag);
            mTvTTag = itemView.findViewById(R.id.mTvTTag);
            mTvTypeTag = itemView.findViewById(R.id.mTvTypeTag);
        }
    }

}
