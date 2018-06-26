package com.system.bhouse.bhouse;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiServiceUtils;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.BaseActivity;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bean.UserInfo;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.DetailProductionOrder.ProductionOrderViewActivity;
import com.system.bhouse.bhouse.CompanyNews.NewsListFragment;
import com.system.bhouse.bhouse.Service.DownloadService;
import com.system.bhouse.bhouse.Service.GridLayoutFragment;
import com.system.bhouse.bhouse.Service.MessageService;
import com.system.bhouse.bhouse.phone.activity.InformationActivity;
import com.system.bhouse.bhouse.setup.AboutWeFragment;
import com.system.bhouse.bhouse.setup.AboutWeFragment_;
import com.system.bhouse.bhouse.setup.MyselfActivity;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotification;
import com.system.bhouse.bhouse.task.adpter.base.SaveFragmentPagerAdapter;
import com.system.bhouse.bhouse.task.view.TopMiddleMenu;
import com.system.bhouse.fragment.EmptyFragment;
import com.system.bhouse.fragment.MyApprovalNotificationFragment;
import com.system.bhouse.fragment.MyApprovalNotificationFragment_;
import com.system.bhouse.ui.IndexViewPager;
import com.system.bhouse.utils.AppManager;
import com.system.bhouse.utils.LogUtil;
import com.system.bhouse.utils.MeasureUtil;
import com.system.bhouse.utils.sharedpreferencesuser;
import com.tencent.android.tpush.XGPushShowedResult;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import rx.Observer;


public class MainActivity extends BaseActivity implements  TopMiddleMenu.OnMenuItemClickListener {

    private ServiceConnection connection;
    private static String Tag = "Bind";
    private static int First = 0;
    private static int Second = 1;
    private static int Third = 2;
    private static int Fourth = 3;
    private static int Fifth = 4;
    private static List<Fragment> fragments = new ArrayList<>();

    //做提示的界面 EmptyFragment 当前界面无数据的fragment
    MyApprovalNotificationFragment emptyFragment1=new MyApprovalNotificationFragment_();

    NewsListFragment newsListFragment = NewsListFragment.newInstance("T1348647909107", "headline", 0);

    EmptyFragment emptyFragment3 = new EmptyFragment();
    AboutWeFragment aboutWeFragment=new AboutWeFragment_();

    //这个是真正的界面 gridlayoutfragment
    GridLayoutFragment gridLayoutFragment = null;


    //做提示的界面 EmptyFragment
//    EmptyFragment emptyFragment=new EmptyFragment();

    public myadapter myadapter;
    private String version;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public View getparent() {
        return parent;
    }

    public void setParent(View parent) {
        this.parent = parent;
    }

    private View parent;

    String[] s1 = {"1", "2", "3", "4", "5"};

    @Bind(R.id.content_pager)
    IndexViewPager contentPager;
    @Bind(R.id.first_page_iv)
    ImageView firstPageIv;
    @Bind(R.id.first_page_tv)
    TextView firstPageTv;
    @Bind(R.id.first_page_item)
    LinearLayout firstPageItem;
    @Bind(R.id.classic_iv)
    ImageView classicIv;
    @Bind(R.id.classic_tv)
    TextView classicTv;
    @Bind(R.id.classic_item)
    LinearLayout classicItem;
    @Bind(R.id.shopping_car_iv)
    ImageView shoppingCarIv;
    @Bind(R.id.shopping_car_tv)
    TextView shoppingCarTv;
    @Bind(R.id.shopping_car_item)
    LinearLayout shoppingCarItem;
    @Bind(R.id.house_keeper_iv)
    ImageView houseKeeperIv;
    @Bind(R.id.house_keeper_tv)
    TextView houseKeeperTv;
    @Bind(R.id.house_keeper_item)
    LinearLayout houseKeeperItem;
    @Bind(R.id.my_lehu_iv)
    ImageView myLehuIv;
    @Bind(R.id.my_lehu_tv)
    TextView myLehuTv;
    @Bind(R.id.my_lehu_item)
    LinearLayout myLehuItem;
    @Bind(R.id.first_page_item_flag)
    ImageView firstPageItemFlag;
    @Bind(R.id.classic_item_flag)
    ImageView classicItemFlag;
    @Bind(R.id.shopping_car_item_flag)
    ImageView shoppingCarItemFlag;
    @Bind(R.id.house_keeper_item_flag)
    ImageView houseKeeperItemFlag;
    @Bind(R.id.my_lehu_item_flag)
    ImageView myLehuItemFlag;
    @Bind(R.id.tv_toolbar_title_mid)
    TextView tv_toolbar_title_mid;

