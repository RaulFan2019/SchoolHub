package cn.fizzo.hub.school.fc;

import okhttp3.Response;

/**
 * Created by ZhangMin on 2016/11/18.
 */

public abstract class OkHttpCallback {

    public abstract void onSuccess(Response response);

    public abstract void onFailure(String msg);

    public void onStart() {
    }
}
