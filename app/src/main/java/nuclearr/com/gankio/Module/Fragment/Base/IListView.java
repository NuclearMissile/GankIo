package nuclearr.com.gankio.Module.Fragment.Base;

/**
 * Created by torri on 2017/10/19.
 */

public interface IListView {
    void loadData(int pageIndex);

    void refreshData();

    void loadMore();

    void onException(Exception e);

    void showLoading();

    void showMessage(String msg);

    void showContent();
}
