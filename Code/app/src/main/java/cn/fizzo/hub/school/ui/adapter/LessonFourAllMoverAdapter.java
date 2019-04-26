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
import cn.fizzo.hub.school.entity.adapter.EffortLessonAE;

/**
 * Created by Raul on 2018/5/11.
 */
public class LessonFourAllMoverAdapter extends RecyclerView.Adapter<LessonFourAllMoverAdapter.GridViewHolder>{

    private static final String TAG = "LessonFourAllMoverAdapter";

    private static final int FULL_SIZE = 15;

    private List<EffortLessonAE.Mover> movers;
    private Context mContext;
    private int page;
    private int reminder;

    public LessonFourAllMoverAdapter(Context context, List<EffortLessonAE.Mover> movers, final int page, final int reminder) {
        this.movers = movers;
        this.mContext = context;
        this.page = page;
        this.reminder = reminder;
    }


    @Override
    public LessonFourAllMoverAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_lesson_four_mover, parent, false);
        return new LessonFourAllMoverAdapter.GridViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LessonFourAllMoverAdapter.GridViewHolder holder, int position) {
        final EffortLessonAE.Mover item = movers.get(page * FULL_SIZE + position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        if (movers.size() - ((page + 1) * FULL_SIZE) > 0) {
            return FULL_SIZE;
        } else {
            return movers.size() - page * FULL_SIZE;
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

        public void bindItem(final EffortLessonAE.Mover mover) {
            tvName.setText(mover.moversRe.nickname);
            if (mover.moversRe.studentnumber < 10) {
                tvNo.setText("0" + mover.moversRe.studentnumber);
            } else {
                tvNo.setText(mover.moversRe.studentnumber + "");
            }

            //心率为0 或 超过60秒没有心率
            if(mover.currHr == 0){
                tvHr.setText("- -");
            } else if(mover.currHr < 45) {
                tvHr.setText("-");
            } else {
                tvHr.setText(mover.currHr + "");
            }

            if (mover.currStep == 0 || reminder == 1) {
                tvStep.setText("- -");
            } else {
                tvStep.setText(mover.currStep + "");
            }

            if (mover.currHr < mover.moversRe.target_hr) {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_four_low);
            } else if (mover.currHr < mover.moversRe.target_hr_high) {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_four_great);
            } else {
                llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_four_high);
            }
        }
    }
}
