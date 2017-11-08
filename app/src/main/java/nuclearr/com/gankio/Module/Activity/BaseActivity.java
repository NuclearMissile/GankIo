package nuclearr.com.gankio.Module.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.LinkedList;

public abstract class BaseActivity extends RxAppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static BaseActivity instance;
    private static LinkedList<Activity> activities = new LinkedList<>();

    public static BaseActivity getInstance() {
        return instance;
    }

    protected static void addActivity(Activity activity) {
        activities.add(activity);
    }

    protected static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    protected static void finishAll(boolean forceKill) {
        activities.forEach(activity -> {
            if (!activity.isFinishing()) activity.finish();
        });
        activities.clear();
        if (forceKill)
            android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "This is " + getClass().getSimpleName());
        addActivity(this);
        setContentView(setContentResID());
        instance = this;
    }

    protected abstract int setContentResID();

    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int resID) {
        return (T) super.findViewById(resID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
