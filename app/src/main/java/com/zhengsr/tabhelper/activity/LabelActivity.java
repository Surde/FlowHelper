package com.zhengsr.tabhelper.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhengsr.tabhelper.R;
import com.zhengsr.tablib.view.adapter.LabelFlowAdapter;
import com.zhengsr.tablib.view.flow.LabelFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LabelActivity extends AppCompatActivity {
    private List<String> mTitle = new ArrayList<>(Arrays.asList("新闻", "娱乐", "学习", "测试后", "新闻", "娱乐", "学习", "测试后"));
    private List<String> mTitle2 = new ArrayList<>(Arrays.asList("Life is like an ocean Only strong willed ".split(" ")));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        singleFlow();
        multiFlow();
        canLongFlow();
    }

    private void singleFlow(){
        LabelFlowLayout flowLayout = findViewById(R.id.singleflow);
        final LabelFlowAdapter adapter;
        flowLayout.setAdapter(adapter = new LabelFlowAdapter<String>(R.layout.item_textview,mTitle) {
            @Override
            public void bindView(View view, String data, int position) {
                setText(view,R.id.item_text,data);
            }

            @Override
            public void onItemSelectState(View view, boolean isSelected) {
                super.onItemSelectState(view, isSelected);
                TextView textView = view.findViewById(R.id.item_text);
                if (isSelected) {
                    textView.setTextColor(Color.WHITE);
                } else {
                    textView.setTextColor(Color.GRAY);
                }
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitle.clear();
                mTitle.addAll(mTitle2);
                adapter.notifyDataChanged();
            }
        });

    }

    private void multiFlow(){
        LabelFlowLayout flowLayout = findViewById(R.id.multiflow);
        flowLayout.setMaxCount(3);
        flowLayout.setAdapter(new LabelFlowAdapter<String>(R.layout.item_textview,mTitle2) {
            @Override
            public void bindView(View view, String data, int position) {
                setText(view,R.id.item_text,data);
            }

            @Override
            public void onItemSelectState(View view, boolean isSelected) {
                super.onItemSelectState(view, isSelected);
                TextView textView = view.findViewById(R.id.item_text);
                if (isSelected) {
                    textView.setTextColor(Color.WHITE);
                } else {
                    textView.setTextColor(Color.GRAY);
                }
            }

            @Override
            public void onReachMacCount(List<Integer> ids, int count) {
                super.onReachMacCount(ids, count);
                Toast.makeText(LabelActivity.this, "最多只能选中 "+count+" 个"+" 已选中坐标: "+ids, Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void canLongFlow(){
        LabelFlowLayout flowLayout = findViewById(R.id.longflow);
        flowLayout.setAdapter(new LabelFlowAdapter<String>(R.layout.item_search_layout,mTitle2) {
            @Override
            public void bindView(View view, String data, int position) {
                setText(view,R.id.search_msg_tv,data)
                        .addChildrenClick(view,R.id.search_delete_iv,position);
            }

            @Override
            public void onItemSelectState(View view, boolean isSelected) {
                super.onItemSelectState(view, isSelected);
                if (!isSelected){
                    view.setBackgroundResource(R.drawable.shape_search);
                    setVisible(view,R.id.search_delete_iv,false);
                }
            }

            @Override
            public void onItemClick(View view, String data, int position) {
                super.onItemClick(view, data, position);
                Toast.makeText(LabelActivity.this, "点击了: "+data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemChildClick(View childView, int position) {
                super.onItemChildClick(childView, position);
                if (childView.getId() == R.id.search_delete_iv){
                    mTitle2.remove(position);
                    notifyDataChanged();
                }
            }

            @Override
            public boolean onItemLongClick(View view,int position) {
                /**
                 * 置所有view 的 select 为 false
                 */
                resetStatus();
                view.setBackgroundResource(R.drawable.shape_search_select);
                setVisible(view,R.id.search_delete_iv,true);
                return super.onItemLongClick(view,position);
            }


        });
    }


}
