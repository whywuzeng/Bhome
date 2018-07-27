package com.system.bhouse.bhouse.CommonTask.TechnologyExecution;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.RelatedDetailBean;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-07-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution
 */

public class TechnologyExecutionActivity extends WWBackActivity implements TechnologyExecutionFragment.TechnologyActivityListenter{

    private static final String TAG = "TechnologyExecutionActivity";

    private static final int RESULT_LOCAL = 2;

    public static final String RESULTQRCOMPONENT = "resultQrComponent";

    public static final String ORDER_ID = "order_id";

    public static final int RESULT_COMPONENT = 2 << 2;

    //二维码构件码
    public String resultQrcomponent = null;

    //订单ID
    public String Order_Id = null;

    private final String[] TitleString=new String[]{
            "工艺执行", "关联明细"
    };

    private final String[] TitleDefaute=new String[]{
            "工艺执行", "请选择"
    };

    private final static int MSG_111=2323<<2;

    private final static int MSG_222=2899<<2;

    private final static int MSG_333=9889<<2;

    /**
     * 加入ViewPager 数据
     * @param
     */
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private RefreshFragmentPagerAdapter pagerAdapter;

    private ItemTouchListener mItemTouchListener;


    Handler mainHandle =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_111:
                    fragments1[1] = fragment;
                    fragmentsUpdateFlag[1] = true;
                    pagerAdapter.notifyDataSetChanged();
                    break;

                case MSG_222:
                    fragments1[2]=null;
                    pagerAdapter.notifyDataSetChanged();
                    break;

                case MSG_333:
                    fragments1[2]=null;
                    fragments1[1]=null;
                    pagerAdapter.notifyDataSetChanged();
                default:
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technology_layout_activity);
        ButterKnife.bind(this);
        setActionBarMidlleTitle("工艺执行");
        annotationDispayHomeAsUp();

        //获取传递值
        Intent intent = getIntent();
        if (intent != null) {
            resultQrcomponent = intent.getStringExtra(RESULTQRCOMPONENT);
            Order_Id = intent.getStringExtra(ORDER_ID);
        }
        /**
         * 加入ViewPager 后的样式展现
         */
        pagerAdapter=new RefreshFragmentPagerAdapter(getSupportFragmentManager());
//      mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        mTabLayout.setupWithViewPager(viewPager);

//        // 适配器必须重写getPageTitle()方法
//        mTabLayout.setTabsFromPagerAdapter(pagerAdapter);
//        // 监听TabLayout的标签选择，当标签选中时ViewPager切换
//        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//        // 监听ViewPager的页面切换，当页面切换时TabLayout的标签跟着切换
//        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        // 当选中的Tab切换时，点击事件
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==1)
                {
//                    mainHandle.sendEmptyMessage(MSG_222);
                }else if (tab.getPosition()==0){
//                    mainHandle.sendEmptyMessage(MSG_333);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /**
         *
         */
         mItemTouchListener=(ItemTouchListener) fragment1;
    }


    @Override
    public void OnFragmentItemClick(String componentQr,String OrderId,ArrayList<RelatedDetailBean> title) {
        if (mItemTouchListener!=null)
        {
            mItemTouchListener.sendRelatedDetail(componentQr,OrderId,title);
            viewPager.setCurrentItem(1);
        }
    }

    /**
     * ViewPager adpter的内容填充
     */
    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public void setTitle(String[] title) {
            this.title = title;
        }

        private  String[] title =TitleDefaute;

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new TechnologyExecutionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", title[i]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }


    boolean[] fragmentsUpdateFlag = {false, false, false, false};


    Fragment fragment = new TechnologyExecutionFragment();

    Fragment fragment1 = new Test1Fragment();

    Fragment[] fragments1={fragment,fragment1};


    public class RefreshFragmentPagerAdapter extends FragmentPagerAdapter {

        private FragmentManager fm;

        public RefreshFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments1[position % fragments1.length];
            KLog.e(TAG, "getItem:position=" + position + ",fragment:"
                    + fragment.getClass().getName() + ",fragment.tag="
                    + fragment.getTag());
            return fragments1[position % fragments1.length];
        }

        @Override
        public int getCount() {
            return fragments1.length;
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

//            if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
//                //如果这个fragment需要更新
//
//                FragmentTransaction ft = fm.beginTransaction();
//                //移除旧的fragment
//                ft.remove(fragment);
//                //换成新的fragment
//                fragment = fragments1[position % fragments1.length];
//                //添加新fragment时必须用前面获得的tag，这点很重要
//                ft.add(container.getId(), fragment, fragmentTag);
//                ft.attach(fragment);
//                ft.commit();
//
//                //复位更新标志
//                fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
//            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TitleString[position];
        }
    }


//    //初始化 spinnerDialog
//    private void showSpinnerDialog() {
//        orderidBtn.setClickable(true);
//        //这个数据  是由 扫码二维码得到的数据.
////        items.add("SCDD-7-20187-0008");
////        items.add("SCDD-7-20186-0007");
////        items.add("SCDD-7-20186-0006");
////        items.add("SCDD-7-20186-0005");
////        items.add("SCDD-7-20186-0002");
////        items.add("SCDD-7-20186-0001");
//
//        spinnerDialog = new SpinnerDialog<Orderbean>(this, items, getResources().getString(R.string.lookup_order_id));
//        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick<Orderbean>() {
//
//            @Override
//            public void onClick(Orderbean item, int position) {
//                tv_orderid_content.setText(item.oriderNumber);
//                Order_Id=item.oriderid;
//                //重新请求工序
//                AskForBackgroud();
//            }
//        });
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case RESULT_COMPONENT:
//                QrCodeComponent(resultCode, data);
//                break;
//        }
    }
    //扫码回调