    FragmentManager mFragmentManager;
    private Toolbar toolbar;

    @Bind(R.id.mSatelliteMenuLeftTop)
    TopMiddleMenu mSatelliteMenuLeftTop;

    private void initTopMenu() {

        List<Integer> imageResourceLeftTop = new ArrayList<>();//菜单图片,可根据需要设置子菜单个数
        imageResourceLeftTop.add(R.drawable.menu_btn_scan_normal);
        imageResourceLeftTop.add(R.drawable.menu_btn_scannamecard_normal);
        imageResourceLeftTop.add(R.drawable.menu_btn_touping_normal);
        imageResourceLeftTop.add(R.drawable.job_909);

//      imageResourceLeftTop.add(R.drawable.job_960);
        List<String> nameMenuItem = new ArrayList<>();//菜单图片,可根据需要设置子菜单个数
        nameMenuItem.add("生产扫码");
        nameMenuItem.add("未完待续");
        nameMenuItem.add("未完待续");
        nameMenuItem.add("未完待续");
//      nameMenuItem.add("财务日志");

        mSatelliteMenuLeftTop.getmBuilder()
                .setMenuItemNameTexts(nameMenuItem)
                .setMenuImage(R.drawable.ic_firsteqrcode)
                .setMenuItemImageResource(imageResourceLeftTop)
                .setOnMenuItemClickListener(this)
                .creat();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "MainActivity onDestroy: ");
        EventBus.getDefault().unregister(this);
        //没开就不要关
        if(connection!=null)
        unbindService(connection);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    //给fragments list里面设值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridLayoutFragment = new GridLayoutFragment();
        Log.e(TAG, "MainActivity onCreate: " );
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        //在这里请求一次 保证值是最新的。 这里得到，未读的条数
        App.getColumnsCount();

        Intent intent = getIntent();
        if (intent!=null)
        {
            UserInfo mInfo = (UserInfo)intent.getParcelableExtra(LoginActivity.USERLOGNDATA);
                //静态保存
                App.USER_INFO = mInfo.getUsername();
                App.MID = mInfo.getMid();
                App.GSMID= mInfo.getGsmid();
                App.MobileKey = mInfo.MobileKey;
                App.Filenum = mInfo.Filenum;
                App.Filesize = mInfo.getFilesizeX();
                App.Mancompany = mInfo.getManCompany();
        }
        //更换存储的域名
        ApiWebService.setUserMaindomain();

        initialize();
        fragments.add(gridLayoutFragment);
        //弟二个 界面   Has_doneFragment 或者 scheduleFragment
        fragments.add(newsListFragment);

        fragments.add(emptyFragment1);

        emptyFragment3.textTitle = "个人信息";
        aboutWeFragment.textTitle="我的";
        fragments.add(aboutWeFragment);

        mFragmentManager = getSupportFragmentManager();
        onsetViewFlow();
//       initNotifacation();
        version = MeasureUtil.getVersion(this);

        getUpdateMsg();

        initTopMenu();

        gridLayoutFragment.setOnMoreClickListener(() ->  updataBottomStatu(Second));

