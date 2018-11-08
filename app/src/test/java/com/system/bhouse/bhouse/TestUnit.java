package com.system.bhouse.bhouse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.system.bhouse.api.manager.ExceptionConverter.AbsAPICallback;
import com.system.bhouse.api.manager.ExceptionConverter.ApiException;
import com.system.bhouse.api.manager.ExceptionConverter.MyGsonConverterFactory;
import com.system.bhouse.api.manager.RetrofitManager;
import com.system.bhouse.bhouse.Bean.NewsModel;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.MaintenanceOutWarehouse.Bean.MaintenanceOutWarehouseBean;
import com.system.bhouse.bhouse.SingleList.SingleList;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2018-07-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse
 */

public class TestUnit {
    @Test
    public void UnitMain(){
        // 添加

        SingleList<String> stringSingleList = new SingleList<>();
        stringSingleList.add(new SingleList.Node("111",null));
        stringSingleList.add(new SingleList.Node("222",null));
        stringSingleList.add(new SingleList.Node("555",null));
        stringSingleList.add(new SingleList.Node("333",null));
        stringSingleList.add(new SingleList.Node("555",null));
        stringSingleList.add(new SingleList.Node("444",null));
        stringSingleList.add(new SingleList.Node("555",null));
        //尾部插入

        //删除是index 2 索引
//        stringSingleList.remove(2);

//        for (int i=0;i<stringSingleList.size;i++)
//        {
//            System.out.println(stringSingleList.get(i).toString());
//        }
//
//        Stack<SingleList.Node> objects = new Stack<SingleList.Node>();
//
//        for (int i=0;i<stringSingleList.size;i++)
//        {
//            objects.push(stringSingleList.get(i));
//        }
//
//        while (objects.peek()!=null)
//        {
//            System.out.println(objects.pop().toString());
//        }

//        stringSingleList.deleteDuplication();

//        SingleList.Node node = stringSingleList.fanzhuan3();

        getSimpleDataString("2018/06/28 09:09:29");
    }

    //"yyyy-MM-dd HH:mm:ss"

    private static final String[] SimpleDataString={"yyyy","MM","dd","HH","mm","ss"};

    public static String getSimpleDataString(final String time)
    {
        String time1=time;
//        2018-06-28 09:09:29
        String regEx="[^0-9]";
        String[] split = time.split(regEx);
        split[0]=SimpleDataString[0];
        split[1]=SimpleDataString[1];
        split[2]=SimpleDataString[2];
        split[3]=SimpleDataString[3];
        split[4]=SimpleDataString[4];
        split[5]=SimpleDataString[5];

        String regEx1="[!^0-9]";
        String regEx2="[0-9]";
        String[] split1 = time.split(regEx2);

        List<String> tmp = new ArrayList<String>();
        for(String str:split1){
            if(str!=null && str.length()!=0){
                tmp.add(str);
            }
        }
        split1= tmp.toArray(new String[0]);

        // 4  6  10  12
        String s = split[0] + split1[4] + split[1] + split1[6] + split[2] + " " + split[3] + split1[10] + split[4] + split1[12] + split[5];
        return s;
    }

    @Test
    public void test12() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<String> list = new ArrayList<>();
        list.add("wuzeng");

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(123);

        boolean b = list.getClass() == integers.getClass();

        ArrayList<Integer> integers1=new ArrayList<>();
        integers1.add(1);

