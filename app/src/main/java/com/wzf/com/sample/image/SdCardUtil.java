package com.wzf.com.sample.image;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * Created by soonlen on 2017/2/27 12:00.
 * email wangzheng.fang@zte.com.cn
 */

public class SdCardUtil {

    public static boolean isMounted() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return true;
        }
        return false;
    }

    public static long avaliableSize() {
        String storageDirectory = null;
        storageDirectory = Environment.getExternalStorageDirectory().toString();
        long avaliableSize = 0;
        try {
            StatFs stat = new StatFs(storageDirectory);
            avaliableSize = stat.getAvailableBlocksLong();
//            avaliableSize = ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
            return avaliableSize;
        } catch (RuntimeException ex) {
            Log.e("error", "sd card mounted is error");
            return 0;
        }
    }

}
