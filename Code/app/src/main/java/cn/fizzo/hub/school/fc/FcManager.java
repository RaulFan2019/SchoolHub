package cn.fizzo.hub.school.fc;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.fizzo.hub.school.entity.net.GetConsoleInfoRE;
import cn.fizzo.hub.school.fc.entity.FcConnectEntity;

public class FcManager {

    private static final String TAG = "Fw";


    private static FcManager instance;//唯一实例
    private Context mContext;

    //连接列表
    private List<FcConnectEntity> fcConnectEntities = new ArrayList<>();

    private FcManager() {
    }

    /**
     * 获取堆栈管理的单一实例
     */
    public static FcManager getManager() {
        if (instance == null) {
            instance = new FcManager();
        }
        return instance;
    }


    /**
     * 初始化
     *
     * @param context
     * @return
     */
    public boolean init(Context context) {
        mContext = context;
        fcConnectEntities.clear();
        return true;
    }


    /**
     * 重新连接FC
     */
    public void reConnectFc(List<GetConsoleInfoRE.CassiaHubsBean> cassiaHubsBeans){
        for (FcConnectEntity entity : fcConnectEntities){
            entity.disConnect();
        }
        fcConnectEntities.clear();
        for (GetConsoleInfoRE.CassiaHubsBean bean : cassiaHubsBeans){
            fcConnectEntities.add(new FcConnectEntity(mContext,bean));
        }
    }

}
