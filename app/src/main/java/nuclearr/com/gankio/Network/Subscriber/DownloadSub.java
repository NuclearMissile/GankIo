package nuclearr.com.gankio.Network.Subscriber;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import nuclearr.com.gankio.BaseApplication;
import nuclearr.com.gankio.Util.FileUtil;
import okhttp3.ResponseBody;

/**
 * Created by torri on 2017/10/23.
 */

public abstract class DownloadSub implements SingleObserver<ResponseBody> {
    private String mSaveFilePath;
    private File mFile;
    private Handler handler = new Handler(Looper.getMainLooper());
    private long downloadedSize = 0;
    private long fileSize = 0;

    public DownloadSub(@NotNull String fileName) {
        mSaveFilePath = FileUtil.getCacheDir(BaseApplication.getInstance()).getAbsolutePath();
        mFile = new File(mSaveFilePath, fileName);
    }

    public DownloadSub(@NotNull String filePath, @NotNull String fileName) {
        mSaveFilePath = filePath;
        mFile = new File(mSaveFilePath, fileName);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public final void onSuccess(ResponseBody responseBody) {
        writeResponseBodyToDisk(responseBody);
        handler.post(() -> onCompleted(mFile));
    }

    private boolean writeResponseBodyToDisk(ResponseBody responseBody) {
        try (InputStream inputStream = responseBody.byteStream();
             OutputStream outputStream = new FileOutputStream(mFile)) {
            byte[] buffer = new byte[8192];
            fileSize = responseBody.contentLength();
            while (true) {
                int readSize = inputStream.read(buffer);
                if (readSize == -1)
                    break;
                outputStream.write(buffer, 0, readSize);
                downloadedSize += readSize;
                handler.post(() -> onProgress(downloadedSize * 1.0f / fileSize, downloadedSize, fileSize));
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            handler.post(() -> onError(e));
            return false;
        }
    }

    @Override
    public final void onError(Throwable e) {
        handler.post(() -> onFailed(e));
    }

    public abstract void onProgress(double progress, long downloadedBytes, long totalBytes);

    public abstract void onCompleted(File file);

    protected abstract void onFailed(Throwable e);
}
