package nuclearr.com.gankio.Module.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nuclearr.com.gankio.Module.Fragment.MainFragment;
import nuclearr.com.gankio.Module.Fragment.RefreshListFragment;
import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.LogUtil;
import nuclearr.com.gankio.Util.ToastUtil;

public final class MainActivity extends BaseActivity {

    private final static Map<Integer, List<String>> mBottomNavTabMap = new ArrayMap<>();
    private static final String TAG = "MainActivity";

    static {
        mBottomNavTabMap.put(0, Arrays.asList("Daily", "Android", "iOS", "Frontend", "Extend", "Video", "Recommend"));
        mBottomNavTabMap.put(1, Arrays.asList("Wow", "Apps", "imrich", "Funny", "Android", "Diediedie", "Thinking", "iOS", "TeamBlog"));
        mBottomNavTabMap.put(2, Arrays.asList("Gank.io", "Jiandan.net", "Zhuangbi.info"));
    }

    private final long MAX_DOUBLE_BACK_DURATION = 1500;
    private long lastBackPressed = 0;
    private int bottomNavIndex = 0;

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private ViewPager mViewPager;
    private BottomNavigationBar mBottomNavBar;
    private TabLayout mTabLayout;
    private SectionsPagerAdapter mPagerAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // todo
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings)
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (beforeOnBackPressed()) {
            long current = System.currentTimeMillis();
            if (current - lastBackPressed > MAX_DOUBLE_BACK_DURATION) {
                ToastUtil.showToast("Press back again to exit.", Toast.LENGTH_SHORT);
                lastBackPressed = current;
            } else {
                finishAll(true);
            }
        }
    }

    private boolean beforeOnBackPressed() {
        if (mCurrentFragment != null && mCurrentFragment instanceof RefreshListFragment) {
            RefreshListFragment listFragment = (RefreshListFragment) mCurrentFragment;
            return listFragment.scrollToTop();
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar($(R.id.toolbar));
        mFragmentManager = getSupportFragmentManager();
        mPagerAdapter = new SectionsPagerAdapter(mFragmentManager);

        mViewPager = $(R.id.container);
        mTabLayout = $(R.id.tabs);
        mBottomNavBar = $(R.id.bottom_nav_bar);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mBottomNavBar.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_chrome_reader_mode_white_24dp, "Reading"))
                .addItem(new BottomNavigationItem(R.drawable.ic_image_white_24dp, "Image"))
                .initialise();
        mBottomNavBar.setTabSelectedListener(new BottomNavigationBar.SimpleOnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                bottomNavIndex = position;
                mTabLayout.removeAllTabs();
                mPagerAdapter.notifyDataSetChanged();
                mBottomNavTabMap.get(position).forEach(s -> mTabLayout.addTab(mTabLayout.newTab().setText(s)));
                mViewPager.setAdapter(mPagerAdapter);
            }
        });
        mBottomNavBar.selectTab(0);
    }

    @Override
    protected int setContentResID() {
        return R.layout.activity_main;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            try {
                mCurrentFragment = new MainFragment();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }
            return mCurrentFragment;
        }

        @Override
        public int getCount() {
            return mBottomNavTabMap.get(bottomNavIndex).size();
        }
    }
}