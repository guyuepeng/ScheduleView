package gg.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import gg.bean.CourseBean;
import gg.bean.Data4RvItem;
import gg.view.R;

/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */

public class CourseViewHolder extends BaseViewHolder {
    private CourseBean courseBean;
    private TextView tv_content;

    public CourseViewHolder(Context context,View itemView) {
        super(context,itemView);
        tv_content= (TextView) itemView.findViewById(R.id.tv_course_content);
    }

    @Override
    public void fillData(Data4RvItem data4RvItem) {
        //这个里面的逻辑需要重新写，只是写了个例子
        courseBean=data4RvItem.getCourseBean();
        if (data4RvItem.getCourseBean()!=null){
            tv_content.setText(data4RvItem.toString()+"\n\n"+ data4RvItem.getType()+courseBean.toString());
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,courseBean.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
