package cn.fizzo.hub.school.entity.event;

/**
 * Created by Raul.fan on 2017/7/16 0016.
 * 与服务器连接状态变化事件
 */
public class ConnectedServiceStatusEE {

    public boolean connected;

    public ConnectedServiceStatusEE() {
    }

    public ConnectedServiceStatusEE(boolean connected) {
        super();
        this.connected = connected;
    }
}
