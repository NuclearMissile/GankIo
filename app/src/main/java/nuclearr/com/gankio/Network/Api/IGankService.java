package nuclearr.com.gankio.Network.Api;

import java.util.List;

import io.reactivex.Single;
import nuclearr.com.gankio.Bean.GanDailyItem;
import nuclearr.com.gankio.Bean.GanDailyListItem;
import nuclearr.com.gankio.Bean.GanItem;
import nuclearr.com.gankio.Bean.SearchResult;
import nuclearr.com.gankio.Network.HttpResult;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface IGankService {

    String BASE_URL = "http://www.gank.io/api/";

    @GET("day/history")
    Single<HttpResult<List<String>>>
    getAvaliableDays();

    @GET("data/{category}/10/{pageIndex}")
    Single<HttpResult<List<GanItem>>>
    getGanItemByCat(@Path("category") String category, @Path("pageIndex") int pagIndex);

    @GET("day/{date}")
    Single<HttpResult<GanDailyItem>>
    getGanDailyByDate(@Path("date") String date);

    @GET("search/query/{keyword}/category/{category}/count/10/page/{pageIndex}")
    Single<HttpResult<List<SearchResult>>>
    getSearchResult(@Path("category") String category, @Path("keyword") String keyword, @Path("pageIndex") int pageIndex);

    @GET("random/data/{category}/10")
    Single<HttpResult<List<GanItem>>>
    getRandomGanItem(@Path("category") String category);

    @GET("history/content/10/{pageIndex}")
    Single<HttpResult<List<GanDailyListItem>>>
    getRecentlyList(@Path("pageIndex") int pageIndex);
}
