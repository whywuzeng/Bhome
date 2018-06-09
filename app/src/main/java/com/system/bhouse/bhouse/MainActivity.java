package com.system.bhouse.bhouse;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiServiceUtils;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.BaseActivity;
import com.system.bhouse.bean.UserInfo;
import com.system.bhouse.bean.UserManagement;
import com.system.bhouse.bhouse.CompanyNews.NewsListFragment;
import com.system.bhouse.bhouse.Service.DownloadService;
import com.system.bhouse.bhouse.Service.GridLayoutFragment;
import com.system.bhouse.bhouse.Service.MessageService;
import com.system.bhouse.bhouse.phone.activity.InformationActivity;
import com.system.bhouse.bhouse.setup.AboutWeFragment;
import com.system.bhouse.bhouse.setup.AboutWeFragment_;
import com.system.bhouse.bhouse.setup.MyselfActivity;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotification;
import com.system.bhouse.bhouse.task.TaskAddActivity_;
import com.system.bhouse.bhouse.task.adpter.base.SaveFragmentPagerAdapter;
import com.system.bhouse.bhouse.task.view.TopMiddleMenu;
import com.system.bhouse.fragment.EmptyFragment;
import com.system.bhouse.fragment.MyApprovalNotificationFragment;
import com.system.bhouse.fragment.MyApprovalNotificationFragment_;
import com.system.bhouse.ui.IndexViewPager;
import com.system.bhouse.ui.ItemPopupWindow;
import com.system.bhouse.utils.AppManager;
import com.system.bhouse.utils.BHEncodeUtils;
import com.system.bhouse.utils.LogUtil;
import com.system.bhouse.utils.MeasureUtil;
import com.system.bhouse.utils.ProgressUtils;
import com.system.bhouse.utils.inteface.GetPopitemProject;
import com.system.bhouse.utils.inteface.getKVforpopup;
import com.system.bhouse.utils.sharedpreferencesuser;
import com.tencent.android.tpush.XGPushShowedResult;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity<T> extends BaseActivity implements getKVforpopup, GetPopitemProject<T>, TopMiddleMenu.OnMenuItemClickListener {

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

    FragmentManager mFragmentManager;
    //    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    private Toolbar toolbar;

    /*
      Has_doneFragment Has_doneFragment=new Has_doneFragment();
    Homepagefragment homepagefragment=new Homepagefragment();
    InformationFragment informationFragment=new InformationFragment();
    OtherFragment otherFragment=new OtherFragment();
    ScheduleFragment scheduleFragment=new ScheduleFragment();
     */

    /**
     * 2017.8.1 加入的Drawlayout 变量
     */

    DrawerLayout mDrawerLayout;

    @Bind(R.id.mSatelliteMenuLeftTop)
    TopMiddleMenu mSatelliteMenuLeftTop;

    private void initTopMenu() {

        List<Integer> imageResourceLeftTop = new ArrayList<>();//菜单图片,可根据需要设置子菜单个数
        imageResourceLeftTop.add(R.drawable.job_app);
        imageResourceLeftTop.add(R.drawable.job_attendance);
        imageResourceLeftTop.add(R.drawable.job_907);
        imageResourceLeftTop.add(R.drawable.job_909);
        imageResourceLeftTop.add(R.drawable.job_960);
        List<String> nameMenuItem = new ArrayList<>();//菜单图片,可根据需要设置子菜单个数
        nameMenuItem.add("库存管理");
        nameMenuItem.add("新建任务");
        nameMenuItem.add("用印保管");
        nameMenuItem.add("出库日志");
        nameMenuItem.add("财务日志");

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
        Log.e(TAG, "MainActivity onDestroy: " );
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
//        initNotifacation();
        version = MeasureUtil.getVersion(this);

        getUpdateMsg();

        initTopMenu();
//        App.LookDataTest(this);

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
                ProgressUtils.DisMissProgress();
            }

            @Override
            public void onError(Throwable e) {
                com.system.bhouse.utils.TenUtils.T.showShort(MainActivity.this, "" + e.getLocalizedMessage());
                KLog.e("getUpdateMsg()  onError()");
                ProgressUtils.DisMissProgress();
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
        //测试提交提交1111111111  2018.6.3 11点
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
//        if (!houseKeeperIv.isSelected())
//        {
//            houseKeeperIv.setBackgroundResource(R.drawable.bg_myselfhuise);
//        }else{
//            if (isNotify) {
//                houseKeeperIv.setBackgroundResource(R.drawable.bg_reddot_myselfhuise);
//            }
//        }
        switch (second) {
            case 0:
                firstPageIv.setSelected(true);
                firstPageTv.setSelected(true);
                firstPageItem.setSelected(true);
                contentPager.setCurrentItem(First);
                setLinyaout(First);
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
                setLinyaout(Second);
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
                setLinyaout(Third);
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
                setLinyaout(Fourth);
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
                setLinyaout(Fifth);
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

    /**
     * @Bind(R.id.first_page_item) LinearLayout firstPageItem;
     * @Bind(R.id.classic_item) LinearLayout classicItem;
     * @Bind(R.id.shopping_car_item) LinearLayout shoppingCarItem;
     * @Bind(R.id.house_keeper_item) LinearLayout houseKeeperItem;
     * @Bind(R.id.my_lehu_item) LinearLayout myLehuItem;
     * 616161
     */

    private void setLinyaout(int i) {

//        int color = Color.rgb(255, 255, 185);
////        int color1_mian = getResources().getColor(R.color.main_bule);
//        int color1_mian = getResources().getColor(R.color.white);
//        switch (i) {
//            case 0:
//                firstPageItem.setBackgroundColor(color);
//                classicItem.setBackgroundColor(color1_mian);
//                shoppingCarItem.setBackgroundColor(color1_mian);
//                houseKeeperItem.setBackgroundColor(color1_mian);
//                myLehuItem.setBackgroundColor(color1_mian);
//                break;
//            case 1:
//                firstPageItem.setBackgroundColor(color1_mian);
//                classicItem.setBackgroundColor(color);
//                shoppingCarItem.setBackgroundColor(color1_mian);
//                houseKeeperItem.setBackgroundColor(color1_mian);
//                myLehuItem.setBackgroundColor(color1_mian);
//                break;
//            case 2:
//                firstPageItem.setBackgroundColor(color1_mian);
//                classicItem.setBackgroundColor(color1_mian);
//                shoppingCarItem.setBackgroundColor(color);
//                houseKeeperItem.setBackgroundColor(color1_mian);
//                myLehuItem.setBackgroundColor(color1_mian);
//                break;
//            case 3:
//                firstPageItem.setBackgroundColor(color1_mian);
//                classicItem.setBackgroundColor(color1_mian);
//                shoppingCarItem.setBackgroundColor(color1_mian);
//                houseKeeperItem.setBackgroundColor(color);
//                myLehuItem.setBackgroundColor(color1_mian);
//                break;
//            case 4:
//                firstPageItem.setBackgroundColor(color1_mian);
//                classicItem.setBackgroundColor(color1_mian);
//                shoppingCarItem.setBackgroundColor(color1_mian);
//                houseKeeperItem.setBackgroundColor(color1_mian);
//                myLehuItem.setBackgroundColor(color);
//                break;
//
//        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initialize() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.text_open, R.string.text_close);

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);



        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


//        Avator_setNaviga();


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
//                    //注销
//                    case R.id.action_zhuxiao:
//                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        AppManager.getAppManager().finishAllActivity();
////                        AppManager.getAppManager().finishActivity(MainActivity.this);
//                        Intent serviceIntent = new Intent( MainActivity.this, MessageService.class);
//                        serviceIntent.setPackage("com.system.bhouse.bhouse");
//                        MainActivity.this.stopService(serviceIntent);
//                        break;
//                    //管理单元网络请求管理
//                    case R.id.action_UserManagement:
//
//                        Intent intent2=new Intent(MainActivity.this,InformationActivity.class);
//                        (MainActivity.this).startActivityForResult(intent2,2);

//                        if(Build.VERSION.SDK_INT>=23)
//                        {
//                            if (Settings.canDrawOverlays(MainActivity.this)) {
//                                showfloteView();
//                            }else
//                            {
//                                Intent intent1 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                                startActivity(intent1);
//
//                            }
//                        }else {
//
//                            showfloteView();
//                        }

//                        break;
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

    //根据网络 或者 本地 来提取头像
    private void Avator_setNaviga() {

        String avator_filefloder = sharedpreferencesuser.getAvator_filefloder(this);

        if (!TextUtils.isEmpty(avator_filefloder)) {

            Glide.with(this).load(avator_filefloder).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                    Bitmap zoomImg = MeasureUtil.zoomImg(resource, MeasureUtil.dip2px(MainActivity.this, 50), MeasureUtil.dip2px(MainActivity.this, 50));

                    Bitmap roundCorner = MeasureUtil.makeRoundCorner(zoomImg);

                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), roundCorner);

                    toolbar.setNavigationIcon(bitmapDrawable);
                }
            });

        }
        else {
            //设置导航菜单
            toolbar.setNavigationIcon(R.drawable.default_icon_hand3);
        }

    }

    //访问管理单元的数据
    private void showfloteView() {
//        itemPopupWindow = null;
//        setParent(toolbar);
//        Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getUserManagement(App.USER_INFO, BHEncodeUtils.BHEncode(App.MobileKey));
//        getNetworkObserabletwo(kehuIp);

        //改成webservice
        ApiWebService.getUserManagement(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                String s = result.toString();
                KLog.e(s);

                Gson gson = new Gson();
                UserManagement[] shengfens = gson.fromJson(result, new TypeToken<UserManagement[]>() {
                }.getType());

                List<UserManagement> shengfens1 = null;
                if (!(shengfens == null) && !TextUtils.isEmpty(shengfens.toString()) && !(shengfens.length == 0)) {
                    Log.i("TGA", shengfens[0].toString() + "--------------------");
//                    handler.sendMessage(obtain);
                    shengfens1 = new ArrayList<>();
                    for (int i = 0; i < shengfens.length; i++) {
                        Log.i("78798798798798798", shengfens[i].toString());
                        shengfens1.add(shengfens[i]);
                    }

                }
                else {

                }
                showWindow(MainActivity.this.getWindow().getDecorView(), shengfens1);

            }

            @Override
            public void ErrorBack(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        }, App.USER_INFO, BHEncodeUtils.BHEncode(App.MobileKey));


        ApiWebService.getGetmidfulllistdsbyusermid_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

            }

            @Override
            public void ErrorBack(String error) {

            }
        }, App.USER_INFO, App.MID);


    }

    @Override
    public void getpopitemproject(T temp, View temp1) {

    }

    //管理单元数据回调，，回调后调用toast
    @Override
    public void setlistKV(String s, View position, Map<String, Integer> map) {
//        map.get(s);
        KLog.e("你进入的公司是 : " + s + " mid  :" + map.get(s));
        Toast.makeText(this, "你进入的公司是 : " + s, Toast.LENGTH_SHORT).show();
        App.Mancompany = s;
        toolbar.getMenu().findItem(R.id.action_text).setTitle(s);
    }

    //middle icon menu 点击的回调
    @Override
    public void onClick(View view, int postion) {
        Toast.makeText(MainActivity.this, "点击了菜单:" + ((TextView) view).getText(), Toast.LENGTH_LONG).show();

        switch (postion) {
            case 1:
                TaskAddActivity_.intent(this).start();
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


    private static ItemPopupWindow itemPopupWindow;

    //弹出管理单元网络  选择的对话框。。这里的对话框写的耦合。
    @SuppressWarnings("deprecation")
    public void showWindow(View v, List<UserManagement> ts) {
        List<UserManagement> shengfenList1 = (List<UserManagement>) ts;
        if (itemPopupWindow == null) {
            itemPopupWindow = new ItemPopupWindow();
//            if(itemPopupWindow.popupWindow.isShowing()) {
//                itemPopupWindow.disswindow();
//            }
        }
        else {
            itemPopupWindow.disswindow();
        }
        itemPopupWindow.initquickadapter(MainActivity.this, v, shengfenList1);
        itemPopupWindow.showWindow(MainActivity.this, v);

    }

    //管理单元网络请求管理
    private void getNetworkObserabletwo(Observable<T> shengfenIp) {
//        Observable<T> shengfenIp = (Observable<T>)ApiServiceUtils.getInstence().getShengfenIp("");
        ProgressUtils.ShowProgress(this);
        shengfenIp.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                ProgressUtils.DisMissProgress();
                /**
                 * 测试
                 */
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                com.system.bhouse.utils.TenUtils.T.showShort
                        (MainActivity.this, e.toString());
                e.printStackTrace();
                e.toString();
            }

            @Override
            public void onNext(T shengfens2) {
                List<T> shengfens1 = null;
                T[] shengfens = (T[]) shengfens2;
                if (!(shengfens == null) && !TextUtils.isEmpty(shengfens.toString()) && !(shengfens.length == 0)) {
                    Log.i("TGA", shengfens[0].toString() + "--------------------");
//                    handler.sendMessage(obtain);
                    shengfens1 = new ArrayList<>();
                    for (int i = 0; i < shengfens.length; i++) {
                        Log.i("78798798798798798", shengfens[i].toString());
                        shengfens1.add(shengfens[i]);
                    }


                    ProgressUtils.DisMissProgress();
//                    Toast.makeText(OrderInputActivity.this, "getIpInfoResponse==" + shengfens, 0).show();
                }
                else {

                    ProgressUtils.DisMissProgress();
                }
//                showWindow(getparent(), shengfens1);
            }

        });
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

    }

}

