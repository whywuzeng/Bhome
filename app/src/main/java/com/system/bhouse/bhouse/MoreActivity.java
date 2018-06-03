package com.system.bhouse.bhouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcool.circletextimageview.CircleTextImageView;

/**
 * Created by Administrator on 2017-8-8.
 */

public class MoreActivity  extends AppCompatActivity {

    private RelativeLayout title;
    private RelativeLayout bdoy;
    private RelativeLayout bodyView;
    private RelativeLayout bodyLayout;
    private RelativeLayout profile;
    private RelativeLayout profileRigth;
    private RelativeLayout setting;
    private LinearLayout functionLay;
    private ImageView adrBookImg;
    private ImageView yunFileImg;
    private ImageView myActivityImg;
    private RelativeLayout share;
    private RelativeLayout recommend;
    private RelativeLayout faq;
    private RelativeLayout feedback;
    private RelativeLayout about;
    private TextView profileHost;
    private CircleTextImageView face;
    private TextView name;
    private TextView dept;
    private TextView position;
    private TextView duty;
    private TextView nodata;
    private ImageView settingIcon;
    private ImageView shareIcon;
    private ImageView recommendIcon;
    private ImageView faqIcon;
    private ImageView feedbackIcon;
    private ImageView aboutIcon;
    private ImageView profileEnter;
    private ImageView settingEnter;
    private ImageView shareEnter;
    private ImageView recommendEnter;
    private ImageView faqEnter;
    private ImageView feedbackEnter;
    private ImageView aboutEnter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        initView();
    }

    private void initView() {
        title = (RelativeLayout) findViewById(R.id.more_title);
        bdoy = (RelativeLayout) findViewById(R.id.more_bdoy);
        LayoutInflater from = LayoutInflater.from(this);
        bodyView = (RelativeLayout) from.inflate(R.layout.more_body, null);
        bodyLayout = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_layout);
        profile = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_profile_layout);
        profileRigth = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_profile_host_layout);
        setting = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_setting_layout);
        functionLay = (LinearLayout) this.bodyView.findViewById(R.id.more_function_lay);

        adrBookImg = (ImageView) this.bodyView.findViewById(R.id.address_book_img);
        yunFileImg = (ImageView) this.bodyView.findViewById(R.id.yun_file_img);
        myActivityImg = (ImageView) this.bodyView.findViewById(R.id.my_activity_img);

        share = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_share_layout);

        recommend = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_recommend_layout);
        faq = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_faq_layout);
        feedback = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_feedback_layout);
        about = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_about_layout);
        profileHost = (TextView) this.bodyView.findViewById(R.id.more_body_profile_host);
        face = (CircleTextImageView) this.bodyView.findViewById(R.id.more_body_face);
        name = (TextView) this.bodyView.findViewById(R.id.more_body_name);
        dept = (TextView) this.bodyView.findViewById(R.id.more_body_dept);
        position = (TextView) this.bodyView.findViewById(R.id.more_body_position);
        duty = (TextView) this.bodyView.findViewById(R.id.more_body_duty);

//        badgeView_profile = new BadgeView((Context) this, this.profileHost);
//        badgeView_profile.setBackgroundResource(R.drawable.list_warning_small);

//        this.bafflePlate = (RelativeLayout) from.inflate(R.layout.baffle_plate, null);
//        Helper.setProgressFor6((ProgressBar) this.bafflePlate.findViewById(R.id.progressBar1));
//        this.baffleLayout = (RelativeLayout) this.bodyView.findViewById(R.id.more_body_baffle_plate);
//        this.baffleLayout.addView(this.bafflePlate, Helper.fillparent);
//        Helper.waitingOff(this.baffleLayout);

        nodata = (TextView) this.bodyView.findViewById(R.id.more_body_nodata);
        settingIcon = (ImageView) this.bodyView.findViewById(R.id.more_body_setting_icon);
        shareIcon = (ImageView) this.bodyView.findViewById(R.id.more_body_share_icon);
        recommendIcon = (ImageView) this.bodyView.findViewById(R.id.more_body_recommend_icon);
        faqIcon = (ImageView) this.bodyView.findViewById(R.id.more_body_faq_icon);
        feedbackIcon = (ImageView) this.bodyView.findViewById(R.id.more_body_feedback_icon);
        aboutIcon = (ImageView) this.bodyView.findViewById(R.id.more_body_about_icon);
        profileEnter = (ImageView) this.bodyView.findViewById(R.id.more_body_profile_enter);
        settingEnter = (ImageView) this.bodyView.findViewById(R.id.more_body_setting_enter);
        shareEnter = (ImageView) this.bodyView.findViewById(R.id.more_body_share_enter);
        recommendEnter = (ImageView) this.bodyView.findViewById(R.id.more_body_recommend_enter);
        faqEnter = (ImageView) this.bodyView.findViewById(R.id.more_body_faq_enter);
        feedbackEnter = (ImageView) this.bodyView.findViewById(R.id.more_body_feedback_enter);
        aboutEnter = (ImageView) this.bodyView.findViewById(R.id.more_body_about_enter);

        this.bdoy.addView(this.bodyView, new ViewGroup.LayoutParams(-1, -2));
        this.face.setTextColor(-1);


    }

}
