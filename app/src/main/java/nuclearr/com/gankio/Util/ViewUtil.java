package nuclearr.com.gankio.Util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.InputMismatchException;

import nuclearr.com.gankio.Module.Fragment.Base.BaseFragment;

public final class ViewUtil {
    private static final String TAG = "ViewUtil";

    public static BaseFragment genFragment(Class<?> cls) {
        BaseFragment result = null;
        try {
            result = (BaseFragment) Class.forName(cls.getName()).newInstance();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return result;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static int dp2px(Context context, int dp) {
        float scale = getDisplayMetrics(context).density;
        return (int) (dp * scale + 0.5f * (dp >= 0 ? 1 : -1));
    }

    public static int px2dp(Context context, int px) {
        float scale = getDisplayMetrics(context).density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    public static int sp2px(Context context, int sp) {
        float scale = getDisplayMetrics(context).scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    public static int px2sp(Context context, int px) {
        float scale = getDisplayMetrics(context).scaledDensity;
        return (int) (px / scale + 0.5f);
    }

    // themeColorRes -> android.R.attr.colorAccent android.R.attr.colorPrimary android.R.attr.colorPrimaryDark
    public static int getThemeColor(Context context, int themeColorRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.theme, typedValue, true);
        if (themeColorRes != android.R.attr.colorAccent &&
                themeColorRes != android.R.attr.colorPrimary &&
                themeColorRes != android.R.attr.colorPrimaryDark)
            throw new InputMismatchException();
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{themeColorRes});
        int color = typedArray.getColor(0, -1);
        typedArray.recycle();
        return color;
    }


}
