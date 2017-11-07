package nuclearr.com.gankio.Network.Api;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import nuclearr.com.gankio.Bean.JiandanItem;


public class JiandanService extends BaseJsoupService {

    private static final String TAG = "JiandanService";
    private static final String BASE_URL = "http://jandan.net/ooxx/";

    public static Single<Integer> getPageCount() {
        return getDocumentObservable(BASE_URL).map(document -> {
            String page = document.getElementsByClass("current-comment-page").text();
            return Integer.parseInt(page.substring(page.indexOf('[') + 1, page.indexOf(']')));
        });
    }

    public static Single<List<JiandanItem>> getItems(int pageIndex) {
        List<JiandanItem> list = new LinkedList<>();
        final String requestUrl = BASE_URL + "page-" + pageIndex + "#comments";
        return getDocumentObservable(requestUrl).map(document -> {
            document.getElementsByClass("view_img_link").forEach(element -> {
                JiandanItem item = new JiandanItem();
                item.setImageUrl("http:" + element.attr("href"));
                list.add(item);
            });
            return list;
        });
    }
}
