package gg.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GG on 2016/11/22.
 * Email:gu.yuepeng@foxmail.com
 * <p>
 * 课程表数据bean
 */
public class ScheduleBean {
    /**
     * 传进来的本周课程列表，要求横向添加CourseBean
     * 即周一的1,2节课，周二的1,2节……周五的1,2节；
     * 周一的3,4节，周二的3,4节……
     */
    private List<CourseBean> courses;
    private int dayInWeek;

    private List datas4ScheduleView;

    public ScheduleBean(List<CourseBean> courses, int dayInWeek) {
        this.courses = courses;
        this.dayInWeek = dayInWeek;
    }
    private void do2Data(){
        datas4ScheduleView=new ArrayList();
        for (int i=0;i<courses.size();i++){
            //每次应该添加周一的课程之前先将view左侧的课程节数添加进去
            if (i%dayInWeek==0){
                datas4ScheduleView.add(new ScheduleItem());
            }
        }
    }
}
