package nuclearr.com.gankio.Module.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.Bean.GanDailyListItem;
import nuclearr.com.gankio.Module.ViewBinder.GanDailyListViewBinder;
import nuclearr.com.gankio.Network.Api.IGankService;
import nuclearr.com.gankio.Network.ServiceFactory;
import nuclearr.com.gankio.Network.Subscriber.HttpResultSub;
import nuclearr.com.gankio.Util.RxUtil;

public final class MainFragment extends RefreshListFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
    }

    @Override
    protected MultiTypeAdapter setAdapter(MultiTypeAdapter adapter) {
        adapter.register(GanDailyListItem.class, new GanDailyListViewBinder());
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void loadData(final int pageIndex) {
        final List data = new ArrayList(10);
        ServiceFactory.getInstance()
                .createService(IGankService.class)
                .getRecentlyList(pageIndex)
                .compose(this.bindToLifecycle())
                .compose(RxUtil.defaultSchedulers_single())
                .subscribe(new HttpResultSub<List<GanDailyListItem>>() {
                    @Override
                    public void _onError(Throwable throwable) {
                        onError(new Exception(throwable));
                    }

                    @Override
                    public void _onSuccess(List<GanDailyListItem> result) {
                        data.addAll(result);
                        onDataReceived(pageIndex, data);
                    }
                });
    }
}
