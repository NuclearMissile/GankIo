package nuclearr.com.gankio.Module.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.Bean.GanDailyListItem;
import nuclearr.com.gankio.Bean.IImageItem;
import nuclearr.com.gankio.Bean.JiandanItem;
import nuclearr.com.gankio.Bean.ZhuangItem;
import nuclearr.com.gankio.Constant;
import nuclearr.com.gankio.Module.Activity.MainActivity;
import nuclearr.com.gankio.Module.Fragment.Base.RefreshListFragment;
import nuclearr.com.gankio.Module.ViewBinder.ImageViewBinder;
import nuclearr.com.gankio.Network.Api.IGankService;
import nuclearr.com.gankio.Network.Api.JiandanService;
import nuclearr.com.gankio.Network.Api.ZhuangService;
import nuclearr.com.gankio.Network.Api.ZhuangService2;
import nuclearr.com.gankio.Network.ServiceFactory;
import nuclearr.com.gankio.Network.Subscriber.HttpResultSub;
import nuclearr.com.gankio.Util.RxUtil;
import nuclearr.com.gankio.Util.ToastUtil;

/**
 * Created by torri on 2017/11/2.
 */

public final class ImageFragment extends RefreshListFragment {

    private int mJiandanPageCount = -1;

    @Override
    protected void regAdapter(MultiTypeAdapter adapter) {
        adapter.register(IImageItem.class, new ImageViewBinder());
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {
        return new GridLayoutManager(MainActivity.getInstance(), 2);
    }

    @Override
    public void loadData(int pageIndex) {
        switch (getArguments().getString("category")) {
            case "Gank.io":
                ServiceFactory.getInstance().createService(IGankService.class)
                        .getRecentlyList(pageIndex, Constant.PAGE_SIZE)
                        .compose(RxUtil.defaultSchedulers_single())
                        .compose(bindToLifecycle())
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
                break;
            case "Jiandan.net":
                if (mJiandanPageCount == -1) {
                    JiandanService.getPageCount()
                            .compose(RxUtil.defaultSchedulers_single())
                            .compose(bindToLifecycle())
                            .subscribe(new SingleObserver<Integer>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(Integer integer) {
                                    mJiandanPageCount = integer;
                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtil.showToast(e.getMessage(), Toast.LENGTH_LONG);
                                }
                            });
                }
                JiandanService.getItems(mJiandanPageCount - pageIndex + 1)
                        .compose(RxUtil.defaultSchedulers_single())
                        .compose(bindToLifecycle())
                        .subscribe(new SingleObserver<List<JiandanItem>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<JiandanItem> jiandanItems) {
                                onDataReceived(pageIndex, jiandanItems);
                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtil.showToast(e.getMessage(), Toast.LENGTH_LONG);
                            }
                        });
                break;
            case "Zhuangbi.info":
                ZhuangService.getItems(pageIndex)
                        .compose(RxUtil.defaultSchedulers_single())
                        .compose(bindToLifecycle())
                        .subscribe(new SingleObserver<List<ZhuangItem>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<ZhuangItem> zhuangItems) {
                                onDataReceived(pageIndex, zhuangItems);
                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtil.showToast(e.getMessage(), Toast.LENGTH_LONG);
                            }
                        });
                break;
            case "Zhuangbi.info2":
                ZhuangService2.getItems(pageIndex)
                        .compose(RxUtil.defaultSchedulers_single())
                        .compose(bindToLifecycle())
                        .subscribe(new SingleObserver<List<ZhuangItem>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<ZhuangItem> zhuangItems) {
                                onDataReceived(pageIndex, zhuangItems);
                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtil.showToast(e.getMessage(), Toast.LENGTH_LONG);
                            }
                        });
                break;
            default:
                break;
        }
    }
}
