package com.system.bhouse.bhouse.DingdanManage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.base.BaseActivity;
import com.system.bhouse.base.BaseFragment;
import com.system.bhouse.bhouse.DingdanManage.Fragment.FragmentDingdan;
import com.system.bhouse.bhouse.DingdanManage.UiInterface.SecondPageListener;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.AppManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016-5-23.
 * ClassName: com.system.bhouse.bhouse.DingdanManage
 * Author:Administrator
 * Fuction: 基础验收确认
 * UpdateUser:
 * UpdateDate:
 */
public class BasisActivity extends BaseActivity {


    @Bind(R.id.topfanhuititle_textview_zhende)
    TextView topfanhuititle_textview_zhende;
    @Bind(R.id.fanhui_lin)
    LinearLayout fanhuiLin;
    private TabLayout viewById;
    private ViewPager viewById1;
    private ArrayList<BaseFragment> objects;
    private ArrayList<String> strings;
    private SecondPageListener mSecondPageListener;

    @Override
    protected int getContentViewId() {
        return R.layout.dingdanclearactivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        initview();
        initdate();
        initEvent();
        topfanhuititle_textview_zhende.setText(R.string.jichuyanshouqueren);
    }

    @Override
    protected String getToolbarTitle() {

        return this.getResources().getString(R.string.jichuyanshouqueren);
    }

    private void initEvent() {

    }

    private void initdate() {
        String[] stringArray = this.getResources().getStringArray(R.array.jichuyanshouqueren);
        for (String s : stringArray) {
            strings.add(s);
        }
        for (int i = 0; i < stringArray.length; i++) {
            FragmentDingdan fragmentDingdan = null;
            if (i % 2 == 0) {
                fragmentDingdan = FragmentDingdan.newInstance(StatusIndex.BasisActivityIndex + "", false);
            }
            else {
                fragmentDingdan = FragmentDingdan.newInstance(StatusIndex.BasisActivityIndex + "", true);
                mSecondPageListener=(SecondPageListener)fragmentDingdan;
            }
            objects.add(fragmentDingdan);
        }

        if (viewById1.getAdapter() == null) {
            BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), objects, strings);
            viewById1.setAdapter(baseFragmentAdapter);
            TabLayout.Tab uselessTab;
            for (int j = 0; j < stringArray.length; j++) {
                uselessTab = viewById.newTab();
            }

        }
        else {
            BaseFragmentAdapter adapter = (BaseFragmentAdapter) viewById1.getAdapter();
            adapter.notifyDataSetChanged();
        }
        viewById1.setCurrentItem(0, false);
        viewById.setupWithViewPager(viewById1);

        viewById1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    mSecondPageListener.getSecondPage();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        viewById.setScrollPosition(0, 0, true);
//        ViewUtil.dynamicSetTablayoutMode(viewById);
//        if (viewById.getTabCount() == 2) {
//            viewById.setTabGravity(TabLayout.GRAVITY_CENTER);

            viewById.setTabMode(TabLayout.MODE_SCROLLABLE);

        //2.MODE_FIXED模式
        viewById.setTabMode(TabLayout.MODE_FIXED);
//        }
        int tabGravity = viewById.getTabGravity();
    }

    private void initview() {
        viewById = (TabLayout) findViewById(R.id.tabs);
        viewById1 = (ViewPager) findViewById(R.id.viewpager);
        objects = new ArrayList<>();
        strings = new ArrayList<>();

    }

    @OnClick(R.id.fanhui_lin)
    public void onClick() {
        /**
         * 这里会出问题
         */
        try {
            AppManager.getAppManager().finishActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
            if (!this.isFinishing()) {
                this.finish();
            }
        }
    }

    class BaseFragmentAdapter extends FragmentPagerAdapter {

        private ArrayList<BaseFragment> objects;
        private ArrayList<String> strings;

        public BaseFragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> objects, ArrayList<String> strings) {
            super(fm);

            this.objects = objects;
            this.strings = strings;
        }

        @Override
        public Fragment getItem(int position) {
            return objects.get(position);
        }

        @Override
        public int getCount() {
            return objects == null ? 0 : objects.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return strings.get(position);
        }
    }
}
