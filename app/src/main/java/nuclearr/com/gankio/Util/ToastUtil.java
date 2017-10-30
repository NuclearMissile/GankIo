package nuclearr.com.gankio.Util;

import android.widget.Toast;

import nuclearr.com.gankio.BaseApplication;

/**
 * Created by torri on 2017/10/20.
 */

public final class ToastUtil {
    private static Toast mToast;

    private ToastUtil() {
    }

    public static void showToast(String text, int duration) {
        if (mToast == null)
            mToast = Toast.makeText(BaseApplication.getInstance(), text, duration);
        else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null)
            mToast.cancel();
    }

}
