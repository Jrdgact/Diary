package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


public class show extends AppCompatActivity implements View.OnClickListener{
    private Database dbHelper;
    Button a_main;
    Button save_content;

    Button sub;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        a_main=(Button)findViewById(R.id.a_main);
        save_content=(Button)findViewById(R.id.save_content);
        sub=(Button)findViewById(R.id.sub);

        a_main.setOnClickListener(this);
        save_content.setOnClickListener(this);
        sub.setOnClickListener(this);
        String a = getIntent().getStringExtra("id");
        //数据库
        dbHelper = new Database(this,"diary",null,1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("diary",null,"id =?",new String[]{a},null,null,null);
        cursor.moveToFirst();
        @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
        @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
        @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
        @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("author"));
        cursor.close();

        //显示title
        EditText titleText = (EditText) findViewById(R.id.title);
        titleText.setText(title);

        //显示时间
        TextView timeText = (TextView) findViewById(R.id.time);
        timeText.setText(time);

        //content
        EditText contentText = (EditText) findViewById(R.id.content);
        contentText.setText(content);

        //显示作者
        EditText a_authorText = (EditText) findViewById(R.id.a_author);
        a_authorText.setText(author);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.a_main){
            Intent intent = new Intent(show.this, MainActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.save_content){
            EditText titleText = (EditText) findViewById(R.id.title);
            if(titleText.getText().toString().isEmpty()){
                Toast.makeText(this,"标题不能为空",Toast.LENGTH_LONG).show();
            }
            else {
                //保存
                String a = getIntent().getStringExtra("id");
                dbHelper = new Database(this,"diary",null,1);
                dbHelper.getWritableDatabase();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //get数据
                ContentValues values= new ContentValues();
                EditText contentText = (EditText) findViewById(R.id.content);
                EditText a_authorText = (EditText) findViewById(R.id.a_author);


                values.put("title",titleText.getText().toString());
                values.put("content",contentText.getText().toString());
                values.put("author",a_authorText.getText().toString());

                db.update("Diary",values,"id=?",new String[]{a});
                Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
                values.clear();
                Intent intent = new Intent(show.this, MainActivity.class);
                startActivity(intent);
            }
        }
        if(v.getId() == R.id.sub){
            String a = getIntent().getStringExtra("id");
            dbHelper = new Database(this,"diary",null,1);
            dbHelper.getWritableDatabase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.delete("Diary","id=?",new String[]{a});
            Toast.makeText(this,"删除成功",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(show.this, MainActivity.class);
            startActivity(intent);

        }
    }
}