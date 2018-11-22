package com.system.bhouse.Common.filewidget;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.system.bhouse.Common.Global;
import com.system.bhouse.Common.filewidget.databean.AttachmentFileObject;
import com.system.bhouse.Common.filewidget.databean.AttachmentHeadFooter;
import com.system.bhouse.Common.filewidget.resoures.AttachmentBaseDetailActivity;
import com.system.bhouse.Common.filewidget.resoures.AttachmentTexTDetailActivity;
import com.system.bhouse.Common.filewidget.resoures.AttachmentsDownloadDetailActivity;
import com.system.bhouse.Common.filewidget.save.FileSaveHelp;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.HeaderAndFooterSectionQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.utils.FileUtil;
import com.system.bhouse.utils.TenUtils.GlideUtils;
import com.system.bhouse.utils.blankutils.TimeUtils;
import com.system.bhouse.utils.blankutils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-11-08.
 * <p>
 * by author wz
 * 文件的一级目录
 * <p>
 * com.system.bhouse.Common.filewidget
 */
public class ProjectAttachmentActivity extends BaseFileDownActivity implements View.OnClickListener,BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    private static final String TAG = "Project";

    @Bind(R.id.listView)
    RecyclerView mRecyclerView;
    @Bind(R.id.folder_actions_layout)
    FrameLayout folderActionsLayout;
    @Bind(R.id.files_actions_layout)
    FrameLayout filesActionsLayout;
    private boolean isEditMode = false;
    @Bind(R.id.common_folder_bottom_upload)
    FrameLayout common_folder_bottom_upload;

    //check被点击
    protected CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            final AttachmentFileObject item = ((AttachmentFileObject) buttonView.getTag());
            item.isSelected = true;
        }
    };
    private ArrayList<AttachmentHeadFooter> objectList;
    private static final int FILE_SELECT_CODE =0x231;
    private ViewGroup listHead;
    private ActionMode mActionMode;
    private ProjectAttachAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MainAppTheme_Base);
        setContentView(R.layout.folder_main_refresh_listview);
        ButterKnife.bind(this);
        initProjectAttachmentActivity();
        setActionBarMidlleTitle("附件");
        //初始化 toolbar
        ToolbarDispayHomeAsUp();
    }

    @Override
    protected void checkFileDownloadStatus() {
        for (AttachmentHeadFooter object:objectList)
        {
            final AttachmentFileObject item = object.t;
            if (null!=item&&!item.isFolder)
            {
                setDownLoadStatus(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void setDownLoadStatus(AttachmentFileObject item) {
        //这里要根据 下载文件名来获取 downloadID的 才能是一个  一个操作
        final long downloadID = getDownloadID(item);
        if (downloadID!=0L)
        {
            item.downloadId =downloadID;
            updateFileDownloadStatus(item);
            item.isDownload=false;
        }else {
            final File dir = FileUtil.getDestinationInExternalPublicDir(FileSaveHelp.getSettingDownPath(this), item.getName());
            if (dir.isFile()&&dir.exists())
            {
                item.isDownload =true;
            }else {
                item.downloadId = 0L;
                item.isDownload =false;
            }
            Log.e(TAG, "setDownLoadStatus: 是否有文件"+dir.isFile()+":"+dir.exists() );
        }
    }

    private void initProjectAttachmentActivity() {

        common_folder_bottom_upload.setOnClickListener(this);
        objectList = new ArrayList<>();
        AttachmentHeadFooter attachmentHeadFooter;
        attachmentHeadFooter = new AttachmentHeadFooter(true, "", true, "");
        objectList.add(attachmentHeadFooter);

        AttachmentFileObject attachmentFileObject = new AttachmentFileObject();
        attachmentFileObject.setName("产品文件");
        attachmentFileObject.isFolder = true;
        attachmentFileObject.size = 200021;
        attachmentFileObject.created_at = 1541726094983L;
        attachmentFileObject.fileType = "dir";

        attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);
        attachmentFileObject = new AttachmentFileObject();
        attachmentFileObject.setName("IMG_1714.PNG");
        attachmentFileObject.fileType = "PNG";
        attachmentFileObject.file_id =Global.getUUID32();
        attachmentFileObject.preview = "https://dn-coding-net-production-file.codehub.cn/64bbff38-0a72-49d1-a973-6ebaab659a30.PNG?imageView2/1/w/90/h/90&e=1542074193&token=goE9CtaiT5YaIP6ZQ1nAafd_C1Z_H2gVP8AwuC-5:Q_2BRMpvVhFHkFEk8E92ZfMTE34=";//链接
        attachmentFileObject.created_at = 1541727370000L;
        attachmentFileObject.size = 61164;
        attachmentFileObject.isDownload=true;
        attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);

        attachmentFileObject = new AttachmentFileObject();
        attachmentFileObject.setName(FILENAMEDOWN);
        attachmentFileObject.fileType = "flv";
        attachmentFileObject.file_id =Global.getUUID32();
        attachmentFileObject.created_at = 1541727370000L;
        attachmentFileObject.size = 20002121;
        attachmentFileObject.isDownload=true;
        attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);


        attachmentFileObject = new AttachmentFileObject();
        attachmentFileObject.setName("OA首页配置备份.docx");
        attachmentFileObject.fileType = "docx";
        attachmentFileObject.file_id =Global.getUUID32();
        attachmentFileObject.created_at = 1541727370000L;
        attachmentFileObject.size = 20002121;
        attachmentFileObject.isDownload=false;
        attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);

        attachmentFileObject = new AttachmentFileObject();
        attachmentFileObject.setName("bg0_fine_day.jpg");
        attachmentFileObject.fileType = "jpg";
        attachmentFileObject.file_id =Global.getUUID32();
        attachmentFileObject.created_at = 1541727370000L;
        attachmentFileObject.size = 200121;
        attachmentFileObject.isDownload=false;
        attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);

        attachmentFileObject = new AttachmentFileObject();
        attachmentFileObject.setName("修改KK密码的地方.png");
        attachmentFileObject.fileType = "png";
        attachmentFileObject.file_id =Global.getUUID32();
        attachmentFileObject.created_at = 1541727370000L;
        attachmentFileObject.size = 200121;
        attachmentFileObject.isDownload=false;
        attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);

        attachmentFileObject = new AttachmentFileObject();
        attachmentFileObject.setName("新建文本文档.txt");
        attachmentFileObject.fileType = "txt";
        attachmentFileObject.file_id =Global.getUUID32();
        attachmentFileObject.created_at = 1541727370000L;
        attachmentFileObject.size = 180121;
        attachmentFileObject.isDownload=false;
        attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);


        attachmentFileObject = new AttachmentFileObject();
        attachmentFileObject.setName("首页.html");
        attachmentFileObject.fileType = "html";
        attachmentFileObject.file_id =Global.getUUID32();
        attachmentFileObject.created_at = 1541727370000L;
        attachmentFileObject.size = 180121;
        attachmentFileObject.isDownload=false;
        attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);

