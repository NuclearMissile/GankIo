package nuclearr.com.gankio.Module.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.Bean.GanDailyListItem;
import nuclearr.com.gankio.Constant;
import nuclearr.com.gankio.Module.Fragment.Base.RefreshListFragment;
import nuclearr.com.gankio.Module.Fragment.ViewBinder.GanDailyViewBinder;
import nuclearr.com.gankio.Network.Api.IGankService;
import nuclearr.com.gankio.Network.ServiceFactory;
import nuclearr.com.gankio.Network.Subscriber.HttpResultSub;
import nuclearr.com.gankio.Util.RxUtil;

public final class MainFragment extends RefreshListFragment {

    @Override
    protected void regAdapter(MultiTypeAdapter adapter) {
        adapter.register(GanDailyListItem.class, new GanDailyViewBinder());
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void loadData(final int pageIndex) {
        ServiceFactory.getInstance()
                .createService(IGankService.class)
                .getRecentlyList(pageIndex, Constant.PAGE_SIZE)
                .compose(this.bindToLifecycle())
                .compose(RxUtil.defaultSchedulers_single())
                .subscribe(new HttpResultSub<List<GanDailyListItem>>() {
                    @Override
                    public void _onError(Throwable throwable) {
                        onError(new Exception(throwable));
                    }

                    @Override
                    public void _onSuccess(List<GanDailyListItem> result) {
                        onDataReceived(pageIndex, result);
                    }
                });
    }
}
