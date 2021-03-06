package com.system.bhouse.bhouse.task.program;

import android.app.Activity;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.base.Global;
import com.system.bhouse.bhouse.task.Interface.InputAction;
import com.system.bhouse.bhouse.task.Interface.SimpleTextWatcher;
import com.system.bhouse.utils.MeasureUtil;

import java.lang.reflect.Method;

import static com.system.bhouse.base.App.popSoftkeyboard;

/**
 * Created by Administrator on 2017-11-17.
 */

public abstract class EnterLayout implements InputAction {

    public TextView sendText;
    public ImageButton send;
    public EditText content;
    private Activity mActivity;
    protected ViewGroup commonEnterRoot;
    protected Type mType = Type.Default;
    protected int inputBoxHeight = 0;
    protected int screenHeight;
    protected int panelHeight;
    protected boolean mEnterLayoutStatus;
    private TextWatcher restoreWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            Object tag = content.getTag();
            if (tag == null) {
                return;
            }

//            CommentBackup.getInstance().save(CommentBackup.BackupParam.create(tag), s.toString());
        }
    };


    public EnterLayout(Activity activity, View.OnClickListener sendTextOnClick, Type type) {
        mType = type;

        mActivity = activity;
        //面板高  dip2px
        panelHeight = MeasureUtil.dip2px(activity,200);
        inputBoxHeight = MeasureUtil.dip2px(activity,48);//输入框的高
        screenHeight = activity.getResources().getDisplayMetrics().heightPixels;

        commonEnterRoot = (ViewGroup) mActivity.findViewById(R.id.commonEnterRoot);

//        if (commonEnterRoot != null && commonEnterRoot.getParent() instanceof EnterLayoutAnimSupportContainer) {//行不通
//            mEnterLayoutAnimSupportContainer = (EnterLayoutAnimSupportContainer) commonEnterRoot.getParent();
//            if (activity instanceof EnterLayoutAnimSupportContainer.OnEnterLayoutBottomMarginChanagedCallBack) {
//                mEnterLayoutAnimSupportContainer.setOnEnterLayoutBottomMarginChanagedCallBack((EnterLayoutAnimSupportContainer.OnEnterLayoutBottomMarginChanagedCallBack) activity);
//            }
//        }


        sendText = (TextView) activity.findViewById(R.id.sendText);  //发送那个 按钮
        if (sendText != null) {

            sendText.setOnClickListener(sendTextOnClick);

            if (mType == Type.TextOnly) {
                sendText.setVisibility(View.VISIBLE);
            }
        }


        send = (ImageButton) activity.findViewById(R.id.send);  //就是 那个 加号 按钮
        if (mType == Type.Default) {
//            send.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mActivity instanceof CameraAndPhoto) {  //实现这个接口  photo
//                        CameraAndPhoto cameraAndPhoto = (CameraAndPhoto) mActivity;
//                        cameraAndPhoto.photo();
//                    }
//                }
//            });
        } else {
            send.setVisibility(View.GONE);
        }

        content = (EditText) activity.findViewById(R.id.comment); //edittext  编辑框
        //拦截输入法 通过点击事件触发输入法
//        if (mType != Type.TextOnly) {
//            interceptInputMethod(content);
//        }

        content.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateSendButtonStyle();
            }
        });
        content.setText("");

        //处理 表情
