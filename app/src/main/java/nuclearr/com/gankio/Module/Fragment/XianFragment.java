package nuclearr.com.gankio.Module.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.concurrent.CancellationException;

import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.Bean.XianItem;
import nuclearr.com.gankio.Module.Fragment.Base.RefreshListFragment;
import nuclearr.com.gankio.Module.ViewBinder.XianViewBinder;
import nuclearr.com.gankio.Network.Api.XianService;
import nuclearr.com.gankio.Util.RxUtil;

public final class XianFragment extends RefreshListFragment {
    @Override
    protected void regAdapter(MultiTypeAdapter adapter) {
        adapter.register(XianItem.class, new XianViewBinder());
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void loadData(int pageIndex) {
        XianService.getItems(getArguments().getString("category"), pageIndex)
                .compose(bindToLifecycle())
                .compose(RxUtil.defaultSchedulers_single())
                .subscribe((xianItems, throwable) -> {
                    if (throwable == null || throwable instanceof CancellationException)
                        onDataReceived(pageIndex, xianItems);
                    else
                        onException((Exception) throwable);
                });
    }
}