        if (App.ColumCount>0)
        {
            isNotify=true;
            houseKeeperIv.setBackgroundResource(R.drawable.bg_reddot_myselfhuise);
        }

    }

    //更新apk的网络请求  这个地址已经失效。
    private void getUpdateMsg() {

        ApiServiceUtils.getInstence().getUpdateMsg().subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                KLog.e("getUpdateMsg()  onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("getUpdateMsg()  onError()");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    InputStream inputStream = responseBody.byteStream();
                    String result = IOUtils.toString(inputStream, "GB2312");
                    KLog.e(result);
                    String[] split = result.split("&");

                    File M_File = new File("/storage/emulated/0/DCIM/bhouseDownoad/VersionCode.text");
                    if (!M_File.exists()) {
//                        File file = FileUtils.getDirsFile();//构造目录文件
//                        String ver_filepath = FileUtils.saveAsFileWriter(split[1], file, "VersionCode.text");
                    }
                    sharedpreferencesuser.putVerFilePath(MainActivity.this, "/storage/emulated/0/DCIM/bhouseDownoad/VersionCode.text");
                    DealwithResult(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isNotify=false;

    public void onEventMainThread(Object object) {
         if (object instanceof XGPushShowedResult)
        {
            isNotify=true;
            houseKeeperIv.setBackgroundResource(R.drawable.bg_reddot_myselfhuise);
        }if (object instanceof XGNotification)
        {
            int columnsCount = App.getColumnsCount();
            if (columnsCount<=0)
            {
                isNotify=false;
                houseKeeperIv.setBackgroundResource(R.drawable.bg_myselfhuise);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "MainActivity onStart: " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "MainActivity onStop: " );
    }

    //处理服务器返回的版本号
    private void DealwithResult(String result) {
        final String[] split = result.split("&");
        String versioncode=null;
        String apk_urlxia=null;

       for (int i=0;i<split.length;i++)
       {
           if (split[i].contains("Ver:"))
           {
               versioncode = split[i];
           }else if (split[i].contains("http://"))
           {

                apk_urlxia = split[i];
           }
       }
        String verFilePath = sharedpreferencesuser.getVerFilePath(this);
        final String[] splitVer = versioncode.split(":");
        //拿到本地版本号和网络的数据比较

        if (Float.valueOf(version)<Float.valueOf(splitVer[1])) {
                ShowUpdataDialog(split[1], versioncode, apk_urlxia);
        }

    }

    //弹出对话框  告知版本更新了
    private void ShowUpdataDialog(String s, final String versioncode, final String apk_urlxia) {
        ShowDeviceMessageCustomDialog.Builder builder = new ShowDeviceMessageCustomDialog.Builder(this);
        builder.setMessage("原来的版本号为:" + version + ",有新版了,版本号：" + versioncode + "\r\n确定更新?");
        builder.setTitle("提示");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //启动apk下载
                DownloadService.launch(MainActivity.this, apk_urlxia, "apk");

                AppManager.getAppManager().AppExit(MainActivity.this);
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AppManager.getAppManager().AppExit(MainActivity.this);
                    }
                });

        ShowDeviceMessageCustomDialog showDeviceMessageCustomDialog = builder.create();
        showDeviceMessageCustomDialog.setCancelable(false);
        showDeviceMessageCustomDialog.setIsKeyDown(false);
        showDeviceMessageCustomDialog.show();
    }

    //初始化通知
    private void initNotifacation() {
        Intent serviceIntent = new Intent(this, MessageService.class);
        serviceIntent.setPackage("com.system.bhouse.bhouse");
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                KLog.e("连起来了" + name);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                KLog.e("连不起来了" + name);
            }
        };
        boolean bindResult = this.bindService(serviceIntent, connection, this.BIND_AUTO_CREATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //翻页的条件回调
    private void onsetViewFlow() {
        updataBottomStatu(First);
        myadapter = new myadapter(mFragmentManager);
        contentPager.setAdapter(myadapter);
        //new String[]{"1","2","3","4"}
        contentPager.setOffscreenPageLimit(5);
        contentPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int position_1;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                position_1 = position;
            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.i(Tag, "////////////////" + position);
//                    updataBottomStatu(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (position_1 + 1 >= s1.length) {
//                    contentPager.setCurrentItem(0);
                }
            }
        });
        contentPager.setCurrentItem(0);
    }

    @OnClick({R.id.first_page_iv, R.id.first_page_tv, R.id.first_page_item, R.id.classic_iv, R.id.classic_tv, R.id.classic_item, R.id.shopping_car_iv, R.id.shopping_car_tv, R.id.shopping_car_item, R.id.house_keeper_iv, R.id.house_keeper_tv, R.id.house_keeper_item, R.id.my_lehu_iv, R.id.my_lehu_tv, R.id.my_lehu_item, R.id.first_page_item_flag, R.id.classic_item_flag, R.id.shopping_car_item_flag, R.id.house_keeper_item_flag, R.id.my_lehu_item_flag})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_page_iv:
            case R.id.first_page_tv:
            case R.id.first_page_item:
            case R.id.first_page_item_flag:
                updataBottomStatu(First);
                break;
            case R.id.classic_iv:
            case R.id.classic_tv:
            case R.id.classic_item:
                updataBottomStatu(Second);
                break;
