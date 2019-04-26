package cn.fizzo.hub.school.entity.event;

import java.util.List;

import cn.fizzo.hub.school.entity.net.GetGroupAntInfoRE;

/**
 * Created by Raul.fan on 2018/1/5 0005.
 */

public class NetAntEE {

    public List<GetGroupAntInfoRE.AntbpmsBean> hrList;

    public NetAntEE() {
    }

    public NetAntEE(List<GetGroupAntInfoRE.AntbpmsBean> hrList) {
        super();
        this.hrList = hrList;
    }
}
