package nuclearr.com.gankio.Util;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import nuclearr.com.gankio.R;

/**
 * Created by torri on 2017/10/23.
 */

public final class CustomTabsUtil {
    public static void openUrl(Context context, String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .build()
                .launchUrl(context, Uri.parse(url));
    }
}
