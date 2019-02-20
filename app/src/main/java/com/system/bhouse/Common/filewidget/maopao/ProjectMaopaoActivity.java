package com.system.bhouse.Common.filewidget.maopao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.system.bhouse.Common.filewidget.resoures.AttachmentBaseDetailActivity;
import com.system.bhouse.Common.message.GifSpanTextView;
import com.system.bhouse.Common.message.MinSizeImageView;
import com.system.bhouse.api.manager.RetrofitManager;
import com.system.bhouse.api.manager.service.HostType;
import net.qiujuer.italker.common.adapter.BaseQuickAdapter;
import net.qiujuer.italker.common.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.GlideUtils;
import com.system.bhouse.utils.blankutils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Observer;

/**
 * Created by Administrator on 2018-11-22.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget.maopao
 */
public class ProjectMaopaoActivity extends AttachmentBaseDetailActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.blankLayout)
    View blankLayout;

    private MaopaoAdapter maopaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maopao_activity);
        ButterKnife.bind(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);


        final Observable<List<maopaoBean.DataBean>> maoPaoMessage = RetrofitManager.getInstance(HostType.SYNC_GITHUB).getMaoPaoMessage();
        maoPaoMessage.subscribe(new Observer<List<maopaoBean.DataBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<maopaoBean.DataBean> o) {
                maopaoAdapter = new MaopaoAdapter(R.layout.recycle_item_maopao, o);
                recycler.setAdapter(maopaoAdapter);
            }
        });
    }

    class MaopaoAdapter extends BaseQuickAdapter<maopaoBean.DataBean, MyMaopaoViewHolder> {

        public MaopaoAdapter(int layoutResId, @Nullable List<maopaoBean.DataBean> data) {
            super(layoutResId, data);

        }

        @Override
        protected void convert(MyMaopaoViewHolder helper, maopaoBean.DataBean item) {
            GlideUtils.loadDefaultNoAnim(item.getOwner().getAvatar(),helper.icon,false, DecodeFormat.DEFAULT, DiskCacheStrategy.ALL);
            helper.name.setText(item.getOwner().getName());
            final String timeSpanByNow = TimeUtils.getFriendlyTimeSpanByNow(item.getUpdated_at());
            helper.time.setText(timeSpanByNow);
//            helper.content.setText();
        }
    }

   static class MyMaopaoViewHolder extends BaseViewHolder {

        @BindView(R.id.icon)
        CircleImageView icon;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        GifSpanTextView content;
        @BindView(R.id.imageSingle)
        MinSizeImageView imageSingle;
        @BindView(R.id.comment)
        TextView comment;
        @BindView(R.id.delete)
        TextView delete;

        public MyMaopaoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
