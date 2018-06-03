package com.system.bhouse.Common.message;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.system.bhouse.Common.MyImageGetter;
import com.system.bhouse.Common.message.bean.Message;
import com.system.bhouse.Common.message.item.ContentAreaImages;
import com.system.bhouse.Common.widget.MainInputView;
import com.system.bhouse.base.AccountInfo;
import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.program.FootUpdate;
import com.system.bhouse.bhouse.task.bean.UserObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2017-12-19.
 */
@EActivity(R.layout.activity_message_list)
public class MessageListActivity extends WWBackActivity implements FootUpdate.LoadMore {

    @ViewById
    MainInputView mEnterLayout;

    @ViewById
    ListView listView;

    @Extra
    UserObject mUserObject;

    View.OnClickListener mOnClickSendText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String s = mEnterLayout.getContent();

        }
    };
    private int mPxImageWidth;
    private int mPxImageDivide;
    private String mGlobalKey;

    MyImageGetter myImageGetter = new MyImageGetter(this);

    private ArrayList<Message.MessageObject> mData;
    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            Message.MessageObject item = (Message.MessageObject) getItem(position);
            if (item.sender.id == (item.friend.id)) {
                return 0;
            }
            else {
                return 1;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Message.MessageObject item = (Message.MessageObject) getItem(position);
            ViewHolder holder;
            boolean isLeft = getItemViewType(position) == 0;
            if (convertView == null) {
                int res = isLeft ? R.layout.message_list_list_item_left : R.layout.message_list_list_item_right;
                convertView = mInflater.inflate(res, parent, false);
                holder = new ViewHolder();

                holder.contentAreaImage = new ContentAreaImages(convertView, mOnClickUser, myImageGetter, mPxImageWidth);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.icon.setOnClickListener(mOnClickUser);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.resend = convertView.findViewById(R.id.resend);
                holder.sending = convertView.findViewById(R.id.sending);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            App.SetPicassopic(item.sender.avatar, holder.icon);

//            holder.icon.setTag(item.sender.global_key);

            long lasttime = item.created_at;
            if (position > 0) {
                Message.MessageObject item1 = (Message.MessageObject) getItem(position - 1);
                lasttime = item1.created_at;
            }
            long self_time = item.created_at;
            if (lessThanStandard(self_time, lasttime)) {
                holder.time.setVisibility(View.GONE);
            }
            else {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setText(App.getTimeDetail(self_time));
            }

            if (item instanceof MyMessage) {
                final MyMessage myMessage = (MyMessage) item;
                holder.resend.setVisibility(View.INVISIBLE);
                holder.sending.setVisibility(View.VISIBLE);
            }
            else {
                holder.resend.setVisibility(View.INVISIBLE);
                holder.sending.setVisibility(View.INVISIBLE);
            }
            boolean isVoice = isVoice(item);
            String data = isVoice ? item.extra : item.content;

            holder.contentAreaImage.setData(data);


            return convertView;
        }

        private boolean lessThanStandard(long selfTime, long lastTime) {
            return (selfTime - lastTime) < (30 * 60 * 1000);
        }
    };

    public boolean isVoice(Message.MessageObject item) {
        return item.extra != null && item.extra.startsWith("[voice]{") && item.extra.endsWith("}[voice]");
    }

    @AfterViews
    protected final void initMessageListActivity() {

        mEnterLayout.setClickSend(mOnClickSendText);

        final int divide = 3;
        mPxImageWidth = App.dpToPx(App.sWidthDp - 72 * 2 - divide * 2) / 3;
        mPxImageDivide = App.dpToPx(divide);

        if (mUserObject == null) {
            //这里访问 userobject 的数据
        }
        else {
            mGlobalKey = mUserObject.global_key;
            initControl();
        }
        setActionBarMidlleTitle(mUserObject.name);

    }

    private void initControl() {
        mData = AccountInfo.loadMessages(this, mUserObject.global_key);
        if (mData.isEmpty()) {
            showDialogLoading();
            loadMore();
        }

        mFootUpdate.initToHead(listView, mInflater, this);
        listView.setAdapter(adapter);
        listView.setSelection(mData.size());
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mEnterLayout.hideKeyboard();
                }
                return false;
            }
        });

        mFootUpdate.showFail();
    }



    //foot updata
    @Override
    public void loadMore() {

        Message.MessageObject messageObject=new Message.MessageObject();
        messageObject.content= "<p>测试咯，这个是中文。测试咯，这个是中文。测试咯，这个是中文。测试咯，这个是中文。</p>";
        messageObject.count=1;
        messageObject.created_at=1514975190000L;

        UserObject friend=new UserObject();
        friend.avatar="https://coding.net/static/fruit_avatar/Fruit-20.png";
        friend.global_key="zalebaba";
        friend.name="咋了吧吧";
        messageObject.friend=friend;

        UserObject send=new UserObject();
        send.avatar="https://coding.net/static/fruit_avatar/Fruit-20.png";
        send.global_key="zalebaba";
        send.name="咋了吧吧";
        messageObject.sender=send;

        mData.add(messageObject);

        AccountInfo.saveMessages(this, mUserObject.global_key,mData);

        if (!mData.isEmpty())
        {
            hideProgressDialog();

            mFootUpdate.showFail();
            listView.setAdapter(adapter);
            listView.setSelection(mData.size());
            adapter.notifyDataSetChanged();
        }

    }

    public static class MyMessage extends Message.MessageObject implements Serializable {

        public static final int STYLE_SENDING = 0;
        public static final int STYLE_RESEND = 1;

        public static final int REQUEST_TEXT = 0;
        public static final int REQUEST_IMAGE = 1;
        public static final int REQUEST_VOICE = 2;

        public int myStyle = 0;
        public int myRequestType = 0;


        public MyMessage(int requestType,  UserObject friendUser) {
            myStyle = STYLE_SENDING;

            myRequestType = requestType;

            friend = friendUser;
            sender = App.sUserObject;

            created_at = Calendar.getInstance().getTimeInMillis();
        }

        public long getCreateTime() {
            return created_at;
        }
    }

    static class ViewHolder {
        TextView time;
        ImageView icon;
        ContentAreaImages contentAreaImage;
        View resend;
        View sending;
    }
}
