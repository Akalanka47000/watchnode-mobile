package app.watchnode.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import app.watchnode.data.NetworkManager;
import app.watchnode.databinding.ActivityModifyUserBinding;

public class ModifyUserActivity  extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(this);
        ActivityModifyUserBinding binding = ActivityModifyUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this, new UserViewModelFactory())
                .get(UserViewModel.class);

        final TextView nameEditText = (TextView) binding.addUserEditTxtName;
        final TextView emailEditText = (TextView) binding.addUserEditTxtEmail;
        final Button btn = binding.setUserBtn;

        Intent intent = getIntent();

        String type = intent.getStringExtra("type");

        if (type.equals("add")) {
            btn.setText("Add user");
            userViewModel.getAddUserResult().observe(this, addUserResult -> {
                if (addUserResult == null) {
                    return;
                }
                if (!addUserResult.getSuccess()) {
                    System.out.println("1"+ addUserResult.getMessage());
                    Toast.makeText(getApplicationContext(), addUserResult.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), addUserResult.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent userIntent = new Intent(this, UserActivity.class);
                    startActivity(userIntent);
                }
                setResult(Activity.RESULT_OK);
            });
            btn.setOnClickListener(v -> {
                userViewModel.addUser(nameEditText.getText().toString(), emailEditText.getText().toString());
            });
        } else {
            btn.setText("Edit user");
            String userId = intent.getStringExtra("userId");
            String username = intent.getStringExtra("username");
            nameEditText.setText(username);
            emailEditText.setVisibility(View.GONE);
            userViewModel.getUpdateUserResult().observe(this, updateUserResult -> {
                if (updateUserResult == null) {
                    return;
                }
                if (!updateUserResult.getSuccess()) {
                    Toast.makeText(getApplicationContext(), updateUserResult.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), updateUserResult.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent userIntent = new Intent(this, UserActivity.class);
                    startActivity(userIntent);
                }
                setResult(Activity.RESULT_OK);
            });
            btn.setOnClickListener(v -> {
                userViewModel.updateUser(userId, nameEditText.getText().toString());
            });
        }
    }
}
