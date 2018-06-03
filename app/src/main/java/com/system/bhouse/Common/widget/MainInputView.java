package com.system.bhouse.Common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


/**
 * Created by chenchao on 16/1/21.
 * 私信的输入框
 */

@EViewGroup(R.layout.input_view_main)
public class MainInputView extends FrameLayout implements KeyboardControl, InputOperate {

    AppCompatActivity activity;

    @ViewById
    TopBar_ topBar;

    @ViewById
    VoiceView_ voiceView;

    @ViewById
    EmojiKeyboard_ emojiKeyboard;
    private final boolean showEmojiOnly;

    public MainInputView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MainInputView);

        showEmojiOnly = array.getBoolean(R.styleable.MainInputView_showEmojiOnly, false);
        array.recycle();

        this.activity = (AppCompatActivity) getContext();
    }

    @Override
    public void setClickSend(OnClickListener click) {
        topBar.setClickSend(click);
    }

    @AfterViews
    void initMainInputView() {
        emojiKeyboard.setInputAction(topBar);
        topBar.setKeyboardControl(this);

        if (showEmojiOnly) {
            emojiKeyboard.showEmojiOnly();
        }

        showSystemInput(false);
    }

    @Override
    public void showSystemInput(boolean show) {
        App.popSoftkeyboard(activity, topBar.getEditText(), show);
        voiceView.setVisibility(GONE);
        emojiKeyboard.setVisibility(GONE);

        topBar.showSystemInput(show);
    }

    @Override
    public void showVoiceInput() {
        App.popSoftkeyboard(activity, topBar.getEditText(), false);
        voiceView.setVisibility(VISIBLE);
        emojiKeyboard.setVisibility(View.GONE);

        topBar.showVoiceInput();
    }

    @Override
    public void showEmojiInput() {
        App.popSoftkeyboard(activity, topBar.getEditText(), false);
        voiceView.setVisibility(View.GONE);
        emojiKeyboard.setVisibility(View.VISIBLE);

        topBar.showEmojiInput();
    }

    @Override
    public void hideCustomInput() {
        voiceView.setVisibility(GONE);
        emojiKeyboard.setVisibility(GONE);

        topBar.showSystemInput(false);
    }

    @Override
    public void clearContent() {
        topBar.clearContent();
    }

    @Override
    public void closeCustomKeyboard() {
        showSystemInput(false);
    }

    @Override
    public String getContent() {
        return topBar.getContent();
    }

    @Override
    public void hideKeyboard() {
        topBar.hideKeyboard();
    }

    @Override
    public void insertText(String s) {
        topBar.insertText(s);
    }

    @Override
    public boolean isPopCustomKeyboard() {
        return voiceView.getVisibility() == VISIBLE
                || emojiKeyboard.getVisibility() == VISIBLE;
    }

    @Override
    public void addTextWatcher(TextWatcher textWatcher) {
        topBar.addTextWatcher(textWatcher);
    }

    @Override
    public void setContent(String s) {
        topBar.setContent(s);
    }

    public void restoreLoad(Object object) {
        topBar.restoreLoad(object);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public boolean isShow() {
        return getVisibility() == VISIBLE;
    }

    public void restoreSaveStop() {
        topBar.restoreSaveStop();
    }

    public void restoreDelete(Object comment) {
        topBar.restoreDelete(comment);
    }

    public EditText getEditText() {
        return topBar.getEditText();
    }
}
