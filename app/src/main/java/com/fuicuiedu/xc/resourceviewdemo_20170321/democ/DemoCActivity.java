package com.fuicuiedu.xc.resourceviewdemo_20170321.democ;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.fuicuiedu.xc.resourceviewdemo_20170321.R;
import com.fuicuiedu.xc.resourceviewdemo_20170321.SimpleAdapter;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoCActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        MugenCallbacks{

    @BindView(R.id.demo_c_rv)RecyclerView recyclerView;
    @BindView(R.id.demo_c_srl)SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.demo_c_prb)ProgressBar progressBar;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_c);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleAdapter = new SimpleAdapter();
        recyclerView.setAdapter(simpleAdapter);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);

        //上拉加载（mugen）,（会检测到列表是否滑动到底部，如果到了底部执行“onLoadMore”）
        Mugen.with(recyclerView,this).start();
    }

    @Override
    public void onRefresh() {
        //刷新3秒后，展示20条新数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                //沉睡3秒
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                simpleAdapter.clear();//刷新数据，清空旧数据
                for (int i = 0; i < 20; i++) {
                    simpleAdapter.addItem("我是新刷新到的第" + i + "条数据");
                }
                //更新UI（需要在UI线程）
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simpleAdapter.notifyDataSetChanged();
                        //刷新完成，关闭progressbar
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    //  -------------------  Mugen回调   ------------------
    //加载数据时的逻辑
    @Override
    public void onLoadMore() {
        //显示加载布局
        progressBar.setVisibility(View.VISIBLE);
        //上拉1.5秒后，加载10条数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                //沉睡1.5秒
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //加载10条新数据
                for (int i = 0; i < 10; i++) {
                    simpleAdapter.addItem("我是新加载到的第" + i + "条数据");
                }
                //加载完成,更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simpleAdapter.notifyDataSetChanged();
                        //隐藏加载prb
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        }).start();
    }

    //加载状态
    @Override
    public boolean isLoading() {
        //判断prb显示状态
        return progressBar.getVisibility() == View.VISIBLE;
    }

    //是否加载完所有数据
    // （如果返回true，则列表滑动到底部时，不再执行onLoadMore）
//    如果返回false，列表滑动到底部时，执行onLoadMore
    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }
}
