package com.system.bhouse.bhouse.setup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.readystatesoftware.viewbadger.BadgeView;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.Custom.TagGroup.TagGroup;
import com.system.bhouse.base.App;
import com.system.bhouse.base.Global;
import com.system.bhouse.base.database.AccountManager;
import com.system.bhouse.bean.EventBean.EventOrganization;
import com.system.bhouse.bhouse.CommonTask.common.CustomDatePicker.CustomDatePickerAlertDialog;
import com.system.bhouse.bhouse.LoginActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.Service.MessageService;
import com.system.bhouse.bhouse.phone.activity.InformationActivity;
import com.system.bhouse.bhouse.setup.WWCommon.WWBaseFragment;
import com.system.bhouse.bhouse.setup.notification.MyNotificationActivity_;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotification;
import com.system.bhouse.bhouse.setup.utils.CameraPhotoUtil;
import com.system.bhouse.bhouse.setup.utils.FileUtil;
import com.system.bhouse.utils.MeasureBarHeightUtils;
import com.system.bhouse.utils.TenUtils.T;
import com.system.bhouse.utils.ViewUtil;
import com.system.bhouse.utils.blankutils.LogUtils;
import com.system.bhouse.utils.blankutils.ToastUtils;
import com.system.bhouse.utils.sharedpreferencesuser;
import com.tencent.android.tpush.XGPushShowedResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup
 */
@EFragment(R.layout.myinformationfragment_layout)
public class AboutWeFragment extends WWBaseFragment {

    public String textTitle = "";

    private Uri fileUri;
    private Uri fileCropUri;

    @ViewById(R.id.iv_avator)
    ImageView iv_avator;

    @ViewById(R.id.iv_avator1)
    CircleImageView avatorCircleImageView;

    @ViewById(R.id.organization_ll_layout)
    LinearLayout organization_ll_layout;

    public final int RESULT_REQUEST_PHOTO_CROP = 11006;

    public final int RESULT_REQUEST_PHOTO = 11005;

    public final int RESULT_REQUEST_ORGANIZATION=11007;

    @ViewById(R.id.edit_ll_layout)
    LinearLayout edit_ll_layout;

    @ViewById(R.id.tv_organization_company)
    TextView tv_organization_company;

    @ViewById(R.id.ll_myself_logout)
    LinearLayout ll_myself_logout;

    @ViewById(R.id.tv_organization_company)
    TextView tvCompanyName;

    @ViewById(R.id.tv_username1)
    TextView tvUsername;

    @ViewById(R.id.tv_company_name1)
    TextView TvCompanyName;

    @ViewById(R.id.badge)
    BadgeView badge;
    @ViewById(R.id.lly_notification)
    LinearLayout  lly_notification;

    @ViewById(R.id.tag_group)
    TagGroup tag_group;
    @ViewById(R.id.tag_group1)
    TagGroup tag_group1;

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.action_capture)
    ImageView actionCapture;

    @ViewById(R.id.tv_toolbar_title_mid)
    TextView tvToolbarTitleMid;

    @AfterViews
    public void initAboutWeFrag() {

        tvUsername.setText(App.menname);
        tvCompanyName.setText(App.Mancompany);
        TvCompanyName.setText(App.Mancompany+"\t"+App.mpname);
//        badge.hide();

        setActionBarTitle("我的");
        iv_avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIcon();
            }
        });

        //编辑资料--activity
        edit_ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserDetailEditActivity_.class);
                getActivity().startActivity(intent);
            }
        });

        organization_ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getActivity(),InformationActivity.class);
                (getActivity()).startActivityForResult(intent2,RESULT_REQUEST_ORGANIZATION);
            }
        });

        ll_myself_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLogoutDialog();
            }
        });

        lly_notification.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                count=0;
//                badge.setVisibility(View.GONE);
                MyNotificationActivity_.intent(getActivity()).start();
            }
        });

        initBadgerView(App.ColumCount);
//        SetAvatorIcon();
        tag_group.setTags(new String[]{"企业版"});
        tag_group1.setTags(new String[]{App.usertype});

        avatorCircleImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = avatorCircleImageView.getWidth();
                int height = avatorCircleImageView.getHeight();
                int top = avatorCircleImageView.getTop();
                int left = avatorCircleImageView.getLeft();
                int bottom = avatorCircleImageView.getBottom();
                int right = avatorCircleImageView.getRight();
                int length = App.menname.length();
                String substring = "系";
                if (length>0) {
                     substring = App.menname.substring(length - 1, length);
                }
                if (width>0)
                {
                    Drawable drawable = avatorCircleImageView.getDrawable();
                    Bitmap textToBitmap = ViewUtil.drawTextToBitmap(getActivity(), substring,left,top,width,height);
                    avatorCircleImageView.setImageBitmap(textToBitmap);
                    avatorCircleImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        initView();
//      initDataPickerDialog();
    }



    /**
     * 展示时间选择器=
     * <p>
     */
    private void initDataPickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        CustomDatePickerAlertDialog dialog = new CustomDatePickerAlertDialog(getActivity(), year, month, day,hour,minute);
        dialog.setPositiveButton("确定", new CustomDatePickerAlertDialog.AntDatePickerDialogClickListener() {
            @Override
            public void onClick(View view, int year, int month, int day,int hour,int minute) {
                LogUtils.e("你设置的日期是：", year + "/" + month + "/" + day);
                ToastUtils.showShort("你设置的日期是%s：", year + "/" + month + "/" + day);
//                showTimePickerDialog(year, month, day);
            }
        });
        dialog.setNegativeButton("取消", null);
