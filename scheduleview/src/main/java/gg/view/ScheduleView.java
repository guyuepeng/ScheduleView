package gg.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import gg.adapter.ScheduleContentRvAdapter;
import gg.bean.CourseBean;
import gg.bean.ScheduleBean;

/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */

public class ScheduleView extends LinearLayout {

    private Context context;
    private View root;
    private LinearLayout scheduleHeadLL;//课程表头：显示星期几
    private RecyclerView scheduleContentRV;//课程表的内容区，左侧为课程号，右侧区为课程内容
//    private int dayInWeek=5;

    public ScheduleView(Context context) {
        this(context,null);
    }

    public ScheduleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        root=inflate(context,R.layout.schedule_view,this);
        scheduleHeadLL= (LinearLayout) root.findViewById(R.id.ll_schedule_head);
        scheduleContentRV= (RecyclerView) root.findViewById(R.id.rv_schedule_content);

        List<CourseBean> mList=new ArrayList<>();
        for (int i=0;i<24;i++){
            mList.add(new CourseBean());
        }
        fillData(mList,5);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
        Log.w("ScheduleView Warning", "ScheduleView: api >= Build.VERSION_CODES.LOLLIPOP" );
    }

    /**
     * 由调用者传入数据
     * 由ScheduleBean
     * @param courses
     * @param dayInWeek
     */
    public void fillData(List<CourseBean> courses,int dayInWeek) {
        //周末由于没有课所以不计入，+1加上最左边的课程号
        scheduleContentRV.setLayoutManager(new GridLayoutManager(context,dayInWeek+1));
        scheduleContentRV.setAdapter(new ScheduleContentRvAdapter(context
                ,new ScheduleBean(courses,dayInWeek),dayInWeek));
    }
}
