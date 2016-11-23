package gg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import gg.bean.Data4RvItem;
import gg.bean.ScheduleBean;
import gg.view.R;
import gg.viewholder.BaseViewHolder;
import gg.viewholder.CourseViewHolder;
import gg.viewholder.IndexViewHolder;

import static gg.api.Constant.TYPE_COURSE;
import static gg.api.Constant.TYPE_COURSE_INDEX;

/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */

public class ScheduleContentRvAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    @Deprecated
    private ScheduleBean scheduleBean;
    private List<Data4RvItem> datas4show;
    private int dayInWeek;//课程表中一周dayInWeek天

    public ScheduleContentRvAdapter(Context context, ScheduleBean scheduleBean, int dayInWeek) {
        super();
        this.context = context;
        this.scheduleBean=scheduleBean;
        datas4show = scheduleBean.getDatas4show();
        this.dayInWeek = dayInWeek;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch (viewType) {
            case TYPE_COURSE_INDEX:
                mView = LayoutInflater.from(context)
                        .inflate(R.layout.item_schedule_rv_course_index, null);
                mView.setClickable(false);//作为index的item不可接受点击事件
                return new IndexViewHolder(context,mView);
            case TYPE_COURSE:
                mView = LayoutInflater.from(context)
                        .inflate(R.layout.item_schedule_rv_course, null);

                return new CourseViewHolder(context,mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Data4RvItem mData = datas4show.get(position);
        //此处不做处理，交给各自的Holder做处理
        holder.fillData(mData);
    }

    @Override
    public int getItemViewType(int position) {
        return datas4show.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return datas4show.size();
    }

    /*
     * 考虑将每行的所有item合并作为recyclerview的一个item，将每个小item设置点击事件
     * 目前的方法似乎没法修改同一行中的每个item的宽度
     * 使用StaggeredGridLayoutManager实现一个横向的瀑布流效果，这样可以实现每个item的宽度不同，但是高度相同
     *
     */
}
