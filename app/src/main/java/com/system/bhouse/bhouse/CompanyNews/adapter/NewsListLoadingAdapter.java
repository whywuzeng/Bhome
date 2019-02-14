package com.system.bhouse.bhouse.CompanyNews.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.system.bhouse.bean.NeteastNewsSummary;
import com.system.bhouse.bhouse.CommonTask.Widget.LoadingAdapter;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt.adapter
 */

public class NewsListLoadingAdapter extends LoadingAdapter<NeteastNewsSummary> {


    private List<NeteastNewsSummary> mComTaskBeans = new ArrayList<>();

    public NewsListLoadingAdapter(List<NeteastNewsSummary> mComTaskBeans) {
        this.mComTaskBeans = mComTaskBeans;
    }



    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_summary, parent, false));

    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

            ItemViewHolder  holder1=(ItemViewHolder)holder;

            GlideUtils.loadDefault(mComTaskBeans.get(position).imgsrc, holder1.ivNewsSummaryPhoto, null, null, DiskCacheStrategy.RESULT);
            holder1.tvNewsSummaryTitle.setText(mComTaskBeans.get(position).title);
            holder1.tvNewsSummaryDigest.setText(mComTaskBeans.get(position).digest);
            holder1.tvNewsSummaryPtime.setText(mComTaskBeans.get(position).ptime);
        }
    }


    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private onItemClickListener mOnItemClickListener;


    public interface onItemClickListener {
        void ItemClick(ItemViewHolder holder, int position);
    }

    static  class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_news_summary_photo)
        ImageView ivNewsSummaryPhoto;
        @BindView(R.id.tv_news_summary_title)
        TextView tvNewsSummaryTitle;
        @BindView(R.id.tv_news_summary_digest)
        TextView tvNewsSummaryDigest;
        @BindView(R.id.tv_news_summary_ptime)
        TextView tvNewsSummaryPtime;
        @BindView(R.id.home_item_root_view)
        CardView homeItemRootView;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


