package io.atthis.atthisdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WMPCXPY on 2017/5/31.
 */

public class Adapter extends BaseAdapter {
    private List<TaskDetail> taskDeatilList = new ArrayList<TaskDetail>();
    Context ct;
    private LayoutInflater inflater;
    public Adapter(Context ct,List<TaskDetail> taskDeatilList) {
        // TODO Auto-generated constructor stub
        this.taskDeatilList = taskDeatilList;
        this.ct = ct;
        inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return taskDeatilList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return taskDeatilList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TaskDetail p = taskDeatilList.get(position);
//        if(convertView==null){
//            convertView = inflater.inflate(R.layout.yd_item, null);
//        }
//        TextView tv1=(TextView)convertView.findViewById(R.id.ydtext1);
//        TextView tv2=(TextView)convertView.findViewById(R.id.ydtext2);
//        tv1.setText(p.getTitle());
//        tv2.setText(p.getTester());
        return convertView;
    }
}
