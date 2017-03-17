package com.microsoft.wehomedemo.ViewModels;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by v-chaojz on 3/17/2017.
 */

public class ListViewModel {
    private Context main;
    private ListView mListView;
    private MyAdapter listAdapter;

    public ListViewModel(Context context,ListView mainListView){
        this.main = context;
        this.mListView = mainListView;
        listViewInit();
    }

    private void listViewInit(){
        mListView.setBackgroundColor(Color.WHITE);
        listAdapter = new MyAdapter();
        mListView.setAdapter(listAdapter);

    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
