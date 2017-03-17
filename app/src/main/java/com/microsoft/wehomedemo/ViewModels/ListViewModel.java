package com.microsoft.wehomedemo.ViewModels;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.microsoft.wehomedemo.R;
import com.microsoft.wehomedemo.ViewModels.Component.HttpUtil;
import com.microsoft.wehomedemo.ViewModels.Event.WhenGetInformations;

import java.util.ArrayList;

/**
 * Created by v-chaojz on 3/17/2017.
 */

public class ListViewModel {
    private Context main;
    private ListView mListView;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> detail;
    private HttpUtil httpUtil;

    public ListViewModel(Context context,ListView mainListView){
        this.main = context;
        this.mListView = mainListView;
        listViewInit();
        httpUtil = new HttpUtil(whenGetInformations);
    }

    private WhenGetInformations whenGetInformations = new WhenGetInformations() {
        @Override
        public void invoke(Object sender, ArrayList<String> information) {
            for (String str:information){
                detail.add(str);
            }
            listAdapter.notifyDataSetChanged();
        }
    };

    private void listViewInit(){
        mListView.setBackgroundColor(Color.WHITE);
        detail = new ArrayList<>();
        detail.add("hello");
//        mListView.setAdapter(listAdapter);
        listAdapter = new ArrayAdapter<String>(main, R.layout.item1,R.id.listText,detail);
        mListView.setAdapter(listAdapter);
    }

    public void getDetailInformation(String city){
        detail.clear();
        detail.add(city);
        httpUtil.getCitiesInformation(city);
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
