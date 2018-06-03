package com.system.bhouse.bhouse.task.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2017-11-29.
 */

public class TaskListHolder {

    public ImageView mIcon;
    public TextView mContent;
    private View timeLineUp;
    private View timeLineDown;

    public TaskListHolder(View convertView) {
        mIcon = (ImageView) convertView.findViewById(R.id.icon);
        mContent = (TextView) convertView.findViewById(R.id.content);
//        mContent.setMovementMethod(LongClickLinkMovementMethod.getInstance());
        timeLineUp = convertView.findViewById(R.id.timeLineUp);
        timeLineDown = convertView.findViewById(R.id.timeLineDown);
        convertView.setTag(getTagId(), this);
    }

    public static int getTagId() {
        return R.id.layout;
    }

    public void updateLine(int position, int count) {
        switch (count) {
            case 1:
                setLine(false, false);
                break;

            default:
                if (position == 0) {
                    setLine(false, true);
                } else if (position == count - 1) {
                    setLine(true, false);
                } else {
                    setLine(true, true);
                }
                break;
        }
    }

    private void setLine(boolean up, boolean down) {
        timeLineUp.setVisibility(up ? View.VISIBLE : View.INVISIBLE);
        timeLineDown.setVisibility(down ? View.VISIBLE : View.INVISIBLE);
    }
}
