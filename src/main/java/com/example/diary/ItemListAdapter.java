package com.example.diary;

import static androidx.core.content.ContextCompat.startActivity;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ItemListAdapter extends BaseAdapter{
    private List<Item> itemList;
    private Context context;
    public ItemListAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 创建列表项视图
        Button button;
        if (convertView == null) {
            button = new Button(context);
        } else {
            button = (Button) convertView;
        }

        // 获取当前位置的Item对象
        Item currentItem = itemList.get(position);

        // 设置按钮文本为Item的name属性
        button.setText(currentItem.getTitle());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, show.class);
                intent.putExtra("id",currentItem.getId());
                context.startActivity(intent);

            }
        });
        // 返回按钮视图
        return button;
    }
}