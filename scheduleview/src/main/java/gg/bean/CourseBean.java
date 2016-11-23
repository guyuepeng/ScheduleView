package gg.bean;

/**
 * Created by GG on 2016/11/22.
 * Email:gu.yuepeng@foxmail.com
 * <p>
 * 课程信息Bean
 */

//TODO:要求根据html解析出来的信息，生成每周的课程信息
public class CourseBean {
    private String courseID;//课程号
    private String courseName;//课程名称
    private String courseTea;//授课老师
    private String courseAddress;//上课教室地点
    private String courseTime;//上课时间
    private String moreInfo;//其他说明

    @Deprecated
    public CourseBean() {
    }

    public CourseBean(String courseName, String courseTea, String courseAddress) {
        this.courseName = courseName;
        this.courseTea = courseTea;
        this.courseAddress = courseAddress;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTea() {
        return courseTea;
    }

    public void setCourseTea(String courseTea) {
        this.courseTea = courseTea;
    }

    public String getCourseAddress() {
        return courseAddress;
    }

    public void setCourseAddress(String courseAddress) {
        this.courseAddress = courseAddress;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
}
