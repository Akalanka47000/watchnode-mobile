package app.watchnode.data.auth;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;
import app.watchnode.data.NetworkManager;
import app.watchnode.data.ResponseResult;
import app.watchnode.data.auth.model.LoggedInUser;

public class AuthRepository {

    private static volatile AuthRepository instance;

    private LoggedInUser user = null;

    private AuthRepository() {}

    public static AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public LoggedInUser getLoggedInUser() {
        return user;
    }

    public void logout(MutableLiveData<ResponseResult> result) {
        NetworkManager.getInstance().post("/api/auth/login", null, result);
    }

    public void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    public void login(String email, String password, MutableLiveData<ResponseResult> result) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("email", email);
        jsonParams.put("password", password);
        NetworkManager.getInstance().post("/api/auth/login", jsonParams, result);
    }

    public void register(String name, String email, String password, MutableLiveData<ResponseResult> result) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("name", name);
        jsonParams.put("email", email);
        jsonParams.put("password", password);
        NetworkManager.getInstance().post("/api/auth/register", jsonParams, result);
    }
}