package nuclearr.com.gankio.Util;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

import nuclearr.com.gankio.Module.Activity.MainActivity;

/**
 * Created by torri on 2017/11/7.
 */

public final class ShareUtil {
    public static void shareText(String link) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link);
        MainActivity.getInstance().startActivity(Intent.createChooser(intent, "Share link to..."));
    }

    public static void shareImage(File file) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        MainActivity.getInstance().startActivity(Intent.createChooser(intent, "Share image to..."));
    }
}
