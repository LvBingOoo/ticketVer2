package com.xhh.ticketver2.utils.volley;

import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.hhhc.obsessive.library.base.BaseAppManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xhh.ticketver2.ui.TicketApplication;
import com.xhh.ticketver2.ui.user.LoginActivity;
import com.xhh.ticketver2.utils.FileUtil;
import com.xhh.ticketver2.utils.MLog;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Allen Lin on 2016/02/17.
 */
public class GsonRequest<T> extends Request<T> {

    private final Listener<T> mListener;
    private static Gson mGson = new Gson();
    private Class<T> mClass;
    private Map<String, String> mParams;//post Params
    private TypeToken<T> mTypeToken;


    public GsonRequest(int method, Map<String, String> params, String url, Class<T> clazz, Listener<T> listener,
                       ErrorListener errorListener) {
        super(method, url, errorListener);
        mClass = clazz;
        mListener = listener;
        mParams = params;
        setMyRetryPolicy();
    }

    public GsonRequest(int method, Map<String, String> params, String url, TypeToken<T> typeToken, Listener<T> listener,
                       ErrorListener errorListener) {
        super(method, url, errorListener);
        mTypeToken = typeToken;
        mListener = listener;
        mParams = params;
        setMyRetryPolicy();
    }

    private void setMyRetryPolicy() {
        setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    //get
    public GsonRequest(String url, Map<String, String> params ,Class<T>clazz, Listener<T> listener, ErrorListener errorListener) {
        this(Method.GET, params, url, clazz, listener, errorListener);
    }

    public GsonRequest(String url, TypeToken<T> typeToken, Listener<T> listener, ErrorListener errorListener) {

        this(Method.GET, null, url, typeToken, listener, errorListener);

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            MLog.e("response = " + jsonString);
            JSONObject jo = new JSONObject(jsonString);
            if (jo.has("msg")) {
                final String msg = jo.getString("msg");
                String tag = (String) getTag();
                if (msg.contains("登录失效")) {
                    try {
                        new Thread() {
                            public void run() {
                                Looper.prepare();
                                Toast.makeText(TicketApplication.getAppContext().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                        }.start();
                    } catch (Exception e) {
                        MLog.e(e.toString());
                    }
                    TicketApplication.TOKEN = null;
                    FileUtil.saveString(TicketApplication.getAppContext(), FileUtil.TOKEN, "");
                    Intent intent = new Intent(TicketApplication.getAppContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    TicketApplication.getAppContext().startActivity(intent);
                    BaseAppManager.getInstance().clear();
                    return Response.error(new ParseError());
                } else {
                    return (Response<T>) Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                }
            } else {
                return (Response<T>) Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
            }
//            if (mTypeToken == null)
//                return Response.success(mGson.fromJson(jsonString, mClass),
//                        HttpHeaderParser.parseCacheHeaders(response));
//            else
//                return (Response<T>) Response.success(mGson.fromJson(jsonString, mTypeToken.getType()),
//                        HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}