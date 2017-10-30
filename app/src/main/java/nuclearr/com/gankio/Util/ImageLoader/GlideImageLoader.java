package nuclearr.com.gankio.Util.ImageLoader;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.NetworkUtil;
import nuclearr.com.gankio.Util.SettingUtil;

final class GlideImageLoader {
    void loadImage(ImageRequest request) {
        Context context = request.getImageView().getContext();
        if (NetworkUtil.isWifiConnected(context)) {
            loadNet(context, request);
            return;
        }
        if (!SettingUtil.isOnlyWifiLoadImage()) {
            loadNet(context, request);
        } else {
            loadCache(context, request);
        }
    }

    private void loadCache(Context context, ImageRequest request) {
        GlideApp.with(context)
                .load(request.getUrl())
                .placeholder(request.getPlaceHolder())
                .error(request.getError())
                .onlyRetrieveFromCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(request.getImageView());
    }

/*    private void loadNet(Context context, ImageRequest request) {
        GlideApp.with(context)
                .load(request.getUrl())
                .placeholder(request.getPlaceHolder())
                .error(request.getError())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .priority(Priority.IMMEDIATE)
                .into(request.getImageView());
    }*/

    // todo fix image load order
    private void loadNet(Context context, ImageRequest request) {
        String url1 = request.getUrl();
        String url2 = (String) request.getImageView().getTag(R.id.image_id);
        if (url1.equals(url2))
            GlideApp.with(context)
                    .load(request.getUrl())
                    .placeholder(request.getPlaceHolder())
                    .error(request.getError())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            request.getImageView().setImageDrawable(resource);
                        }
                    });
    }
}
