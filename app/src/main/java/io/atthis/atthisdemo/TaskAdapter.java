package io.atthis.atthisdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by WMPCXPY on 2017/5/31.
 */

public class TaskAdapter extends BaseAdapter {
    private List<TaskDetail> mData;//定义数据。
    private LayoutInflater mInflater;//定义Inflater,加载我们自定义的布局。

    public TaskAdapter(LayoutInflater infalater, List<TaskDetail> data){
        this.mData = data;
        this.mInflater = infalater;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup) {
        //获得ListView中的view
        View viewTask = mInflater.inflate(R.layout.item_listview,null);
        //获得学生对象
        TaskDetail task = mData.get(position);

        //获得自定义布局中每一个控件的对象。

        ImageView sideImage = (ImageView) viewTask.findViewById(R.id.testImage);
        TextView title = (TextView) viewTask.findViewById(R.id.titler);
        TextView tester = (TextView) viewTask.findViewById(R.id.tester);
        TextView tester2 = (TextView) viewTask.findViewById(R.id.tester2);

        //将数据一一添加到自定义的布局中。

        //sideImage.setImageResource(task.getImageId());
        sideImage.setImageResource(R.drawable.inventory);
        title.setText(task.getTitle());
        tester.setText(task.getTester());
        tester2.setText(task.getTester2());
        return viewTask ;
    }
//    public View getView()

}
