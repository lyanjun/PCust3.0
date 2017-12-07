package com.c3.library.view.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * SmallVerticalLayout的数据绑定适配器
 * 使用该适配器来填充控件
 * Created by Lyan on 17/12/1.
 */

public abstract class SmallVerticalAdapter<T> {

    private List<T> dataList;
    private LayoutInflater inflater;


    public SmallVerticalAdapter(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getItemCounts() {
        return dataList.size();
    }

    @LayoutRes
    protected abstract int layoutResID();

    public View createItemView(ViewGroup rootView) {
        if (null == inflater) {
            inflater = LayoutInflater.from(rootView.getContext());
        }
        final View itemView = inflater.inflate(layoutResID(), rootView, false);
        setItemView(itemView);
        return itemView;
    }

    /**
     * 可在这里拓展添加点击事件
     *
     * @param itemView
     */
    protected void setItemView(@NonNull View itemView) {
    }

    public void getItemView(@NonNull View itemView, final int position) throws NullPointerException {
        if (null != onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        setItemChildView(itemView, position, dataList.get(position));
    }

    protected abstract void setItemChildView(@NonNull View itemView, int position, @NonNull T entity);

    /**
     * 回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public SmallVerticalAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }
}
