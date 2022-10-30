package app.watchnode.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.watchnode.data.ResponseResult;
import app.watchnode.data.user.UserRepository;

public class UserViewModel extends ViewModel {

    private MutableLiveData<ResponseResult> addUserResult = new MutableLiveData<>();
    private MutableLiveData<ResponseResult> allUsersResult = new MutableLiveData<>();
    private MutableLiveData<ResponseResult> updateUserResult = new MutableLiveData<>();
    private MutableLiveData<ResponseResult> deleteUserResult = new MutableLiveData<>();
    private UserRepository userRepository;

    UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<ResponseResult> getAddUserResult() {
        return addUserResult;
    }
    public LiveData<ResponseResult> getAllUsersResult() {
        return allUsersResult;
    }
    public LiveData<ResponseResult> getUpdateUserResult() {
        return updateUserResult;
    }
    public LiveData<ResponseResult> getDeleteUserResult() {
        return deleteUserResult;
    }

    public void addUser(String name, String email) {
        try {
            userRepository.addUser(addUserResult, name, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllUsers() {
        try {
            userRepository.getAllUsers(allUsersResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String id, String name) {
        try {
            userRepository.updateUser(updateUserResult, id, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String id) {
        try {
            userRepository.deleteUser(deleteUserResult, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
