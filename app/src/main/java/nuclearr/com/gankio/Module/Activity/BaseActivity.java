package nuclearr.com.gankio.Module.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.LinkedList;

public abstract class BaseActivity extends RxAppCompatActivity {

    private static final String TAG = "BaseActivity";
    private static LinkedList<Activity> activities = new LinkedList<>();

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
    }

    protected abstract int setContentResID();

    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int resID) {
        return (T) super.findViewById(resID);
    }

    protected void startActivityWithoutExtra(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    protected void startActivityWithExtra(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}