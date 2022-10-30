package app.watchnode.ui.home;

import app.watchnode.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

import app.watchnode.data.schedule.model.Event;

public class ScheduleListAdapter extends ArrayAdapter<Event>{

    private ArrayList<Event> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtStart;
        TextView txtEventName;
    }

    public ScheduleListAdapter(ArrayList<Event> data, Context context) {
        super(context, R.layout.schedule_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.schedule_item, parent, false);
            viewHolder.txtStart = (TextView) convertView.findViewById(R.id.start_time);
            viewHolder.txtEventName = (TextView) convertView.findViewById(R.id.event_name);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        Date date = new Date(dataModel.getStart());
        viewHolder.txtStart.setText(date.toLocaleString().substring(13, date.toLocaleString().length()));
        viewHolder.txtEventName.setText(dataModel.getName());
        return convertView;
    }
}