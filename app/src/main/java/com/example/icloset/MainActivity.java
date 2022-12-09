package com.example.icloset;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.icloset.adapter.MyPagerAdapter;
import com.example.icloset.base.BaseActivity;
import com.example.icloset.bean.HistoryBean;
import com.example.icloset.bean.RecordBean;
import com.example.icloset.bean.WeaBean;
import com.example.icloset.tools.Config;
import com.example.icloset.tools.SharePerferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    TextView mTvTip, mTvTemperature, mTvCity, mTvWeather;
    ViewPager mViewPager1, mViewPager2, mViewPager3;

    private LocationManager locationManager;
    private String urlStringformat = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=49d459ed61e4eed45169e8100aeb0558&units=metric";
    private WeaBean weaBean;
    List<RecordBean> topsList = new ArrayList<>();
    List<RecordBean> bottomsList = new ArrayList<>();
    List<RecordBean> shoesList = new ArrayList<>();
    MyPagerAdapter adapter1;
    MyPagerAdapter adapter2;
    MyPagerAdapter adapter3;
    LayoutInflater inflater;
    int topsIndex = -1;
    int bottomsIndex = -1;
    int shoesIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvTip = findViewById(R.id.mTvTip);
        mTvTemperature = findViewById(R.id.mTvTemperature);
        mTvCity = findViewById(R.id.mTvCity);
        mTvWeather = findViewById(R.id.mTvWeather);
        mViewPager1 = findViewById(R.id.mViewPager1);
        mViewPager2 = findViewById(R.id.mViewPager2);
        mViewPager3 = findViewById(R.id.mViewPager3);
        mViewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                topsIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomsIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                shoesIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        findViewById(R.id.mBtnLike).setOnClickListener(view -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.mBtnYes).setOnClickListener(view -> save());
        findViewById(R.id.mBtnCloth).setOnClickListener(view -> {
            Intent intent = new Intent(this, ClothActivity.class);
            startActivity(intent);
        });

        mTvTip.setText("Hi," + SharePerferenceUtils.getString(this, "account", ""));
        getLocation();
        inflater = getLayoutInflater();
    }

    private void setData() {
        if (weaBean != null) {
            topsList.clear();
            bottomsList.clear();
            shoesList.clear();
            topsIndex = -1;
            bottomsIndex = -1;
            shoesIndex = -1;
            String tempTag = Config.getTempTag(weaBean.getMain().getTemp());
            List<RecordBean> allList = SharePerferenceUtils.getRecordList(this);
            for (RecordBean bean : allList) {
                if (tempTag.equals(bean.getTTag())) {
                    switch (bean.getTypeTag()) {
                        case "Top":
                            topsList.add(bean);
                            break;
                        case "Bottom":
                            bottomsList.add(bean);
                            break;
                        case "Shoes":
                            shoesList.add(bean);
                            break;
                    }
                }
            }
            if (topsList.size() > 0) {
                topsIndex = 0;
            }
            if (bottomsList.size() > 0) {
                bottomsIndex = 0;
            }
            if (shoesList.size() > 0) {
                shoesIndex = 0;
            }
            adapter1 = new MyPagerAdapter(this, inflater, topsList);
            mViewPager1.setAdapter(adapter1);

            adapter2 = new MyPagerAdapter(this, inflater, bottomsList);
            mViewPager2.setAdapter(adapter2);

            adapter3 = new MyPagerAdapter(this, inflater, shoesList);
            mViewPager3.setAdapter(adapter3);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void save() {
        if (weaBean != null) {
            String top = "";
            String bottom = "";
            String shoes = "";
            if (topsIndex != -1) {
                top = topsList.get(topsIndex).getPath();
            }
            if (bottomsIndex != -1) {
                bottom = bottomsList.get(bottomsIndex).getPath();
            }
            if (shoesIndex != -1) {
                shoes = shoesList.get(shoesIndex).getPath();
            }
            if (TextUtils.isEmpty(top) || TextUtils.isEmpty(bottom) || TextUtils.isEmpty(shoes)) {
                return;
            }
            List<HistoryBean> historyBeanList = SharePerferenceUtils.getHistoryList(this);
            for (HistoryBean bean : historyBeanList) {
                if (TextUtils.equals(bean.getTop(), top)
                        && TextUtils.equals(bean.getBottom(), bottom)
                        && TextUtils.equals(bean.getShoes(), shoes)) {
                    return;
                }
            }
            HistoryBean historyBean = new HistoryBean(top, bottom, shoes);
            historyBean.setWeather(weaBean.getWeather().get(0).getMain());
            historyBean.setTemp(weaBean.getMain().getTemp());
            SharePerferenceUtils.putHistoryListItem(this, historyBean);
            showToast("You Choose Beautiful Clothes Today!");
        }
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showToast("Please Grant Location Permission");
            return;
        }
        Criteria criteria = new Criteria();
        String bestProv = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProv);
        if (location != null) {
            String urlString = String.format(urlStringformat, location.getLatitude(), location.getLongitude());
            dpOkHttp(urlString);
        } else {
            String urlString = String.format(urlStringformat, 32.8482013, -177.2273002);
            dpOkHttp(urlString);
            showToast("Fail to locate GPS");
        }

    }

    private void setWeather() {
        mTvCity.setText(weaBean.getName());
        mTvTemperature.setText(weaBean.getMain().getTemp() + "℃");
        mTvWeather.setText(weaBean.getWeather().get(0).getMain());
        setData();
    }

    private void dpOkHttp(String url) {
        Log.d("===", url);
        showProgressDialog();
        OkHttpClient httpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        httpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        hideProgressDialog();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        hideProgressDialog();
                        weaBean = gson.fromJson(response.body().string(), WeaBean.class);
                        Log.d("===", gson.toJson(weaBean));
                        if (weaBean.getCod() == 200) {
                            runOnUiThread(() -> setWeather());
                        } else {
                            showToast("Fail to fetch weather information");
                        }
                    }
                });
    }

}