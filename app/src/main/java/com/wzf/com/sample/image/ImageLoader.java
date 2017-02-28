package com.wzf.com.sample.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.wzf.com.sample.util.L;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by soonlen on 2017/2/27 10:40.
 * email wangzheng.fang@zte.com.cn
 */

public class ImageLoader {

    private static final String ERROR_WRONG_ARGUMENTS = "Wrong arguments were passed to displayImage() method (ImageView reference must not be null)";
    final static int MAX_CACHE_COUNT = 20;
    final static int APP_VERSION = 1;
    final static long DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    private final static String TAG = ImageLoader.class.getClass().getSimpleName();
    private final static String BM_PATH = "bitmap";
    private static Set<DownloadTask> taskCollection;

    static ImageLoader instance;
    static Context mContext;
    //一级缓存
    static LruCache<String, Bitmap> firstCache = new LruCache<String, Bitmap>(MAX_CACHE_COUNT) {
        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            if (oldValue != null) {
                Log.e(TAG, "remove bitmap from first cache , url is " + key);
//                secondCache.put(key, new SoftReference<>(oldValue));
                putSecondeCache(key, oldValue);
            }
        }
    };
    //二级缓存
    static ConcurrentHashMap<String, SoftReference<Bitmap>> secondCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(MAX_CACHE_COUNT) {
        @Override
        public boolean remove(Object key, Object value) {
            return super.remove(key, value);
        }
    };
    //磁盘缓存
    static DiskLruCache diskCache;
    static DefaultDrawble defaultDrawble = new DefaultDrawble();

    public static void init(Context context) {
        mContext = context;
        taskCollection = new HashSet<>();
        initDiskLruCache();
    }

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    /**
     * @param url
     * @param imageView
     */
    public void loadImage(String url, ImageView imageView) {
//        TimeUtil.start();
        //如果url和imageview为空，直接抛出异常
        if (null == url)
            throw new IllegalArgumentException(ERROR_WRONG_ARGUMENTS);
        if (imageView == null)
            throw new IllegalArgumentException(ERROR_WRONG_ARGUMENTS);
        //一级缓存中获取图片
        Bitmap bitmap = getFromFirstCache(url);
        if (bitmap != null) {
            Log.e(TAG, "get bitmap from first cache , url is " + url);
            imageView.setImageBitmap(bitmap);
            return;
        }
        //二级缓存中获取图片
        bitmap = getFromSecondCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            putFistCache(url, bitmap);
            Log.e(TAG, "get bitmap from second cache , url is " + url);
            return;
        }
        //启动线程去展示图片，如果缓存中有，则展示缓存中的图片，如果没有，则网络获取
//        imageView.setImageDrawable(defaultDrawble);
        DownloadTask task = new DownloadTask(imageView);
        task.execute(url);
        taskCollection.add(task);
    }

    /**
     * 下载图片线程
     */
    class DownloadTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;
        private ImageView imageView;

        public DownloadTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageView.setImageDrawable(defaultDrawble);
            DownloadTask oTask = (DownloadTask) imageView.getTag();
            if (oTask != null) {
                oTask.cancel(true);
            }
            imageView.setTag(this);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            FileDescriptor fileDescriptor = null;
            FileInputStream fileInputStream = null;
            DiskLruCache.Snapshot snapShot = null;
            try {
                // 生成图片URL对应的key
                final String key = hashKeyForDisk(imageUrl);
                // 查找key对应的缓存
                snapShot = diskCache.get(key);
                if (snapShot == null) {
                    // 如果没有找到对应的缓存，则准备从网络上请求数据，并写入缓存
                    DiskLruCache.Editor editor = diskCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(imageUrl, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    // 缓存被写入后，再次查找key对应的缓存
                    snapShot = diskCache.get(key);
                }
                if (snapShot != null) {
                    fileInputStream = (FileInputStream) snapShot.getInputStream(0);
                    fileDescriptor = fileInputStream.getFD();
                }
                // 将缓存数据解析成Bitmap对象
                Bitmap bitmap = null;
                if (fileDescriptor != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                }
                if (bitmap != null) {
                    // 将Bitmap对象添加到内存缓存当中
                    putFistCache(imageUrl, bitmap);
                    putSecondeCache(imageUrl, bitmap);
                }
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileDescriptor == null && fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // 将下载好的图片显示出来。
            if (!this.isCancelled() && imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }
    }

    /**
     * 建立HTTP请求，并获取Bitmap对象。
     *
     * @param
     * @return 解析后的Bitmap对象
     */
    private boolean downloadUrlToStream(String urlString,
                                        OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            L.e("prepare to download url is " + urlString);
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            L.e("download is success url is " + urlString);
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 添加二级缓存
     *
     * @param url
     * @param bitmap
     */
    private static synchronized void putSecondeCache(String url, Bitmap bitmap) {
        Log.e(TAG, "add bitmap to seconde cache, url is  " + url);
        secondCache.put(url, new SoftReference<Bitmap>(bitmap));
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 初始化DiskLruCache
     */
    private static synchronized void initDiskLruCache() {
        if (diskCache != null)
            return;
        try {
            File file = getDiskCacheDir(BM_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            diskCache = DiskLruCache.open(file, APP_VERSION, 1, DISK_CACHE_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取DiskCache路径
     *
     * @param path
     * @return
     */
    private static File getDiskCacheDir(String path) {
        String cachePath;
        if (SdCardUtil.isMounted() && SdCardUtil.avaliableSize() > 20 * 1024 * 1024 * 8) {
            cachePath = mContext.getExternalCacheDir().getPath();
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }
        Log.e(TAG, "cache path is " + cachePath);
        return new File(cachePath + File.separator + path);
    }

    /**
     * 放置一张图片到一级缓存中
     *
     * @param url
     * @param bitmap
     */
    private synchronized void putFistCache(String url, Bitmap bitmap) {
        Log.e(TAG, "add bitmap to first cache, url is  " + url);
        firstCache.put(url, bitmap);
    }

    /**
     * 从二级缓存中获取图片
     *
     * @param url
     * @return
     */
    private synchronized Bitmap getFromSecondCache(String url) {
        SoftReference<Bitmap> ref = secondCache.get(url);
        if (ref != null) {
            Bitmap bitmap = ref.get();
            if (bitmap != null) {
                return bitmap;
            }
        }
        return null;
    }

    /**
     * 从一级缓存中获取bitmap
     *
     * @param url
     * @return
     */
    private synchronized Bitmap getFromFirstCache(String url) {
        Bitmap bitmap = firstCache.get(url);
        if (bitmap != null)
            return bitmap;
        return null;
    }

    /**
     * 默认展示的图片透明
     */
    static class DefaultDrawble extends ColorDrawable {
        public DefaultDrawble() {
            super(Color.TRANSPARENT);
        }
    }
}
