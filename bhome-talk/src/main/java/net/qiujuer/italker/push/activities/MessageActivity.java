package net.qiujuer.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.factory.model.Author;
import net.qiujuer.italker.factory.model.db.Group;
import net.qiujuer.italker.factory.model.db.Message;
import net.qiujuer.italker.factory.model.db.Session;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.model.db.view.MemberUserModel;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.R2;
import net.qiujuer.italker.push.frags.message.ChatGroupFragment;
import net.qiujuer.italker.push.frags.message.ChatUserFragment;
import net.qiujuer.italker.push.frags.message.ISelfMessageListener;
import net.qiujuer.italker.utils.BitmapWaterMarkUtil;

import butterknife.BindView;

public class MessageActivity extends Activity implements ISelfMessageListener {
    // 接收者Id，可以是群，也可以是人的Id
    public static final String KEY_RECEIVER_ID = "KEY_RECEIVER_ID";
    // 是否是群
    private static final String KEY_RECEIVER_IS_GROUP = "KEY_RECEIVER_IS_GROUP";

    private String mReceiverId;
    private boolean mIsGroup;

    @BindView(R2.id.iv_bg)
    ImageView ivBackgroud;
    /**
     * 通过Session发起聊天
     *
     * @param context 上下文
     * @param session Session
     */
    public static void show(Context context, Session session) {
        if (session == null || context == null || TextUtils.isEmpty(session.getId()))
            return;
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, session.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP, session.getReceiverType() == Message.RECEIVER_TYPE_GROUP);
        context.startActivity(intent);
    }

    /**
     * 显示人的聊天界面
     *
     * @param context 上下文
     * @param author  人的信息
     */
    public static void show(Context context, Author author) {
        if (author == null || context == null || TextUtils.isEmpty(author.getId()))
            return;
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, author.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP, false);
        context.startActivity(intent);
    }

    /**
     * 发起群聊天
     *
     * @param context 上下文
     * @param group   群的Model
     */
    public static void show(Context context, Group group) {
        if (group == null || context == null || TextUtils.isEmpty(group.getId()))
            return;
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, group.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP, true);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }


    @Override
    protected boolean initArgs(Bundle bundle) {
        mReceiverId = bundle.getString(KEY_RECEIVER_ID);
        mIsGroup = bundle.getBoolean(KEY_RECEIVER_IS_GROUP);
        return !TextUtils.isEmpty(mReceiverId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        AndroidBug5497Workaround.assistActivity(this);
        setTitle("");
        Fragment fragment;
        if (mIsGroup) {
            fragment = new ChatGroupFragment();
            ChatGroupFragment groupFragment = (ChatGroupFragment) fragment;
            groupFragment.setSelfUserListener(this);
        }
        else {
            fragment = new ChatUserFragment();
            ChatUserFragment userFragment = (ChatUserFragment) fragment;
            userFragment.setSelfUserListener(this);
        }

        // 从Activity传递参数到Fragment中去
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RECEIVER_ID, mReceiverId);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, fragment)
                .commit();
    }

    @Override
    public void onSelfUserListener(Object user) {
        BitmapDrawable bitmapDrawable = null;
        if (user instanceof User) {
            bitmapDrawable = BitmapWaterMarkUtil.BitmapDrawableWithBitmap(((User) user).getName(), this);
        }
        else if (user instanceof MemberUserModel) {
            bitmapDrawable = BitmapWaterMarkUtil.BitmapDrawableWithBitmap(((MemberUserModel) user).name ,this);
        }
        ivBackgroud.setBackgroundDrawable(bitmapDrawable);
    }
}
