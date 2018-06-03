package com.system.bhouse.bhouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.system.bhouse.adpter.SaleAnalysisGridAdapter;
import com.system.bhouse.bhouse.DingdanManage.DingdanStateUpdateActivity;
import com.system.bhouse.utils.AppManager;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-3-15.
 * 销售分析的 九宫格 布局的管理  mRecyclerView布局  现在在用 马上要换成列表了
 */
public class SaleAnalysisActivity extends AutoLayoutActivity {
    private RecyclerView mRecyclerView;
    private SaleAnalysisGridAdapter mSaleAnalysisStaggeredAdapter;
    private List<Integer> mDatas=new ArrayList<>();
    private LinearLayout  fanhui_lin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saleanalysisbule);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        fanhui_lin=(LinearLayout)findViewById(R.id.fanhui_lin);
        initdata();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_saleactivity);
//        mSaleAnalysisStaggeredAdapter = new SaleAnalysisStaggeredAdapter(this, mDatas);
        mSaleAnalysisStaggeredAdapter = new SaleAnalysisGridAdapter(this, mDatas);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mSaleAnalysisStaggeredAdapter);
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initEvent();

    }

    private void initdata() {
        for(int i=0;i<Sin.length;i++){
            mDatas.add(Sin[i]);
        }
    }

    private void initEvent() {
        mSaleAnalysisStaggeredAdapter.setOnItemClickLitener(new SaleAnalysisGridAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent;
                switch (position) {
                    case 1:
                        //合同订单录入  --订单状态更新
                        intent = new Intent(SaleAnalysisActivity.this, DingdanStateUpdateActivity.class);
                        startActivity(intent);
                        break;
                    case 2:

//                        intent=new Intent(SaleAnalysisActivity.this,ceshirecycler.class);
//                        startActivity(intent);
                        break;
                    case 0:
                        break;
                    case 3:

                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        Toast.makeText(SaleAnalysisActivity.this, "error", 0).show();
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(SaleAnalysisActivity.this,
                        position + " long click", Toast.LENGTH_SHORT).show();
            }
        });

        fanhui_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 这里会出问题
                 */
                try {
                    AppManager.getAppManager().finishActivity(SaleAnalysisActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    if(!SaleAnalysisActivity.this.isFinishing()) {
                        SaleAnalysisActivity.this.finish();
                    }
                }
            }
        });
    }


    class Myadapter extends BaseAdapter {
        private ImageView imgviewitem;
        private TextView tviditem;

        @Override
        public int getCount() {
            return ints.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.gridviewitem, null);
                holder = new ViewHolder();
                holder.imgViewitem = (ImageView) convertView.findViewById(R.id.img_viewitem);
                holder.tvIditem = (TextView) convertView.findViewById(R.id.tv_iditem);
                holder.imgViewitem.setAdjustViewBounds(false);
                holder.imgViewitem.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.imgViewitem.setPadding(8, 8, 8, 8);
                convertView.setTag(holder);
            }
            else {

                holder = (ViewHolder) convertView.getTag();
            }
            holder.imgViewitem.setImageResource(ints[position]);
            holder.tvIditem.setText(Sin[position]);
            return convertView;
        }

        private Context context;

        Myadapter(Context context) {
            this.context = context;
        }

        class ViewHolder {
            ImageView imgViewitem;
            TextView tvIditem;
        }
    }

    private int[] Sin = {R.string.dingdanguanli,R.string.dingdanzhuangtaigengxin, R.string.shouhouguanli,
          R.string.zhaopianshangchuan };
    private int[] ints = {R.drawable.caigou, R.drawable.chengben, R.drawable.xiaoshou, R.drawable.yusuan};
}
