package cn.fizzo.hub.school.entity.model;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/20 14:32
 */
public class PeStatus {

    public int oldStatus;
    public int newStatus;

    public PeStatus() {
    }

    public PeStatus(int oldStatus, int newStatus) {
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }
}
