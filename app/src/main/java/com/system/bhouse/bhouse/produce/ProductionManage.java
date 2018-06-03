//package com.system.bhouse.bhouse.produce;
//
//import android.content.Intent;
//import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.bzh.recycler.ExCommonAdapter;
//import com.bzh.recycler.ExRecyclerView;
//import com.bzh.recycler.ExViewHolder;
//import com.system.bhouse.bhouse.R;
//import com.system.bhouse.bhouse.produce.Startpreparation.StartpreActivity_;
//import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.EActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.ButterKnife;
//
//
///**
// * Created by Administrator on 2017-9-4.
// */
//@EActivity(R.layout.activity_productionmange)
//public class ProductionManage extends WWBackActivity {
//
//
//    private ExRecyclerView mRecyclerView;
//
//    private List<BaseInfoEntity> data;
//
//    private int[] ids={R.mipmap.dingdanguanli,R.mipmap.wodedingdan,R.mipmap.shouhouguanli,R.mipmap.diangdanmingxi,R.mipmap.diangdanmingxi};
//    private String[] strs={"","开始备料","备料完成","领料出库","完工入库"};
//
//    @AfterViews
//    protected void onCreateStart() {
//        initview();
//        initToolbar();
//        initGroupList();
//    }
//
//    private void initview() {
//
//        mRecyclerView=(ExRecyclerView)findViewById(R.id.recycler_view);
//
//    }
//
//    private void initGroupList() {
//        setData();
//
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new MyItemDecoration());
//
//
//        ExCommonAdapter adapte=  new GGExCommonAdapter(this) {
//            @Override
//            protected void convert(ExViewHolder viewHolder, BaseInfoEntity item, int position) {
//                if (item instanceof  BaseInfoEntity&&null!=viewHolder.getView(R.id.tv_film_name)) {
//                    viewHolder.setText(R.id.tv_film_name, strs[position]);
//                    viewHolder.setText(R.id.tv_film_publish_time, strs[position]);
//
//                    ( (ImageView)viewHolder.getView(R.id.xiaoshou_left_img)).setBackgroundResource(ids[position]);
//
//                }
//            }
//        };
//
//        adapte.setData(data);
//
//        BaseInfoEntity model1=new BaseInfoEntity();
//        model1.setName("生产执行管控");
//        model1.ADviewID=12;
//        adapte.setData(model1,0);
//
//        mRecyclerView.setAdapter(adapte);
//
//        adapte.setOnItemClickListener(new ExCommonAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(ExViewHolder viewHolder, int yCurrentDataIndex) {
//                Intent intent;
//                switch (yCurrentDataIndex) {
//                    case 1:
//                        StartpreActivity_.intent(ProductionManage.this).start();
//                        break;
//                    case 2:
//                        StartpreActivity_.intent(ProductionManage.this).Iscomplete("complete").start();
//                        break;
//                    case 0:
//                        break;
//                    case 3:
//                        break;
//                    case 4:
//                        break;
//                    case 5:
//                        break;
//                    default:
//                        Toast.makeText(ProductionManage.this, "error", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//
//        });
//    }
//
//    private void setData() {
//        data=new ArrayList<>();
//
//        for (int i=0;i<5;i++)
//        {
//            BaseInfoEntity infoEntity=new BaseInfoEntity();
//            infoEntity.setName("===shuju "+i);
//            data.add(infoEntity);
//        }
//
//    }
//
//    private class MyItemDecoration extends RecyclerView.ItemDecoration {
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            outRect.bottom=6;
//        }
//
//        @Override
//        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            super.onDrawOver(c, parent, state);
//        }
//
//        @Override
//        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            super.onDraw(c, parent, state);
//        }
//    }
//
//
//
//    private void initToolbar() {
//
//        setActionBarMidlleTitle("生产执行管控");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ButterKnife.unbind(this);
//    }
//}
