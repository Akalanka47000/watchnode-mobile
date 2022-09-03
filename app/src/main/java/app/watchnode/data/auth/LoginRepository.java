package app.watchnode.data.auth;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import app.watchnode.data.NetworkManager;
import app.watchnode.data.ResponseResult;
import app.watchnode.data.auth.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoggedInUser user = null;
    
    private LoginRepository() {}

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
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
        LoggedInUser u = new LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe", "", "", new ArrayList<>(Arrays.asList("", "")));
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("email", email);
        jsonParams.put("password", password);
        NetworkManager.getInstance().post("/api/auth/login", jsonParams, result);
    }
}