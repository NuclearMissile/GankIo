package nuclearr.com.gankio.Util.ImageLoader;

import android.text.TextUtils;
import android.widget.ImageView;

/**
 * Created by torri on 2017/10/19.
 */

public final class ImageRequest {
    private static final String TAG = "ImageRequest";
    private String url;
    private int placeHolder;
    private int error;
    private ImageView imageView;

    public ImageRequest(Builder builder) {
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.error = builder.error;
        this.imageView = builder.imageView;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getError() {
        return error;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public static class Builder {
        private String url;
        private int placeHolder;
        private int error;
        private ImageView imageView;

        public Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder error(int error) {
            this.error = error;
            return this;
        }

        Builder imgView(ImageView imgView) {
            this.imageView = imgView;
            return this;
        }

        ImageRequest create() {
            if (imageView == null)
                throw new IllegalArgumentException(TAG + ": imageView is null");
            if (TextUtils.isEmpty(url))
                throw new IllegalArgumentException(TAG + ": url is empty");
            return new ImageRequest(this);
        }
    }
}
