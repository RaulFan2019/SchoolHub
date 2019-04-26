package cn.fizzo.hub.school.ui.activity.pe;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.config.SPEnums;
import cn.fizzo.hub.school.config.UrlConfig;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.entity.event.ConnectedServiceStatusEE;
import cn.fizzo.hub.school.entity.model.PeStatus;
import cn.fizzo.hub.school.entity.net.BaseRE;
import cn.fizzo.hub.school.entity.net.GetPeStatusRE;
import cn.fizzo.hub.school.network.BaseResponseParser;
import cn.fizzo.hub.school.network.HttpExceptionHelper;
import cn.fizzo.hub.school.network.RequestParamsBuilder;
import cn.fizzo.hub.school.ui.activity.BaseActivity;
import cn.fizzo.hub.school.ui.fragment.pe.BasePeFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeBlankFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeClazzFourFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeClazzOneFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeClazzSixFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeClazzTwoFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeClockFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeReportFourFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeReportOneFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeReportSixFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeReportTwoFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeStudentOneFragment;
import cn.fizzo.hub.school.ui.fragment.pe.PeWallOneFragment;
import cn.fizzo.hub.school.utils.LogU;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/18 12:14
 * 体育课页面
 */
public class PeActivity extends BaseActivity {

    private static final String TAG = "PeActivity";

    /* msg */
    private static final int MSG_UPDATE_PE_INFO = 0x01;                 //请求更新课程信息
    private static final int MSG_GET_NEW_PE_INFO = 0x02;                //获取到新的课程信息

    private static final int INTERVAL_UPDATE_PE_INFO = 2 * 1000;        //更新课程的时间间隔


    private static final int STATUS_BLANK = 0x00;                       //无课状态
    private static final int STATUS_ALL_HR = 0x01;                      //显示心率墙
    private static final int STATUS_CLAZZ = 0x02;                       //显示班级
    private static final int STATUS_ONLY_ONE = 0x03;                    //显示单个学生
    private static final int STATUS_REPORT = 0x04;                      //显示报告

