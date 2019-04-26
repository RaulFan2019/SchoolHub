package cn.fizzo.hub.school.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.entity.adapter.PeWallStudentAE;
import cn.fizzo.hub.school.ui.widget.common.CircularImage;
import cn.fizzo.hub.school.utils.ImageU;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/25 17:37
 */
public class PeWallOneAdapter extends  RecyclerView.Adapter<PeWallOneAdapter.GridViewHolder>{


    private static final int FULL_SIZE = 48;

    private List<PeWallStudentAE> students;
    private Context mContext;
    private int mPage;

    public PeWallOneAdapter(Context context, List<PeWallStudentAE> students,int page){
        this.students = students;
        this.mContext = context;
        this.mPage = page;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_pe_wall_student, parent, false);
        return new PeWallOneAdapter.GridViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        final PeWallStudentAE item = students.get(mPage * FULL_SIZE + position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        if (students.size() - ((mPage + 1) * FULL_SIZE) > 0) {
            return FULL_SIZE;
        } else {
            return students.size() - mPage * FULL_SIZE;
        }
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNo;
        public TextView tvName;
        public TextView tvStep;
        public TextView tvHr;
        public CircularImage ivAvatar;
        public LinearLayout llBase;

        public GridViewHolder(View itemView) {
            super(itemView);
            tvNo = (TextView) itemView.findViewById(R.id.tv_no);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvStep = (TextView) itemView.findViewById(R.id.tv_step);
            tvHr = (TextView) itemView.findViewById(R.id.tv_hr);
            llBase = (LinearLayout) itemView.findViewById(R.id.ll_base);
            ivAvatar = (CircularImage) itemView.findViewById(R.id.iv_avatar);
        }

        public void bindItem(final PeWallStudentAE student) {
            tvName.setText(student.basicInfo.nickname);
            ImageU.loadUserImage(student.basicInfo.avatar, ivAvatar);
            if (student.basicInfo.number < 10) {
                tvNo.setText("0" + student.basicInfo.number);
            } else {
                tvNo.setText(student.basicInfo.number + "");
            }
            //心率为0 或 超过60秒没有心率
            if(student.bpm == 0){
                tvHr.setText("- -");
            }else {
                tvHr.setText(student.bpm + "");
            }
            tvStep.setText(student.stepCount);

            if (student.bpm < student.basicInfo.bpm_motion) {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_double_low);
            } else if (student.bpm < student.basicInfo.bpm_alert) {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_double_great);
            } else {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_double_high);
            }
        }
    }

}
