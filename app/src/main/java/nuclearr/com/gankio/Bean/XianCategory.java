package nuclearr.com.gankio.Bean;

import org.jetbrains.annotations.Contract;

/**
 * Created by torri on 2017/10/9.
 */

public final class XianCategory {

    private String title;
    private String category;

    @Contract(pure = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Contract(pure = true)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
