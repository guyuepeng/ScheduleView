package gg.bean;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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
    /**
     * 课程表每周显示几天
     */
    private int dayInWeek;

    /**
     * 实际填充在recyclerview中的数据源
     * 包含已经处理好的index item
     */
    private List<Data4RvItem> datas4show;

    public ScheduleBean(List<CourseBean> courses, int dayInWeek) {
        this.courses = courses;
        this.dayInWeek = dayInWeek;
        prepareData4show();
    }



    public List<Data4RvItem> getDatas4show() {
        if (datas4show == null)
            Log.e(TAG, "getDatas4show: datas4show==null");
        return datas4show;
    }

    /**
     * 将index和课程整合到一起
     * 准备用于填充布局的数据源
     */
    private void prepareData4show() {
        datas4show = new ArrayList();
        for (int i = 0; i < courses.size(); i++) {
            //每次应该添加周一的课程之前先将view左侧的课程节数添加进去
            if (i % dayInWeek == 0) {
                datas4show.add(new Data4RvItem(i / dayInWeek));
            }
            datas4show.add(new Data4RvItem(courses.get(i)));
        }
    }
}
