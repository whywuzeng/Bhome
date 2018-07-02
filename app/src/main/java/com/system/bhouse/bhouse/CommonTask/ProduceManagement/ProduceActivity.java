package com.system.bhouse.bhouse.CommonTask.ProduceManagement;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.FinishedStorage.FinishedStorageActivity;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.PickingOutLibary.PickingOutLibaryActivity;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.PlateMaterialActivity;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.ProductionOrderActivity;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.MultipleItemQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.MultipleItem;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.ProduceItemDataBean;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.SectionMultipleItem;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.activity.InformationActivity;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.utils.MeasureUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-06-20.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.ProduceManagement
 */

public class ProduceActivity extends WWBackActivity implements BaseQuickAdapter.OnItemChildClickListener{

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private static final int RESULT_LOCAL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce);
        ButterKnife.bind(this);
        annotationDispayHomeAsUp();
        setActionBarMidlleTitle("生产工作台");
        final List<SectionMultipleItem> data = getData();
        final MultipleItemQuickAdapter multipleItemQuickAdapter = new MultipleItemQuickAdapter(this, data);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new TimeLineItemTopBottomDecoration());
        multipleItemQuickAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });
        recyclerView.setAdapter(multipleItemQuickAdapter);
        multipleItemQuickAdapter.setOnItemChildClickListener(this);

    }

    private List<SectionMultipleItem> getData(){

        List<SectionMultipleItem> list = new ArrayList<>();
        list.add(new SectionMultipleItem(true, "生产管理"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.inbox_btn_approval_normal, "生产订单"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(true, "物料管控"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.dm_btn_document_press, "领料出库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.changeteam_tip_placeholder, "完工入库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.inbox_btn_traceless_normal, "托盘配料"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(true, "工艺管理"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.more_btn_pc, "工艺执行"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(true, "养护管理"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.more_btn_forward, "养护入库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.inbox_btn_vote_normal, "养护出库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(true, "其他"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.agora_handup_on, "台车磨具分离"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.college_img_public_normal, "装柜入库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
//            list.add(new SectionMultipleItem(MultipleItem.IMG_TEXT_SPAN_SIZE, MultipleItem.TEXT_SPAN_SIZE, "content"));
//            list.add(new SectionMultipleItem(MultipleItem.TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE));
//            list.add(new SectionMultipleItem(MultipleItem.IMG_TEXT_SPAN_SIZE, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
//            list.add(new SectionMultipleItem(MultipleItem.TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
        return list;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent;
        switch (position){
            case 1:
                intent=new Intent(this, ProductionOrderActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent=new Intent(this,PickingOutLibaryActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent=new Intent(this, FinishedStorageActivity.class);
                startActivity(intent);
                break;
            case 5:
               intent= new Intent(this, PlateMaterialActivity.class);
               startActivity(intent);
                break;
        }
    }

    static class TimeLineItemTopBottomDecoration extends RecyclerView.ItemDecoration {

        private final Paint mGroutPaint;
        private final TextPaint mTextPaint;
        private final int mLeftMargin;

        interface GroupNameListener{
            String getGroupName(int pos);
        }

        public void setGroupNameListener(GroupNameListener groupNameListener) {
            this.groupNameListener = groupNameListener;
        }

        private  GroupNameListener groupNameListener;


        public void setmSpace(int mSpace) {
            this.mSpace = mSpace;
        }

        public String getGroupName(int pos){
            if (groupNameListener!=null)
            {
                groupNameListener.getGroupName(pos);
            }else {
              switch (pos){
                  case 0:
                      return "测试组名0";
                  case 1:
                      return "测试组名1";
                  case 2:
                      return "测试组名2";
                  case 3:
                      return "测试组名3";
                  case 4:
                      return "测试组名4";
                  case 5:
                      return "测试组名5";
                  case 6:
                      return "测试组名6";
                  case 7:
                      return "测试组名7";
                  case 8:
                      return "测试组名8";
                  case 9:
                      return "测试组名9";
                  case 10:
                      return "测试组名10";
                  case 11:
                      return "测试组名11";
                  case 12:
                      return "测试组名12";
                  case 13:
                      return "测试组名13";
                  case 14:
                      return "测试组名14";
                  case 15:
                      return "测试组名15";
                  default:
                      return "错误组名";
              }
            }
            return "错误组名";
        }

        /**
         * 判断是否 第一个Item 或者新组的Item
         */
        private boolean isFirstGroup(int pos){
            if (pos==0)
            {
                return true;
            }else {
                String pregroupName = getGroupName(pos);
                String currentGroupName = getGroupName(pos + 1);
                return !TextUtils.equals(pregroupName,currentGroupName);
            }
        }

        private int mSpace;

        public void setmGroupHeight(int mGroupHeight) {
            this.mGroupHeight = mGroupHeight;
        }

        private int mGroupHeight;

        public TimeLineItemTopBottomDecoration(){
            mLeftMargin =5;
            //设置悬浮栏的画笔---mGroutPaint
            mGroutPaint = new Paint();
            mGroutPaint.setColor(Color.BLUE);
            //设置悬浮栏中文本的画笔
            mTextPaint = new TextPaint();
            mTextPaint.setAntiAlias(true);
            mTextPaint.setTextSize(15);
            mTextPaint.setColor(Color.WHITE);
            mTextPaint.setTextAlign(Paint.Align.LEFT);

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (mSpace <= 0) {
                mSpace = MeasureUtil.dip2px(App.getContextApp(), 30);
            }
            int childAdapterPosition = parent.getChildAdapterPosition(view);

            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int itemCount = layoutManager.getItemCount();

            if (childAdapterPosition == 0) {
//            outRect.top = mSpace;
            }
            if (childAdapterPosition == itemCount - 1||childAdapterPosition == itemCount - 2) {
                outRect.bottom = mSpace;
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            final int itemCount = state.getItemCount();
            final int childCount = parent.getChildCount();
            final int left = parent.getLeft() + parent.getPaddingLeft();
            final int right = parent.getRight() - parent.getPaddingRight();
            String preGroupName;      //标记上一个item对应的Group
            String currentGroupName = null;       //当前item对应的Group
            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(view);
                preGroupName = currentGroupName;
                currentGroupName = getGroupName(position);
                if (currentGroupName == null || TextUtils.equals(currentGroupName, preGroupName))
                    continue;
                int viewBottom = view.getBottom();
                float top = Math.max(mGroupHeight, view.getTop());//top 决定当前顶部第一个悬浮Group的位置
                if (position + 1 < itemCount) {
                    //获取下个GroupName
                    String nextGroupName = getGroupName(position + 1);
                    //下一组的第一个View接近头部
                    if (!currentGroupName.equals(nextGroupName) && viewBottom < top) {
                        top = viewBottom;
                    }
                }
//根据top绘制group
                c.drawRect(left, top - mGroupHeight, right, top, mGroutPaint);
                Paint.FontMetrics fm = mTextPaint.getFontMetrics();
//文字竖直居中显示
                float baseLine = top - (mGroupHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
                c.drawText(currentGroupName, left + mLeftMargin, baseLine, mTextPaint);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_site,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.action_organize) {
            action_organize();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void action_organize() {
        Intent intent2=new Intent(this,InformationActivity.class);
        this.startActivityForResult(intent2,RESULT_LOCAL);
    }
}
