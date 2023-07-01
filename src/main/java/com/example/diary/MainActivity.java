package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Database dbHelper;
    Button message;
    Button add;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        //数据库
        dbHelper = new Database(this,"diary",null,1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //插入条数据
//        ContentValues values= new ContentValues();
//        values.put("title","若你困于无风之地");
//        values.put("content","做点正事吧，巴巴托斯！");
//        values.put("author","特瓦林");
//        values.put("time","2023-10-12 13：13：13");
//        db.insert("Diary",null,values);
//        Toast.makeText(this,"成功",Toast.LENGTH_LONG).show();
//        values.clear();
        //展示所有数据
        String[] titles={"",""};
        String[] ids={"",""};
        int i=0;
        int k=0;
        Cursor cursor = db.query("diary",null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do{
                i++;
            }while(cursor.moveToNext());
        }
        if(cursor.moveToFirst()) {
            do{
                //自动扩容
                if(titles.length<i){
                    titles = new String[i];
                    ids = new String[i];
                    for(int j=0;j<titles.length;j++){
                        titles[j] = "";
                        ids[j] = "";
                    }
                }
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                titles[k]=title;
                ids[k]=id;
                k++;
            }while(cursor.moveToNext());
        }
        cursor.close();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,titles);
//        ListView data = (ListView)findViewById(R.id.data);
//        data.setAdapter(adapter);
        List<Item> itemList = new ArrayList<>();
        for (int z=0;z<ids.length;z++){
            Item item = new Item();
            item.setId(ids[z]);
            item.setTitle(titles[z]);
            itemList.add(item);
        }
        ListView data = (ListView)findViewById(R.id.data);
        ItemListAdapter adapter = new ItemListAdapter(this,itemList);
        data.setAdapter(adapter);


        //显示作者名字
        SharedPreferences prefs = getSharedPreferences("data",MODE_PRIVATE);
        String name = prefs.getString("name","小明");
        TextView authorText = (TextView) findViewById(R.id.author);
        authorText.setText(name);
        //信息按钮
        message = (Button)findViewById(R.id.message);
        message.setOnClickListener(this);
        //添加
        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.message){
            Intent intent = new Intent(MainActivity.this, User.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.add){
            Intent intent = new Intent(MainActivity.this, add.class);
            startActivity(intent);
        }
    }
}