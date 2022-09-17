package app.watchnode.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import org.json.JSONException;
import app.watchnode.R;
import app.watchnode.data.NetworkManager;
import app.watchnode.data.auth.AuthRepository;
import app.watchnode.data.auth.model.LoggedInUser;
import app.watchnode.databinding.ActivityRegisterBinding;
import app.watchnode.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(this);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        final TextView nameEditText = binding.name;
        final TextView emailEditText = (TextView) binding.email;
        final TextView passwordEditText = (TextView) binding.password;
        final Button registerButton = binding.register;
        final Button loginSwitch = binding.loginSwitch;
        final ProgressBar loadingProgressBar = binding.loading;

        registerViewModel.getRegisterFormState().observe(this, registerFormState -> {
            if (registerFormState == null) {
                return;
            }
            registerButton.setEnabled(registerFormState.isDataValid());
            if (registerFormState.getNameError() != null) {
                emailEditText.setError(getString(registerFormState.getNameError()));
            }
            if (registerFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(registerFormState.getPasswordError()));
            }
        });

        registerViewModel.getRegisterResult().observe(this, registerResult -> {
            if (registerResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (!registerResult.getSuccess()) {
                Toast.makeText(getApplicationContext(), registerResult.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                try {
                    LoggedInUser user = LoggedInUser.fromJson(registerResult.getData().getJSONObject("user"));
                    AuthRepository.getInstance().setLoggedInUser(user);
                    updateUiWithUser();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            setResult(Activity.RESULT_OK);
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nameEditText != null) {
                    registerViewModel.registerDataChanged(nameEditText.getText().toString(), emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (nameEditText != null) {
                    registerViewModel.register(nameEditText.getText().toString(), emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
            }
            return false;
        });

        registerButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            if (nameEditText != null) {
                registerViewModel.register(nameEditText.getText().toString(), emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        loginSwitch.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome) + AuthRepository.getInstance().getLoggedInUser().getName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }
}