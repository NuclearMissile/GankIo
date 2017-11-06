package nuclearr.com.gankio.Bean;

import org.jetbrains.annotations.Contract;

/**
 * Created by torri on 2017/10/17.
 */

public final class JiandanItem implements IImageItem {

    private String imageUrl;

    @Contract(pure = true)
    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getThumbnailUrl() {
        return getImageUrl().replace("large", "mw600");
    }
}
