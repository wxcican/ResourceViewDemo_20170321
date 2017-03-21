package com.fuicuiedu.xc.resourceviewdemo_20170321.demoa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fuicuiedu.xc.resourceviewdemo_20170321.R;
import com.fuicuiedu.xc.resourceviewdemo_20170321.SimpleAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoAActivity extends AppCompatActivity {

    @BindView(R.id.demo_a_recyclerview)RecyclerView recyclerView;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_a);
        ButterKnife.bind(this);

        //设置布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加适配器
        simpleAdapter = new SimpleAdapter();
        recyclerView.setAdapter(simpleAdapter);

        //添加假数据
        for (int i = 0; i < 100; i++) {
            simpleAdapter.addItem("我是第" + i + "条数据");
        }
        simpleAdapter.notifyDataSetChanged();

    }
}
