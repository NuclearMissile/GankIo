package nuclearr.com.gankio.Module.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Created by torri on 2017/10/20.
 */

public abstract class BaseFragment extends RxFragment {
    private View mContentView;
    private ProgressDialog mProgressDialog;

    public View getContentView() {
        return mContentView;
    }

    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResID(), container, false);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCanceledOnTouchOutside(false);
        setUpView();
        return mContentView;
    }

    protected abstract void setUpView();

    protected abstract int setLayoutResID();

    protected final <T extends View> T $(int id) {
        return (T) mContentView.findViewById(id);
    }
}
