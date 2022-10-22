package app.watchnode.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import app.watchnode.R;
import app.watchnode.data.NetworkManager;
import app.watchnode.data.user.model.User;
import app.watchnode.databinding.ActivityAllUsersBinding;
import app.watchnode.ui.home.HomeActivity;
import app.watchnode.ui.login.LoginActivity;

public class UserActivity  extends AppCompatActivity {
    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(this);
        ActivityAllUsersBinding binding = ActivityAllUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.appToolBar);
        setSupportActionBar(myToolbar);
        myToolbar.setOnMenuItemClickListener(item->{
            switch (item.getItemId()) {
                case R.id.logoutItem:
                    Intent logoutIntent = new Intent(this, LoginActivity.class);
                    startActivity(logoutIntent);
                    return true;
                case R.id.homeItem:
                    Intent homeIntent = new Intent(this, HomeActivity.class);
                    startActivity(homeIntent);
                    return true;
                case R.id.usersItem:
                    Intent userIntent = new Intent(this, UserActivity.class);
                    startActivity(userIntent);
                    return true;
                default:
                    return false;
            }
        });
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }


    public void refresh () {
        userViewModel = new ViewModelProvider(this, new UserViewModelFactory())
                .get(UserViewModel.class);

        userViewModel.getAllUsers();

        userViewModel.getAllUsersResult().observe(this, allUsersResult -> {
            if (allUsersResult == null) {
                return;
            }
            ImageView searchImg = findViewById(R.id.searchImgUsers);
            ListView users = findViewById(R.id.userList);
            if (allUsersResult.getSuccess()) {
                    if (allUsersResult.getDataList() != null) {
                        System.out.println(12312313);
                        ArrayList<User> userList = new ArrayList<>();
                        JSONArray usrArr = allUsersResult.getDataList();
                        for (int i = 0 ; i < usrArr.length(); i++) {
                            try {
                                userList.add(User.fromJson(usrArr.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        UserListAdapter adapter= new UserListAdapter(userList,getApplicationContext());
                        users.setAdapter(adapter);
                        users.setVisibility(View.VISIBLE);
                        searchImg.setVisibility(View.GONE);
                    } else {
                        searchImg.setVisibility(View.VISIBLE);
                    }
            } else {
                System.out.println(allUsersResult.getMessage());
            }
            setResult(Activity.RESULT_OK);
        });
    }
}
