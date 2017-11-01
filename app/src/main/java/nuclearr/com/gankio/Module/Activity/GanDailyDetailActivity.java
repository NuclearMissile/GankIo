package nuclearr.com.gankio.Module.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.ImageLoader.ImageLoader;

public final class GanDailyDetailActivity extends BaseActivity {

    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageView = $(R.id.gan_daily_detail_image_view);
        mRecyclerView = $(R.id.gan_daily_detail_recycler_view);

        Intent intent = getIntent();
        CollapsingToolbarLayout collapsingToolbarLayout = $(R.id.gan_daily_detail_collapsing_toolbar);
        Toolbar toolbar = $(R.id.gan_daily_detail_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(intent.getStringExtra("title"));
        ImageLoader.showImage(mImageView, intent.getStringExtra("image"));

    }

    @Override
    protected int setContentResID() {
        return R.layout.activity_gan_daily_detail;
    }
}
