package com.example.dlwls.myfragmentexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dlwls.myfragmentexample.R;
import com.example.dlwls.myfragmentexample.model.Book;

import java.util.List;

public class BookAdapter extends BaseAdapter {
    List<Book> lists;
    Context context;
    LayoutInflater inflater;

    public BookAdapter(List<Book> lists, Context context){
        this.context = context;
        this.lists = lists;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        View layout = inflater.inflate(R.layout.data_item, null);
        TextView tvTitle = layout.findViewById(R.id.tvTitle);
        TextView tvAuthor = layout.findViewById(R.id.tvAuthor);

        tvTitle.setText(this.lists.get(pos).title + " #" + pos);
        tvAuthor.setText(this.lists.get(pos).author);
        return layout;
    }
}
