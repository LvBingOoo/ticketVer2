package com.xhh.ticketver2.utils.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xhh.ticketver2.ui.TicketApplication;
import com.xhh.ticketver2.utils.MLog;
import com.cc.util.code.AesEncryptionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by Allen Lin on 2016/02/18.
 */
public class VolleyManager {
    private static VolleyManager mVolleyManager = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static class VolleyManagerHolder {
        private static final VolleyManager INSTANCE = new VolleyManager(TicketApplication.getAppContext());
    }

    /**
     * @param context
     */
    private VolleyManager(Context context) {

        mRequestQueue = Volley.newRequestQueue(context, new OkHttp3Stack(new OkHttpClient()));

        mImageLoader = new ImageLoader(mRequestQueue,
                new LruBitmapCache(context));
    }

    /**
     * 单例模式（静态内部类）
     *
     * @return VolleyManager instance
     */
    public static VolleyManager newInstance() {
        return VolleyManagerHolder.INSTANCE;

    }

    private <T> Request<T> add(Request<T> request) {
        return mRequestQueue.add(request);//添加请求到队列
    }

    /**
     * @param tag
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public StringRequest StrRequest(Object tag, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(url, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * @param tag
     * @param method
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public StringRequest StrRequest(Object tag, int method, String url, Response.Listener<String> listener,
                                    Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(method, url, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * ImageRequest
     *
     * @param tag
     * @param url
     * @param listener
     * @param maxWidth
     * @param maxHeight
     * @param scaleType
     * @param decodeConfig
     * @param errorListener
     * @return
     */
    public ImageRequest ImageRequest(Object tag, String url, Response.Listener<Bitmap> listener,
                                     int maxWidth, int maxHeight, ImageView.ScaleType scaleType,
                                     Bitmap.Config decodeConfig, Response.ErrorListener errorListener) {
        ImageRequest request = new ImageRequest(url, listener, maxWidth, maxHeight, scaleType,
                decodeConfig, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * ImageLoader 图片默认大小
     *
     * @param imageView
     * @param imgViewUrl
     * @param defaultImageResId
     * @param errorImageResId
     */
    public void ImageLoaderRequest(ImageView imageView, String imgViewUrl, int defaultImageResId,
                                   int errorImageResId) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId,
                errorImageResId);
        mImageLoader.get(imgViewUrl, listener);
    }


    /**
     * ImageLoader 指定图片大小
     *
     * @param imageView
     * @param imgViewUrl
     * @param defaultImageResId
     * @param errorImageResId
     * @param maxWidth
     * @param maxHeight
     */
    public void ImageLoaderRequest(ImageView imageView, String imgViewUrl, int defaultImageResId,
                                   int errorImageResId, int maxWidth, int maxHeight) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId,
                errorImageResId);
        mImageLoader.get(imgViewUrl, listener, maxWidth, maxHeight);
    }

    /**
     * Get方法
     *
     * @param tag
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     * @param <T>
     * @return
     */
    public <T> GsonRequest<T> GsonGetRequest(Object tag, String url, Map<String, String> params , Class<T> clazz, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(url,params, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * Post方式1：Map参数
     * @param tag
     * @param params
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     * @param <T>
     * @return
     */
    public <T> GsonRequest<T> GsonPostRequest(Object tag, Map<String, String> params, String url,
                                              Class<T> clazz, Response.Listener<T> listener,
                                              Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }
    public <T> GsonRequest<T> MGsonPostRequest(Object tag, Map<String, String> params, String url,
                                               Class<T> clazz, Response.Listener<T> listener,

                                               Response.ErrorListener errorListener) {
        if (!TextUtils.isEmpty(TicketApplication.TOKEN)){
            params.put("token",TicketApplication.TOKEN);
        }
        params.put("from","android");
//        Gson gson =new Gson();
//        String content = gson .toJson(params);
        MLog.e("requst ---- url="+ url+ "---content"+ params.toString());
//        content = AesEncryptionUtil.getEnAesBase64(content);
//        params.clear();
//        params.put("data",content);

        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }
    public <T> GsonRequest<T> MGsonPostRequest2(Object tag, Map<String, String> params, JSONObject jo , String url,
                                                Class<T> clazz, Response.Listener<T> listener,

                                                Response.ErrorListener errorListener) {
        params.put("from","android");
        JSONObject contentJo = new JSONObject();
        try {
            Iterator it=params.keySet().iterator();
            while(it.hasNext()){
                String key;
                String value;
                key=it.next().toString();
                value = params.get(key);
                System.out.println(key+"--"+value);
                contentJo.put(key,value);
            }
            contentJo.put("gift_data",jo);
        }catch (Exception e){
            e.printStackTrace();
        }
//        Gson gson =new Gson();
//        String content = gson .toJson(params);
        MLog.e("requst ---- url="+ url+ "---content"+ contentJo.toString());
        String content = AesEncryptionUtil.getEnAesBase64(contentJo.toString());
        params.clear();
        params.put("data",content);

        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }
    public <T> GsonRequest<T> MGsonPostJsonRequest(Object tag, JSONObject jo , String url,
                                                   Class<T> clazz, Response.Listener<T> listener,
                                                   Response.ErrorListener errorListener) {
        try {
            jo.put("from","android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Gson gson =new Gson();
//        String content = gson .toJson(params);
        MLog.e("requst ---- url="+ url+ "---content"+ jo.toString());
        String content = AesEncryptionUtil.getEnAesBase64(jo.toString());
        Map<String, String> params = new HashMap<>();
        params.put("data",content);

        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, params, url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }


    public <T> GsonRequest<T> MGsonGetRequest(Object tag, Map<String, String> params, String url,
                                               Class<T> clazz, Response.Listener<T> listener,

                                               Response.ErrorListener errorListener) {
        if (!TextUtils.isEmpty(TicketApplication.TOKEN)){
            params.put("token",TicketApplication.TOKEN);
        }
        params.put("from","android");
//        Gson gson =new Gson();
//        String content = gson .toJson(params);
        MLog.e("requst ---- url="+ url+ "---content"+ params.toString());
//        content = AesEncryptionUtil.getEnAesBase64(content);
//        params.clear();
//        params.put("data",content);

        GsonRequest<T> request = new GsonRequest<T>(Request.Method.GET, params, url, clazz, listener, errorListener);
        request.setTag(tag);
        add(request);
        return request;
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
    public void cancelAll() {
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
