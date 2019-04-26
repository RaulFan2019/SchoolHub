package cn.fizzo.hub.school.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.fizzo.hub.school.R;

/**
 * Created by Raul.fan on 2018/3/12 0012.
 * Mail:raul.fan@139.com
 * QQ: 35686324
 */

public class SportLessonBorderRvPresenter extends MyOpenPresenter{

    private int count;
    private Context mContext;
    private SportLessonBorderAdapter mAdapter;

    public SportLessonBorderRvPresenter(Context context, int count) {
        this.mContext = context;
        this.count = count;
    }


    @Override
    public void setAdapter(SportLessonBorderAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_lesson_border, parent, false);
        return new SportLessonBorderRvPresenter.GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        SportLessonBorderRvPresenter.GridViewHolder vh = (SportLessonBorderRvPresenter.GridViewHolder) viewHolder;

    }


    class GridViewHolder extends ViewHolder {
        public GridViewHolder(View itemView) {
            super(itemView);
        }
    }

}
