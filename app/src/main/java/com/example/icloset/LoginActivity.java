package com.example.icloset;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.icloset.base.BaseActivity;
import com.example.icloset.bean.Person;
import com.example.icloset.tools.DBManager;
import com.example.icloset.tools.SharePerferenceUtils;

import java.util.List;


public class LoginActivity extends BaseActivity {
    EditText mEtAcount;
    EditText mEtPw;
    DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbManager = new DBManager(this);

        mEtAcount = findViewById(R.id.mEtAcount);
        mEtPw = findViewById(R.id.mEtPw);
        findViewById(R.id.mBtnLogin).setOnClickListener(v -> login());
        findViewById(R.id.mBtnRig).setOnClickListener(v -> startActivityForResult(new Intent(this, GisActivity.class),1));

        if(!TextUtils.isEmpty(SharePerferenceUtils.getString(this,"account",""))){
            mEtAcount.setText(SharePerferenceUtils.getString(this,"account",""));
            mEtPw.setText(SharePerferenceUtils.getString(this,"password",""));
        }


        checkPermission();
    }

    private void login(){
        if(TextUtils.isEmpty(mEtAcount.getText().toString())){
            showToast("Please input username");
            return;
        }
        if(TextUtils.isEmpty(mEtPw.getText().toString())){
            showToast("Please input password");
            return;
        }

        List<Person> list = dbManager.query();
        if(list==null||list.size()==0){
            showToast("Account does not Exist");
            return;
        }
        //check if password works
        for(Person person:list){
            if(TextUtils.equals(person.getAccount(),mEtAcount.getText().toString())
                    &&TextUtils.equals(person.getPassword(),mEtPw.getText().toString())){
                SharePerferenceUtils.putString(this,"token", mEtAcount.getText().toString());
                SharePerferenceUtils.putString(this,"account", mEtAcount.getText().toString());
                SharePerferenceUtils.putString(this,"password", mEtPw.getText().toString());
                SharePerferenceUtils.putString(this,"head", person.getHead());
                showToast("Login in Successfully");
                startActivity(new Intent(this,MainActivity.class));
                finish();
                return;
            }
        }

        showToast("Wrong Username or Password");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }

    private void checkPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        if(PackageManager.PERMISSION_GRANTED!=permissionCheck){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            mEtAcount.setText(data.getStringExtra("account"));
            mEtPw.setText(data.getStringExtra("pw"));
            login();
        }
    }
}

