package cn.fizzo.hub.school.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.leanback.mode.OpenPresenter;

import java.util.List;

import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.entity.model.MainMenuItem;
import cn.fizzo.hub.school.ui.widget.common.NormalTextView;

/**
 * Created by Raul.fan on 2017/9/1 0001.
 */

public class MainMenuRvPresenter extends MyOpenPresenter {


    private List<MainMenuItem> meusItems;
    private MainMenuRvAdapter mAdapter;

    public MainMenuRvPresenter(List<MainMenuItem> items) {
        this.meusItems = items;
    }

    @Override
    public void setAdapter(MainMenuRvAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getItemCount() {
        return meusItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    @Override
    public OpenPresenter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_menu, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OpenPresenter.ViewHolder viewHolder, int position) {
        GridViewHolder vh = (GridViewHolder) viewHolder;

        View vIc = vh.vIc;
        NormalTextView tvTitle = vh.tvTitle;

        tvTitle.setText(meusItems.get(position).title);
        vIc.setBackgroundResource(meusItems.get(position).resId);
    }


    class GridViewHolder extends OpenPresenter.ViewHolder {

        View vIc;
        NormalTextView tvTitle;

        public GridViewHolder(View itemView) {
            super(itemView);
            vIc = itemView.findViewById(R.id.v_ic);
            tvTitle = (NormalTextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
