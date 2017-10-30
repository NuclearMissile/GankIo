package nuclearr.com.gankio.Network.Subscriber;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CancellationException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import nuclearr.com.gankio.Network.HttpResult;

/**
 * Created by torri on 2017/10/23.
 */

public abstract class HttpResultSub<T> implements SingleObserver<HttpResult<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onSuccess(@NotNull HttpResult<T> result) {
        if (!result.error)
            _onSuccess(result.results);
        else
            _onError(new Throwable("result.error = true"));
    }

    public abstract void _onError(Throwable throwable);

    public abstract void _onSuccess(T result);

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            if (!(e instanceof CancellationException)) {
                e.printStackTrace();
                _onError(new Throwable(e.getMessage() == null ? e.toString() : e.getMessage()));
            }
        } else {
            _onError(new Exception("null error message"));
        }
    }
}
