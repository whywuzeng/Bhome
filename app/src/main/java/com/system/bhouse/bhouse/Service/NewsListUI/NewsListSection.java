package com.system.bhouse.bhouse.Service.NewsListUI;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.bhouse.bean.NeteastNewsSummary;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.sectioned.Section;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-10-18.
 */

public class NewsListSection extends Section {

    public List<NeteastNewsSummary> getNewsListBeens() {
        return newsListBeens;
    }

    private List<NeteastNewsSummary> newsListBeens;
    private Context mContext;

    public NewsListSection(@NotNull List<NeteastNewsSummary> newsListBeens, Context mContext) {
        super(R.layout.newslist_header_layout, R.layout.newslist_item_resource, R.layout.layout_home_recommend_empty, R.layout.layout_home_recommend_empty);
        this.newsListBeens = newsListBeens;
        this.mContext = mContext;
    }

    @Override
    public int getContentItemsTotal() {

        return newsListBeens == null ? 0 : newsListBeens.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        ItemResViewHolder itemResViewHolder = new ItemResViewHolder(view);
        return itemResViewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemResViewHolder) {
            ItemResViewHolder holder1 = (ItemResViewHolder) holder;
            Glide.with(mContext).load(newsListBeens.get(position).imgsrc).placeholder(R.drawable.ic_fail).into(holder1.ivNewsSummaryPhoto);
            holder1.tvNewsSummaryTitle.setText(newsListBeens.get(position).title + "");
            holder1.tvNewsSummaryDigest.setText(newsListBeens.get(position).digest + "");
            holder1.tvNewsSummaryPtime.setText(newsListBeens.get(position).ptime + "");

            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.OnItemClick(v,position);
                    }
                }
            });
        }

    }

    static class ItemResViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_news_summary_photo)
        ImageView ivNewsSummaryPhoto;
        @Bind(R.id.tv_news_summary_title)
        TextView tvNewsSummaryTitle;
        @Bind(R.id.tv_news_summary_digest)
        TextView tvNewsSummaryDigest;
        @Bind(R.id.tv_news_summary_ptime)
        TextView tvNewsSummaryPtime;
        @Bind(R.id.home_item_root_view)
        CardView homeItemRootView;

        ItemResViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeadViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        if (holder instanceof HeadViewHolder)
        {
          HeadViewHolder holder1 = (HeadViewHolder)holder;
          holder1.tvMore.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (moreClickListener!=null)
                  {
                      moreClickListener.OnMoreClick();
                  }
              }
          });
        }
    }

     static class HeadViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_more)
        TextView tvMore;

        HeadViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private onItemClickListener mOnItemClickListener;

    public interface onItemClickListener{
        void OnItemClick(View view,int position);
    }


    public void setMoreClickListener(OnMoreClickListener moreClickListener) {
        this.moreClickListener = moreClickListener;
    }

    private OnMoreClickListener moreClickListener;


   public interface OnMoreClickListener{
        void OnMoreClick();
    }
}
