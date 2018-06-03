package com.system.bhouse.bhouse.DingdanManage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
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

/**
 * Created by Administrator on 2016-4-27.
 * ClassName: com.system.bhouse.bhouse.DingdanManage
 * Author:Administrator
 * Fuction: 钥匙上交activity
 * UpdateUser:
 * UpdateDate:
 */
public class KeyHandinActivity extends BaseActivity {


    @Bind(R.id.topfanhuititle_textview_zhende)
    TextView topfanhuititle_textview_zhende;
    private TabLayout viewById;
    private ViewPager viewById1;
    private ArrayList<BaseFragment> objects;
    private ArrayList<String> strings;
    private LinearLayout fanhui_lin;
    private SecondPageListener mSecondPageListener;

    @Override
    protected String getToolbarTitle() {

        return this.getResources().getString(R.string.yaoshishangjiao);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.keyhandin_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        initview();
        initdate();
        initEvent();
        topfanhuititle_textview_zhende.setText(R.string.yaoshishangjiao);
    }

    private void initEvent() {

        fanhui_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 这里会出问题
                 */
                try {
                    AppManager.getAppManager().finishActivity(KeyHandinActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!KeyHandinActivity.this.isFinishing()) {
                        KeyHandinActivity.this.finish();
                    }
                }
            }
        });
    }

    private void initdate() {
        String[] stringArray = this.getResources().getStringArray(R.array.yaoshishangjiao);
        for (String s : stringArray) {
            strings.add(s);
        }
        for (int i = 0; i < stringArray.length; i++) {
            FragmentDingdan fragmentDingdan=null;
            if(i%2==0){
                fragmentDingdan = FragmentDingdan.newInstance(StatusIndex.KeyHandinActivityIndex + "",false);
            }else{
                fragmentDingdan = FragmentDingdan.newInstance(StatusIndex.KeyHandinActivityIndex + "",true);

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

        viewById.setTabMode(TabLayout.MODE_SCROLLABLE);

        //2.MODE_FIXED模式
        viewById.setTabMode(TabLayout.MODE_FIXED);

    }

    private void initview() {
        viewById = (TabLayout) findViewById(R.id.tabs);
        viewById1 = (ViewPager) findViewById(R.id.viewpager);
        fanhui_lin = (LinearLayout) findViewById(R.id.fanhui_lin);
        objects = new ArrayList<>();
        strings = new ArrayList<>();

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
