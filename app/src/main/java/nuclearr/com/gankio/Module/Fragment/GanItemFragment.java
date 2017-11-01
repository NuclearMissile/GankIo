package nuclearr.com.gankio.Module.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.Bean.GanItem;
import nuclearr.com.gankio.Constant;
import nuclearr.com.gankio.Module.Fragment.Base.RefreshListFragment;
import nuclearr.com.gankio.Module.Fragment.ViewBinder.GanItemViewBinder;
import nuclearr.com.gankio.Network.Api.IGankService;
import nuclearr.com.gankio.Network.ServiceFactory;
import nuclearr.com.gankio.Network.Subscriber.HttpResultSub;
import nuclearr.com.gankio.Util.RxUtil;


public final class GanItemFragment extends RefreshListFragment {

    @Override
    protected MultiTypeAdapter regAdapter(MultiTypeAdapter adapter) {
        adapter.register(GanItem.class, new GanItemViewBinder());
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void loadData(int pageIndex) {
        ServiceFactory.getInstance()
                .createService(IGankService.class)
                .getGanItemByCat(getArguments().getString("category"), pageIndex, Constant.PAGE_SIZE)
                .compose(bindToLifecycle())
                .compose(RxUtil.defaultSchedulers_single())
                .subscribe(new HttpResultSub<List<GanItem>>() {
                    @Override
                    public void _onError(Throwable throwable) {
                        onError(new Exception(throwable));
                    }

                    @Override
                    public void _onSuccess(List<GanItem> result) {
                        onDataReceived(pageIndex, result);
                    }
                });
    }
}
