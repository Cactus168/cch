package com.jo.cch.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jo.cch.sql.LearnLog;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "wonder.mbtiles";//数据库名称
    private static final int DB_VERSION = 1;//版本只能增加 不能减小，版本号不变数据库不会更新
    private Context mContext;
    private static DatabaseHelper instance;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, LearnLog.class);//学习日志
            Log.e("数据库", "创建成功。。。。。。。。");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
      /*  if (oldVersion < 2) {
            try {
                TableUtils.dropTable(connectionSource, LearnLog.class, true);
                getLearnLog().executeRaw("ALTER TABLE `t_learn_log` ADD COLUMN no TEXT DEFAULT 0;");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }
        return instance;
    }
    /**
     * 每张表对于一个
     */
    private Dao<LearnLog, String> learnLog;

    /**
     * 获得userDao
     *
     * @return
     * @throws SQLException
     */
    public Dao<LearnLog, String> getLearnLog() throws SQLException {
        if (learnLog == null) {
            learnLog = getDao(LearnLog.class);
        }
        return learnLog;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

}
