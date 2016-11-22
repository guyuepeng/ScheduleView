package gg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import gg.bean.ScheduleBean;

/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */

public class ScheduleContentRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ScheduleBean scheduleBean;//课程表的数据源
    private int dayInWeek;//课程表中一周dayInWeek天
    public ScheduleContentRvAdapter(Context context, ScheduleBean scheduleBean,int dayInWeek) {
        super();
        this.context=context;
        this.scheduleBean=scheduleBean;
        this.dayInWeek=dayInWeek;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
