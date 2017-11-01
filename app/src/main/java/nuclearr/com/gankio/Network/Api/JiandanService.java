package nuclearr.com.gankio.Network.Api;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import nuclearr.com.gankio.Bean.JiandanItem;
import nuclearr.com.gankio.Util.LogUtil;
import nuclearr.com.gankio.Util.RxUtil;


public class JiandanService extends BaseJsoupService {

    private static final String TAG = "JiandanService";
    private static final String BASE_URL = "http://jandan.net/ooxx/";
    private int pageCount = -1;

    public Single<List<JiandanItem>> getItems(int pageIndex) {
        List<JiandanItem> list = new LinkedList<>();
        if (pageCount == -1) {
            getDocumentObservable(BASE_URL).compose(RxUtil.all_io_single()).subscribe(document -> {
                String page = document.getElementsByClass("current-comment-page").text();
                pageCount = Integer.parseInt(page.replace("[", "").replace("]", ""));
            });
        }
        LogUtil.i(TAG, "PagCount:" + pageCount);
        final String requestUrl = BASE_URL + "page-" + (pageCount - pageIndex + 1) + " #comments";
        return getDocumentObservable(requestUrl).map(document -> {
            document.getElementsByClass("view_img_link").forEach(element -> {
                JiandanItem item = new JiandanItem();
                item.setPictureUrl(element.attr("href"));
                list.add(item);
            });
            return list;
        });
    }
}
