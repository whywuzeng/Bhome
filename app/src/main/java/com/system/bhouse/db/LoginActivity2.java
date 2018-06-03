package com.system.bhouse.db;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.view.WaveView;

/**
 * Created by Administrator on 2017-12-22.
 */

public class LoginActivity2 extends AppCompatActivity implements View.OnClickListener{

    private WaveView wave;
    private ImageView psw_visiable;
    private EditText password;
    private boolean isChecked=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yun_login);
        wave=(WaveView)findViewById(R.id.wave);
        psw_visiable=(ImageView)findViewById(R.id.psw_visiable);
        password=(EditText)findViewById(R.id.password_btn);

        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                wave, "waveShiftRatio1", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(2300);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        waveShiftAnim.start();

        psw_visiable.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.psw_visiable:

                if(isChecked){
                    //选择状态 显示明文--设置为可见的密码
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    psw_visiable.setImageResource(R.drawable.login_btn_eye_kejie);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    psw_visiable.setImageResource(R.drawable.login_btn_eye_bukejian);
                }
                isChecked=!isChecked;
                break;

        }
    }
}
