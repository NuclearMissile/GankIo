package nuclearr.com.gankio.Network.Api;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import nuclearr.com.gankio.Bean.ZhuangItem;


public final class ZhuangService extends BaseJsoupService {

    private final String BASE_URL = "https://www.zhuangbi.info/hot/";

    public Single<List<ZhuangItem>> getItems(int pageIndex) {
        final String requestUrl = BASE_URL + "?page=" + pageIndex;
        return getDocumentObservable(BASE_URL).map(document -> {
            List<ZhuangItem> list = new LinkedList<>();
            document.body().getElementsByClass("thumbnail").forEach(element -> {
                ZhuangItem item = new ZhuangItem();
                item.setThumbnailURL(element.attr("src"));
                item.setTitle(element.attr("title"));
                item.setPictureURL(element.parent()
                        .getElementsByClass("download-link")
                        .attr("href"));
                list.add(item);
            });
            return list;
        });
    }
}
