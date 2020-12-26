package com.jo.cch.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;
import com.jo.cch.db.DatabaseHelper;
import com.jo.cch.sql.LearnLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearnLogDao {

    private Dao<LearnLog, String> logDaoOpe;

    private DatabaseHelper helper;

    @SuppressWarnings("unchecked")
    public LearnLogDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            logDaoOpe = helper.getDao(LearnLog.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据
    public void insert(LearnLog log) {
        try {
            logDaoOpe.create(log);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //批量添加数据
    public void insertList(List<LearnLog> logs) {
        try {
            logDaoOpe.create(logs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据删除数据
    public void delete(String id) {
        DeleteBuilder<LearnLog, String> deleteBuilder = logDaoOpe.deleteBuilder();
        Where<LearnLog, String> deleteWhere = deleteBuilder.where();
        try {
            deleteWhere.eq("id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除
     */
    public void deleteList(List<LearnLog> logs) {
        try {
            logDaoOpe.delete(logs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改数据
    public int update(LearnLog log) {
        int i = 0;
        try {
            i = logDaoOpe.update(log);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    //获取id查询
    public LearnLog queryById(String id) {
        LearnLog log = null;
        try {
            List<LearnLog> list = logDaoOpe.queryBuilder().where().eq("id", id).query();
            if(list != null && list.size() > 0){
                log = list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return log;
    }

    /**
     * 查询全部
     * @return
     */
    public List<LearnLog> queryAll() {
        List<LearnLog> list = null;
        try {
            list = logDaoOpe.queryBuilder().orderBy("endDate", true).query();
            Collections.reverse(list);
            //list = (ArrayList<LearnLog>) logDaoOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
