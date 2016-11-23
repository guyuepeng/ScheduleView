package gg.viewholder;

import android.content.Context;
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

    public IndexViewHolder(Context context,View itemView) {
        super(context,itemView);
        tv_top = (TextView) itemView.findViewById(R.id.tv_course_index_top);
        tv_bottom = (TextView) itemView.findViewById(R.id.tv_course_index_bottom);
    }


    @Override
    public void fillData(Data4RvItem data4RvItem) {
        setIndex2TV(data4RvItem.getTopIndex());
    }

    private void setIndex2TV(int index) {
        tv_top.setText(index + "");
        tv_bottom.setText(index + 1 + "");
    }
}
