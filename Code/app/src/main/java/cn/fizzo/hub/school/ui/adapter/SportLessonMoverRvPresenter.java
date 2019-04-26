package cn.fizzo.hub.school.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.mode.OpenPresenter;

import java.util.List;

import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.entity.adapter.EffortLessonAE;
import cn.fizzo.hub.school.ui.widget.common.CircularImage;
import cn.fizzo.hub.school.utils.ImageU;

/**
 * Created by Raul.fan on 2018/3/12 0012.
 * Mail:raul.fan@139.com
 * QQ: 35686324
 */

public class SportLessonMoverRvPresenter extends MyOpenPresenter{

    private List<EffortLessonAE.Mover> movers;
    private Context mContext;
    private SportLessonMoverAdapter mAdapter;

    public SportLessonMoverRvPresenter(Context context, List<EffortLessonAE.Mover> movers) {
        this.movers = movers;
        this.mContext = context;
    }


    @Override
    public void setAdapter(SportLessonMoverAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getItemCount() {
        if (movers != null){
            return movers.size();
        }else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    @Override
    public OpenPresenter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_lesson_mover, parent, false);
        return new SportLessonMoverRvPresenter.GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OpenPresenter.ViewHolder viewHolder, int position) {
        SportLessonMoverRvPresenter.GridViewHolder vh = (SportLessonMoverRvPresenter.GridViewHolder) viewHolder;

        TextView tvNo = vh.tvNo;
        TextView tvName= vh.tvName;
        TextView tvStep = vh.tvStep;
        TextView tvHr = vh.tvHr;
        CircularImage ivAvatar = vh.ivAvatar;
        LinearLayout llBase = vh.llBase;

        tvName.setText(movers.get(position).moversRe.nickname
//                + "(" + movers.get(position).moversRe.antplus_serialno + ")"
        );
        ImageU.loadUserImage(movers.get(position).moversRe.avatar,ivAvatar);
        if (movers.get(position).moversRe.studentnumber < 10){
            tvNo.setText("0" + movers.get(position).moversRe.studentnumber);
        }else {
            tvNo.setText(movers.get(position).moversRe.studentnumber + "");
        }
        //心率为0 或 超过60秒没有心率
        if (movers.get(position).currHr == 0) {
            tvHr.setText("- -");
        } else if (movers.get(position).currHr < 45){
            tvHr.setText("-");
        }else {
            tvHr.setText(movers.get(position).currHr + "");
        }

        if (movers.get(position).currStep == 0) {
            tvStep.setText("- -");
        } else {
            tvStep.setText(movers.get(position).currStep + "");
        }

        if (movers.get(position).currHr < movers.get(position).moversRe.target_hr) {
            llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_low);
        } else if (movers.get(position).currHr < movers.get(position).moversRe.target_hr_high) {
            llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_great);
        } else {
            llBase.setBackgroundResource(R.drawable.bg_item_sport_lesson_high);
        }
    }


    class GridViewHolder extends OpenPresenter.ViewHolder {

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
    }

}
