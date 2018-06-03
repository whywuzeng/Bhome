package com.system.bhouse.bhouse.setup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.LoginActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.Service.MessageService;
import com.system.bhouse.bhouse.phone.activity.InformationActivity;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.utils.CameraPhotoUtil;
import com.system.bhouse.bhouse.setup.utils.FileUtil;
import com.system.bhouse.base.Global;
import com.system.bhouse.utils.sharedpreferencesuser;

/**
 * Created by Administrator on 2017-10-26.
 */

public class MyselfActivity extends WWBackActivity implements Global.StartActivity {

    private Toolbar toolbar;
    private Uri fileUri;
    private Uri fileCropUri;

    private ImageView iv_avator;
    private LinearLayout organization_ll_layout;

    private final int RESULT_REQUEST_PHOTO_CROP = 1006;

    private LinearLayout edit_ll_layout;

    private TextView tv_organization_company;
    private LinearLayout ll_myself_logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myself_activity_listlayout);
        TextView tvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        TextView tvUsername= (TextView)findViewById(R.id.tv_username);
        tvUsername.setText(App.USER_INFO);
        tvCompanyName.setText(App.Mancompany);
        toolbar=(Toolbar)findViewById(R.id.toolbar_com);
        iv_avator=(ImageView)findViewById(R.id.iv_avator);
        edit_ll_layout=(LinearLayout)findViewById(R.id.edit_ll_layout);
        organization_ll_layout=(LinearLayout)findViewById(R.id.organization_ll_layout);
        tv_organization_company=(TextView)findViewById(R.id.tv_organization_company);
        ll_myself_logout=(LinearLayout)findViewById(R.id.ll_myself_logout);

        setActionBarTitle("我的");

        iv_avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIcon();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //编辑资料--activity
        edit_ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MyselfActivity.this,UserDetailEditActivity_.class);
                MyselfActivity.this.startActivity(intent);

            }
        });

        organization_ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MyselfActivity.this,InformationActivity.class);
                (MyselfActivity.this).startActivityForResult(intent2,2);
            }
        });

        ll_myself_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyselfActivity.this, LoginActivity.class);
                startActivity(intent);
//                AppManager.getAppManager().finishAllActivity();
                Intent serviceIntent = new Intent( MyselfActivity.this, MessageService.class);
                serviceIntent.setPackage("com.system.bhouse.bhouse");
                MyselfActivity.this.stopService(serviceIntent);
            }
        });

        SetAvatorIcon();
    }

   public void setIcon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    private final int RESULT_REQUEST_PHOTO = 1005;

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

    protected void SetAvatorIcon()
    {
        String avator_filefloder = sharedpreferencesuser.getAvator_filefloder(this);
        if (!TextUtils.isEmpty(avator_filefloder))
        {

            Glide.with(this).load(avator_filefloder).asBitmap().placeholder(R.mipmap.ic_loading_small_bg).diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.default_icon_hand3).into(iv_avator);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_REQUEST_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    fileUri = data.getData();
                }

                fileCropUri = CameraPhotoUtil.getOutputMediaFileUri();
                Global.cropImageUri(this, fileUri, fileCropUri, 640, 640, RESULT_REQUEST_PHOTO_CROP);
            }

        }else if (requestCode == RESULT_REQUEST_PHOTO_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String filePath = FileUtil.getPath(this, fileCropUri);
                    sharedpreferencesuser.putAvator_filefloder(MyselfActivity.this,filePath);
                    SetAvatorIcon();
                } catch (Exception e) {
                    Global.errorLog(e);
                }
            }
        }else if(requestCode==2)
        {
            if (data!=null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    String change = extras.getString("change");
                    tv_organization_company.setText(change);
                    //把菜单改一下
                    Toast.makeText(this, "你选择的组织架构是:" + change, Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
