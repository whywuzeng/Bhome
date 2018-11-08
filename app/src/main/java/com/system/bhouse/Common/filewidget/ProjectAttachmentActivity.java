package com.system.bhouse.Common.filewidget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.system.bhouse.Common.filewidget.databean.AttachmentFileObject;
import com.system.bhouse.Common.filewidget.databean.AttachmentHeadFooter;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.HeaderAndFooterSectionQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.SmartRefreshBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-11-08.
 * <p>
 * by author wz
 * 文件的一级目录
 * <p>
 * com.system.bhouse.Common.filewidget
 */
public class ProjectAttachmentActivity extends SmartRefreshBaseActivity {


    @Bind(R.id.listView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_main_refresh_listview);
        ButterKnife.bind(this);
        initProjectAttachmentActivity();
    }

    private void initProjectAttachmentActivity() {

        List<AttachmentHeadFooter> objectList =new ArrayList<>();
        AttachmentHeadFooter attachmentHeadFooter ;
        attachmentHeadFooter= new AttachmentHeadFooter(true,"",true,"");
        objectList.add(attachmentHeadFooter);

         AttachmentFileObject attachmentFileObject = new AttachmentFileObject();
        attachmentFileObject.setName("产品文件");
        attachmentFileObject.isFolder=true;
        attachmentFileObject.size =200021;
          attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);
        attachmentFileObject =new AttachmentFileObject();
        attachmentFileObject.fileType="jpg";
        attachmentFileObject.preview="";//链接
        attachmentFileObject.size =200021;
         attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);
        attachmentFileObject =new AttachmentFileObject();
        attachmentFileObject.fileType="apk";
        attachmentFileObject.size =200021;
        attachmentHeadFooter = new AttachmentHeadFooter(attachmentFileObject);
        objectList.add(attachmentHeadFooter);

//        1:加载头部隔离带
        final ProjectAttachAdapter adapter = new ProjectAttachAdapter(R.layout.project_attachment_file_list_item, R.layout.divide_top_15, R.layout.divide_bottom_15, objectList);



    }

    //smartlayout 加载更多
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    class ProjectAttachAdapter extends HeaderAndFooterSectionQuickAdapter<AttachmentFileObject,BaseViewHolder>  {

        public ProjectAttachAdapter(int layoutResId, int sectionHeadResId, int sectionFooterResId, List data) {
            super(layoutResId, sectionHeadResId, sectionFooterResId, data);

        }

        @Override
        protected void convertHead(BaseViewHolder helper, AttachmentFileObject item) {
        }

        @Override
        protected void converFooter(BaseViewHolder helper, AttachmentFileObject item) {

        }

        @Override
        protected void convert(BaseViewHolder helper, AttachmentFileObject item) {
            helper.setText(R.id.name,item.getName());
            if (item.isFolder)
            {

            }
        }
    }
}
