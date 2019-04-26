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
import cn.fizzo.hub.school.config.PageCountState;
import cn.fizzo.hub.school.entity.adapter.EffortLessonAE;
import cn.fizzo.hub.school.ui.widget.common.CircularImage;
import cn.fizzo.hub.school.utils.ImageU;

/**
 * Created by Raul on 2018/4/24.
 */

public class LessonSingleAllMoverAdapter extends  RecyclerView.Adapter<LessonSingleAllMoverAdapter.GridViewHolder> {

    private List<EffortLessonAE.Mover> movers;
    private Context mContext;
    private int mCountType;

    public LessonSingleAllMoverAdapter(Context context, List<EffortLessonAE.Mover> movers,int countType) {
        this.movers = movers;
        this.mContext = context;
        this.mCountType = countType;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mCountType == PageCountState.COUNT_30) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_lesson_single_mover_30, parent, false);
            return new LessonSingleAllMoverAdapter.GridViewHolder(v);
        } else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_lesson_single_mover, parent, false);
            return new LessonSingleAllMoverAdapter.GridViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        final EffortLessonAE.Mover item = movers.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return movers.size();
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

        public void bindItem(final EffortLessonAE.Mover mover){
            tvName.setText(mover.moversRe.nickname
//                + "(" + movers.get(position).moversRe.antplus_serialno + ")"
            );
            ImageU.loadUserImage(mover.moversRe.avatar,ivAvatar);
            if (mover.moversRe.studentnumber < 10){
                tvNo.setText("0" + mover.moversRe.studentnumber);
            }else {
                tvNo.setText(mover.moversRe.studentnumber + "");
            }
            //心率为0 或 超过60秒没有心率
            if (mover.currHr == 0) {
                tvHr.setText("- -");
            } else if (mover.currHr < 45){
                tvHr.setText("-");
            }else {
                tvHr.setText(mover.currHr + "");
            }

            if (mover.currStep == 0) {
                tvStep.setText("- -");
            } else {
                tvStep.setText(mover.currStep + "");
            }

            if (mover.currHr < mover.moversRe.target_hr) {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_low);
            } else if (mover.currHr < mover.moversRe.target_hr_high) {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_great);
            } else {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_high);
            }
        }
    }

}
