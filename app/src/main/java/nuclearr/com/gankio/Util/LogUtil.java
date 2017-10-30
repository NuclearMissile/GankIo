package nuclearr.com.gankio.Util;

import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;


/**
 * Created by torri on 2017/10/10.
 */

public final class LogUtil {
    private static boolean DEBUG_FLAG = true;

    private LogUtil() {
    }

    public static void i(Object o, String msg) {
        if (DEBUG_FLAG)
            Log.i(getTag(o), msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG_FLAG)
            Log.i(tag, msg);
    }

    public static void e(Object o, String msg) {
        if (DEBUG_FLAG)
            Log.e(getTag(o), msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG_FLAG)
            Log.e(tag, msg);
    }

    public static void w(Object o, String msg) {
        if (DEBUG_FLAG)
            Log.w(getTag(o), msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG_FLAG)
            Log.w(tag, msg);
    }

    @NotNull
    private static String getTag(Object o) {
        String tag = o.getClass().getSimpleName();
        if (TextUtils.isEmpty(tag))
            tag = "AnonymityClass";
        return tag;
    }
}
