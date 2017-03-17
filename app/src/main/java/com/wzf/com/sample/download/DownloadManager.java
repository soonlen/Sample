package com.wzf.com.sample.download;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.wzf.com.sample.util.L;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

/**
 * Created by soonlen on 2017/3/1 16:37.
 * email wangzheng.fang@zte.com.cn
 */

public class DownloadManager {

    static DownloadManager instance;
    private LinkedHashMap<String, DownloadTask> tasks;
    private LinkedHashMap<String, Handler> handlers;

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    private void init() {
        if (handlers == null)
            handlers = new LinkedHashMap<>();
        if (tasks == null)
            tasks = new LinkedHashMap<>();
    }

    public void download(Context context, Handler handler, String url) {
        init();
        String key = url;
        if (TextUtils.isEmpty(key))
            return;
        if (handlers.get(handler) == null) {
            handlers.put(key, handler);
        }
        DownloadTask task = tasks.get(key);
        if (task == null) {
            task = new DownloadTask(handler, context);
            task.setPause(false);
            task.execute(key);
            tasks.put(key, task);
        } else {
            task.cancel(true);
            tasks.remove(task);
            task = new DownloadTask(handler, context);
            task.setPause(false);
            task.execute(key);
            tasks.put(key, task);
        }
    }

    public void pause(String url) {
        String key = url;
        if (TextUtils.isEmpty(key))
            return;
        DownloadTask task = tasks.get(key);
        if (task != null) {
            task.setPause(true);
        }
    }

    class DownloadTask extends AsyncTask<String, Integer, DownloadInfo> {

        private Context context;
        private Handler mHandler;
        private DownloadDbHelper dbHelper = null;
        private long length = 0;
        private long currentLength = 0;
        private boolean pause = false;
        private String strUrl;
        private DownloadInfo info;

        public void setPause(boolean pause) {
            this.pause = pause;
            if (pause)
                this.cancel(pause);
        }

        public DownloadTask(Handler handler, Context context) {
            this.mHandler = handler;
            this.context = context;
            dbHelper = DownloadDbHelper.getInstance(this.context);
        }

        @Override
        protected DownloadInfo doInBackground(String... params) {
            strUrl = params[0];
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            RandomAccessFile randomAccessFile = null;
            // 文件保存路径
            String path = Environment.getExternalStorageDirectory().getPath();
            // 文件名
            String fileName = strUrl.substring(strUrl.lastIndexOf('/'));
            info = dbHelper.get(strUrl);
            if (info != null) {
                currentLength = info.getStartPos();
            }else {
                info = new DownloadInfo();
                info.setUrl(strUrl);
            }
            L.e("保存的路径：" + path + ",file name is " + fileName);
            L.e("数据库中获取到的下载进度为：" + currentLength);
            try {
                connection = (HttpURLConnection) new URL(strUrl).openConnection();
                connection.setAllowUserInteraction(true);
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "NetFox");
                connection.setRequestProperty("Range", "bytes=" + currentLength + "-");
                length = connection.getContentLength();
                L.e("要下载的文件总长度：" + length);
                info.setCompeleteSize(length);
                inputStream = connection.getInputStream();
                randomAccessFile = new RandomAccessFile(path + fileName, "rw");
                randomAccessFile.seek(currentLength);
                byte[] buff = new byte[8192];
                while (!pause) {
                    int readLength = inputStream.read(buff);
                    if (readLength != -1) {
                        randomAccessFile.write(buff, 0, readLength);
                        currentLength += readLength;
                        publishProgress((int) (currentLength * 1.0 / length) * 100);
                        info.setStartPos(currentLength);
                        dbHelper.insert(1, currentLength, 1, length, strUrl);
                    }
                    if (currentLength == length) {
                        info.setCompeleteSize(currentLength);
                        break;
                    }
                }
                L.e("线程执行完的当前进度：" + currentLength);
                handlers.remove(mHandler);
                tasks.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
                dbHelper.closeDb();
            }
            return info;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            if (!pause && !this.isCancelled()) {
                Message msg = mHandler.obtainMessage();
                msg.what = 11;
                msg.arg1 = values[0];
                mHandler.sendMessage(msg);
//            }
        }

        @Override
        protected void onPostExecute(DownloadInfo info) {
            Message msg = mHandler.obtainMessage();
            msg.obj = info;
            if (!pause && !this.isCancelled()) {
                msg.what = 12;
                //下载完成
                dbHelper.delete(strUrl);
            } else if (pause) {
                msg.what = 13;
                L.e("暂停了下载……");
            } else if (this.isCancelled()) {
                msg.what = 14;
                L.e("取消了下载……");
            }
            mHandler.sendMessage(msg);
        }
    }

}
