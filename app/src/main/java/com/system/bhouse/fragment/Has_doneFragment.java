package com.system.bhouse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.bhouse.bhouse.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/7.
 */
public class Has_doneFragment extends Fragment {
//    @Bind(R.id.text_title)
//    TextView textTitle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View inflate = inflater.inflate(R.layout.has_donefragment, container, false);
//        ButterKnife.bind(this, inflate);

//        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_emptyview_weinew, null);

        View root = inflater.inflate(R.layout.fragment_emptyview_weinew, container, false);
        ButterKnife.bind(this, root);

//      textTitle.setText(R.string.shopping_car);

        initTopBar();
        return root;
    }

    private void initTopBar() {
//        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                popBackStack();
//            }
//        });

//        mEmptyView.show("", "敬请期待。");

        // 切换其他情况的按钮
//        mTopBar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                showBottomSheetList();
//            }
//        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
