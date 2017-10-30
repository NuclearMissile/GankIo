package nuclearr.com.gankio.Bean;

import org.jetbrains.annotations.Contract;

/**
 * Created by torri on 2017/10/17.
 */

public final class JiandanItem {

    private String pictureUrl;

    @Contract(pure = true)
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
