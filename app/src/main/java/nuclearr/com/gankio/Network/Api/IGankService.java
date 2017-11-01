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

    @GET("data/{category}/{pageSize}/{pageIndex}")
    Single<HttpResult<List<GanItem>>>
    getGanItemByCat(@Path("category") String category, @Path("pageIndex") int pagIndex, @Path("pageSize") int pageSize);

    @GET("day/{date}")
    Single<HttpResult<GanDailyItem>>
    getGanDailyByDate(@Path("date") String date);

    @GET("search/query/{keyword}/category/{category}/count/{pageSize}/page/{pageIndex}")
    Single<HttpResult<List<SearchResult>>>
    getSearchResult(@Path("category") String category, @Path("keyword") String keyword, @Path("pageIndex") int pageIndex, @Path("pageSize") int pageSize);

    @GET("random/data/{category}/{pageSize}")
    Single<HttpResult<List<GanItem>>>
    getRandomGanItem(@Path("category") String category, @Path("pageSize") int pageSize);

    @GET("history/content/{pageSize}/{pageIndex}")
    Single<HttpResult<List<GanDailyListItem>>>
    getRecentlyList(@Path("pageIndex") int pageIndex, @Path("pageSize") int pageSize);
}
