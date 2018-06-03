package com.system.bhouse.Common.message.item;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.bhouse.Common.Global;
import com.system.bhouse.Common.HtmlContent;
import com.system.bhouse.bhouse.R;

import java.util.ArrayList;

import static com.system.bhouse.base.App.SetPicassopic;

/**
 * Created by Administrator on 2017-12-29.
 */

public class ContentAreaImages extends ContentAreaBase{

    private int contentMarginBottom = 0;
    private View imageLayout0;
    private View imageLayout1;
    private  static int ImageViewMax[]= {
            R.id.image0,
            R.id.image1,
            R.id.image2,
            R.id.image3,
            R.id.image4,
            R.id.image5,
    };

    private final static int ImageViewMaxCount=ImageViewMax.length;
    private ImageView[] imageViews=new ImageView[ImageViewMaxCount];


    public ContentAreaImages(View convertView, View.OnClickListener onClickContent, Html.ImageGetter imageGetterParamer,int pxImageWidth) {
        super(convertView, onClickContent, imageGetterParamer);

        contentMarginBottom = convertView.getResources().getDimensionPixelSize(R.dimen.message_text_margin_bottom);
        imageLayout0=convertView.findViewById(R.id.imagesLayout0);
        imageLayout1=convertView.findViewById(R.id.imagesLayout1);

        for (int i=0;i<ImageViewMaxCount;i++)
        {
            imageViews[i]=(ImageView)convertView.findViewById(ImageViewMax[i]);
            imageViews[i].setOnClickListener(null);
            imageViews[i].setFocusable(false);
            imageViews[i].setLongClickable(true);
            ViewGroup.LayoutParams lp = imageViews[i].getLayoutParams();
            lp.width=pxImageWidth;
            lp.height=pxImageWidth;
        }

    }

    public void  setData(String data) {
        final Global.MessageParse maopaoData =  HtmlContent.parseMessage(data);
        if (maopaoData.text.isEmpty()) {
            content.setVisibility(View.GONE);
            content.setText("");
        }else {
//            content.setTag(MaopaoListBaseFragment.TAG_COMMENT_TEXT, maopaoData.text);

            content.setVisibility(View.VISIBLE);
            content.setText(Global.changeHyperlinkColorMaopao(maopaoData.text, imageGetter,
                    Global.tagHandler, content.getContext().getAssets()));

            content.setText( maopaoData.text);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) content.getLayoutParams();
            if (maopaoData.uris.size() > 0) {
                lp.bottomMargin = contentMarginBottom;
            } else {
                lp.bottomMargin = 0;
            }
            content.setLayoutParams(lp);
        }

//        voice_play.setVisibility(View.GONE);
//        linearLayout.setOnClickListener(null);
        content.setOnClickListener(null);
//        lp_voiceLayout.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//        lp_voiceLayout.gravity = Gravity.NO_GRAVITY;

        setImageUrl(maopaoData.uris);

    }

    private void setImageUrl(ArrayList<String> uris) {
        if (uris.size()==0){
            imageLayout0.setVisibility(View.GONE);
            imageLayout1.setVisibility(View.GONE);
        }else if (uris.size()<3)
        {
            imageLayout0.setVisibility(View.VISIBLE);
            imageLayout1.setVisibility(View.GONE);
        }else {
            imageLayout0.setVisibility(View.VISIBLE);
            imageLayout1.setVisibility(View.VISIBLE);
        }
        int min = Math.min(uris.size(), imageViews.length);
        ImageView[]  dispalyImageViews=imageViews;

        for (ImageView imageView :imageViews)
        {
            imageView.setVisibility(View.GONE);
        }

        for (int i=0;i<min;i++)
        {
            SetPicassopic(uris.get(i),dispalyImageViews[i]);
        }
    }
}
