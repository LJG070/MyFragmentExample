package com.example.dlwls.myfragmentexample;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dlwls.myfragmentexample.adapter.BookAdapter;
import com.example.dlwls.myfragmentexample.model.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchDataFragment extends Fragment{

    String dbName;
    String tableName;

   SQLiteDatabase db;
    List<Book> items;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.search_data_fragment, container, false);

        ListView list = (ListView)root.findViewById(R.id.list_view);
        View item = View.inflate(getContext(), R.layout.data_item, null);
        TextView tvTitle = (TextView)item.findViewById(R.id.tvTitle);
        TextView tvAuthor = (TextView)item.findViewById(R.id.tvAuthor);

        SharedPreferences pref = container.getContext().getSharedPreferences("DB", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        this.dbName = pref.getString("DB", null);
        this.tableName = pref.getString("TABLE", null);

        if(this.dbName == null){
            this.db = container.getContext().openOrCreateDatabase("DBTEST", Context.MODE_PRIVATE, null);
            editor.putString("DB", "DBTEST");
            this.tableName = this.createTable();
            editor.putString("TABLE", this.tableName);
        } else{
            this.db = container.getContext().openOrCreateDatabase("DBTEST", Context.MODE_PRIVATE, null);
        }

        this.searchData();

        BookAdapter adapter = new BookAdapter(this.items, getContext());
        listView = (ListView)root.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        return root;
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

    private void searchData(){
        String sql = "SELECT title, author, content FROM " + this.tableName;
        Cursor c = this.db.rawQuery(sql, null);
        this.items = new ArrayList<Book>();

        for(int i = 0; i < c.getCount(); i++){
            c.moveToNext();

            Book temp = new Book();
            temp.title = c.getString(0);
            temp.author = c.getString(1);
            temp.content = c.getString(2);
            this.items.add(temp);
        }
    }
}
