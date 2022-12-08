package com.example.icloset.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.icloset.bean.HistoryBean;
import com.example.icloset.bean.RecordBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharePerferenceUtils {

    private static SharedPreferences sp;
    private static Gson gson = new Gson();

    //  store boolean variable
    public static void putBoolean(Context ctx, String key, boolean value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    // read boolean variable
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void putString(Context ctx, String key, String value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }


    public static void removeKey(Context ctx, String key) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }


    public static void putInt(Context ctx, String key, int value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    public static List<String> getStringList(Context ctx, String key){
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        String str = sp.getString(key,"");
        if(TextUtils.isEmpty(str)){
            return new ArrayList<>();
        }
        return gson.fromJson(str,new TypeToken<List<String>>(){}.getType());
    }

    public static void putStringList(Context ctx, String key, List<String> value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        String str = gson.toJson(value);
        sp.edit().putString(key, str).commit();
    }

    public static void putStringListItem(Context ctx, String key, String value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        List<String> list = getStringList(ctx,key);
        list.add(value);
        putStringList(ctx,key,list);
    }

    public static List<RecordBean> getRecordList(Context ctx){
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        String key = "Record";
        String str = sp.getString(key,"");
        if(TextUtils.isEmpty(str)){
            return new ArrayList<>();
        }
        return gson.fromJson(str,new TypeToken<List<RecordBean>>(){}.getType());
    }

    public static void putRecordList(Context ctx, List<RecordBean> value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        String key = "Record";
        String str = gson.toJson(value);
        sp.edit().putString(key, str).commit();
    }

    public static void putRecordListItem(Context ctx, RecordBean value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        List<RecordBean> list = getRecordList(ctx);
        list.add(value);
        putRecordList(ctx,list);
    }

    public static List<HistoryBean> getHistoryList(Context ctx){
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        String key = "History";
        String str = sp.getString(key,"");
        if(TextUtils.isEmpty(str)){
            return new ArrayList<>();
        }
        return gson.fromJson(str,new TypeToken<List<HistoryBean>>(){}.getType());
    }

    public static void putHistoryList(Context ctx, List<HistoryBean> value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        String key = "History";
        String str = gson.toJson(value);
        sp.edit().putString(key, str).commit();
    }

    public static void putHistoryListItem(Context ctx, HistoryBean value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        List<HistoryBean> list = getHistoryList(ctx);
        list.add(value);
        putHistoryList(ctx,list);
    }
}
