package nuclearr.com.gankio.Module.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.Bean.GanDailyDetailItem;
import nuclearr.com.gankio.Bean.GanItem;
import nuclearr.com.gankio.Module.ViewBinder.GanItemViewBinderForDetailView;
import nuclearr.com.gankio.Module.ViewBinder.SimpleTextViewBinder;
import nuclearr.com.gankio.Network.Api.IGankService;
import nuclearr.com.gankio.Network.ServiceFactory;
import nuclearr.com.gankio.Network.Subscriber.HttpResultSub;
import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.ImageLoader.ImageLoader;
import nuclearr.com.gankio.Util.RxUtil;
import nuclearr.com.gankio.Util.ToastUtil;

public final class GanDailyDetailActivity extends BaseActivity {
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageView = $(R.id.gan_daily_detail_image_view);
        mRecyclerView = $(R.id.gan_daily_detail_recycler_view);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(String.class, new SimpleTextViewBinder());
        mAdapter.register(GanItem.class, new GanItemViewBinderForDetailView());
        mRecyclerView.setAdapter(mAdapter);

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
        loadData(intent.getStringExtra("date"));
    }

    private void loadData(String date) {
        ServiceFactory.getInstance()
                .createService(IGankService.class)
                .getGanDailyByDate(date)
                .compose(RxUtil.defaultSchedulers_single())
                .compose(bindToLifecycle())
                .subscribe(new HttpResultSub<GanDailyDetailItem>() {
                    @Override
                    public void _onError(Throwable throwable) {
                        // todo
                        ToastUtil.showToast(throwable.getMessage(), Toast.LENGTH_LONG);
                        throwable.printStackTrace();
                    }

                    @Override
                    public void _onSuccess(GanDailyDetailItem result) {
                        final List data = new ArrayList(15);
                        if (result.getAndroid() != null) {
                            data.add("Android");
                            data.addAll(result.getAndroid());
                        }
                        if (result.getiOS() != null) {
                            data.add("iOS");
                            data.addAll(result.getiOS());
                        }
                        if (result.getFrontend() != null) {
                            data.add("Frontend");
                            data.addAll(result.getFrontend());
                        }
                        if (result.getRecommend() != null) {
                            data.add("Recommend");
                            data.addAll(result.getRecommend());
                        }
                        if (result.getVideo() != null) {
                            data.add("Video");
                            data.addAll(result.getVideo());
                        }
                        if (result.getExtend() != null) {
                            data.add("Extend");
                            data.addAll(result.getExtend());
                        }
                        mAdapter.setItems(data);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected int setContentResID() {
        return R.layout.activity_gan_daily_detail;
    }
}