//        1:加载头部隔离带
          adapter = new ProjectAttachAdapter(R.layout.project_attachment_file_list_item, R.layout.divide_top_15, R.layout.divide_bottom_15, objectList);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        listHead = (ViewGroup) getLayoutInflater().inflate(R.layout.upload_file_layout, mRecyclerView, false);
        adapter.addHeaderView(listHead);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
    }

    //smartlayout 加载更多
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_folder_bottom_upload:
                FoloderUpload();
                break;

            default:
                break;
        }
    }

    //上传文件
    private void FoloderUpload() {
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//可以选择什么类型  选择所有类型intent.setType(“video/;image/”);//同时选择视频和图片 ;//选择图片
//        您的 Intent 必须指定 MIME 类型，并且必须声明 CATEGORY_OPENABLE 类别。必要时，您可以使用 EXTRA_MIME_TYPES extra 添加一个 MIME 类型数组来指定多个 MIME 类型 — 如果您这样做，必须将 setType() 中的主 MIME 类型设置为 "*/*"。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //这里是要找到所有能处理Intent.ACTION_SET_WALLPAPER请求的activity，其字符串表示为android.intent.action.SET_WALLPAPER
        //找到所有匹配的 ，activity，然后形成一个列表来显示.
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个上传的文件"), FILE_SELECT_CODE);
        }catch (android.content.ActivityNotFoundException ex) {
            ToastUtils.showShort("请安装文件管理器");
        }
    }

    //进入编辑状态
    @OnClick(R.id.toolbar_menu_img)
    public void onViewClicked() {
        doEdit();
    }

    private ActionMode.Callback startActionMode = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            final MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.project_attachment_file_edit, menu);
            filesActionsLayout.setVisibility(View.VISIBLE);
            folderActionsLayout.setVisibility(View.GONE);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode=null;
            filesActionsLayout.setVisibility(View.GONE);
            folderActionsLayout.setVisibility(View.VISIBLE);
            setListEditMode(false);
        }
    };

    private void doEdit() {
        if (mActionMode != null) {
            return;
        }

        mActionMode = startSupportActionMode(startActionMode);
        setListEditMode(true);
    }

    private void setListEditMode(boolean isEdit) {
        this.isEditMode =isEdit;
        adapter.notifyDataSetChanged();
    }

    //recycleview item里view的点击事件
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.more:
                onMoreAction(view);
                break;

            default:
                break;
        }
    }

    private void onMoreAction(View view) {
        final AttachmentFileObject tag = (AttachmentFileObject) view.getTag();
        if (tag.isDownload)
        {
            //点击listitem 这里跟Item点击是一样的
            onItemListListener(tag);
        }else{
            // TODO: 2018-11-14 这里是 可以 继续研究 https cookie 传给cooding的服务器为什么不对 ，出现401请求错误
            //下载
//           RetrofitManager.getInstance(HostType.COODING_INFO).getPicResrouce().subscribe(new Observer<Object>() {
//               @Override
//               public void onCompleted() {
//
//               }
//
//               @Override
//               public void onError(Throwable e) {
//
//               }
//
//               @Override
//               public void onNext(Object o) {
//
//               }
//           });
            actionDownloadSingle(tag);

        }
    }

    private void onItemListListener(AttachmentFileObject item){
        if (isEditMode)
        {
            if (!item.isFolder) {
                item.isSelected = !item.isSelected;
                adapter.notifyDataSetChanged();
            }
        }else {

            if (item.isFolder)
            {
                //点的事Folder

            }else {
                if (item.isDownload)
                {
                    jumpDetail(item);
                }
            }
        }

    }

    private void jumpDetail(AttachmentFileObject item) {
        if (AttachmentFileObject.isTxt(item.fileType))
        {
            final Intent intent = new Intent(this, AttachmentTexTDetailActivity.class);
            intent.putExtra(AttachmentBaseDetailActivity.ARG_ATTACHMENTOBJECT,item);
            startActivity(intent);

        }else {
            final File file = FileUtil.getDestinationInExternalPublicDir(FileSaveHelp.getSettingDownPath(this), item.getName());
            final Intent intent = new Intent(this, AttachmentsDownloadDetailActivity.class);
            intent.putExtra(AttachmentsDownloadDetailActivity.ARG_ATTACHFILEPATH, file.getAbsolutePath());
            intent.putExtra(AttachmentsDownloadDetailActivity.ARG_ATTACHMENTOBJECT, item);
            startActivity(intent);
            ToastUtils.showShort("点击是去:" + item.fileType);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //item 点击事件
        final AttachmentHeadFooter attachmentHeadFooter = objectList.get(position);
        final AttachmentFileObject item = attachmentHeadFooter.t;
        if (item!=null)
        {
            onItemListListener(item);
        }
    }

    class ProjectAttachAdapter extends HeaderAndFooterSectionQuickAdapter<AttachmentHeadFooter, BaseViewHolder> {

        public ProjectAttachAdapter(int layoutResId, int sectionHeadResId, int sectionFooterResId, List data) {
            super(layoutResId, sectionHeadResId, sectionFooterResId, data);

        }

        @Override
        protected void convertHead(BaseViewHolder helper, AttachmentHeadFooter item) {
        }

        @Override
        protected void converFooter(BaseViewHolder helper, AttachmentHeadFooter item) {

        }

        @Override
        protected void convert(BaseViewHolder helper, AttachmentHeadFooter itemParent) {
            final AttachmentFileObject item = itemParent.t;
            helper.setText(R.id.name,item.getName());
            final ImageView view = (ImageView) helper.getView(R.id.icon);
            if (item.isFolder)
            {
                helper.setImageResource(R.id.icon,R.drawable.ic_project_git_folder2);
                helper.setVisible(R.id.icon,true);
                helper.setBackgroundColor(R.id.icon,Color.TRANSPARENT);
                helper.setVisible(R.id.icon_txt,false);
                helper.setVisible(R.id.file_info_layout,false);
                helper.setText(R.id.folder_name,item.getName());
                helper.setVisible(R.id.folder_name,true);
            }else if (item.isImage())
            {
                GlideUtils.loadDefaultNoAnim(item.preview,view,false,DecodeFormat.PREFER_ARGB_8888,DiskCacheStrategy.ALL);
                helper.setVisible(R.id.icon,true);
                helper.setBackgroundRes(R.id.icon,R.drawable.shape_image_icon_bg);
                helper.setVisible(R.id.icon_txt,false);
                helper.setVisible(R.id.file_info_layout,true);
                helper.setVisible(R.id.folder_name,false);
            }else {
                GlideUtils.loadDefaultNoAnim(item.getIconResourceId(),view,false,DecodeFormat.PREFER_ARGB_8888,DiskCacheStrategy.ALL);
                helper.setVisible(R.id.icon,true);
                helper.setBackgroundRes(R.id.icon,android.R.color.transparent);
                helper.setVisible(R.id.icon_txt,false);
                helper.setVisible(R.id.file_info_layout,true);
                helper.setVisible(R.id.folder_name,false);
            }

            helper.setText(R.id.comment,Global.HumanReadableFilesize(item.getSize()));
            helper.setText(R.id.desc,String.format("发布于%s",TimeUtils.getFriendlyTimeSpanByNow(item.created_at)));
            helper.setText(R.id.username,"wuzeg");

            //分享
            if (item.isShared()) {
                helper.setVisible(R.id.shareMark,true);
            } else {
                helper.setVisible(R.id.shareMark,false);
            }

            final CheckBox checkBox = (CheckBox) helper.getView(R.id.checkbox);
            checkBox.setTag(item);
            if (isEditMode)
            {
                if (!item.isFolder)
                {
                    checkBox.setVisibility(View.VISIBLE);
                }else {
                    checkBox.setVisibility(View.INVISIBLE);
                }
                if (item.isSelected)
                {
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }

            }else {
                checkBox.setVisibility(View.GONE);
            }

            checkBox.setOnCheckedChangeListener(onCheckedChangeListener);

            if (item.downloadId!=0L)
            {
                helper.setTag(R.id.cancel,item);
                final int status = item.bytesAndStatus[2];
                if (isDownloading(status))
                {
                    if (item.bytesAndStatus[1]<0)
                    {
                        helper.setProgress(R.id.progressBar,0);
                    }else {
                        helper.setProgress(R.id.progressBar,item.bytesAndStatus[0]*100/item.bytesAndStatus[1]);
                    }
                    item.isDownload =false;
                    helper.setVisible(R.id.desc_layout,false);
                    helper.setVisible(R.id.comment,false);
                    helper.setVisible(R.id.more,false);
                    helper.setVisible(R.id.progress_layout,true);
                }else {
                    if (status == DownloadManager.STATUS_FAILED)
                    {
                        item.isDownload =false;
                        ToastUtils.showShort("下载失败");
                    }else if (status == DownloadManager.STATUS_SUCCESSFUL){
                        item.isDownload =true;
                        //可以删掉 shareprefse 里面的文件。读取IO太大。就慢了 删掉shareprefere 才能downloadid 为零
                        susscesRemoveFile(item);
                        ToastUtils.showShort("下载成功");
                    }else {
                        item.isDownload =false;
                    }
                    item.downloadId =0;

                    helper.setVisible(R.id.desc_layout,true);
                    helper.setVisible(R.id.comment,true);
                    helper.setVisible(R.id.more,true);
                    helper.setVisible(R.id.progress_layout,false);
                }
            }else {
                helper.setVisible(R.id.desc_layout,true);
                helper.setVisible(R.id.comment,true);
                helper.setVisible(R.id.more,true);
                helper.setVisible(R.id.progress_layout,false);
            }

            helper.setTag(R.id.more,item);
            helper.addOnClickListener(R.id.more);
            helper.setText(R.id.downloadFlag,item.isDownload? "查看":"下载");

            helper.setBackgroundRes(R.id.item_layout_root,item.isDownload?R.drawable.list_item_selector_project_file:R.drawable.list_item_selector);

            if (item.isFolder)
            {
                helper.setVisible(R.id.more,false);
            }else {
                helper.setVisible(R.id.more,true);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case FILE_SELECT_CODE:
                    fileSelect(data);
                    break;

                    default:
                        break;
            }
        }
    }

    //选择文件回调
    private void fileSelect(Intent data) {
        final Uri filedata = data.getData();
        final String path = FileUtil.getPath(this, filedata);
        final File selecteFile = new File(path);
        if (selecteFile!=null&&!selecteFile.exists())
        {
            //新文件
            uploadFile(selecteFile);
        }else {
            //重复文件
            showDialog(selecteFile.getName(), "是否覆盖同名文件", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    uploadFile(selecteFile);
                }
            });
        }
    }

    private void uploadFile(File selecteFile) {
        FileListHeadItem fileImte = new FileListHeadItem(this);
        listHead.addView(fileImte);
        FileListHeadItem.Param param = new FileListHeadItem.Param(selecteFile.getAbsolutePath(), selecteFile.getAbsolutePath(), selecteFile);
        fileImte.setData(param);
    }
}
