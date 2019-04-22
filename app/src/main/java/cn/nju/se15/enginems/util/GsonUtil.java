package cn.nju.se15.enginems.util;

import android.util.Log;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by 果宝 on 2018/3/24.
 */

public class GsonUtil<T> {

    public RequestBody createRequestBody(T postBean) {
        Gson gson = new Gson();
        String postJsonStr = gson.toJson(postBean);
        Log.e("GsonUtil", postJsonStr);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postJsonStr);

        return body;
    }
}
