package com.wzf.com.sample.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wzf.com.sample.util.L;

/**
 * Created by soonlen on 2017/3/1 15:47.
 * email wangzheng.fang@zte.com.cn
 */

public class DownloadDbHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "download";
    private final static String ID = "_id";
    private final static String THREAD_ID = "thread_id";
    private final static String S_POSI = "start_pos";
    private final static String E_POSI = "end_pos";
    private final static String COMPLETE_SIZE = "compelete_size";
    private final static String URL = "url";

    static DownloadDbHelper instance;
    private SQLiteDatabase database;

    public static DownloadDbHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DownloadDbHelper.class) {
                if (instance == null) {
                    instance = new DownloadDbHelper(context);
                }
            }
        }
        return instance;
    }

    private DownloadDbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public long insert(int thread_id, long s, long e, long total_size, String url) {
        ContentValues values = new ContentValues();
        values.put(THREAD_ID, thread_id);
        values.put(S_POSI, s);
        values.put(E_POSI, e);
        values.put(COMPLETE_SIZE, total_size);
        values.put(URL, url);
        if (database == null || !database.isOpen())
            database = getWritableDatabase();
        long result = -1L;
        if (has(url)) {
            result = database.updateWithOnConflict(DB_NAME, values, URL + "=?", new String[]{url}, 4);
        } else {
            result = database.insertWithOnConflict(DB_NAME, null, values, 5);
        }
        return result;
    }

    public DownloadInfo get(String url) {
        if (database == null || !database.isOpen())
            database = getReadableDatabase();
        DownloadInfo info = null;
        Cursor cursor = database.query(DB_NAME, new String[]{THREAD_ID, S_POSI, E_POSI, COMPLETE_SIZE, URL}, URL + "=?", new String[]{url}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                info = getInfoFromCursor(cursor);
            }
        }
        return info;
    }

    private DownloadInfo getInfoFromCursor(Cursor cursor) {
        if (cursor == null)
            return null;
        DownloadInfo info = new DownloadInfo();
        info.setThreadId(cursor.getInt(cursor.getColumnIndex(THREAD_ID)));
        info.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
        info.setStartPos(cursor.getInt(cursor.getColumnIndex(S_POSI)));
        info.setEndPos(cursor.getInt(cursor.getColumnIndex(E_POSI)));
        info.setCompeleteSize(cursor.getInt(cursor.getColumnIndex(COMPLETE_SIZE)));
        return info;
    }

    public boolean has(String url) {
        if (database == null || !database.isOpen())
            database = getReadableDatabase();
        Cursor c = database.query(DB_NAME, new String[]{URL}, URL + "=?", new String[]{url}, null, null, null);
        if (c.moveToNext()) {
            return true;
        }
        return false;
    }

    /**
     * 删除一个条目
     * @param url
     * @return
     */
    public int delete(String url) {
        if (database == null || !database.isOpen())
            database = getWritableDatabase();
        int result = database.delete(DB_NAME, URL + "=?", new String[]{url});
        return result;
    }

    public void closeDb() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        L.e("创建了数据库.....download_info");
        db.execSQL("create table " + DB_NAME + "("
                + ID + " integer PRIMARY KEY AUTOINCREMENT, "
                + THREAD_ID + " integer, "
                + S_POSI + " integer, "
                + E_POSI + " integer, "
                + COMPLETE_SIZE + " integer,"
                + URL + " char)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
