package cn.fizzo.hub.school.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.leanback.mode.OpenPresenter;

/**
 * Created by Raul.fan on 2018/1/2 0002.
 */

public class MainMenuRvAdapter extends RecyclerView.Adapter {

    private MyOpenPresenter mPresenter;


    public MainMenuRvAdapter(MyOpenPresenter presenter) {
        this.mPresenter = presenter;
        if (this.mPresenter != null)
            this.mPresenter.setAdapter(this);
    }

    public OpenPresenter getPresenter() {
        return this.mPresenter;
    }


    @Override
    public int getItemViewType(int position) {
        return this.mPresenter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return this.mPresenter.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        OpenPresenter presenter = this.mPresenter;
        OpenPresenter.ViewHolder presenterVh;
        presenterVh = presenter.onCreateViewHolder(parent, viewType);
        view = presenterVh.view;
        MainMenuRvAdapter.ViewHolder viewHolder = new MainMenuRvAdapter.ViewHolder(view, presenter, presenterVh);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainMenuRvAdapter.ViewHolder viewHolder = (MainMenuRvAdapter.ViewHolder) holder;
        viewHolder.mPresenter.onBindViewHolder(viewHolder.getViewHolder(), position);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        MainMenuRvAdapter.ViewHolder viewHolder = (MainMenuRvAdapter.ViewHolder) holder;
        viewHolder.mPresenter.onViewAttachedToWindow(viewHolder.mHolder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        MainMenuRvAdapter.ViewHolder viewHolder = (MainMenuRvAdapter.ViewHolder) holder;
        viewHolder.mPresenter.onViewDetachedFromWindow(viewHolder.mHolder);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        MainMenuRvAdapter.ViewHolder viewHolder = (MainMenuRvAdapter.ViewHolder) holder;
        viewHolder.mPresenter.onUnbindViewHolder(viewHolder.mHolder);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        final OpenPresenter.ViewHolder mHolder;
        final OpenPresenter mPresenter;

        public ViewHolder(View itemView, OpenPresenter presenter, OpenPresenter.ViewHolder holder) {
            super(itemView);
            this.mPresenter = presenter;
            this.mHolder = holder;
        }

        public OpenPresenter.ViewHolder getViewHolder() {
            return this.mHolder;
        }

        public OpenPresenter getOpenPresenter() {
            return this.mPresenter;
        }

    }
}