//    private void QrCodeComponent(int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            Bundle bundle = data.getBundleExtra("bundle");
//            String resultQr = bundle.getString("result");
//            int extraPosition = bundle.getInt("position");
//
//            ApiWebService.Get_Pro_Working_Main_poid_byprid_QR_Code_Json(this, new ApiWebService.SuccessCall() {
//                @Override
//                public void SuccessBack(String result) {
//                    //[{"订单ID":"08776473ea6a4c0ea7a3291d4aa0d359","订单编号":"SCDD-7-201807-0008"}]
//                    String orderID = null;
//                    String orderNumber = null;
//                    try {
//                        JSONArray jsonArray = new JSONArray(result);
//                        Orderbean orderbean;
//                        for (int i=0;i<jsonArray.length();i++)
//                        {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            orderID = jsonObject.optString("订单ID", "");
//                            orderNumber = jsonObject.optString("订单编号", "");
//                            orderbean=new Orderbean();
//                            orderbean.oriderid=orderID;
//                            orderbean.oriderNumber=orderNumber;
//                            items.add(orderbean);
//                        }
//                        showSpinnerDialog();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (!TextUtils.isEmpty(orderID) && !TextUtils.isEmpty(orderNumber)) {
//                            resultQrcomponent = resultQr;
//                            Order_Id = orderID;
//
//                            //显示textview数值
//                            tv_component_content.setText(resultQr);
//                        }else{
//                            tv_component_content.setText("");
//                        }
//                    }
//                }
//
//                @Override
//                public void ErrorBack(String error) {
//
//                }
//            }, resultQr);
//        }
//    }

//    protected static class MyBaseViewHolder extends BaseViewHolder {
//
//        public MyBaseViewHolder(View view) {
//            super(view);
//        }
//    }
//
//    protected InnerHandle Technologyhandler = new InnerHandle(this) {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            if (ValueUtils.IsFirstValueExist(TechnologyBeans)) {
//
//                for (int i = 0; i < TechnologyBeans.size(); i++) {
//                    TechnologyBean technologyBean = TechnologyBeans.get(i);
//                    if (technologyBean.getWorkOrderStatus().equals("执行中")) {
//                        initRecycleViewBg(adapter, technologyBean.getWorkOrderSequence() - 1);
//                    }
//                }
//            }
//        }
//    };
//
//
//    //弱引用 Handle的写法
//    public class InnerHandle extends Handler {
//        private final WeakReference<TechnologyExecutionActivity> wwTimeLineActivityWeakReference;
//
//        public InnerHandle(TechnologyExecutionActivity mActivity){
//            this.wwTimeLineActivityWeakReference=new WeakReference<TechnologyExecutionActivity>(mActivity);
//        }
//    }
//
//    private void AskForBackgroud() {
//
//        ApiWebService.Get_Pro_Working_Main_poid_Json(App.getContextApp(), new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//                TechnologyBeans = App.getAppGson().fromJson(result, new TypeToken<List<TechnologyBean>>() {
//                }.getType());
//
//                if (ValueUtils.IsFirstValueExist(TechnologyBeans)) {
//                    adapter.setNewData(data);
//
//                    Technologyhandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Technologyhandler.sendEmptyMessage(1);
//                        }
//                    }, 500);
//
//                }
//                else {
//                    adapter.setEmptyView(notDataView);
//                }
//
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//
//            }
//        }, resultQrcomponent, Order_Id);
//    }
//
//    //点击响应
//    protected void IntentToFragment(int position) {
//        ShowShort(position + "");
//        switch (position) {
//            case 0:
//
//                break;
//            case 1:
//
//                break;
//            case 2:
//
//                break;
//            case 3:
//
//                break;
//            case 4:
//
//                break;
//            case 5:
//
//                break;
//            case 6:
//
//                break;
//        }
//    }
//
//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////        initRecycleViewBg(adapter, view, position);
//        IntentToFragment(position);
//    }
//
//    private void initRecycleViewBg(BaseQuickAdapter adapter, int position) {
//        View currentView = adapter.getViewByPosition(my_recycle_view, position, R.id.rl_content_layout);
//        SelectColorBg(currentView);
//        currentView.setTag(Color.rgb(51, 138, 185));
//        int itemCount = adapter.getItemCount();
//        for (int i = 0; i < itemCount; i++) {
//            if (i > position) {
//                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rl_content_layout);
//                UnSelectColorBg(byPosition);
//            }
//            else if (i < position) {
//                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rl_content_layout);
//                DisableBg(byPosition);
//            }
//            if (i <= position) {
//                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rlTimeline);
//                SelectLineDotBg(byPosition);
//            }
//            else {
//                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rlTimeline);
//                UnSelectLineDotBg(byPosition);
//            }
//        }
//    }
//
//
//    protected void DisableBg(View view)
//    {
//        DisablePloy ploy = new SelectColorBg();
//        ploy.Disablebg(view);
//    }
//
//    protected void UnSelectLineDotBg(View view) {
//        UnSelectPloy ploy = new UnSelectLineDotBg();
//        ploy.UnSelectbg(view);
//    }
//
//    protected void SelectLineDotBg(View view) {
//        SelectPloy ploy = new UnSelectLineDotBg();
//        ploy.selectBg(view);
//    }
//
//    protected void SelectColorBg(View view) {
//        SelectPloy ploy = new SelectColorBg();
//        ploy.selectBg(view);
//    }
//
//    protected void UnSelectColorBg(View view) {
//        UnSelectPloy ploy = new SelectColorBg();
//        ploy.UnSelectbg(view);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ButterKnife.unbind(this);
//    }
}
