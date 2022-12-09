package com.example.icloset;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.icloset.adapter.GridAdapter;
import com.example.icloset.base.BaseActivity;
import com.example.icloset.bean.RecordBean;
import com.example.icloset.tools.SharePerferenceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClothActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    String FILE_PATH = "";
    File currentImg;
    Uri currentUri;
    GridAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    public void setData(){
        List<RecordBean> data = SharePerferenceUtils.getRecordList(this);
        List<RecordBean> clothList = new ArrayList<>();
        List<RecordBean> trousersList = new ArrayList<>();
        List<RecordBean> shoesList = new ArrayList<>();
        List<RecordBean> otherList = new ArrayList<>();
        List<RecordBean> list = new ArrayList<>();
        for(RecordBean bean:data){
            switch (bean.getTypeTag()){
                case "Cloth":
                    clothList.add(bean);
                    break;
                case "Trousers":
                    trousersList.add(bean);
                    break;
                case "Shoes":
                    shoesList.add(bean);
                    break;
                default:
                    otherList.add(bean);
                    break;
            }
        }
        list.addAll(clothList);
        list.addAll(trousersList);
        list.addAll(shoesList);
        list.addAll(otherList);
        adapter = new GridAdapter(this,list);
        mRecyclerView.setAdapter(adapter);
    }

    public void add(View view){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Choose")
                .setNeutralButton("Cancel", null)
                .setNegativeButton("Photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        photo();
                    }
                })
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        camera();
                    }
                }).create();
        dialog.show();
    }

    private void photo(){
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 11);
    }

    private void camera(){
        FILE_PATH = Environment.getExternalStorageDirectory() +"/icloset_img/"+System.currentTimeMillis()+".jpg";
        Log.d("===","FILE_PATH:"+FILE_PATH);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        currentImg = new File(FILE_PATH);
        try {
            if(!currentImg.getParentFile().exists()){
                currentImg.getParentFile().mkdirs();
            }
            currentImg.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            currentUri = FileProvider.getUriForFile(this,
                    "com.example.icloset.fileprovider", currentImg);
        } else {
            currentUri = Uri.fromFile(currentImg);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, currentUri);
        startActivityForResult(intent, 12);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            RecordBean recordBean = new RecordBean(0,picturePath);
            SharePerferenceUtils.putRecordListItem(this,recordBean);
            setData();
        }else if(requestCode == 12 && resultCode == RESULT_OK){
            //content
            Log.d("===","currentUri:"+currentUri);
            RecordBean recordBean = new RecordBean(1,FILE_PATH);
            SharePerferenceUtils.putRecordListItem(this,recordBean);
            setData();
        }

    }

}
