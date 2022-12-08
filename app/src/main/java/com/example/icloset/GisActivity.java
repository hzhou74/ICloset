package com.example.icloset;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icloset.bean.Person;
import com.example.icloset.tools.DBManager;

import java.util.ArrayList;
import java.util.List;


public class GisActivity extends AppCompatActivity {
    DBManager dbManager;
    EditText mEtAcount;
    EditText mEtPw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);

        dbManager = new DBManager(this);

        mEtAcount = findViewById(R.id.mEtAcount);
        mEtPw = findViewById(R.id.mEtPw);
        findViewById(R.id.mBtnBack).setOnClickListener(v -> finish());
        findViewById(R.id.mBtnOK).setOnClickListener(v -> reg());
    }

    private void reg(){
        if(TextUtils.isEmpty(mEtAcount.getText().toString())){
            showToast("Please Set Your Username");
            return;
        }
        if(TextUtils.isEmpty(mEtPw.getText().toString())){
            showToast("Please Set Your Password");
            return;
        }

        Person person = new Person(mEtAcount.getText().toString(),mEtPw.getText().toString());
        List<Person> list = new ArrayList<>();
        list.add(person);
        dbManager.add(list);

        showToast("Sign Up Successfully");
        Intent intent = new Intent();
        intent.putExtra("account",mEtAcount.getText().toString());
        intent.putExtra("pw",mEtPw.getText().toString());
        setResult(1,intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
