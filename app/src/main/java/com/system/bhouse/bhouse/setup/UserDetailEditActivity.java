package com.system.bhouse.bhouse.setup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.system.bhouse.base.AccountInfo;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.utils.CameraPhotoUtil;
import com.system.bhouse.bhouse.setup.utils.DatePickerFragment;
import com.system.bhouse.bhouse.setup.utils.FileUtil;
import com.system.bhouse.base.Global;
import com.system.bhouse.bhouse.setup.utils.ListModify;
import com.system.bhouse.bhouse.task.bean.UserObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

@EActivity(R.layout.activity_user_detail_edit)
@OptionsMenu(R.menu.user_detail_edit)
public class UserDetailEditActivity extends WWBackActivity implements DatePickerFragment.DateSet {

    public final static int USERINFO_NAME = 0;
    public final static int USERINFO_SEX = 1;
    public final static int USERINFO_BIRTHDAY = 2;
    public final static int USERINFO_LOCATION = 3;
    public final static int USERINFO_SLOGAN = 4;
    public final static int USERINFO_COMPANY = 5;
    public final static int USERINFO_JOB = 6;
    public final static int USERINFO_TAGS = 7;
//    final String HOST_USER = Global.HOST_API + "/user/key/%s";
//    final String HOST_USERINFO = Global.HOST_API + "/user/updateInfo";
    private final int RESULT_REQUEST_PHOTO = 1005;
    private final int RESULT_REQUEST_PHOTO_CROP = 1006;
//    public String HOST_USER_AVATAR = Global.HOST_API + "/user/avatar?update=1";
    ImageView icon;
    @StringArrayRes
    String[] sexs;
    UserObject user;
    @StringArrayRes
    String[] user_info_list_first;
    String[] user_info_list_second;
    @ViewById
    ListView listView;
    String[] user_jobs;
    @ViewById
    TextView toolbar_title;
    @ViewById
    Toolbar toolbar_com;

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return user_info_list_first.length;
        }

        @Override
        public Object getItem(int position) {
            return user_info_list_second[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_2_text_align_right, parent, false);
                holder = new ViewHolder();
                holder.first = (TextView) convertView.findViewById(R.id.first);
                holder.second = (TextView) convertView.findViewById(R.id.second);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.first.setText(user_info_list_first[position]);

            String seondString = user_info_list_second[position];
            if (seondString.isEmpty()) {
                seondString = "未填写";
            }
            holder.second.setText(seondString);

            return convertView;
        }
    };

//    String HOST = Global.HOST_API + "/user/key/%s";
//    String HOST_JOB = Global.HOST_API + "/options/jobs";
    private Uri fileCropUri;
    private Uri fileUri;
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            //showButtomToast("position" + position);
            switch ((int) id) {

                case USERINFO_NAME:
                    //昵称
                    SetUserInfoActivity_.intent(UserDetailEditActivity.this).title("昵称").row(USERINFO_NAME).startForResult(ListModify.RESULT_EDIT_LIST);
                    break;

                case USERINFO_SEX:
                    //性别
                    setSexs();
                    break;

                case USERINFO_BIRTHDAY:
                    //生日
                    DialogFragment newFragment = new DatePickerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(DatePickerFragment.PARAM_MAX_TODYA, true);
                    bundle.putString(DatePickerFragment.PARAM_DATA, user.birthday);
                    newFragment.setArguments(bundle);
                    newFragment.setCancelable(true);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                    getSupportFragmentManager().executePendingTransactions();
                    break;

                case USERINFO_LOCATION:
                    //所在地
//                    UserProvincesDialogFragment provincesDialogFragment = new UserProvincesDialogFragment();
//                    Bundle provincesBundle = new Bundle();
//                    provincesBundle.putString(ProvincesPickerDialog.LOCATION, user.location);
//                    provincesBundle.putString(ProvincesPickerDialog.TITLE, "选择所在地");
//                    provincesDialogFragment.setArguments(provincesBundle);
//                    provincesDialogFragment.setCallBack(new ProvincesPickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(String provinceStr) {
//                            if (!user.location.trim().equals(provinceStr)) {
//                                user.location = provinceStr;
//                                action_done();
//                            }
//                        }
//                    });
//                    //provincesDialogFragment.setCancelable(true);
//                    provincesDialogFragment.show(getSupportFragmentManager(), "provincesPicker");
//                    getSupportFragmentManager().executePendingTransactions();
                    break;

                case USERINFO_SLOGAN:
//                    座右铭
                    SetUserInfoActivity_.intent(UserDetailEditActivity.this).title("座右铭").row(USERINFO_SLOGAN).startForResult(ListModify.RESULT_EDIT_LIST);
                    break;

                case USERINFO_COMPANY:
                    //公司
                    SetUserInfoActivity_.intent(UserDetailEditActivity.this).title("公司").row(USERINFO_COMPANY).startForResult(ListModify.RESULT_EDIT_LIST);
                    break;

                case USERINFO_JOB:
                    //职位
                    chooseJob();
                    break;

                case USERINFO_TAGS:
                    //个性标签
//                    SetUserTagActivity_.intent(UserDetailEditActivity.this).title("个性标签").startForResult(ListModify.RESULT_EDIT_LIST);
                    break;
            }
        }
    };

    void setIcon() {
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

    @AfterViews
    protected final void initUserDetailEditActivity() {

        setSupportActionBar(toolbar_com);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        toolbar_title.setText("个人信息");

        listViewAddHeaderSection(listView);
        listViewAddFootSection(listView);
        user = AccountInfo.loadAccount(this);

        View head = mInflater.inflate(R.layout.activity_user_info_head, null, false);
        icon = (ImageView) head.findViewById(R.id.icon);

//        icon.setOnClickListener();
//        iconfromNetwork(icon, user.avatar);
//        icon.setTag(new MaopaoListFragment.ClickImageParam(user.avatar));

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIcon();
            }
        });
        listView.addHeaderView(head);

        getUserInfoRows();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClickListener);

        setActionBarTitle("个人信息");

