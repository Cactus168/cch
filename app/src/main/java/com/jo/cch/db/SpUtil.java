package com.jo.cch.db;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @类名 SharedPerfer
 * @功能描述 SharedPreferences存储轻量级信息
 *
 */
public class SpUtil {

    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    private Object object;
    public static SpUtil sp;

    public static SpUtil getInstance(Context context) {
        if (sp == null) {
            sp = new SpUtil(context);
        }
        return sp;
    }

    private SpUtil(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = preferences.edit();
    }
    /**
     * 保存数据 , 所有的类型都适用
     *
     * @param key
     * @param object
     */
    public synchronized void saveParam(String key, Object object) {
        if (editor == null)
            editor = preferences.edit();
        // 得到object的类型
        String type = object.getClass().getSimpleName();
        if ("String".equals(type)) {
            // 保存String 类型
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            // 保存integer 类型
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            // 保存 boolean 类型
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            // 保存float类型
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            // 保存long类型
            editor.putLong(key, (Long) object);
        } else {
            if (!(object instanceof Serializable)) {
                throw new IllegalArgumentException(object.getClass().getName() + " 必须实现Serializable接口!");
            }
            // 不是基本类型则是保存对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                String productBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                editor.putString(key, productBase64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        editor.commit();
    }

    /**
     * 得到保存数据的方法，所有类型都适用
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getParam(String key, Object defaultObject) {
        if (defaultObject == null) {
            return getObject(key);
        }
        String type = defaultObject.getClass().getSimpleName();
        if ("String".equals(type)) {
            return preferences.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return preferences.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return preferences.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return preferences.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return preferences.getLong(key, (Long) defaultObject);
        }
        return getObject(key);
    }

    public synchronized <T> void saveList(String key, List<T> list) {
        saveParam(key,new Gson().toJson(list, new TypeToken<List<T>>(){}.getType()));
    }

    public synchronized <T> List<T> getList(String key, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            List<T> listx = new Gson().fromJson(getParam(key, "").toString(), new ParameterizedTypeImpl(List.class, new Class[]{clazz}));
            if(listx != null){
                list.addAll(listx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 移除信息
     */
    public synchronized void remove(String key) {
        if (editor == null)
            editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * Whether to use for the first time
     *
     * @return
     */
    public boolean isFirst() {
        return (Boolean) getParam("isFirst", true);
    }

    /**
     * set user first use is false
     *
     * @return
     */
    public void setFirst(Boolean isFirst) {
        saveParam("isFirst", isFirst);
    }

    /**
     * Set up the first time login
     *
     * @return
     */
    public boolean isLogin() {
        return (Boolean) getParam("isLogin", false);
    }

    /**
     * @return
     */
    public void setLogin(Boolean isLogin) {
        saveParam("isLogin", isLogin);
    }

    public Object getObject(String key) {
        String wordBase64 = preferences.getString(key, "");
        byte[] base64 = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            ObjectInputStream bis = new ObjectInputStream(bais);
            object =  bis.readObject();
            return object;
        } catch (Exception e) {

        }
        return null;
    }

}
