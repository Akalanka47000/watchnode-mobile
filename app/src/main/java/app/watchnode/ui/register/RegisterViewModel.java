package app.watchnode.ui.register;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.watchnode.R;
import app.watchnode.data.ResponseResult;
import app.watchnode.data.auth.AuthRepository;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<ResponseResult> registerResult = new MutableLiveData<>();
    private AuthRepository authRepository;

    RegisterViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<ResponseResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String name, String email, String password) {
        try {
            authRepository.register(name, email, password, registerResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerDataChanged(String name, String email, String password) {
        if (!isUserNameValid(name)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null, null));
        } else if (!isUserNameValid(email)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_password));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
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