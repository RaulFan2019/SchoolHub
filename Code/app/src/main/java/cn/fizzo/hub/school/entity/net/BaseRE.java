package cn.fizzo.hub.school.entity.net;

import org.xutils.http.annotation.HttpResponse;

import cn.fizzo.hub.school.network.BaseResponseParser;



/**
 * Created by Raul.Fan on 2016/11/7.
 */
@HttpResponse(parser = BaseResponseParser.class)
public class BaseRE {
    public String result;//正确返回的内容
    public int errorcode;//错误编号
    public String errormsg;//错误描述


    public BaseRE() {
    }

    public BaseRE(String result, int errorcode, String errormsg) {
        super();
        this.result = result;
        this.errorcode = errorcode;
        this.errormsg = errormsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
