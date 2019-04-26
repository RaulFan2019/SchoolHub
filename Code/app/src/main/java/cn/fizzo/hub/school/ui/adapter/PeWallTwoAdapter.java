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

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/25 17:37
 */
public class PeWallTwoAdapter extends  RecyclerView.Adapter<PeWallTwoAdapter.GridViewHolder>{


    private static final int FULL_SIZE = 30;

    private List<PeWallStudentAE> students;
    private Context mContext;
    private int mPage;

    public PeWallTwoAdapter(Context context, List<PeWallStudentAE> students, int page){
        this.students = students;
        this.mContext = context;
        this.mPage = page;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_pe_wall_two_student, parent, false);
        return new PeWallTwoAdapter.GridViewHolder(v);
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
        public LinearLayout llBase;

        public GridViewHolder(View itemView) {
            super(itemView);
            tvNo = (TextView) itemView.findViewById(R.id.tv_no);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvStep = (TextView) itemView.findViewById(R.id.tv_step);
            tvHr = (TextView) itemView.findViewById(R.id.tv_hr);
            llBase = (LinearLayout) itemView.findViewById(R.id.ll_base);
        }

        public void bindItem(final PeWallStudentAE student) {
            tvName.setText(student.basicInfo.nickname);
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
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_four_low);
            } else if (student.bpm < student.basicInfo.bpm_alert) {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_four_great);
            } else {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_four_high);
            }
        }
    }

}
