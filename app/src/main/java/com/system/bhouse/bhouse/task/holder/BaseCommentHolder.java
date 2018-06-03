package com.system.bhouse.bhouse.task.holder;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.task.bean.BaseComment;
import com.system.bhouse.bhouse.task.bean.DynamicObject;

/**
 * Created by Administrator on 2017-11-27.
 */

public class BaseCommentHolder {

    private final ImageView icon;
    private final TextView name;
    private final TextView time;
    private final Html.ImageGetter imageGetter;
//    private final View mathLabButton;
    protected View layout;

    public BaseCommentHolder(View convertView, int rootLayoutId, View.OnClickListener onClickComment, Html.ImageGetter imageGetter,View.OnClickListener clickUser) {
        layout=convertView.findViewById(rootLayoutId);
        layout.setOnClickListener(onClickComment);

        icon = (ImageView)convertView.findViewById(R.id.icon);
        icon.setOnClickListener(clickUser);

        name =(TextView) convertView.findViewById(R.id.name);
        time = (TextView) convertView.findViewById(R.id.time);

        this.imageGetter =imageGetter;

//        mathLabButton = convertView.findViewById(R.id.commentWebviewDetail);
//        if (mathLabButton!=null)
//        {
//            //
//        }

    }

    public BaseCommentHolder(View convertView,View.OnClickListener onClickComment,Html.ImageGetter imageGetter,View.OnClickListener clickUser)
    {
        this(convertView,R.id.Commentlayout,onClickComment,imageGetter,clickUser);
    }

    public void setContent(Object param)
    {
        if (param instanceof BaseComment)
        {
            BaseComment comment = (BaseComment) param;
            String namestring= comment.owner.name;
            long timeParam= comment.created_at;
            String iconUri= comment.owner.avatar;

            App.SetPicassopic(iconUri,icon);
            name.setText(namestring);
            time.setText(App.dayToNow(timeParam));

        }else if (param instanceof DynamicObject.Commit)
        {
            DynamicObject.Commit commit = (DynamicObject.Commit) param;
            String namestring= commit.committer.name;
//            commit.committer.
        }

    }

}
