package nuclearr.com.gankio.Network.Api;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import nuclearr.com.gankio.Bean.XianCategory;
import nuclearr.com.gankio.Bean.XianItem;
import nuclearr.com.gankio.Network.ServiceFactory;


public final class XianService extends BaseJsoupService {

    private final static String BASE_URL = "http://gank.io/xiandu/";

    public static Single<List<XianCategory>> getCategory() {
        return getDocumentObservable(BASE_URL).map(document -> {
            List<XianCategory> list = new LinkedList<>();
            document.body().getElementById("xiandu_cat").getElementsByTag("a").forEach(element -> {
                XianCategory category = new XianCategory();
                category.setTitle(element.text());
                category.setCategory(element.attr("href")
                        .substring(element.attr("href").lastIndexOf("/") + 1));
                list.add(category);
            });
            return list;
        });
    }

    public static Single<List<XianItem>> getItems(String category, int pageIndex) {
        if ("xiandu".equals(category))
            category = "wow";
        final String requestUrl = BASE_URL + "/" + category + "/" + "page" + pageIndex;
        return getDocumentObservable(requestUrl).map(document -> {
            List<XianItem> list = new LinkedList<>();
            document.body().getElementsByClass("xiandu_item").forEach(element -> {
                XianItem item = new XianItem();
                item.setTitle(element.getElementsByClass("site-title").text());
                item.setUrl(element.getElementsByClass("site-title").attr("href"));
                item.setTime(element.getElementsByTag("small").text());
                item.setSource(element.getElementsByClass("site-name").get(0).attr("title"));
                item.setSourceAvatar(element.getElementsByClass("site-name").get(0)
                        .getElementsByTag("img").get(0).attr("src"));
                list.add(item);
            });
            return list;
        });
    }
}
