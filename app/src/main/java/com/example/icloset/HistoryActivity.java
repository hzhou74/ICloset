package com.example.icloset;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icloset.adapter.HistoryAdapter;
import com.example.icloset.base.BaseActivity;
import com.example.icloset.bean.HistoryBean;
import com.example.icloset.tools.SharePerferenceUtils;

import java.util.List;

public class HistoryActivity extends BaseActivity {
    TextView mTvTitle;
    RecyclerView mRecyclerView;
    List<HistoryBean> list;
    HistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTvTitle = findViewById(R.id.mTvTitle);
        mTvTitle.setText(SharePerferenceUtils.getString(this, "account", "")+"'iCloset");

        list = SharePerferenceUtils.getHistoryList(this);
        Log.d("===",list.toString());
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new HistoryAdapter(this,list);
        mRecyclerView.setAdapter(adapter);
    }

    public void back(View view){
        finish();
    }
}
