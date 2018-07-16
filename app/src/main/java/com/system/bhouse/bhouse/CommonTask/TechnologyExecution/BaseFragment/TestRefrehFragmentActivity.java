package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.TechnologyExecutionFragment;

/**
 * Created by Administrator on 2018-07-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment
 */

public class TestRefrehFragmentActivity extends AppCompatActivity {

    private static final String TAG = "TestRefrehFragmentActivity";

    public static final int MSG_111 = 232 << 2;

    FragmentPagerAdapter adapter;
    ViewPager pager;

    TechnologyExecutionFragment technologyExecutionFragment = new TechnologyExecutionFragment();
    TechnologyExecutionFragment technologyExecutionFragment1 = new TechnologyExecutionFragment();
    TechnologyExecutionFragment technologyExecutionFragment2 = new TechnologyExecutionFragment();
    TechnologyExecutionFragment technologyExecutionFragment3 = new TechnologyExecutionFragment();
    TechnologyExecutionFragment technologyExecutionFragment4 = new TechnologyExecutionFragment();

    Fragment[] fragments = {technologyExecutionFragment, technologyExecutionFragment1, technologyExecutionFragment2, technologyExecutionFragment3, technologyExecutionFragment4};

    boolean[] fragmentsUpdateFlag = {false, false, false, false};

    Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_111:
                    fragments[3] = technologyExecutionFragment4;
                    fragmentsUpdateFlag[3] = true;
                    adapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public class RefreshFragmentPagerAdapter extends FragmentPagerAdapter {

        private FragmentManager fm;

        public RefreshFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments[position % fragments.length];
            KLog.e(TAG, "getItem:position=" + position + ",fragment:"
                    + fragment.getClass().getName() + ",fragment.tag="
                    + fragment.getTag());
            return fragments[position % fragments.length];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//得到缓存的fragment
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
//得到tag，这点很重要
            String fragmentTag = fragment.getTag();


            if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
//如果这个fragment需要更新

                FragmentTransaction ft = fm.beginTransaction();
//移除旧的fragment
                ft.remove(fragment);
//换成新的fragment
                fragment = fragments[position % fragments.length];
//添加新fragment时必须用前面获得的tag，这点很重要
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commit();

//复位更新标志
                fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
            }
        }
    }
