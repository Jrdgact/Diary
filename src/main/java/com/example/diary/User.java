package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class User extends AppCompatActivity implements View.OnClickListener{

    Button save_data;
    Button restore_data;
    Button u_main;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        save_data = (Button) findViewById(R.id.save_data);
        restore_data = (Button) findViewById(R.id.restore_data);
        u_main = (Button) findViewById(R.id.u_main);

        save_data.setOnClickListener(this);
        restore_data.setOnClickListener(this);
        u_main.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.save_data){
            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
            //获取姓名
            EditText name = (EditText) findViewById(R.id.name);
            if(name.getText().toString().isEmpty()){
                name.setText("小明");
            }
            editor.putString("name",name.getText().toString());
            //获取年龄
            EditText age = (EditText) findViewById(R.id.age);
            if(TextUtils.isEmpty(age.getText().toString())){
                age.setText("8");
                editor.putInt("age",8);
            }else{
                editor.putInt("age",Integer.parseInt(age.getText().toString()));
            }
            //获取性别
            RadioGroup sex = (RadioGroup)findViewById(R.id.sex);
            if(sex.getCheckedRadioButtonId()==R.id.m){
                editor.putBoolean("sex",true);
            }
            else{
                editor.putBoolean("sex",false);
            }

            editor.commit();
        }

        if(v.getId() == R.id.restore_data){
            SharedPreferences prefs = getSharedPreferences("data",MODE_PRIVATE);
            String name = prefs.getString("name","小明");
            int age = prefs.getInt("age",8);
            boolean sex = prefs.getBoolean("sex",false);


            EditText nameText = (EditText) findViewById(R.id.name);
            nameText.setText(name);
            EditText ageText = (EditText) findViewById(R.id.age);
            ageText.setText(String.valueOf(age));
            RadioGroup sexRG = (RadioGroup)findViewById(R.id.sex);
            if(sex)
                sexRG.check(R.id.m);
            else
                sexRG.check(R.id.w);

        }
        if(v.getId() == R.id.u_main){
            Intent intent = new Intent(User.this, MainActivity.class);
            startActivity(intent);
        }
    }
}