//        dialog.setSkipButton("跳过", new CustomDatePickerAlertDialog.AntDatePickerDialogClickListener() {
//            @Override
//            public void onClick(View view, int selectedYear, int selectedMonth, int selectedDay,int selectedHour,int selectedMinute) {
//
//            }
//        });
        dialog.setTitle("请设置时间——年月日");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void initView() {
        mToolbar.setTitleTextColor(Color.WHITE);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mDrawerLayout.openDrawer(Gravity.LEFT);
                Intent intent = new Intent(getActivity(), MyselfActivity.class);
                getActivity().startActivity(intent);

            }
        });

        //toolbar button的点击的回调
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //二维码扫描管理  //组织架构的选择界面
                    case R.id.action_capture:
                        Intent intent1 = new Intent(getActivity(), InformationActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });

        actionCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), InformationActivity.class);
                startActivity(intent1);
            }
        });

        tvToolbarTitleMid.setText("我的");
        MeasureBarHeightUtils.setHeight(mToolbar,getActivity());
    }


    private void ShowLogoutDialog() {
        ShowDeviceMessageCustomDialog.Builder builder = new ShowDeviceMessageCustomDialog.Builder(getActivity());
        ShowDeviceMessageCustomDialog dialog = builder.setMessage(R.string.is_sure_logout).setTitle(R.string.logout).setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //点击触发认证事件
                //登录信息置空
                AccountManager.Logout();
                //跳转界面app
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Intent serviceIntent = new Intent(getActivity(), MessageService.class);
                serviceIntent.setPackage("com.system.bhouse.bhouse");
                getActivity().stopService(serviceIntent);
            }
        }).setNegativeButton(R.string.btn_dialog_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }

    protected void SetAvatorIcon()
    {
        String avator_filefloder = sharedpreferencesuser.getAvator_filefloder(getActivity());
        if (!TextUtils.isEmpty(avator_filefloder))
        {
            Glide.with(this).load(avator_filefloder).asBitmap().placeholder(R.mipmap.ic_loading_small_bg).diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.default_icon_hand3).into(iv_avator);
        }
    }

    public void setIcon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("更换头像")
                .setItems(R.array.camera_gallery, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            camera();
                        } else {
                            photo();
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = CameraPhotoUtil.getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, RESULT_REQUEST_PHOTO);
    }

    private void photo() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_REQUEST_PHOTO);
    }

    public void onEventMainThread(Object object) {
        if (object instanceof EventOrganization)
        {
            String resultOrgnazetion = ((EventOrganization)object).getMessage();
            tvCompanyName.setText(resultOrgnazetion);
            TvCompanyName.setText(App.Mancompany+"\t"+App.mpname);
        }else if (object instanceof XGPushShowedResult)
        {
//            badge.show();
            badge.setVisibility(View.VISIBLE);
            int columnsCount = App.getColumnsCount();
            initBadgerView(columnsCount);
            HaWeiShortcutBadger.handleBadge(columnsCount);
        }else if (object instanceof XGNotification)
        {
            int columnsCount = App.getColumnsCount();
            initBadgerView(columnsCount);
            HaWeiShortcutBadger.handleBadge(columnsCount);
        }
    }



    private void initBadgerView(int count){
        badge.setFocusable(false);
        displayNotify(badge,countToString(count));
    }

    public  void displayNotify(BadgeView badgeView, String messageCount) {
        if (messageCount.isEmpty()) {
            badgeView.setVisibility(View.INVISIBLE);
        } else {
            badgeView.setText(messageCount);
            badgeView.setVisibility(View.VISIBLE);
        }
    }

    public  String countToString(int count) {
        if (count == 0) {
            return "";
        } else if (count > 99) {
            return "99+";
        } else {
            return String.valueOf(count);
        }
    }

    @OnActivityResult(RESULT_REQUEST_PHOTO)
    void getStartActivityForResult(int result, Intent data)
    {
        if (result== Activity.RESULT_OK)
        {
            if (data != null) {
                fileUri = data.getData();
            }

            fileCropUri = CameraPhotoUtil.getOutputMediaFileUri();
            Global.cropImageUri(this, fileUri, fileCropUri, 640, 640, RESULT_REQUEST_PHOTO_CROP);
        }
    }

    @OnActivityResult(RESULT_REQUEST_PHOTO_CROP)
    void getPhotoCrop(int result,Intent data)
    {
        if (result == Activity.RESULT_OK) {
            try {
                String filePath = FileUtil.getPath(getActivity(), fileCropUri);
                sharedpreferencesuser.putAvator_filefloder(getActivity(),filePath);
                SetAvatorIcon();
            } catch (Exception e) {
                Global.errorLog(e);
            }
        }
    }

    @OnActivityResult(RESULT_REQUEST_ORGANIZATION)
    void getOrganizationStr(int result,Intent data){
        if (result == Activity.RESULT_OK)
        {
            if (data!=null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    String change = extras.getString("change");
                    tv_organization_company.setText(change);
                    //把菜单改一下
                    T.showShort(getActivity(),"你选择的组织是"+change);
                }
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

}