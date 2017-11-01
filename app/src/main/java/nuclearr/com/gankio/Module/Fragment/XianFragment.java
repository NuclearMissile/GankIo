package nuclearr.com.gankio.Module.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.Bean.XianItem;
import nuclearr.com.gankio.Module.Fragment.Base.RefreshListFragment;
import nuclearr.com.gankio.Module.Fragment.ViewBinder.XianViewBinder;
import nuclearr.com.gankio.Network.Api.XianService;
import nuclearr.com.gankio.Util.RxUtil;

/**
 * Created by torri on 2017/11/1.
 */

public class XianFragment extends RefreshListFragment {
    @Override
    protected MultiTypeAdapter regAdapter(MultiTypeAdapter adapter) {
        adapter.register(XianItem.class, new XianViewBinder());
        return adapter;
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
                .subscribe(new SingleObserver<List<XianItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<XianItem> xianItems) {
                        onDataReceived(pageIndex, xianItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onError(new Exception(e));
                    }
                });
    }
}
