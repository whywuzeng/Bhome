package com.system.bhouse.Common.message.item;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2017-12-29.
 */

public class ContentAreaBase {

    protected TextView content;
    protected Html.ImageGetter imageGetter;

    public ContentAreaBase(View convertView , View.OnClickListener onClickContent, Html.ImageGetter imageGetterParamer){
        content= (TextView)convertView.findViewById(R.id.content);
        content.setMovementMethod(LinkMovementMethod.getInstance());
        content.setOnClickListener(onClickContent);

        this.imageGetter=imageGetterParamer;
    }

    //父类的  setdata
    public void setData(String data)
    {

    }
}
