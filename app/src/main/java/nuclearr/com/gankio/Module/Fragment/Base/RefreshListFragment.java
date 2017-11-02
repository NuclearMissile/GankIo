package nuclearr.com.gankio.Module.Fragment.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.ToastUtil;

public abstract class RefreshListFragment extends BaseFragment implements IListView {
    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mRefreshLayout;
    protected List mItems;
    protected int mCurrentPageIndex;
    protected RecyclerView.LayoutManager mLayoutManager;

    private MultiTypeAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData(mCurrentPageIndex);
        setUserVisibleHint(true);
    }

    @Override
    protected void setUpView() {
        mItems = new LinkedList();
        mRecyclerView = $(R.id.recycler_view);
        mRefreshLayout = $(R.id.refresh_layout);
        mLayoutManager = setLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MultiTypeAdapter(mItems);
        regAdapter(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
        mCurrentPageIndex = getInitPageIndex();

        mRefreshLayout.setOnRefreshListener(this::refreshData);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mRecyclerView.canScrollVertically(1)) {
                    loadMore();
                }
            }
        });
    }

    @Override
    protected final int setLayoutResID() {
        return R.layout.refresh_list_fragment;
    }

    protected abstract void regAdapter(MultiTypeAdapter adapter);

    protected abstract RecyclerView.LayoutManager setLayoutManager();

    // todo fix scroll to top
    public boolean scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
        return !mRecyclerView.canScrollVertically(-1);
    }

    protected final void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        if (mRecyclerView != null)
            mRecyclerView.addItemDecoration(decoration);
    }

    protected final void onDataReceived(int pageIndex, List items) {
        if (pageIndex == getInitPageIndex() && (items == null || items.size() <= 0)) {
            showMessage("No data.");
        } else if (pageIndex == getInitPageIndex()) {
            // showMessage(mRefreshLayout.isRefreshing() ? "Refreshing..." : "Now loading...");
            mItems.clear();
            mItems.addAll(items);
        } else if (items != null && items.size() > 0) {
            // showMessage(mRefreshLayout.isRefreshing() ? "Refreshing..." : "Loading more...");
            mItems.addAll(items);
        } else {
            mCurrentPageIndex--;
            showMessage("No more data.");
        }
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    protected int getInitPageIndex() {
        return 1;
    }

    @Override
    public abstract void loadData(int pageIndex);

    @Override
    public void refreshData() {
        loadData(mCurrentPageIndex);
    }

    @Override
    public final void loadMore() {
        loadData(++mCurrentPageIndex);
    }

    @Override
    public void onException(Exception e) {
        showMessage(e.getMessage());
        e.printStackTrace();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.showToast(msg, Toast.LENGTH_SHORT);
    }
}
