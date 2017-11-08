package nuclearr.com.gankio.Module.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.function.Consumer;

import nuclearr.com.gankio.Network.Api.IDefaultService;
import nuclearr.com.gankio.Network.ServiceFactory;
import nuclearr.com.gankio.Network.Subscriber.DownloadSub;
import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.FileUtil;
import nuclearr.com.gankio.Util.ImageLoader.ImageLoader;
import nuclearr.com.gankio.Util.RxUtil;
import nuclearr.com.gankio.Util.ShareUtil;
import nuclearr.com.gankio.Util.ToastUtil;

public final class ImageActivity extends BaseActivity {
    private String mUrl;
    private File mFile;
    private ProgressDialog mProgressDialog;

    @Override
    protected int setContentResID() {
        return R.layout.image_activity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.image_download:
                if (!getStoragePermission(1))
                    return false;
                downLoad(null);
                break;
            case R.id.image_share:
                if (!getStoragePermission(2))
                    return false;
                downLoad(ShareUtil::shareImage);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void downLoad(Consumer<File> consumer) {
        if (mFile != null && mFile.exists()) {
            if (consumer != null)
                consumer.accept(mFile);
            else
                ToastUtil.showToast("Download success.", Toast.LENGTH_SHORT);
            return;
        }
        mProgressDialog.show();
        ServiceFactory.getInstance()
                .createService(IDefaultService.class)
                .download(mUrl)
                .compose(RxUtil.all_io_single())
                .compose(bindToLifecycle())
                .subscribe(new DownloadSub(FileUtil.getSaveImagePath(this), FileUtil.getFileName(mUrl)) {
                    @Override
                    public void onProgress(double progress, long downloadedBytes, long totalBytes) {
                        mProgressDialog.setProgress((int) (progress * 100));
                    }

                    @Override
                    public void onCompleted(File file) {
                        mFile = file;
                        if (consumer != null)
                            consumer.accept(file);
                        else
                            ToastUtil.showToast("Download success.", Toast.LENGTH_SHORT);
                        mProgressDialog.dismiss();
                    }

                    @Override
                    protected void onFailed(Throwable e) {
                        e.printStackTrace();
                        ToastUtil.showToast(e.getMessage(), Toast.LENGTH_LONG);
                        mProgressDialog.dismiss();
                    }
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = $(R.id.image_view_toolbar);
        PhotoView photoView = $(R.id.photo_view);
        mProgressDialog = new ProgressDialog(this);
        getStoragePermission(0);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        if (intent.getStringExtra("title") != null)
            toolbar.setTitle(intent.getStringExtra("title"));
        ImageLoader.showImage(photoView, mUrl);
        setSupportActionBar(toolbar);
        File file = new File(FileUtil.getSaveImagePath(this), FileUtil.getFileName(mUrl));
        if (file.exists())
            mFile = file;

        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Downloading...");
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            ToastUtil.showToast("Permission denied.", Toast.LENGTH_SHORT);
            return;
        }
        switch (requestCode) {
            case 1:
                downLoad(null);
                break;
            case 2:
                downLoad(ShareUtil::shareImage);
                break;
            default:
                break;
        }
    }

    private boolean getStoragePermission(int requestCode) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
}
