package com.system.bhouse.bhouse.DingdanManage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.system.bhouse.base.BaseActivity;
import com.system.bhouse.bhouse.DingdanManage.Adapter.DingdanStateUpdateAdapter;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.AppManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.bhouse.DingdanManage
 * Author:Administrator
 * Fuction: 一个用mRecyclerView 来显示的九宫格的 来展示  订单更换状态界面
 * UpdateUser:
 * UpdateDate:
 */
public class DingdanStateUpdateActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
//    private DingManagerStaggeredAdapter mSaleAnalysisStaggeredAdapter;
    private  DingdanStateUpdateAdapter mSaleAnalysisStaggeredAdapter;
    private List<Integer> mDatas = new ArrayList<>();
    private LinearLayout fanhui_lin;

    private int[] Sin = {R.string.gongchangpaichan,R.string.jichuyanshouqueren, R.string.dingdanfahuo, R.string.dingdananzhuang, R.string.yanshoushengqiu, R.string.dingdanyanshou, R.string.yaoshishangjiao
    };

    @Override
    protected int getContentViewId() {
        return R.layout.dingdanstateupdateactivity;
    }

    @Override
    protected String getToolbarTitle() {

        return this.getResources().getString(R.string.dingdangenghuanzhuangtai);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        fanhui_lin = (LinearLayout) findViewById(R.id.fanhui_lin);
        initdata();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_stateupdatectivity);

//        mSaleAnalysisStaggeredAdapter = new DingManagerStaggeredAdapter(this, mDatas);

        mSaleAnalysisStaggeredAdapter=new DingdanStateUpdateAdapter(this,null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mSaleAnalysisStaggeredAdapter);
        // 设置item动画
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initEvent();
    }

    private void initEvent() {

        mSaleAnalysisStaggeredAdapter.setOnItemClickLitener(new DingdanStateUpdateAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(SaleAnalysisActivity.this, position + " click", Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (position) {

                    case 0:
                        intent = new Intent(DingdanStateUpdateActivity.this, OneSimpleActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(DingdanStateUpdateActivity.this, BasisActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(DingdanStateUpdateActivity.this, DingdanSellActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(DingdanStateUpdateActivity.this, DingdanClearActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(DingdanStateUpdateActivity.this, AcceptanceActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(DingdanStateUpdateActivity.this, DingdanCheckActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(DingdanStateUpdateActivity.this, KeyHandinActivity.class);
                        startActivity(intent);

                        break;
                    default:
                        Toast.makeText(DingdanStateUpdateActivity.this, "errro", 0).show();
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        fanhui_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 这里会出问题
                 */
                try {
                    AppManager.getAppManager().finishActivity(DingdanStateUpdateActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!DingdanStateUpdateActivity.this.isFinishing()) {
                        DingdanStateUpdateActivity.this.finish();
                    }
                }
            }
        });

    }

    private void initdata() {

        for (int i = 0; i < Sin.length; i++) {
            mDatas.add(Sin[i]);
        }
    }

    static class DingManagerStaggeredAdapter extends RecyclerView.Adapter<DingManagerStaggeredAdapter.MyViewHolder> {

        private LayoutInflater mInflater;
        private List<Integer> mDatas;
        private float dimension_top;
        private float dimension_right;
        private float dimension_left;
        private float dimension_bottom;
        private float dimension;
        private int[] ints = {R.drawable.hetongluru, R.drawable.dingdanchaxun, R.drawable.dingdanchaxun, R.drawable.dingdanchaxun,
                R.drawable.shouhoufenxi, R.drawable.fankuichaxun};
//        R.drawable.shigongrenyuanxinxi

        interface OnItemClickLitener {
            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        public DingManagerStaggeredAdapter(Context context, List<Integer> datas) {
            this.mDatas = datas;
            mInflater = LayoutInflater.from(context);
            Resources resources = context.getResources();
            dimension = resources.getDimension(R.dimen.a15x);
            dimension_top = resources.getDimension(R.dimen.a15x);
            dimension_left = resources.getDimension(R.dimen.a15x);
            dimension_right = resources.getDimension(R.dimen.a15x);
            dimension_bottom = resources.getDimension(R.dimen.a15x);

        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                    R.layout.item_dingdanupdate_mian, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
            ViewGroup.LayoutParams layoutParams1 = holder.fram.getLayoutParams();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(layoutParams1);
            layoutParams.setMargins((int) dimension_left, (int) dimension_top, (int) dimension_right, (int) dimension_bottom);
            if (position == 100) {
                layoutParams.width = (int) dimension;
                ViewGroup.LayoutParams layoutParams12 = holder.item_sale_rela.getLayoutParams();
                layoutParams12.width = (int) dimension;
                layoutParams12.height = (int) dimension;
                holder.item_sale_rela.setLayoutParams(layoutParams12);
            }

            holder.tv.setLayoutParams(lp);
            holder.fram.setLayoutParams(layoutParams);

            holder.tv.setText(mDatas.get(position));
            holder.iv.setImageResource(ints[position]);
            holder.fram.setBackgroundResource(R.drawable.item_bg_sale_lan);
            // 如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
//                    removeData(pos);
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public void addData(int position) {
            notifyItemInserted(position);
        }

        public void removeData(int position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;
            FrameLayout fram;
            ImageView iv;
            RelativeLayout item_sale_rela;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
                fram = (FrameLayout) view.findViewById(R.id.saleanalysis_fram);
                iv = (ImageView) view.findViewById(R.id.image_sale_item);
                item_sale_rela = (RelativeLayout) view.findViewById(R.id.item_sale_rela);
            }
        }

    }
}