//            case R.id.shopping_car_iv:
//            case R.id.shopping_car_tv:
//            case R.id.shopping_car_item:
//                updataBottomStatu(Third);
//                break;
            case R.id.house_keeper_iv:
            case R.id.house_keeper_tv:
            case R.id.house_keeper_item:
                updataBottomStatu(Fourth);
                break;
            case R.id.my_lehu_iv:
            case R.id.my_lehu_tv:
            case R.id.my_lehu_item:
                updataBottomStatu(Fifth);
                //第2个 通讯录

                break;
        }
    }


    private long secondTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        long spaceTime = 0;
        long firstTime = 0;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (contentPager.getCurrentItem() != 0) {
                goShopping();

                return false;
            }
            firstTime = System.currentTimeMillis();
            spaceTime = firstTime - secondTime;
            secondTime = firstTime;
            if (spaceTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                AppManager.getAppManager().AppExit(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goShopping() {

        updataBottomStatu(First);
    }


    private void updataBottomStatu(int second) {
        switch (second) {
            case 0:
                firstPageIv.setSelected(true);
                firstPageTv.setSelected(true);
                firstPageItem.setSelected(true);
                contentPager.setCurrentItem(First);
                //这里设置内容
                tv_toolbar_title_mid.setText(firstPageTv.getText());
                firstPageItemFlag.setVisibility(View.GONE);
                firstPageItemFlag.setSelected(true);

                classicItemFlag.setSelected(false);
                shoppingCarItemFlag.setSelected(false);
                houseKeeperItemFlag.setSelected(false);
                myLehuItemFlag.setSelected(false);

                classicIv.setSelected(false);
                classicTv.setSelected(false);
                classicItem.setSelected(false);

                shoppingCarIv.setSelected(false);
                shoppingCarTv.setSelected(false);
                shoppingCarItem.setSelected(false);

                houseKeeperIv.setSelected(false);
                houseKeeperTv.setSelected(false);
                houseKeeperItem.setSelected(false);

                myLehuIv.setSelected(false);
                myLehuTv.setSelected(false);
                myLehuItem.setSelected(false);
                break;

            case 1:
                classicIv.setSelected(true);
                classicTv.setSelected(true);
                classicItem.setSelected(true);
                contentPager.setCurrentItem(Second);
                tv_toolbar_title_mid.setText(classicTv.getText());
                firstPageItemFlag.setSelected(false);
                classicItemFlag.setSelected(true);
                classicItemFlag.setVisibility(View.GONE);
                shoppingCarItemFlag.setSelected(false);
                houseKeeperItemFlag.setSelected(false);
                myLehuItemFlag.setSelected(false);

                firstPageIv.setSelected(false);
                firstPageTv.setSelected(false);
                firstPageItem.setSelected(false);

                shoppingCarIv.setSelected(false);
                shoppingCarTv.setSelected(false);
                shoppingCarItem.setSelected(false);

                houseKeeperIv.setSelected(false);
                houseKeeperTv.setSelected(false);
                houseKeeperItem.setSelected(false);

                myLehuIv.setSelected(false);
                myLehuTv.setSelected(false);
                myLehuItem.setSelected(false);
                break;

            case 2:
                shoppingCarIv.setSelected(true);
                shoppingCarTv.setSelected(true);
                shoppingCarItem.setSelected(true);
                contentPager.setCurrentItem(Third);
                tv_toolbar_title_mid.setText(shoppingCarTv.getText());
                firstPageItemFlag.setSelected(false);
                classicItemFlag.setSelected(false);
                shoppingCarItemFlag.setSelected(true);
                shoppingCarItemFlag.setVisibility(View.GONE);
                houseKeeperItemFlag.setSelected(false);
                myLehuItemFlag.setSelected(false);

                firstPageIv.setSelected(false);
                firstPageTv.setSelected(false);
                firstPageItem.setSelected(false);

                classicIv.setSelected(false);
                classicTv.setSelected(false);
                classicItem.setSelected(false);

                houseKeeperIv.setSelected(false);
                houseKeeperTv.setSelected(false);
                houseKeeperItem.setSelected(false);

                myLehuIv.setSelected(false);
                myLehuTv.setSelected(false);
                myLehuItem.setSelected(false);

                break;

            case 3:
                houseKeeperIv.setSelected(true);
                houseKeeperTv.setSelected(true);
                houseKeeperItem.setSelected(true);
                contentPager.setCurrentItem(Fourth);
                tv_toolbar_title_mid.setText(houseKeeperTv.getText());
                firstPageItemFlag.setSelected(false);
                classicItemFlag.setSelected(false);
                shoppingCarItemFlag.setSelected(false);
                houseKeeperItemFlag.setSelected(true);
                houseKeeperItemFlag.setVisibility(View.GONE);
                myLehuItemFlag.setSelected(false);

                myLehuIv.setSelected(false);
                myLehuTv.setSelected(false);
                myLehuItem.setSelected(false);


                shoppingCarIv.setSelected(false);
                shoppingCarTv.setSelected(false);
                shoppingCarItem.setSelected(false);

                classicIv.setSelected(false);
                classicTv.setSelected(false);
                classicItem.setSelected(false);

                firstPageIv.setSelected(false);
                firstPageTv.setSelected(false);
                firstPageItem.setSelected(false);

                break;

            case 4:
                myLehuIv.setSelected(true);
                myLehuTv.setSelected(true);
                myLehuItem.setSelected(true);
//                contentPager.setCurrentItem(Fifth);
                contentPager.setCurrentItem(Third);
                tv_toolbar_title_mid.setText(myLehuTv.getText());
                firstPageItemFlag.setSelected(false);
                classicItemFlag.setSelected(false);
                shoppingCarItemFlag.setSelected(false);
                houseKeeperItemFlag.setSelected(false);
                myLehuItemFlag.setSelected(true);
                myLehuItemFlag.setVisibility(View.GONE);

                firstPageIv.setSelected(false);
                firstPageTv.setSelected(false);
                firstPageItem.setSelected(false);

                classicIv.setSelected(false);
                classicTv.setSelected(false);
                classicItem.setSelected(false);

                shoppingCarIv.setSelected(false);
                shoppingCarTv.setSelected(false);
                shoppingCarItem.setSelected(false);

                houseKeeperIv.setSelected(false);
                houseKeeperTv.setSelected(false);
                houseKeeperItem.setSelected(false);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mDrawerLayout.openDrawer(Gravity.LEFT);
                Intent intent = new Intent(MainActivity.this, MyselfActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });

        //toolbar button的点击的回调
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //二维码扫描管理  //组织架构的选择界面
                    case R.id.action_capture:
                        Intent intent1 = new Intent(MainActivity.this, InformationActivity.class);
                        startActivity(intent1);
                        break;

                }

                return false;
            }
        });
    }

    public static final int REQUST_QRCODE = 10008;

    //middle icon menu 点击的回调
    @Override
    public void onClick(View view, int postion) {

        switch (postion) {
            case 0:
                Intent intent = new Intent(this, CaptureActivity.class);
                intent.putExtra("position",postion);
                intent.putExtra(CaptureActivity.TipContentTag,"生产订单ID获取备料详细信息");
                startActivityForResult(intent, REQUST_QRCODE);
                break;
            case 1:
//                TaskAddActivity_.intent(this).start();
                break;
        }
    }

    class myadapter extends SaveFragmentPagerAdapter {

        public myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            saveFragment(fragments.get(arg0));
            return fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }


    //android6.0 判断权限的
    private static final int REQUEST_CODE = 1;

    private void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "权限开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (requestCode == 2) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    String change = extras.getString("change");
                    //把菜单改一下
                    Toast.makeText(this, "你选择的组织架构是:" + change, Toast.LENGTH_LONG).show();
                }
            }
        }
        else if (requestCode == REQUST_QRCODE) {

            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getBundleExtra("bundle");
                String resultQr = bundle.getString("result");
                int extraPosition = bundle.getInt("position");

                StatusBean statusBean = new StatusBean();
                //从初始化  或者  后台请求 得到状态
                SubmitStatusBeanImpl submitStatusBean = new SubmitStatusBeanImpl();
                submitStatusBean.setVisCheckBtn(true).setVisDeleteBtn(true);
                statusBean.setBean(submitStatusBean);
                statusBean.setLookStatus(true);

                Intent intent = new Intent(this, ProductionOrderViewActivity.class);
                intent.putExtra("result",resultQr);
                intent.putExtra("position",extraPosition);

                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("data",statusBean);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        }
    }
}

