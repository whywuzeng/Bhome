package com.system.bhouse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.bhouse.bhouse.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-9-14.
 */

public class EmptyFragment extends Fragment {

    @BindView(R.id.iv_cen_liuhan)
    ImageView imageView;

    public String textTitle ="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_emptyview_weinew, container, false);
        ButterKnife.bind(this, root);

//        Glide.with(getActivity()).load(R.drawable.timg1)
//                .into(imageView);

        initTopBar();


        return root;

    }

    private void initTopBar() {
//        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        mEmptyView.show("", "敬请期待。");
//
//        // 切换其他情况的按钮
//        mTopBar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//
//        mTopBar.setTitle(textTitle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
