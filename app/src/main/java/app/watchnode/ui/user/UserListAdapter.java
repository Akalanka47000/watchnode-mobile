package app.watchnode.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.ArrayList;
import app.watchnode.R;
import app.watchnode.data.user.UserRepository;
import app.watchnode.data.user.model.User;
import app.watchnode.ui.login.LoginActivity;

public class UserListAdapter extends ArrayAdapter<User> {

    private ArrayList<User> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        TextView txtEmail;
        ImageButton deleteBtn;
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
            viewHolder.deleteBtn = (ImageButton) convertView.findViewById(R.id.btn_delete_usr);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UserListAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }
        UserViewModel userViewModel = new UserViewModel(UserRepository.getInstance());
        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtEmail.setText(dataModel.getEmail());
        viewHolder.deleteBtn.setOnClickListener(v -> {
            userViewModel.deleteUser(dataModel.getId());
            this.dataSet.remove(dataModel);
            this.notifyDataSetChanged();
        });
        return convertView;
    }
}
