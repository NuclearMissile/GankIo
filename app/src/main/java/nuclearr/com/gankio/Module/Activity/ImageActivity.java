package nuclearr.com.gankio.Module.Activity;

import android.Manifest;
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
                if (ContextCompat.checkSelfPermission(ImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(ImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                else
                    downLoad();
                break;
            case R.id.image_share:
                if (mFile != null)
                    ShareUtil.shareImage(mFile);
                else
                    downloadAndShare();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadAndShare() {
        if (mFile != null)
            return;
        mDialog.show();
        ServiceFactory.getInstance()
                .createService(IDefaultService.class)
                .download(mUrl)
                .compose(RxUtil.all_io_single())
                .compose(bindToLifecycle())
                .subscribe(new DownloadSub(FileUtil.getFileName(mUrl)) {
                    @Override
                    public void onProgress(double progress, long downloadedBytes, long totalBytes) {

                    }

                    @Override
                    public void onCompleted(File file) {
                        mDialog.dismiss();
                        ShareUtil.shareImage(file);
                    }

                    @Override
                    protected void onFailed(Throwable e) {
                        mDialog.dismiss();
                        e.printStackTrace();
                        ToastUtil.showToast(e.getMessage(), Toast.LENGTH_LONG);
                    }
                });
    }

    private void downLoad() {
        if (mFile != null)
            return;
        mDialog.show();
        ServiceFactory.getInstance()
                .createService(IDefaultService.class)
                .download(mUrl)
                .compose(RxUtil.all_io_single())
                .compose(bindToLifecycle())
                .subscribe(new DownloadSub(FileUtil.getSaveImagePath(this), FileUtil.getFileName(mUrl)) {
                    @Override
                    public void onProgress(double progress, long downloadedBytes, long totalBytes) {

                    }

                    @Override
                    public void onCompleted(File file) {
                        mDialog.dismiss();
                        mFile = file;
                        ToastUtil.showToast("Download success.", Toast.LENGTH_SHORT);
                    }

                    @Override
                    protected void onFailed(Throwable e) {
                        mDialog.dismiss();
                        e.printStackTrace();
                        ToastUtil.showToast(e.getMessage(), Toast.LENGTH_LONG);
                    }
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = $(R.id.image_view_toolbar);
        PhotoView photoView = $(R.id.photo_view);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        if (intent.getStringExtra("title") != null)
            toolbar.setTitle(intent.getStringExtra("title"));
        photoView.layout(0, 0, 0, 0);
        ImageLoader.showImage(photoView, mUrl);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    downLoad();
                else
                    ToastUtil.showToast("Permission denied.", Toast.LENGTH_SHORT);
                break;
        }
    }
}
