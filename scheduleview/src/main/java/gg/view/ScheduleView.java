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

import gg.adapter.ScheduleContentRvAdapter;

/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */

public class ScheduleView extends LinearLayout {

    private Context context;
    private View root;
    private LinearLayout scheduleHeadLL;//课程表头：显示星期几
    private RecyclerView scheduleContentRV;//课程表的内容区，左侧为课程号，右侧区为课程内容
    private int dayInWeek=5;

    public ScheduleView(Context context) {
        this(context,null);
    }

    public ScheduleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        root=inflate(context,R.layout.schedule_view,null);
        scheduleHeadLL= (LinearLayout) root.findViewById(R.id.schedule_head_ll);
        scheduleContentRV= (RecyclerView) root.findViewById(R.id.schedule_content_rv);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
        Log.w("ScheduleView Warning", "ScheduleView: api >= Build.VERSION_CODES.LOLLIPOP" );
    }

    private void init() {
        initRecyclerView();

    }

    private void initRecyclerView() {
        //周末由于没有课所以不计入，+1加上最左边的课程号
        scheduleContentRV.setLayoutManager(new GridLayoutManager(context,dayInWeek+1));
        scheduleContentRV.setAdapter(new ScheduleContentRvAdapter());
    }
}