//        content.addTextChangedListener(new SimpleTextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (count == 1 || count == 2) {
//                    String newString = s.subSequence(start, start + count).toString();
//                    String imgName = EmojiTranslate.sEmojiMap.get(newString);
//                    if (imgName != null) {
//                        final String format = ":%s:";
//                        String replaced = String.format(format, imgName);
//
//                        Editable editable = content.getText();
//                        editable.replace(start, start + count, replaced);
//                        editable.setSpan(new EmojiconSpan(mActivity, imgName), start, start + replaced.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    }
//                } else {
//                    int emojiStart = 0;
//                    int emojiEnd;
//                    boolean startFinded = false;
//                    int end = start + count;
//                    for (int i = start; i < end; ++i) {
//                        if (s.charAt(i) == ':') {
//                            if (!startFinded) {
//                                emojiStart = i;
//                                startFinded = true;
//                            } else {
//                                emojiEnd = i;
//                                if (emojiEnd - emojiStart < 2) { // 指示的是两个：的位置，如果是表情的话，间距肯定大于1
//                                    emojiStart = emojiEnd;
//                                } else {
//                                    String newString = s.subSequence(emojiStart + 1, emojiEnd).toString();
//                                    EmojiconSpan emojiSpan = new EmojiconSpan(mActivity, newString);
//                                    if (emojiSpan.getDrawable() != null) {
//                                        Editable editable = content.getText();
//                                        editable.setSpan(emojiSpan, emojiStart, emojiEnd + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                        startFinded = false;
//                                    } else {
//                                        emojiStart = emojiEnd;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        });
    }

    private void interceptInputMethod(EditText et) {
        // Android.edittext点击时,隐藏系统弹出的键盘,显示出光标
        // 3.0以下版本可以用editText.setInputType(InputType.TYPE_NULL)来实现。
        // 3.0以上版本除了调用隐藏方法:setShowSoftInputOnFocus(false)
        int sdkInt = Build.VERSION.SDK_INT;// 16 -- 4.1系统
        if (sdkInt >= 11) {
            Class<EditText> cls = EditText.class;
            try {
                Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(et, false);
            } catch (Exception e) {
                Global.errorLog(e);
            }
        } else {
            et.setInputType(EditorInfo.TYPE_NULL);
        }
    }

    public EnterLayout(Activity activity, View.OnClickListener sendTextOnClick) {
        this(activity, sendTextOnClick, Type.Default);
    }

    public static void insertText(EditText edit, String s) {
        edit.requestFocus();
        int insertPos = edit.getSelectionStart();

        String insertString = s + " ";
        Editable editable = edit.getText();
        editable.insert(insertPos, insertString);
    }

    public void animEnterLayoutStatusChanaged(final boolean isOpen) {
        if (mEnterLayoutStatus == isOpen) {
            return;
        }
        mEnterLayoutStatus = isOpen;
//        if (commonEnterRoot != null && mEnterLayoutAnimSupportContainer != null) {
//            ValueAnimator va = ValueAnimator.ofInt(isOpen ? new int[]{-panelHeight, 0} : new int[]{0, -panelHeight});
//            va.setDuration(300);
//            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    int v = (int) animation.getAnimatedValue();
//                    mEnterLayoutAnimSupportContainer.setEnterLayoutBottomMargin(v);
//                    if (isOpen) {
//                        onEnterLayoutPopUp(v);
//                    } else {
//                        onEnterLayoutDropDown(v);
//                    }
//                }
//            });
//            va.start();
//        }
    }

    protected void onEnterLayoutPopUp(int bottom) {

    }

    protected void onEnterLayoutDropDown(int bottom) {

    }

    protected void updateEnterLayoutBottom(int bottom) {
        if (bottom == 0) {
            mEnterLayoutStatus = true;
        } else if (bottom == -panelHeight) {
            mEnterLayoutStatus = false;
        }
//        if (mEnterLayoutAnimSupportContainer != null) {
//            mEnterLayoutAnimSupportContainer.setEnterLayoutBottomMargin(bottom);
//        }
    }

    public void updateSendButtonStyle() {
        if (mType == Type.Default) {
            if (sendButtonEnable()) {
                sendText.setVisibility(View.VISIBLE);
                send.setVisibility(View.GONE);
            } else {
                sendText.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);
            }
        }

        if (sendButtonEnable()) { //又有 文字 和图片
            sendText.setBackgroundResource(R.color.green);
            sendText.setTextColor(0xffffffff);
        } else { //只有图片
            sendText.setBackgroundResource(R.color.white);
            sendText.setTextColor(0xff999999);
        }
    }

    protected boolean sendButtonEnable() {
        return content.getText().length() > 0;
    }

    public void hideKeyboard() {

        popSoftkeyboard(mActivity, content, false);
    }

    public void popKeyboard() {

        content.requestFocus();
        popSoftkeyboard(mActivity, content, true);
    }

    public void insertText(String s) {
        insertText(content, s);
    }

    public void setText(String s) {
        content.requestFocus();
        Editable editable = content.getText();
        editable.clear();
        editable.insert(0, s);
    }

    @Override
    public void insertEmoji(String s) {
        int insertPos = content.getSelectionStart();
        final String format = ":%s:";
        String replaced = String.format(format, s);

        Editable editable = content.getText();
        editable.insert(insertPos, String.format(format, s));

//        editable.setSpan(new EmojiconSpan(mActivity, s), insertPos, insertPos + replaced.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void deleteOneChar() {
        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        content.dispatchKeyEvent(event);
    }

    public void clearContent() {
        content.setText("");
    }

    public String getContent() {
        return content.getText().toString();
    }

    public void hide() {
        if (commonEnterRoot != null) {
            commonEnterRoot.setVisibility(View.GONE);
        }

    }

    public void show() {
        if (commonEnterRoot != null) {
            commonEnterRoot.setVisibility(View.VISIBLE);
        }

    }

    public boolean isShow() {
        return commonEnterRoot != null && commonEnterRoot.getVisibility() == View.VISIBLE;
    }


    public void restoreDelete(Object comment) {

//        CommentBackup.getInstance().delete(CommentBackup.BackupParam.create(comment));
    }

    public void restoreLoad(final Object object) {
        if (object == null) {
            return;
        }
//        if (commonEnterRoot != null && mEnterLayoutAnimSupportContainer != null
//                && !mEnterLayoutAnimSupportContainer.isAdjustResize()) {
//            //common_enter_emoji控件由于在某些情况下第一次进入activity恢复数据时会出现显示不正常现象，
//            // 因此先让控件以空文本形式正常显示出来之后再恢复数据
//            clearContent();
//            content.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    String lastInput = CommentBackup.getInstance().load(CommentBackup.BackupParam.create(object));
//                    content.getText().append(lastInput);
//                }
//            }, 100);
//        } else {
            clearContent();
//            String lastInput = CommentBackup.getInstance().load(CommentBackup.BackupParam.create(object));
//            content.getText().append(lastInput);
//        }

    }

    public enum Type {
        Default, TextOnly
    }

    public enum InputType {
        Text, Voice, Emoji
    }

}
