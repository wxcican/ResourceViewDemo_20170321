package com.fuicuiedu.xc.resourceviewdemo_20170321;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/21 0021.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.DemoViewHolder>{

    private ArrayList<String> datas = new ArrayList<>();

    //添加数据
    public void addItem(String data){
        datas.add(data);
    }
    //清空数据
    public void clear(){
        datas.clear();
    }

    //创建
    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //把item视图注入进来
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text,parent,false);
        return new DemoViewHolder(view);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        //拿到数据
        String data = datas.get(position);
        //绑定数据
        holder.textView.setText(data);
    }

    //获取item数量
    @Override
    public int getItemCount() {
        return datas.size();
    }


    //先实现ViewHolder
    static class DemoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.textView)TextView textView;

        public DemoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
