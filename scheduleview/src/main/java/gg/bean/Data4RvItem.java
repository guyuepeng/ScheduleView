package gg.bean;

import gg.api.Constant;

/**
 * Created by GG on 2016/11/22.
 * Email:gu.yuepeng@foxmail.com
 */

public class Data4RvItem {
    //若是左侧的序号则为true，默认false
    @Deprecated
    private boolean isIndexItem = false;

    private int type= Constant.TYPE_COURSE;

    private int topIndex;
    private CourseBean mCourseBean;

    /**
     * 当item为index时则调用此无参数的构造方法
     * 利用传入的index的值可以计算出该item的top应该显示的数字
     * 计算公式：top=index*2+1
     */
    public Data4RvItem(int index) {
        isIndexItem = true;
        type=Constant.TYPE_COURSE_INDEX;
        topIndex = index*2+1;
    }

    public Data4RvItem(CourseBean courseBean) {
        mCourseBean = courseBean;
        type=Constant.TYPE_COURSE;
    }


    public int getTopIndex(){
        return topIndex;
    }
    public CourseBean getCourseBean(){
        return mCourseBean;
    }
    public int getType(){
        return type;
    }


    @Deprecated
    public boolean isIndexItem(){
        return isIndexItem;
    }
    @Deprecated
    public Object getData(){
        if (isIndexItem)
            return topIndex;
        return mCourseBean;
    }

}
