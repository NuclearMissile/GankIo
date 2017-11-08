package nuclearr.com.gankio.Util.ImageLoader;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

final class GlideImageLoader {
/*
    void loadImage(ImageRequest request) {
        Context context = BaseApplication.getInstance();
        if (NetworkUtil.isWifiConnected(context)) {
            loadNet(context, request);
            return;
        }
        if (!SettingUtil.isOnlyWifiLoadImage()) {
            loadNet(context, request);
        } else {
            loadCache(context, request);
        }
    }*/

    void loadCache(Context context, ImageRequest request) {
        GlideApp.with(context)
                .load(request.getUrl())
                .placeholder(request.getPlaceHolder())
                .error(request.getError())
                .onlyRetrieveFromCache(true)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(request.getImageView());
    }

    void loadNet(Context context, ImageRequest request) {
        GlideApp.with(context)
                .load(request.getUrl())
                .placeholder(request.getPlaceHolder())
                .error(request.getError())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .priority(Priority.IMMEDIATE)
                .into(request.getImageView());
    }
}