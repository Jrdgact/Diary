package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class add extends AppCompatActivity implements View.OnClickListener{

    private Database dbHelper;
    Button a_main;
    Button save_content;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        a_main=(Button)findViewById(R.id.a_main);
        save_content=(Button)findViewById(R.id.save_content);

        a_main.setOnClickListener(this);
        save_content.setOnClickListener(this);



        //显示作者
        SharedPreferences prefs = getSharedPreferences("data",MODE_PRIVATE);
        String name = prefs.getString("name","小明");
        EditText a_authorText = (EditText) findViewById(R.id.a_author);
        a_authorText.setText(name);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.a_main){
            Intent intent = new Intent(add.this, MainActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.save_content){
            EditText titleText = (EditText) findViewById(R.id.title);
            if(titleText.getText().toString().isEmpty()){
                Toast.makeText(this,"标题不能为空",Toast.LENGTH_LONG).show();
            }
            else {
                //保存
                dbHelper = new Database(this,"diary",null,1);
                dbHelper.getWritableDatabase();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //插入条数据
                ContentValues values= new ContentValues();
                EditText contentText = (EditText) findViewById(R.id.content);
                EditText a_authorText = (EditText) findViewById(R.id.a_author);
                //保存日期
                Date currentDate = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = dateFormat.format(currentDate);

                values.put("title",titleText.getText().toString());
                values.put("content",contentText.getText().toString());
                values.put("time",currentTime);
                values.put("author",a_authorText.getText().toString());
                db.insert("Diary",null,values);
                Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
                values.clear();
                Intent intent = new Intent(add.this, MainActivity.class);
                startActivity(intent);
            }
        }


    }
}