package com.fuicuiedu.xc.resourceviewdemo_20170321.demod;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/3/21 0021.
 */

public abstract class BaseItemView<Model> extends FrameLayout{

    public BaseItemView(Context context) {
        super(context);
        initView();
    }

    //初始化视图
    protected abstract void initView();

    //绑定数据
    protected abstract void bindModel(Model model);
}
