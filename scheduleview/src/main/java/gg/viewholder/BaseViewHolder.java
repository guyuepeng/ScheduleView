package gg.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import gg.bean.Data4RvItem;

/**
 * Created by majiadong on 16-11-22.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 在此方法中将根据各自类型将传入的data填充到布局中
     * @param data4RvItem
     */
    public abstract void fillData(Data4RvItem data4RvItem);
}
