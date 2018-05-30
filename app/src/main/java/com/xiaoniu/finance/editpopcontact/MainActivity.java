package com.xiaoniu.finance.editpopcontact;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText mEdit;
    private ImageView mEdita;
    private ArrayList<String> mData = new ArrayList<>();
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvent();
    }

    private void initEvent() {
        mEdit = findViewById(R.id.edit_text);
        mEdita = findViewById(R.id.edit_icon);
        mEdita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });

    }

    private void showPopWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(LinearLayoutCompat.LayoutParams.MATCH_PARENT, 1000);
            ListView listView = new ListView(MainActivity.this);
            mockData();
            listView.setAdapter(new ContactAdapter());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mEdit.setText(mData.get(position));
                    popupWindow.dismiss();
                    mEdit.setSelection(mData.get(position).length());
                }
            });
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
            popupWindow.setContentView(listView);
            popupWindow.showAsDropDown(mEdit);
        }

    }

    /**
     * 创建数据
     */

    private void mockData() {
        for (int i = 0; i < 100; i++) {
            mData.add("mock数据第 " + i + " 条信息");
        }
    }

    private class ContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(MainActivity.this, R.layout.contact_layout, null);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.add = (ImageView) convertView.findViewById(R.id.add);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(mData.get(position));
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    private class ViewHolder {
        TextView title;
        ImageView add;
    }
}
