package nuclearr.com.gankio.Util;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import nuclearr.com.gankio.BaseApplication;

public final class SettingUtil {
    public static boolean isOnlyWifiLoadImage() {
        return PrefUtil.getBoolean(BaseApplication.getInstance(), "OnlyWifiLoadImage", false);
    }

    public static void setOnlyWifiLoadImage(boolean isEnable) {
        PrefUtil.putBoolean(BaseApplication.getInstance(), "OnlyWifiLoadImage", isEnable);
    }

    public static void countCacheSize(@NotNull final CountCacheSizeLisenter lisenter) {
        new Thread(() -> {
            final long result = getCacheSize(BaseApplication.getInstance().getCacheDir());
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> lisenter.onResult(result));
        }).start();
    }

    public static void clearCache(@NotNull final ClearCacheListener linsenter) {
        new Thread(() -> {
            clearFile(BaseApplication.getInstance().getCacheDir());
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(linsenter::onResult);
        }).start();
    }

    private static long getCacheSize(File dir) {
        if (dir == null || !dir.isDirectory())
            return 0;
        long size = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile())
                size += file.length();
            else if (file.isDirectory()) {
                size += file.length();
                size += getCacheSize(file);
            }
        }
        return size;
    }

    private static void clearFile(File file) {
        if (file == null || !file.exists())
            return;

        if (file.isDirectory()) {
            for (File child : file.listFiles())
                clearFile(child);
        } else {
            file.delete();
        }
    }

    private static String formatFileSize(long fileS) {
        java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = decimalFormat.format((double) fileS) + "Bytes";
        } else if (fileS < 1048576) {
            fileSizeString = decimalFormat.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = decimalFormat.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = decimalFormat.format((double) fileS / 1073741824) + "GB";
        }

        if (fileSizeString.startsWith(".")) {
            return "0Bytes";
        }
        return fileSizeString;
    }

    public interface ClearCacheListener {
        void onResult();
    }

    public interface CountCacheSizeLisenter {
        void onResult(long result);
    }
}