    /* view */
    @BindView(R.id.ll_top_left)
    LinearLayout llTopLeft;
    @BindView(R.id.ll_top_middle)
    LinearLayout llTopMiddle;
    @BindView(R.id.ll_top_right)
    LinearLayout llTopRight;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_bottom_left)
    LinearLayout llBottomLeft;
    @BindView(R.id.ll_bottom_middle)
    LinearLayout llBottomMiddle;
    @BindView(R.id.ll_bottom_right)
    LinearLayout llBottomRight;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    /* local data */
    private String mLastUpdateTime = "1970-12-19 10:16:00";               //课程核对时间


    /* lesson status */
    private List<GetPeStatusRE.LessonsBean> listPe = new ArrayList<>();   //课程情况列表
    private List<PeStatus> listStatus = new ArrayList<>();                //课程状态列表
    private int mCurrLessonSize = 0;                                      //当前正在上课的班级数量

    private BasePeFragment fragmentPe1;                                   //第一个班级的页面
    private BasePeFragment fragmentPe2;                                   //第二个班级的页面
    private BasePeFragment fragmentPe3;                                   //第三个班级的页面
    private BasePeFragment fragmentPe4;                                   //第四个班级的页面
    private BasePeFragment fragmentPe5;                                   //第五个班级的页面
    private BasePeFragment fragmentPe6;                                   //第六个班级的页面

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pe;
    }

    @Override
    protected void myHandleMsg(Message msg) {
        switch (msg.what) {
            case MSG_UPDATE_PE_INFO:
                getUpdateChecking();
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_PE_INFO, INTERVAL_UPDATE_PE_INFO);
                break;
            case MSG_GET_NEW_PE_INFO:
                peStatusChanged();
                updateViewsByPeStatusChanged();
                break;
        }
    }

    @Override
    protected void initData() {
        //初始化6个班级的状态列表
        for (int i = 0; i < 6; i++) {
            listStatus.add(new PeStatus());
        }
    }

    @Override
    protected void initViews() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragmentPe1 = new PeClockFragment();
        transaction.replace(R.id.ll_top_left, fragmentPe1);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void doMyCreate() {
        SPDataApp.setLastPageBeforeCrash(PeActivity.this, SPEnums.PAGE_SPORT_PE);
        mHandler.sendEmptyMessage(MSG_UPDATE_PE_INFO);
    }

    @Override
    protected void causeGC() {
    }

    /**
     * 课程状态发生改变更新页面
     */
    private void updateViewsByPeStatusChanged() {
        boolean sizeChanged = listPe.size() == mCurrLessonSize;

        //处理第一个班级 , 数量发生改变，或状态发生改变
        if (sizeChanged
                || listStatus.get(0).oldStatus != listStatus.get(0).newStatus) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            if (listPe.size() > 0) {
                bundle.putInt("lessonId", listPe.get(0).id);
                bundle.putString("clazzName", listPe.get(0).class_name);
                bundle.putSerializable("students", (Serializable) listPe.get(0).students);
            }
            //6屏状态
            if (listPe.size() > 4) {
                if (listStatus.get(0).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(0).planned_duration);
                    fragmentPe1 = new PeReportSixFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(0).planned_duration);
                    fragmentPe1 = new PeClazzSixFragment();
                }
                //4屏状态
            } else if (listPe.size() > 2) {
                if (listStatus.get(0).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(0).planned_duration);
                    fragmentPe1 = new PeReportFourFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(0).planned_duration);
                    fragmentPe1 = new PeClazzFourFragment();
                }
            } else if (listPe.size() == 2) {
                if (listStatus.get(0).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(0).planned_duration);
                    fragmentPe1 = new PeReportTwoFragment();
                } else {
                    fragmentPe1 = new PeClazzTwoFragment();
                }
            } else if (listPe.size() == 1) {
                switch (listStatus.get(0).newStatus) {
                    case STATUS_BLANK:
                        fragmentPe1 = new PeClockFragment();
                        break;
                    case STATUS_ALL_HR:
                        fragmentPe1 = new PeWallOneFragment();
                        bundle.putSerializable("segments", (Serializable) listPe.get(0).segments);
                        break;
                    case STATUS_CLAZZ:
                        fragmentPe1 = new PeClazzOneFragment();
                        bundle.putSerializable("delegatesNumber", (Serializable) listPe.get(0).delegates_number);
                        bundle.putInt("plannedDuration", listPe.get(0).planned_duration);
                        break;
                    case STATUS_ONLY_ONE:
                        fragmentPe1 = new PeStudentOneFragment();
                        bundle.putSerializable("student", listPe.get(0).students.get(listPe.get(0).select_number - 1));
                        bundle.putInt("plannedDuration", listPe.get(0).planned_duration);
                        break;
                    case STATUS_REPORT:
                        fragmentPe1 = new PeReportOneFragment();
                        bundle.putInt("plannedDuration", listPe.get(0).planned_duration);
                        break;
                }
            } else {
                fragmentPe1 = new PeClockFragment();
            }
            transaction.replace(R.id.ll_top_left, fragmentPe1);
            transaction.commitAllowingStateLoss();
            fragmentPe1.setArguments(bundle);
        }
        //处理第二个班级
        if (sizeChanged || listStatus.get(1).newStatus != listStatus.get(1).oldStatus) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            if (listPe.size() > 1) {
                bundle.putInt("lessonId", listPe.get(1).id);
                bundle.putString("clazzName", listPe.get(1).class_name);
                bundle.putSerializable("students", (Serializable) listPe.get(1).students);
            }
            //6屏状态
            if (listPe.size() > 4) {
                if (listStatus.get(1).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(1).planned_duration);
                    fragmentPe2 = new PeReportSixFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(1).planned_duration);
                    fragmentPe2 = new PeClazzSixFragment();
                }
                //4屏状态
            } else if (listPe.size() > 2) {
                if (listStatus.get(1).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(1).planned_duration);
                    fragmentPe2 = new PeReportFourFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(1).planned_duration);
                    fragmentPe2 = new PeClazzFourFragment();
                }
            } else if (listPe.size() == 2) {
                if (listStatus.get(1).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(1).planned_duration);
                    fragmentPe2 = new PeReportTwoFragment();
                } else {
                    fragmentPe2 = new PeClazzTwoFragment();
                }
            } else {
                fragmentPe2 = new PeBlankFragment();
            }
            transaction.replace(R.id.ll_top_middle, fragmentPe2);
            transaction.commitAllowingStateLoss();
            fragmentPe2.setArguments(bundle);

        }
        //处理第三个班级
        if (sizeChanged || listStatus.get(2).newStatus != listStatus.get(2).oldStatus) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            if (listPe.size() > 2) {
                bundle.putInt("lessonId", listPe.get(2).id);
                bundle.putString("clazzName", listPe.get(2).class_name);
                bundle.putSerializable("students", (Serializable) listPe.get(2).students);
            }
            //6屏状态
            if (listPe.size() > 4) {
                if (listStatus.get(2).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(2).planned_duration);
                    fragmentPe3 = new PeReportSixFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(2).planned_duration);
                    fragmentPe3 = new PeClazzSixFragment();
                }
                //4屏状态
            } else if (listPe.size() > 2) {
                if (listStatus.get(2).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(2).planned_duration);
                    fragmentPe3 = new PeReportFourFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(2).planned_duration);
                    fragmentPe3 = new PeClazzFourFragment();
                }
            } else {
                fragmentPe3 = new PeBlankFragment();
            }
            transaction.replace(R.id.ll_bottom_left, fragmentPe3);
            transaction.commitAllowingStateLoss();
            fragmentPe3.setArguments(bundle);
        }
        //处理第四个班级
        if (sizeChanged || listStatus.get(3).newStatus != listStatus.get(3).oldStatus) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            if (listPe.size() > 3) {
                bundle.putInt("lessonId", listPe.get(3).id);
                bundle.putString("clazzName", listPe.get(3).class_name);
                bundle.putSerializable("students", (Serializable) listPe.get(3).students);
            }
            //6屏状态
            if (listPe.size() > 4) {
                if (listStatus.get(3).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(3).planned_duration);
                    fragmentPe4 = new PeReportSixFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(3).planned_duration);
                    fragmentPe4 = new PeClazzSixFragment();
                }
                //4屏状态
            } else if (listPe.size() > 3) {
                if (listStatus.get(3).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(3).planned_duration);
                    fragmentPe4 = new PeReportFourFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(3).planned_duration);
                    fragmentPe4 = new PeClazzFourFragment();
                }
            } else {
                fragmentPe4 = new PeBlankFragment();
            }
            transaction.replace(R.id.ll_bottom_middle, fragmentPe4);
            transaction.commitAllowingStateLoss();
            fragmentPe4.setArguments(bundle);
        }
        //处理第五个班级
        if (sizeChanged || listStatus.get(4).newStatus != listStatus.get(4).oldStatus) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            if (listPe.size() > 4) {
                bundle.putInt("lessonId", listPe.get(4).id);
                bundle.putString("clazzName", listPe.get(4).class_name);
                bundle.putSerializable("students", (Serializable) listPe.get(4).students);
            }
            //6屏状态
            if (listPe.size() > 4) {
                if (listStatus.get(4).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(4).planned_duration);
                    fragmentPe5 = new PeReportSixFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(4).planned_duration);
                    fragmentPe5 = new PeClazzSixFragment();
                }
            } else {
                fragmentPe5 = new PeBlankFragment();
            }
            transaction.replace(R.id.ll_top_right, fragmentPe5);
            transaction.commitAllowingStateLoss();
            fragmentPe5.setArguments(bundle);
        }
        //处理第六个班级
        if (sizeChanged || listStatus.get(5).newStatus != listStatus.get(5).oldStatus) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            if (listPe.size() > 5) {
                bundle.putInt("lessonId", listPe.get(5).id);
                bundle.putString("clazzName", listPe.get(5).class_name);
                bundle.putSerializable("students", (Serializable) listPe.get(5).students);
            }
            //6屏状态
            if (listPe.size() > 5) {
                if (listStatus.get(5).newStatus == STATUS_REPORT) {
                    bundle.putInt("plannedDuration", listPe.get(5).planned_duration);
                    fragmentPe6 = new PeReportSixFragment();
                } else {
                    bundle.putInt("plannedDuration", listPe.get(5).planned_duration);
                    fragmentPe6 = new PeClazzSixFragment();
                }
            } else {
                fragmentPe6 = new PeBlankFragment();
            }
            transaction.replace(R.id.ll_bottom_right, fragmentPe6);
            transaction.commitAllowingStateLoss();
            fragmentPe6.setArguments(bundle);
        }

        mCurrLessonSize = listPe.size();
        for (PeStatus status : listStatus) {
            status.oldStatus = status.newStatus;
        }
    }

    /**
     * 课程状态发生改变
     */
    private void peStatusChanged() {
        //遍历所有班级的状态
        for (int i = 0; i < listStatus.size(); i++) {
            if (i < listPe.size()) {
                listStatus.get(i).newStatus = listPe.get(i).page_status;
            } else {
                listStatus.get(i).newStatus = STATUS_BLANK;
            }
        }
        //课程数量发生改变,页面隐藏发生改变
        //没有班级上课或只有1个班级上课
        if (listPe.size() == 0 || listPe.size() == 1) {
            llBottom.setVisibility(View.GONE);
            llTopMiddle.setVisibility(View.GONE);
            llTopRight.setVisibility(View.GONE);
            return;
        }
        llTopMiddle.setVisibility(View.VISIBLE);
        //有2个班级在上课
        if (listPe.size() == 2) {
            llBottom.setVisibility(View.GONE);
            llTopRight.setVisibility(View.GONE);
            return;
        }
        llBottom.setVisibility(View.VISIBLE);
        llBottomLeft.setVisibility(View.VISIBLE);
        //有3个或4个班级在上课
        if (listPe.size() == 3 || listPe.size() == 4) {
            llTopRight.setVisibility(View.GONE);
            llBottomRight.setVisibility(View.GONE);
            if (listPe.size() == 3) {
                llBottomMiddle.setVisibility(View.INVISIBLE);
            } else {
                llBottomMiddle.setVisibility(View.VISIBLE);
            }
            return;
        }
        llTopRight.setVisibility(View.VISIBLE);
        llBottomMiddle.setVisibility(View.VISIBLE);
        if (listPe.size() == 5) {
            llBottomRight.setVisibility(View.INVISIBLE);
        } else {
            llBottomRight.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 校验时间信息
     */
    private void getUpdateChecking() {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                String url = SPDataApp.getServiceIp(PeActivity.this) + UrlConfig.URL_GET_PE_STATUS_INFO;
                RequestParams params = RequestParamsBuilder.buildGetPeStatusRP(PeActivity.this,
                        url, 1);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
//                            LogU.v(TAG,"getUpdateChecking:" + result.result);
                            GetPeStatusRE peStatusRE = JSON.parseObject(result.result, GetPeStatusRE.class);
                            //需要获取最新信息
                            if (!peStatusRE.update_time.equals(mLastUpdateTime)) {
                                getLessonInfo();
                            }
                        }
                        LocalApp.getInstance().getEventBus().post(new ConnectedServiceStatusEE(true));
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LocalApp.getInstance().getEventBus().post(new ConnectedServiceStatusEE(false));
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }

    /**
     * 获取上课信息
     */
    private void getLessonInfo() {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                String url = SPDataApp.getServiceIp(PeActivity.this) + UrlConfig.URL_GET_PE_STATUS_INFO;
                RequestParams params = RequestParamsBuilder.buildGetPeStatusRP(PeActivity.this,
                        url, 2);
                x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            GetPeStatusRE peStatusRE = JSON.parseObject(result.result, GetPeStatusRE.class);
                            mLastUpdateTime = peStatusRE.update_time;
                            listPe.clear();
                            listPe.addAll(peStatusRE.lessons);
                            mHandler.sendEmptyMessage(MSG_GET_NEW_PE_INFO);
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LogU.v(TAG, "oError:" + HttpExceptionHelper.getErrorMsg(ex));
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }


}
