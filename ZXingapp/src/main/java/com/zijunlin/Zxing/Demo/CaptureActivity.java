package com.zijunlin.Zxing.Demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zijunlin.Zxing.Demo.Utils.ToastUtil;
import com.zijunlin.Zxing.Demo.camera.CameraManager;
import com.zijunlin.Zxing.Demo.decoding.CaptureActivityHandler;
import com.zijunlin.Zxing.Demo.decoding.InactivityTimer;
import com.zijunlin.Zxing.Demo.view.ViewfinderView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

public class CaptureActivity extends Activity implements Callback
{

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private Button captureLightBtn;
	private boolean isLightOn;
	private int positionExtra;
	public static final String TipContentTag="tipcontent";
	public static final int LongLongTime=60000000;
	private String tipContent;
	private ToastUtil mToastUtil;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zxmain);
		// ��ʼ�� CameraManager
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
				captureLightBtn= (Button) findViewById(R.id.capture_light_btn);
		 positionExtra = getIntent().getIntExtra("position",-1);
		 tipContent=getIntent().getStringExtra(TipContentTag);
		 if (TextUtils.isEmpty(tipContent))
		 {
		 	tipContent="如扫码时间过慢,请重试";
		 }
		mToastUtil=new ToastUtil();
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);

		captureLightBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isLightOn) {
//					cameraManager.setTorch(false);
					CameraManager.get().disableFlash();
					captureLightBtn.setSelected(false);
				} else {
//					cameraManager.setTorch(true);
					CameraManager.get().enableFlash();
					captureLightBtn.setSelected(true);
				}
				isLightOn = !isLightOn;
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface)
		{
			initCamera(surfaceHolder);
		}
		else
		{
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
		{
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

		if(isLightOn){
			CameraManager.get().disableFlash();
			isLightOn = !isLightOn;
		}
		mToastUtil.Long(this,tipContent).show();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		if (handler != null)
		{
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy()
	{
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder)
	{
		try
		{
			CameraManager.get().openDriver(surfaceHolder);
		}
		catch (IOException ioe)
		{
			return;
		}
		catch (RuntimeException e)
		{
			return;
		}
		if (handler == null)
		{
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		if (!hasSurface)
		{
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView()
	{
		return viewfinderView;
	}

	public Handler getHandler()
	{
		return handler;
	}

	public void drawViewfinder()
	{
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(final Result obj, final Bitmap barcode)
	{
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();

		if (!CommonUtils.isEmpty(obj.getText()) && CommonUtils.isUrl(obj.getText())) {
			Intent intent1 = new Intent(Intent.ACTION_VIEW);
			intent1.setData(Uri.parse(obj.getText()));
			startActivity(intent1);

			Intent intent = new Intent();
//				Uri content_url = Uri.parse(obj.getText());
//				intent.setData(content_url);
//                intent.putExtra("barcode",barcode);
			Bundle bundle = new Bundle();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			barcode.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] bitmapByte = baos.toByteArray();
			Log.i("TAG---------", bitmapByte.toString());
			bundle.putByteArray("bitmap", bitmapByte);
			bundle.putString("result", obj.getText());
			bundle.putInt("position",positionExtra);
			intent.putExtra("bundle", bundle);
			CaptureActivity.this.setResult(RESULT_OK, intent);
//				startActivity(intent);
			finish();

		}else {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			if (barcode == null) {
				dialog.setIcon(null);
			}
			else {

				Drawable drawable = new BitmapDrawable(barcode);
				dialog.setIcon(drawable);
			}
			dialog.setTitle("扫描结果");
			dialog.setMessage(obj.getText());
			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//��Ĭ���������ɨ��õ��ĵ�ַ
					Intent intent = new Intent();
//				Uri content_url = Uri.parse(obj.getText());
//				intent.setData(content_url);
//                intent.putExtra("barcode",barcode);
					Bundle bundle = new Bundle();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					barcode.compress(Bitmap.CompressFormat.PNG, 100, baos);
					byte[] bitmapByte = baos.toByteArray();
					Log.i("TAG---------", bitmapByte.toString());
					bundle.putByteArray("bitmap", bitmapByte);
					bundle.putString("result", obj.getText());
					bundle.putInt("position",positionExtra);
					intent.putExtra("bundle", bundle);
					CaptureActivity.this.setResult(RESULT_OK, intent);
//				startActivity(intent);

					finish();
				}
			});
			dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			final AlertDialog alertDialog = dialog.create();
//			alertDialog.show();
//这个
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {

					Intent intent = new Intent();
//				Uri content_url = Uri.parse(obj.getText());
//				intent.setData(content_url);
//                intent.putExtra("barcode",barcode);
					Bundle bundle = new Bundle();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					barcode.compress(Bitmap.CompressFormat.PNG, 100, baos);
					byte[] bitmapByte = baos.toByteArray();
					bundle.putByteArray("bitmap", bitmapByte);
					bundle.putString("result", obj.getText());
					bundle.putInt("position",positionExtra);
					intent.putExtra("bundle", bundle);
					CaptureActivity.this.setResult(RESULT_OK, intent);
//				startActivity(intent);

					finish();
				}
			},500);
		}
	}

	private void initBeepSound()
	{
		if (playBeep && mediaPlayer == null)
		{
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try
			{
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			}
			catch (IOException e)
			{
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate()
	{
		if (playBeep && mediaPlayer != null)
		{
			mediaPlayer.start();
		}
		if (vibrate)
		{
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener()
	{
		public void onCompletion(MediaPlayer mediaPlayer)
		{
			mediaPlayer.seekTo(0);
		}
	};

}