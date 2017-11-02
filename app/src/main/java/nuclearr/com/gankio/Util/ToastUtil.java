package nuclearr.com.gankio.Util;

import android.widget.Toast;

import nuclearr.com.gankio.BaseApplication;

public final class ToastUtil {
    private static Toast mToast;

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
