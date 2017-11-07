package nuclearr.com.gankio.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ServiceFactory {
    private final Gson gson;
    private OkHttpClient okHttpClient;

    private ServiceFactory() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
    }

    public static ServiceFactory getInstance() {
        ServiceFactory factory = SingletonHolder.INSTANCE;
        factory.okHttpClient = OkHttpProvider.getOkHttpClient();
        return factory;
    }

    public static ServiceFactory getOnlyCacheInstance() {
        ServiceFactory factory = SingletonHolder.INSTANCE;
        factory.okHttpClient = OkHttpProvider.getOnlyCacheOkHttpClient();
        return factory;
    }

    public <S> S createService(Class<S> serviceClass) {
        String baseUrl = "";
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }
}
