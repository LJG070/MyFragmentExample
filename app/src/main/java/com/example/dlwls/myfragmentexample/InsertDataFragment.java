package com.example.dlwls.myfragmentexample;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dlwls.myfragmentexample.model.Book;

import java.util.ArrayList;
import java.util.List;

public class InsertDataFragment extends Fragment {
    EditText edTitle;
    EditText edAuthor;
    EditText edContent;
    Button btnSave;

    SQLiteDatabase db;
    String dbName;
    String tableName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.insert_data_fragment, container, false);

        this.edTitle = (EditText)root.findViewById(R.id.edTitle);
        this.edAuthor = (EditText)root.findViewById(R.id.edAuthor);
        this.edContent = (EditText)root.findViewById(R.id.edContent);
        this.btnSave = (Button)root.findViewById(R.id.btn_save);

        SharedPreferences pref = container.getContext().getSharedPreferences("DB", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        this.dbName = pref.getString("DB", null);
        this.tableName = pref.getString("TABLE", null);

        Button saveBtn = (Button)root.findViewById(R.id.btn_save);

        if(this.dbName == null){
            this.db = container.getContext().openOrCreateDatabase("DBTEST", Context.MODE_PRIVATE, null);
            editor.putString("DB", "DBTEST");
            this.tableName = this.createTable();
            editor.putString("TABLE", this.tableName);
        } else{
            this.db = container.getContext().openOrCreateDatabase("DBTEST", Context.MODE_PRIVATE, null);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edTitle.getText().toString();
                String author = edAuthor.getText().toString();
                String content = edContent.getText().toString();
                if(view.getId() == R.id.btn_save){
                    if(title.isEmpty() || author.isEmpty() || content.isEmpty()){
                        Toast.makeText(getContext().getApplicationContext(), "제목, 저자, 내용을 입력해주세요", Toast.LENGTH_LONG).show();
                        return;
                    }
                    insertData(title, author, content);
                    Toast.makeText(getContext().getApplicationContext(), "저장 되었습니다" + title, Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }
    private void insertData(String title, String author, String content){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("author", author);
        contentValues.put("content", content);
        this.db.insert(this.tableName, null, contentValues);
    }

    private String createTable(){
        String Query = "CREATE TABLE IF NOT EXISTS book_tb("
                +"title TEXT,"
                +"author TEXT,"
                +"content TEXT"
                +")";
        this.db.execSQL(Query);
        return "book_tb";
    }


}
