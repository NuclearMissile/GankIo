package nuclearr.com.gankio.Util.ImageLoader;

import android.widget.ImageView;

import nuclearr.com.gankio.BaseApplication;
import nuclearr.com.gankio.R;

public final class ImageLoader {
    private static volatile GlideImageLoader glideImageLoader;

    private static GlideImageLoader getFactory() {
        if (glideImageLoader == null)
            synchronized (ImageLoader.class) {
                if (glideImageLoader == null)
                    glideImageLoader = new GlideImageLoader();
            }
        return glideImageLoader;
    }

    public static void showImage(ImageView imageView, String url) {
        getFactory().loadNet(BaseApplication.getInstance(), new ImageRequest.Builder()
                .url(url)
                .imgView(imageView)
                .placeHolder(R.drawable.default_img)
                .error(R.color.md_red_400)
                .create());
    }

    public static void showImage2(ImageView imageView, String url) {
        getFactory().loadNet2(BaseApplication.getInstance(), new ImageRequest.Builder()
                .url(url)
                .imgView(imageView)
                .placeHolder(R.drawable.default_img)
                .error(R.color.md_red_400)
                .create());
    }
}
