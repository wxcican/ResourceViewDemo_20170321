package com.fuicuiedu.xc.resourceviewdemo_20170321.demod;


//带下拉刷新，上拉加载的自定义列表视图，完成数据的适配显示，（完成数据的加载）
//列表视图，RecyclerView
//下拉刷新，SwipeRefreshLayout
//上拉加载，Mugen

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.fuicuiedu.xc.resourceviewdemo_20170321.R;
import com.fuicuiedu.xc.resourceviewdemo_20170321.SimpleAdapter;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseResourceView<Model,ItemView extends BaseItemView<Model>> extends FrameLayout implements
        SwipeRefreshLayout.OnRefreshListener,
        MugenCallbacks{


    public BaseResourceView(Context context) {
        this(context,null);
    }

    public BaseResourceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseResourceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @BindView(R.id.recyclerview)RecyclerView recyclerview;
    @BindView(R.id.swiperefreshLayout)SwipeRefreshLayout swiperefreshLayout;
    @BindView(R.id.progressbar)ProgressBar progressbar;
    private ModelAdapter adapter;

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.base_resource_view,this,true);
        ButterKnife.bind(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ModelAdapter();
        recyclerview.setAdapter(adapter);

        //下拉刷新
        swiperefreshLayout.setOnRefreshListener(this);
        //上拉加载
        Mugen.with(recyclerview,this).start();
    }

    //让使用者，重写实现此方法，确定itemView的视图
    protected abstract ItemView creatItemView();

    //数据的加载
//    1.对外提供“加载数据”的方法，让使用者必须实现
//    2.刷新，加载操作实现

    //  ######################  下拉刷新，上拉加载监听start    ##################
    @Override
    public void onRefresh() {
        //执行刷新操作
    }

    @Override
    public void onLoadMore() {
        //执行加载操作
    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }
    //  ######################  下拉刷新，上拉加载监听over   ##################
    protected class ModelAdapter extends RecyclerView.Adapter{
//        数据，视图
//        怎么实现，使用时，想用什么数据，就用什么数据呢？    -> 使用泛型

        private ArrayList<Model> datas = new ArrayList<>();

        public void clear(){
            datas.clear();
            notifyDataSetChanged();//刷新一下
        }

        public void addData(ArrayList<Model> data){
            datas.addAll(data);
            notifyDataSetChanged();//刷新一下
        }

        //创建Vh
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemView itemView =  creatItemView();
            return new RecyclerView.ViewHolder(itemView){
            };
        }
        //数据绑定
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            把当前item的"数据"，绑定到当前item的"视图"上
            Model model = datas.get(position);
            ItemView itemView = (ItemView) holder.itemView;
            //如何绑定？->itemView父类已经写好绑定数据的方法了
            itemView.bindModel(model);
        }

        //获取item数量
        @Override
        public int getItemCount() {
            return datas.size();
        }
    }
}
