package com.xiuman.xinjiankang.utils;

import android.content.Context;


import java.io.File;

/**
 * @author csx
 * 名称：CacheUtil.java
 * 描述：缓存管理工具
 * 时间 2014-11-24
 */
public class CacheUtil {
    public final static String cache_dir = "xingjiankang/cache/images";
    public final static int MAX_FAILCOUNT = 3; // 最大失败次数，超过即不再重新抓取
    /**
     * @param context
     * @描述：清除缓存
     * @时间 2014-11-24
     */
    public static void clearAppCache(Context context) {
        // 清除数据缓存
        clearFolder(context.getFilesDir(), System.currentTimeMillis());
        clearFolder(context.getCacheDir(), System.currentTimeMillis());
    }

    /**
     * 清除文件存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     * @return
     */
    public static int clearFolder(File dir, long curTime) {
        int cnt = 0;
        int deletedFiles = 0;
        do {
            if (dir != null && dir.isDirectory()) {
                try {
                    for (File child : dir.listFiles()) {
                        if (child.isDirectory()) {
                            deletedFiles += clearFolder(child, curTime);
                        }
                        if (child.lastModified() < curTime) {
                            if (child.delete()) {
                                deletedFiles++;
                            }
                        }
                    }
                } catch (Exception e) {
                    cnt++;
                }
            }
            return deletedFiles;
        } while (cnt < MAX_FAILCOUNT);

    }


    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
