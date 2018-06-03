package com.zijunlin.Zxing.Demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class ResultActivity extends Activity {


    ImageView testIv;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxactivity_result);
        testIv= (ImageView) findViewById(R.id.test_iv);
        txtResult= (TextView) findViewById(R.id.txtResult);
        Intent intent = this.getIntent();
        Uri data = intent.getData();
//        Bundle extras = intent.getExtras();
//        String data= extras.getString("result");
//        Bitmap bitmap = (Bitmap)extras.getParcelable("bitmap");
        Bitmap barcode =null;
//                (Bitmap)intent.getParcelableExtra("barcode");
        initview(data, barcode);

    }

    private void initview(Uri data,Bitmap bitmap) {
        txtResult.setText(data.toString());
//        testIv.setImageBitmap(bitmap);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==1){
//            Bundle extras = data.getExtras();
//            Bitmap bitmap = (Bitmap)extras.getParcelable("bitmap");
//            String result = extras.getString("result");
//            txtResult.setText(result);
//            testIv.setImageBitmap(bitmap);
//        }
//    }
}