        integers1.getClass().getMethod("add",Object.class).invoke(integers1,"abc");
    }

    public class FF11{
        public void method1(){
            System.out.println("");
        }
    }

    class MainClass<T extends FF11>{
        private T obj;
        public MainClass(T x){
            this.obj=x;
        }
        public void Opearte(){
            obj.method1();
        }
    }

    @Test
    public void methodtest(){

        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new JsonDeserializer<List<?>>() {
            @Override
            public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if (json.isJsonArray()) {
                    JsonArray array = json.getAsJsonArray();

                    JsonStrTrim(array);

                    Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                    List list = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        //如何处理element 字段
                        JsonElement element = array.get(i);
                        if (element.isJsonObject())
                        {

                        }
                        Object item = context.deserialize(element, itemType);
                        list.add(item);
                    }
                    return list;
                } else {
                    //和接口类型不符，返回空List
                    return Collections.EMPTY_LIST;
                }
            }
        }).create();


        String result="[{" +
                "\"仓库\":\"养护窑002库位\"," +
                "\"仓库ID\":\"d88aade93adb435bad0911e712cc59cc\"," +
                "\"养护开始时间\":\"2018/8/7 15:04:49              \"," +
                "\"养护结束时间\":\"2018/8/7 15:54:49\"" +
                "}]";


        String trim = result.trim();

        ArrayList<MaintenanceOutWarehouseBean> loadingcarbean = gson.fromJson(trim, new TypeToken<List<MaintenanceOutWarehouseBean>>() {
        }.getType());

    }

    public JsonArray JsonStrTrim(JsonArray arr){

        if( arr != null && arr.size() > 0){
                 JsonObject obj=null;
            for (int i = 0; i < arr.size(); i++) {

                 obj = (JsonObject) arr.get(i);
                // 取出 jsonObject 中的字段的值的空格
                Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
                for (Map.Entry<String,JsonElement> entry:entries)
                {
                    String key = entry.getKey();
                    String value= entry.getValue().getAsString();

                    if(value == null){
                        continue ;
                    }else if("".equals(value.trim())){
                        continue ;
                    }else{
                        obj.addProperty(key, value.replace(" ", ""));
                    }
                }
            }
        }
        return arr;
    }


//    http://localhost:3003/api/news

    @Test
    public void Apitest(){
        ApiService service = ApiService.Factory.createService();
        service.request1("news")
                .subscribe(new AbsAPICallback<NewsModel>("网络错误","解析错误","未知错误") {
                    @Override
                    protected void onError(ApiException ex) {

                    }

                    @Override
                    protected void onPermissionError(ApiException ex) {

                    }

                    @Override
                    protected void onResultError(ApiException ex) {

                    }

                    @Override
                    public void onNext(NewsModel newsModel) {

                    }
                });


    }

    private static volatile OkHttpClient sOkHttpClient;

    // 打印返回的json数据拦截器
    private static Interceptor  mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            request = requestBuilder.build();

            final Response response = chain.proceed(request);

            KLog.e("请求网址: \n" + request.url() + " \n " + "请求头部信息：\n" + request.headers() + "响应头部信息：\n" + response.headers());

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e("");
                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
                KLog.v("--------------------------------------------开始打印返回数据----------------------------------------------------");
                KLog.json(buffer.clone().readString(charset));
                KLog.v("--------------------------------------------结束打印返回数据----------------------------------------------------");
            }

            return response;
        }
    };

    // 配置OkHttpClient
    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (sOkHttpClient == null) {
                    // OkHttpClient配置是一样的,静态创建一次即可
                    // 指定缓存路径,缓存大小100Mb

                    sOkHttpClient = new OkHttpClient.Builder()

                            .connectTimeout(30, TimeUnit.SECONDS).build();

                }
            }
        }
        return sOkHttpClient;
    }


    //ApiService.java
    public interface ApiService {

        @retrofit2.http.GET("{id}")
        Observable<NewsModel> request1(@Path("id") String request);

//        @POST("app/api")
//        Observable<Response2> request2(@Body Request2 request);
        /**
         * Create a new ApiService
         */
        class Factory {
            private Factory() {  }

            public static ApiService createService( ) {
                getOkHttpClient();
                Retrofit build = new Retrofit.Builder().baseUrl("http://localhost:3003/api" + "/")
                        .addConverterFactory(MyGsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(sOkHttpClient)
                        .build();

                return build.create(ApiService.class);
            }
        }
    }
}
