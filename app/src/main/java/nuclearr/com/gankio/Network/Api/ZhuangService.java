package nuclearr.com.gankio.Network.Api;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import nuclearr.com.gankio.Bean.ZhuangItem;


public class ZhuangService extends BaseJsoupService {

    private final static String BASE_URL = "https://www.zhuangbi.info/";

    public static Single<List<ZhuangItem>> getItems(int pageIndex) {
        return getItems(pageIndex, BASE_URL);
    }

    public static Single<List<ZhuangItem>> getItems(int pageIndex, String baseUrl) {
        String requestUrl = baseUrl + "?page=" + pageIndex;
        return getDocumentObservable(requestUrl).map(document -> {
            List<ZhuangItem> list = new LinkedList<>();
            document.body().getElementsByClass("thumbnail").forEach(element -> {
                ZhuangItem item = new ZhuangItem();
                item.setImageUrl(element.attr("src"));
                item.setThumbnailUrl(element.attr("src"));
                item.setTitle(element.attr("title"));
                if (!item.getImageUrl().isEmpty())
                    list.add(item);
            });
            return list;
        });
    }
}
