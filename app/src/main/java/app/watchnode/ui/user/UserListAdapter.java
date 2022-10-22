package app.watchnode.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import app.watchnode.R;
import app.watchnode.data.user.model.User;

public class UserListAdapter extends ArrayAdapter<User> {

    private ArrayList<User> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        TextView txtEmail;
    }

    public UserListAdapter(ArrayList<User> data, Context context) {
        super(context, R.layout.user_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User dataModel = getItem(position);
        UserListAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new UserListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.list_item_user_name);
            viewHolder.txtEmail = (TextView) convertView.findViewById(R.id.list_item_user_email);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UserListAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtEmail.setText(dataModel.getEmail());
        return convertView;
    }
}
