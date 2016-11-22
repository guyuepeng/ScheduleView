package gg.viewholder;

import android.view.View;
import android.widget.TextView;

import gg.bean.Data4RvItem;
import gg.view.R;

/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */

public class IndexViewHolder extends BaseViewHolder {
    private TextView tv_top;
    private TextView tv_bottom;

    public IndexViewHolder(View itemView) {
        super(itemView);
        tv_top= (TextView) itemView.findViewById(R.id.tv_course_index_top);
        tv_bottom= (TextView) itemView.findViewById(R.id.tv_course_index_bottom);
    }

    @Override
    public void fillData(Data4RvItem data4RvItem) {
        tv_top.setText(data4RvItem.getTopIndex()+"");
        tv_bottom.setText(data4RvItem.getTopIndex()+1+"");
    }
}
