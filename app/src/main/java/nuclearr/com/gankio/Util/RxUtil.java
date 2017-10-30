package nuclearr.com.gankio.Util;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;
import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by torri on 2017/10/12.
 */

public final class RxUtil {
    @NonNull
    @Contract(pure = true)
    public static <T> FlowableTransformer<T, T> defaultSchedulers_flow() {
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    @NonNull
    @Contract(pure = true)
    public static <T> SingleTransformer<T, T> defaultSchedulers_single() {
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    @NonNull
    @Contract(pure = true)
    public static <T> SingleTransformer<T, T> all_io_single() {
        return upstream -> upstream.observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
    }

    @NonNull
    @Contract(pure = true)
    public static <T> FlowableTransformer<T, T> all_io_flow() {
        return upstream -> upstream.observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
    }
}