//        getNetwork(String.format(HOST_USER, user.global_key), HOST_USER);
    }

    void getUserInfoRows() {
        user_info_list_second = new String[]{
                user.name,
                sexs[user.sex],
                user.birthday,
                user.location,
                user.slogan,
                user.company,
                user.job_str,
                user.tags_str
        };
    }

    void action_done() {
//        RequestParams params = new RequestParams();
//        params.put("email", user.email);
//        params.put("lavatar", user.lavatar);
//        params.put("name", user.name);
//        params.put("sex", user.sex);
//        params.put("phone", user.phone);
//        params.put("birthday", user.birthday);
//        params.put("location", user.location.trim());
//        params.put("company", user.company);
//        params.put("slogan", user.slogan);
//        params.put("introduction", user.introduction);
//        params.put("job", user.job);
//        params.put("tags", user.tags);

//        postNetwork(HOST_USERINFO, params, HOST_USERINFO);
//
//        umengEvent(UmengEvent.USER, "修改个人信息");

        AccountInfo.saveAccount(this,user);

    }

//    @Override
//    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
//        if (tag.equals(HOST_USERINFO) || tag.equals(HOST_USER)) {
//            if (code == 0) {
//                user = new UserObject(respanse.getJSONObject("data"));
//                AccountInfo.saveAccount(this, user);
//                //MyApp.sUserObject = user;
//                getUserInfoRows();
//                adapter.notifyDataSetChanged();
//            } else {
//                showErrorMsg(code, respanse);
//            }
//
//        } else if (tag.equals(HOST_USER_AVATAR)) {
//            if (code == 0) {
//                String iconUri = respanse.getString("data");
//                iconfromNetwork(icon, iconUri);
//
//                user.avatar = iconUri;
//                AccountInfo.saveAccount(this, user);
//                icon.setTag(new MaopaoListFragment.ClickImageParam(user.avatar));
//
//
//            } else {
//                showErrorMsg(code, respanse);
//            }
//        } else if (tag.equals(HOST)) {
//            if (code == 0) {
//                user = new UserObject(respanse.getJSONObject("data"));
//                AccountInfo.saveAccount(this, user);
//                MyApp.sUserObject = user;
//                getUserInfoRows();
//                adapter.notifyDataSetChanged();
//                //setControlContent(user);
//            } else {
//                showErrorMsg(code, respanse);
//            }
//        } else if (tag.equals(HOST_JOB)) {
//            if (code == 0) {
//                umengEvent(UmengEvent.USER, "修改个人信息");
//
//                ArrayList<String> jobs = new ArrayList<>();
//                jobs.add("");
//                JSONObject jobJSONObject = respanse.getJSONObject("data");
//                Iterator it = jobJSONObject.keys();
//                while (it.hasNext()) {
//                    String key = (String) it.next();
//                    String value = jobJSONObject.getString(key);
//                    jobs.add(value);
//                }
//                user_jobs = new String[jobs.size()];
//                //jobs.toArray(user_jobs);
//                user_jobs[0] = "不选择";
//                for (int i = 1; i < jobs.size(); i++) {
//                    user_jobs[i] = jobJSONObject.optString(i + "");
//                }
//                showJobDialog();
//
//            }
//        }
//    }

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

        } else if (requestCode == RESULT_REQUEST_PHOTO_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String filePath = FileUtil.getPath(this, fileCropUri);
//                    RequestParams params = new RequestParams();
//                    params.put("file", new File(filePath));
//                    postNetwork(HOST_USER_AVATAR, params, HOST_USER_AVATAR);

                } catch (Exception e) {
                    Global.errorLog(e);
                }
            }
        } else if (requestCode == ListModify.RESULT_EDIT_LIST) {
            if (resultCode == Activity.RESULT_OK) {
                //showButtomToast("EDITED");
                //updateUserinfo();
                user = AccountInfo.loadAccount(this);
                getUserInfoRows();
                adapter.notifyDataSetChanged();
            }
        }
    }

//    public void updateUserinfo() {
//        UserObject oldUser = AccountInfo.loadAccount(this);
//        getNetwork(String.format(HOST, oldUser.global_key), HOST);
//    }

    void setSexs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别")
                .setItems(R.array.sexs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user.sex = which;
                        action_done();
                    }
                });
        //builder.create().show();
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void dateSetResult(String date, boolean clear) {
        if (!user.birthday.equals(date)) {
            user.birthday = date;
            action_done();
        }
    }

    public void chooseJob() {
        if (user_jobs == null) {
//            getNetwork(HOST_JOB, HOST_JOB);
        } else {
            showJobDialog();
        }
    }

    private void showJobDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("职位")
                .setItems(user_jobs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user.job = which;
                        action_done();
                    }
                });

        //builder.create().show();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        Point outSize = new Point();
        d.getSize(outSize);
        p.height = (int) (outSize.y * 0.6); // 高度设置为屏幕的0.6
        //p.width = (int) (d.getWidth() * 0.8);
        dialog.getWindow().setAttributes(p);
    }

    static class ViewHolder {
        TextView first;
        TextView second;
    }

}
