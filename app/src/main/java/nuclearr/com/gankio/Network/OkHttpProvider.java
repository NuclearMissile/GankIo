package nuclearr.com.gankio.Network;

import android.annotation.SuppressLint;
import android.support.v4.util.Preconditions;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import nuclearr.com.gankio.BaseApplication;
import nuclearr.com.gankio.Util.LogUtil;
import nuclearr.com.gankio.Util.NetworkUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


final class OkHttpProvider {
    private final static long DEFAULT_CONNECT_TIMEOUT = 5;
    private final static long DEFAULT_WRITE_TIMEOUT = 5;
    private final static long DEFAULT_READ_TIMEOUT = 5;
    private final static String UA = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";

    static OkHttpClient getOnlyCacheOkHttpClient() {
        return getOkHttpClient(new CacheControlInterceptor());
    }

    static OkHttpClient getOkHttpClient() {
        return getOkHttpClient(new FromNetWorkControlInterceptor());
    }

    private static OkHttpClient getOkHttpClient(Interceptor cacheControl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);

        File cacheDirectory = new File(BaseApplication.getInstance().getCacheDir(), "OkHttpCache");
        builder.cache(new Cache(cacheDirectory, 100 * 1024 * 1024));
        builder.addInterceptor(new UAInterceptor(UA));
        builder.addInterceptor(new LoggingInterceptor());
        builder.addInterceptor(cacheControl);
        builder.addNetworkInterceptor(cacheControl);
        return builder.build();
    }

    private static class CacheControlInterceptor implements Interceptor {
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isConnected(BaseApplication.getInstance()))
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            Response response = chain.proceed(request);
            if (NetworkUtil.isConnected(BaseApplication.getInstance())) {
                int maxAge = 60;
                String cacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(cacheControl))
                    cacheControl = "public, max-age=" + maxAge;
                response = response.newBuilder().removeHeader("Pragma")
                        .header("Cache-Control", cacheControl)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 30;
                response = response.newBuilder().removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    private static class FromNetWorkControlInterceptor implements Interceptor {
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            return chain.proceed(request);
        }
    }

    private static class UAInterceptor implements Interceptor {

        private static final String USER_AGENT_HEADER_NAME = "User-Agent";
        private final String userAgentHeaderValue;

        @SuppressLint("RestrictedApi")
        UAInterceptor(String userAgentHeaderValue) {
            this.userAgentHeaderValue = Preconditions.checkNotNull(userAgentHeaderValue, "userAgentHeaderValue = null");
        }

        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            final Request requestWithUA = chain.request().newBuilder()
                    .removeHeader(USER_AGENT_HEADER_NAME)
                    .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
                    .build();
            return chain.proceed(requestWithUA);
        }
    }

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            LogUtil.i(this, String.format(Locale.CHINA, "Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            LogUtil.i(this, String.format(Locale.CHINA, "Receive response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }
}
