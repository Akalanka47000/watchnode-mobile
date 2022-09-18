package app.watchnode.ui.schedule;

import app.watchnode.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import app.watchnode.data.schedule.model.Schedule;

public class AllScheduleAdapter extends ArrayAdapter<Schedule>{

    private ArrayList<Schedule> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtScheduleName;
    }

    public AllScheduleAdapter(ArrayList<Schedule> data, Context context) {
        super(context, R.layout.schedule_row, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Schedule dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.schedule_row, parent, false);
            viewHolder.txtScheduleName = (TextView) convertView.findViewById(R.id.schedule_name);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.txtScheduleName.setText(dataModel.getName());
        return convertView;
    }
}