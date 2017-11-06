package nuclearr.com.gankio.Module.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.Bean.GanDailyListItem;
import nuclearr.com.gankio.Bean.GanItem;
import nuclearr.com.gankio.Constant;
import nuclearr.com.gankio.Module.Fragment.Base.RefreshListFragment;
import nuclearr.com.gankio.Module.ViewBinder.GanDailyViewBinder;
import nuclearr.com.gankio.Module.ViewBinder.GanItemViewBinder;
import nuclearr.com.gankio.Network.Api.IGankService;
import nuclearr.com.gankio.Network.ServiceFactory;
import nuclearr.com.gankio.Network.Subscriber.HttpResultSub;
import nuclearr.com.gankio.Util.RxUtil;
import nuclearr.com.gankio.Util.ToastUtil;

public final class HomeFragment extends RefreshListFragment {

    @Override
    protected void regAdapter(MultiTypeAdapter adapter) {
        adapter.register(GanDailyListItem.class, new GanDailyViewBinder());
        adapter.register(GanItem.class, new GanItemViewBinder());
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void loadData(final int pageIndex) {
        IGankService service = ServiceFactory.getInstance().createService(IGankService.class);
        String arg = getArguments().getString("category");
        if (arg.equals("Daily")) {
            service.getRecentlyList(pageIndex, Constant.PAGE_SIZE)
                    .compose(this.bindToLifecycle())
                    .compose(RxUtil.defaultSchedulers_single())
                    .subscribe(new HttpResultSub<List<GanDailyListItem>>() {
                        @Override
                        public void _onError(Throwable throwable) {
                            ToastUtil.showToast(throwable.getMessage(), Toast.LENGTH_LONG);
                        }

                        @Override
                        public void _onSuccess(List<GanDailyListItem> result) {
                            onDataReceived(pageIndex, result);
                        }
                    });
        } else {
            service.getGanItemByCat(arg, pageIndex, Constant.PAGE_SIZE)
                    .compose(bindToLifecycle())
                    .compose(RxUtil.defaultSchedulers_single())
                    .subscribe(new HttpResultSub<List<GanItem>>() {
                        @Override
                        public void _onError(Throwable throwable) {
                            ToastUtil.showToast(throwable.getMessage(), Toast.LENGTH_LONG);
                        }

                        @Override
                        public void _onSuccess(List<GanItem> result) {
                            onDataReceived(pageIndex, result);
                        }
                    });
        }
    }
}
