package gg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import gg.api.Constant;
import gg.bean.Data4RvItem;
import gg.bean.ScheduleBean;
import gg.view.R;
import gg.viewholder.BaseViewHolder;
import gg.viewholder.CourseViewHolder;
import gg.viewholder.IndexViewHolder;

/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */

public class ScheduleContentRvAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    //    private ScheduleBean scheduleBean;
    private List<Data4RvItem> datas4show;
    private int dayInWeek;//课程表中一周dayInWeek天

    public ScheduleContentRvAdapter(Context context, ScheduleBean scheduleBean, int dayInWeek) {
        super();
        this.context = context;
//        this.sheduleBean=scheduleBean;
        datas4show = scheduleBean.getDatas4show();
        this.dayInWeek = dayInWeek;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch (viewType) {
            case Constant.TYPE_COURSE_INDEX:
                mView = LayoutInflater.from(context)
                        .inflate(R.layout.item_schedule_rv_course_index, parent);
                mView.setClickable(false);//作为index的item不可接受点击事件
                return new IndexViewHolder(mView);
            case Constant.TYPE_COURSE:
                mView = LayoutInflater.from(context)
                        .inflate(R.layout.item_schedule_rv_course, parent);
                return new CourseViewHolder(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        Data4RvItem mData = datas4show.get(position);
        //此处不做处理，交给各自子ViewHolder处理
        holder.fillData(mData);
//        /*
//        正常情况下应该是：数据和item的性质是同步的
//        即数据中显示是此处应该是index item,那么此处的getItemViewType也应该是TYPE_COURSE_INDEX
//        为了确保无误，我在此处加了双重判断
//        */
//        if (mData.isIndexItem() && getItemViewType(position) == Constant.TYPE_COURSE_INDEX) {
//            ((IndexViewHolder) holder).fillData(mData.getTopIndex());
//        } else if (!mData.isIndexItem() && getItemViewType(position) == Constant.TYPE_COURSE) {
//            ((CourseViewHolder) holder).fillData(mData.getCourseBean());
//        } else {
//            Toast.makeText(context, "ScheduleContentRvAdapter onBindViewHolder has something wrong."
//                    , Toast.LENGTH_LONG).show();
//            throw new RuntimeException("ScheduleContentRvAdapter onBindViewHolder has something wrong.");
//        }
        //-------------------Deprecated------------------------
//        switch (getItemViewType(position)){
//            case Constant.TYPE_COURSE_INDEX:
//                ((IndexViewHolder) holder).fillData(((int) mData.getData()));
//                break;
//            case Constant.TYPE_COURSE:
//                ((CourseViewHolder) holder).fillData(((CourseBean) mData.getData()));
//                break;
//        }

    }

    @Override
    public int getItemViewType(int position) {
        //position以0为起点，即每行的第一个item为index
        if (position % (dayInWeek + 1) == 0)
            return Constant.TYPE_COURSE_INDEX;
        return Constant.TYPE_COURSE;
    }

    @Override
    public int getItemCount() {
        return datas4show.size();
    }


    /**
     * 考虑将每行的所有item合并作为recyclerview的一个item，将每个小item设置点击事件
     * 通过viewHolder.a.setWidth()来设置宽度
     * 目前的方法似乎没法修改同一行中的每个item的宽度
     * 使用StaggeredGridLayoutManager实现一个横向的瀑布流效果，这样可以实现每个item的宽度不同，但是高度相同，
     */
}
