package com.system.bhouse.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.system.bhouse.api.ApiServiceUtils;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.lijunguan.imgselector.utils.KLog;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * AsyncTask的用法
 * <p/>
 * android提供了几种在其他线程中访问UI线程的方法。
 * Activity.runOnUiThread( Runnable )
 * View.post( Runnable )
 * View.postDelayed( Runnable, long )
 * Hanlder
 */
public class UploadFileTask extends AsyncTask<List<String>, Void, String> {
//	http://192.168.12.151:8034/Default.aspx?filename=
//	http://192.168.12.151:8070/Getupfile.aspx?filename=
//	http://192.168.12.151:8070/Getupfile.aspx?ccid=87&sid=5&username=admin&filename=
//	http://192.168.12.151:8070/Getupfile.aspx?ccid=87&sid=7&key=ruweri358063jfdi2y438&username=admin&filename=image.png

    public static final String requestURL = "http://192.168.12.151:8070/Getupfile.aspx?filename=";

    private PutStringListener listener;

    public void setPutStringListener(PutStringListener listener) {
        this.listener = listener;
    }

    /**
     * 可变长的输入参数，与AsyncTask.exucute()对应
     */
    private ProgressDialog pdialog;
    private Activity context = null;

    public String getSUCCESS() {
        return SUCCESS;
    }

    public void setSUCCESS(String SUCCESS) {
        this.SUCCESS = SUCCESS;
    }

    private String SUCCESS = "0";

    public UploadFileTask(Activity ctx) {
        this.context = ctx;
//		pdialog=ProgressDialog.show(context, "正在加载...", "系统正在处理您的请求");
        ProgressUtils.ShowProgress(context);
    }

    @Override
    protected void onPostExecute(String result) {
        // 返回HTML页面的内容
//		pdialog.dismiss();
//			Toast.makeText(context, result,Toast.LENGTH_LONG ).show();
//        ProgressUtils.DisMissProgress();
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(List<String>... params) {
        final String[] s1 = {"0"};
        final List<String> param = params[0];
        final List<String> param_url = params[1];


        if (param.size() > 0) {
            Observable.just(param).flatMap(new Func1<List<String>, Observable<String>>() {
                @Override
                public Observable<String> call(List<String> strings) {

                    return Observable.from(strings);
                }
            }).delay(1, TimeUnit.SECONDS).subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    KLog.e("上传失败了 : " + e.getLocalizedMessage());
                }

                @Override
                public void onNext(String s) {
                    File file = new File(s);


                    try {
                        FileInputStream fis;
                        fis = new FileInputStream(file);
                        long length = file.length();
                        int available = fis.available();

                        if(!(0>= App.Filesize)) {
                            if(FileSizeUtil.getFileOrFilesSize(file.getAbsolutePath(), 1) > 4194304){
                                com.socks.library.KLog.e("图片文件超出4M");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//					Observable<String> uploadImage = ApiServiceUtils.getInstence().getUpload(file, file.getName());
//					uploadImage.subscribe(new Observer<String>() {
//						@Override
//						public void onCompleted() {
//
//						}
//
//						@Override
//						public void onError(Throwable e) {
//							KLog.e("上传失败了 : " + e.getLocalizedMessage());
//						}
//
//						@Override
//						public void onNext(String s) {
//							KLog.e("上传成功了");
//							setSUCCESS("1");
//						}
//					});

                    String ccid = param_url.get(0);
                    String sid = param_url.get(1);
                    String MobileKey = param_url.get(2);
                    String username = param_url.get(3);
                    String filename=param_url.get(4);
                    

//					String s64_ccid= Base64.encodeToString(ccid.getBytes(), Base64.NO_WRAP);
//					String s64_sid = Base64.encodeToString(sid.getBytes(), Base64.NO_WRAP);
//					String s64_MobileKey = Base64.encodeToString(MobileKey.getBytes(), Base64.NO_WRAP);
                    String s64_sid_encode = null;
                    String s64_MobileKey_encode = null;
                    String s64_ccid_encode = null;
                    try {

                        s64_ccid_encode = BHEncodeUtils.BHEncode(ccid);
                        s64_sid_encode = BHEncodeUtils.BHEncode(sid);
                        s64_MobileKey_encode = BHEncodeUtils.BHEncode(MobileKey);
//						s64_ccid_encode=URLEncoder.encode(s64_ccid);
//						s64_sid_encode = URLEncoder.encode(s64_sid);
//						s64_MobileKey_encode=URLEncoder.encode(s64_MobileKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String name = file.getName();
                    String[] split = name.split("\\.");

                    //http://192.168.12.151:8070/Getupfile.aspx?ccid=ODc%3D&sid=Nw%3D%3D&key=cnV3ZXJpMzU4MDYzamZkaTJ5NDM4&username=admin&filename=image.png

//                    String url_64 = ApiServiceUtils.ENDPONIT+"Getupfile.aspx?ccid=" + s64_ccid_encode + "&sid=" + s64_sid_encode + "&key=" + s64_MobileKey_encode + "&username=" + username + "&filename="+filename+"."+split[1];
                    //取了判断的
                    String url_64 = ApiServiceUtils.ENDPONIT+"Getupfile.aspx?ccid=" + s64_ccid_encode + "&sid=" + s64_sid_encode + "&key=" + s64_MobileKey_encode + "&username=" + username + "&filename="+filename+"."+"png";

                  String filename_web= filename+"."+"png";

                    String URLFileName = requestURL + file.getName();

                    s1[0] = UploadUtils.uploadFile(file, url_64);//这里直接拼成URL链接

//                    putWebServiceNet(file, username, s64_sid_encode, s64_MobileKey_encode, s64_ccid_encode, filename_web, s1);

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.putStringFormate(s1[0]);
                        }
                    });

                }
            });
        }

//		return UploadUtils.uploadFile( file, requestURL);
        return s1[0];
    }

    /**
     * 访问webservice的网络请求
     * @param file
     * @param username
     * @param s64_sid_encode
     * @param s64_MobileKey_encode
     * @param s64_ccid_encode
     * @param filename_web
     * @param s1
     */
    private void putWebServiceNet(File file, String username, String s64_sid_encode, String s64_MobileKey_encode, String s64_ccid_encode, String filename_web, final String[] s1) {
        try {
            FileInputStream fStream = new FileInputStream(file);
            String uploadBuffer = BHEncodeUtils.BHEncode(new String(toByteArray(fStream)));
            ApiWebService.putUploadPic(context, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    com.socks.library.KLog.e(result);
                    s1[0] = result;
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.putStringFormate(s1[0]);
                        }
                    });
                }

                @Override
                public void ErrorBack(String error) {
                    s1[0] = error;
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.putStringFormate(s1[0]);
                        }
                    });

                }
            }, uploadBuffer, filename_web, s64_ccid_encode, s64_sid_encode, username, s64_MobileKey_encode);

            fStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*4];
        int n=0;
        while ( (n=in.read(buffer)) !=-1) {
            out.write(buffer,0,n);
        }
        return out.toByteArray();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }


    public interface PutStringListener {
        void putStringFormate(String s);
    }

}