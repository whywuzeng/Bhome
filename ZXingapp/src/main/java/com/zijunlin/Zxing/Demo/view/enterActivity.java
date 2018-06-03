package com.zijunlin.Zxing.Demo.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zijunlin.Zxing.Demo.CaptureActivity;
import com.zijunlin.Zxing.Demo.R;

/**
 * Created by Administrator on 2016/3/9.
 */
public class enterActivity extends Activity implements View.OnClickListener{
    private static int REQUST_CODE = 0;
    Button button1;
    ImageView imForbac;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxenter_activity);
        tvResult=(TextView)findViewById(R.id.tv_result);
        imForbac=(ImageView)findViewById(R.id.im_forbac);
        button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(enterActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUST_CODE) {
            if (resultCode == RESULT_OK) { //putByteArray
                Bundle bundle = data.getBundleExtra("bundle");
                byte[] bitmaps = bundle.getByteArray("bitmap");
                String result = bundle.getString("result");
                tvResult.setText(result);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length);
                imForbac.setImageBitmap(bitmap);
            }
        }
    }

}
