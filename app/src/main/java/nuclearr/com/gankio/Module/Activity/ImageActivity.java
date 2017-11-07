package nuclearr.com.gankio.Module.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.chrisbanes.photoview.PhotoView;

import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.ImageLoader.ImageLoader;

public final class ImageActivity extends BaseActivity {
    @Override
    protected int setContentResID() {
        return R.layout.image_activity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_view_menu, menu);
        return true;
    }

    // todo
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.image_download:
                break;
            case R.id.image_share:
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = $(R.id.image_view_toolbar);
        PhotoView photoView = $(R.id.photo_view);

        Intent intent = getIntent();
        if (intent.getStringExtra("title") != null)
            toolbar.setTitle(intent.getStringExtra("title"));
        ImageLoader.showImage2(photoView, intent.getStringExtra("url"));
        setSupportActionBar(toolbar);
    }
}
