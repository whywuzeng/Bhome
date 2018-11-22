package com.system.bhouse.Common.filewidget.maopao;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.system.bhouse.Common.filewidget.resoures.AttachmentBaseDetailActivity;
import com.system.bhouse.api.manager.RetrofitManager;
import com.system.bhouse.api.manager.service.HostType;
import com.system.bhouse.bhouse.R;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    @Bind(R.id.recycler)
    RecyclerView recycler;

    @Bind(R.id.blankLayout)
    View blankLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maopao_activity);
        ButterKnife.bind(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);

        final Observable<Object> maoPaoMessage = RetrofitManager.getInstance(HostType.SYNC_GITHUB).getMaoPaoMessage();
        maoPaoMessage.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });

    }

//    class MaopaoAdapter extends BaseQuickAdapter<>
}
