package app.watchnode.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

import app.watchnode.data.NetworkListener;
import app.watchnode.data.auth.LoginRepository;
import app.watchnode.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        try {
            loginRepository.login(email, password, new NetworkListener() {
                @Override
                public void onSuccess(boolean success, String message, JSONObject data) throws JSONException {
                    loginResult.setValue(new LoginResult(new LoggedInUserView(data.getJSONObject("user").getString("name"))));
                }
                @Override
                public void onError(boolean success, String message, JSONObject data) {
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}