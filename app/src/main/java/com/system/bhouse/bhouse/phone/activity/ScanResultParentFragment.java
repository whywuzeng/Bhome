package com.system.bhouse.bhouse.phone.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.view.WechatTab;
import com.system.bhouse.bhouse.setup.WWCommon.WWBaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-10-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.phone.activity
 */

public class ScanResultParentFragment extends WWBaseFragment {

    @BindView(R.id.tabs)
    WechatTab tabs;
    @BindView(R.id.scanresultProgram)
    ViewPager scanresultProgram;
    private String[] typeItem;
    private String[] typeItemTag;


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_scanresult_parent);
        final ActionBar actionBar = getActionBar();
        if (actionBar!=null)
        {
            actionBar.setElevation(0);
        }

        init();
    }

    private void init() {
        typeItem = getActivity().getResources().getStringArray(R.array.dynamic_type);
        typeItemTag = getActivity().getResources().getStringArray(R.array.dynamic_type_params);
        final DynamicPagerAdapter dynamicPagerAdapter = new DynamicPagerAdapter(getChildFragmentManager(), typeItem, typeItemTag);
        scanresultProgram.setAdapter(dynamicPagerAdapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        scanresultProgram.setPageMargin(pageMargin);

        tabs.setViewPager(scanresultProgram);
    }

    class DynamicPagerAdapter extends FragmentStatePagerAdapter{

        String[] typeItemTag;

        String[] typeItem;

        public DynamicPagerAdapter(FragmentManager fm, String[] typeItem, String[] typeItemTag) {
            super(fm);
            this.typeItem = typeItem;
            this.typeItemTag = typeItemTag;
        }


        @Override
        public Fragment getItem(int position) {
            final ScanResultFragment scanResultFragment = new ScanResultFragment();
            return scanResultFragment;
        }

        @Override
        public int getCount() {
            return typeItem.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return typeItem[position];
        }
    }
}
