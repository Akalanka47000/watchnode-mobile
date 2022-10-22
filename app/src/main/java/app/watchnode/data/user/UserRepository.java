package app.watchnode.data.user;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import app.watchnode.data.NetworkManager;
import app.watchnode.data.ResponseResult;
public class UserRepository {

    private static volatile UserRepository instance;

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void addUser(MutableLiveData<ResponseResult> result, String name) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("name", name);
        NetworkManager.getInstance().post("/api/users", jsonParams,  result);
    }

    public void getAllUsers(MutableLiveData<ResponseResult> result) {
        NetworkManager.getInstance().get("/api/users",  result);
    }

    public void updateUser(MutableLiveData<ResponseResult> result, String userId, String name) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("name", name);
        NetworkManager.getInstance().put("/api/users/" + userId, jsonParams, result);
    }

    public void deleteUser(MutableLiveData<ResponseResult> result, String userId) {
        NetworkManager.getInstance().delete("/api/users/" + userId,  result);
    }
}
