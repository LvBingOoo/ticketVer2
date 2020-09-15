package com.xhh.ticketver2.interactor;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.TicketApplication;
import com.xhh.ticketver2.utils.MLog;
import com.xhh.ticketver2.utils.volley.VolleyManager;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class CommInteractor {

    private BaseLoadedListener<Object> mLoadedListener;

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

    public CommInteractor(BaseLoadedListener<Object> loadedListener) {
        mLoadedListener = loadedListener;
    }

    public void post(final String tag, String url, Map<String, String> params) {

        VolleyManager.newInstance().MGsonPostRequest(tag, params, url, String.class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String data) {
                        mLoadedListener.onSuccess(tag, data);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mLoadedListener.onException(error.getMessage());
                    }
                });
    }

    public void get(final String tag, String url, Map<String, String> params) {
        VolleyManager.newInstance().GsonGetRequest(tag, url, params, String.class, new Response.Listener<String>() {
            @Override
            public void onResponse(String data) {
                mLoadedListener.onSuccess(tag, data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoadedListener.onException(error.getMessage());
            }
        });
    }

    public void upLoadFile(final String requestTag, String url, File file) {
        try {
            OkHttpClient client = new OkHttpClient();
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (!TextUtils.isEmpty(TicketApplication.TOKEN)) {
                builder.addFormDataPart("token", TicketApplication.TOKEN);
            }
            builder.addFormDataPart("file", file.getName(), RequestBody.create(null, file));
            MultipartBody requestBody = builder.build();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

//            okhttp3.Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mLoadedListener.onException("Unexpected code " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        MLog.e("=====upload==body=" + body);
                        mLoadedListener.onSuccess(requestTag, body);
                    } else {
                        mLoadedListener.onException("Unexpected code222 ");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            mLoadedListener.onException("Unexpected errr ");
        }

    }
}
