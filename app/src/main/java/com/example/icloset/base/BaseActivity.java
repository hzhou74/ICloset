package com.example.icloset.base;

import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity {
    protected Gson gson = new Gson();
    protected ProgressDialog progressDialog;

    protected void showProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
    }

    protected void hideProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    protected void showToast(String msg){
        runOnUiThread(()-> Toast.makeText(this,msg,Toast.LENGTH_SHORT).show());
    }

}
