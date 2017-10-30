package nuclearr.com.gankio.Network.Api;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by torri on 2017/10/10.
 */

public interface IDefaultService {

    String BASE_URL = "Dummy";

    @GET
    Single<ResponseBody> loadString(@Url String url);

    @GET
    @Streaming
    Single<ResponseBody> download(@Url String url);
}
