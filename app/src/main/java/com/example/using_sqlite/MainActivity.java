package com.example.using_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    EditText name,phone,id,task,nameshow,phoneshow,taskshow;
    Button submit,show,next;
    String names,phones,tasks;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createtable();
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        task=(EditText)findViewById(R.id.task);
        nameshow=(EditText)findViewById(R.id.nameshow);
        id=(EditText)findViewById(R.id.id);

        phoneshow=(EditText)findViewById(R.id.phoneshow);
        taskshow=(EditText)findViewById(R.id.taskshow);
        submit=(Button)findViewById(R.id.submit);
        cursor=sqLiteDatabase.rawQuery("select * from data",null);
        next=(Button)findViewById(R.id.next);
        show=(Button)findViewById(R.id.show);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cursor.isLast())
                {
                    cursor.moveToFirst();
                    id.setText(cursor.getString(0).toString());

                    nameshow.setText(cursor.getString(1).toString());
                    phoneshow.setText(cursor.getString(2).toString());
                    taskshow.setText(cursor.getString(3).toString());
                }

                else {
                    cursor.moveToNext();

                    id.setText(cursor.getString(0).toString());

                    nameshow.setText(cursor.getString(1).toString());
                    phoneshow.setText(cursor.getString(2).toString());
                    taskshow.setText(cursor.getString(3).toString());
                }
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names=name.getText().toString();
                phones=phone.getText().toString();
                tasks=task.getText().toString();
                if(allFielfFill()) {
                    saveData();

                }
                else {
                    Toast.makeText(MainActivity.this,"Fill all Fields",Toast.LENGTH_LONG).show();


                }
            }
        });



    }

    private void showData() {
        if(cursor.getCount()==0)
        {
            Toast.makeText(MainActivity.this,"No Data",Toast.LENGTH_LONG).show();

        }
        else {
            cursor.moveToFirst();
            id.setText(cursor.getString(0).toString());

            nameshow.setText(cursor.getString(1).toString());
            phoneshow.setText(cursor.getString(2).toString());
            taskshow.setText(cursor.getString(3).toString());
        }


    }


    private boolean allFielfFill() {
        if(TextUtils.isEmpty(names) || TextUtils.isEmpty(phones) || TextUtils.isEmpty(tasks)) {
            return false;

        }
        else
            return true;
    }

    private void saveData() {
        String query="insert into data values(null,'"+names+"','"+phones+"','"+tasks+"');";
        sqLiteDatabase.execSQL(query);
        name.setText("");
        phone.setText("");
        task.setText("");
        Toast.makeText(this,"Input Done",Toast.LENGTH_LONG).show();
    }


    private void createtable() {
        sqLiteDatabase=openOrCreateDatabase("db", Context.MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("create table if not exists data(id INTEGER PRIMARY KEY AUTOINCREMENT not null,name VARCHAR, phone VARCHAR, task VARCHAR);");

    }

}
