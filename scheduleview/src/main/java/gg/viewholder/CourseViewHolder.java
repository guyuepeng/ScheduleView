package gg.viewholder;

import android.view.View;
import android.widget.TextView;

import gg.bean.CourseBean;
import gg.bean.Data4RvItem;
import gg.view.R;

/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */

public class CourseViewHolder extends BaseViewHolder {
    public View root;
    private CourseBean courseBean;

    public TextView tv_content;

    public CourseViewHolder(View itemView) {
        super(itemView);
        root = itemView;
        tv_content= (TextView) itemView.findViewById(R.id.tv_course_content);
    }

    @Override
    public void fillData(Data4RvItem data4RvItem) {
        courseBean=data4RvItem.getCourseBean();
        tv_content.setText(courseBean.toString());

    }
    private void setOnClickListener(){
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something
            }
        });
    }

}
