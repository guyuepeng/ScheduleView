package gg.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gg.adapter.ScheduleContentRvAdapter;
import gg.bean.CourseBean;
import gg.bean.Data4RvItem;
import gg.bean.ScheduleBean;
import gg.utils.DateUtils;

import static gg.api.Constant.TIMES;
import static gg.api.Constant.TYPE_COURSE;
import static gg.api.Constant.TYPE_COURSE_INDEX;


/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */


//TODO: View适配了Match_parent,但是需要设置每个item的高度至少应该是父控件高度的1/5这样才能保证可以覆盖父控件，避免在父控件下方留白

public class ScheduleView extends LinearLayout {

    private Context context;
    private View root;
    private LinearLayout scheduleHeadLL;//课程表头：显示星期几
    private RecyclerView scheduleContentRV;//课程表的内容区，左侧为课程号，右侧区为课程内容

    @Deprecated
    private int daysInWeek;

    public ScheduleView(Context context) {
        this(context, null);
    }

    public ScheduleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        root = inflate(context, R.layout.schedule_view, this);
        scheduleHeadLL = (LinearLayout) root.findViewById(R.id.ll_schedule_head);
        scheduleContentRV = (RecyclerView) root.findViewById(R.id.rv_schedule_content);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
        Log.w("ScheduleView Warning", "ScheduleView: api >= Build.VERSION_CODES.LOLLIPOP");
    }


    /**
     * 由外部输入数据
     *
     * @param startYMD   本周起始年月日-->数组长度为3，分别为year,month,day
     * @param daysInWeek 输入的课程列表每周有几天
     * @param courses    当周课程列表
     */
    public void fillData(int[] startYMD, int daysInWeek, List<CourseBean> courses) {
        if (startYMD.length!=3)
            throw new RuntimeException("参数startYMD：   本周起始年月日-->数组长度为3，分别为year,month,day，传入参数异常");
        if (daysInWeek < 0 || daysInWeek > 7)
            throw new RuntimeException("daysInWeek参数异常\nFunction fillData(List,int) got an incorrect param(daysInWeek)");
        this.daysInWeek = daysInWeek;
        ScheduleBean mScheduleBean = new ScheduleBean(courses, daysInWeek);
        initHeader(startYMD, daysInWeek);
        initRecyclerView(mScheduleBean, daysInWeek);

    }

    /**
     * 根据传入的dayInWeek初始化header中
     */
    private void initHeader(int[] startYMD, int dayInWeek) {
        /*
        此处需要动态设置头部LinearLayout中每个item所占的比例
        1:TIMES:TIMES……共计dayInWeek个TIMES
         */
        View v = inflate(context, R.layout.item_header_blank, null);
        scheduleHeadLL.addView(v, new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        for (int index = 0; index < dayInWeek; index++) {
            addChild2Header(startYMD, index);
        }
    }

    private String[] week = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    private void addChild2Header(int[] startYMD, int index) {
        View v = inflate(context, R.layout.item_header_weekday_index, null);
        ((TextView) v.findViewById(R.id.tv_weekday)).setText(week[index]);
        ((TextView) v.findViewById(R.id.tv_date)).setText(DateUtils.getMMdd(startYMD[0], startYMD[1], startYMD[2], index));
        scheduleHeadLL.addView(v, new LayoutParams(0, LayoutParams.WRAP_CONTENT, TIMES));
    }

    /**
     * 获取到数据之后根据数据来初始化RecyclerView
     *
     * @param mScheduleBean
     * @param dayInWeek
     */
    private void initRecyclerView(ScheduleBean mScheduleBean, int dayInWeek) {
        final List<Data4RvItem> datas = mScheduleBean.getDatas4show();
        //+1加上最左边的课程号
        GridLayoutManager manager = new GridLayoutManager(context, TIMES * dayInWeek + 1);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Data4RvItem data = datas.get(position);
                switch (data.getType()) {
                    case TYPE_COURSE_INDEX:
                        return 1;//index这个item的宽度的整个宽度的1/TIMES*daysInWeek+1
                    case TYPE_COURSE:
                        return TIMES;
                }
                return 0;
            }
        });
        scheduleContentRV.setLayoutManager(manager);
        scheduleContentRV.addItemDecoration(new RecyclerView.ItemDecoration() {


            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(1, 1, 1, 1);
            }

        });
        scheduleContentRV.setAdapter(new ScheduleContentRvAdapter(context
                , mScheduleBean, dayInWeek));
    }
}
