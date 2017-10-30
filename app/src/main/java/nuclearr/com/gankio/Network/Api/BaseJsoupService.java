package nuclearr.com.gankio.Network.Api;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import io.reactivex.Single;
import nuclearr.com.gankio.Network.ServiceFactory;

/**
 * Created by torri on 2017/10/20.
 */
abstract class BaseJsoupService {
    @NotNull
    protected static Single<Document> getDocumentObservable(String requestUrl) {
        return ServiceFactory.getInstance()
                .createService(IDefaultService.class)
                .loadString(requestUrl)
                .map(responseBody -> Jsoup.parse(responseBody.string()));
    }
}
