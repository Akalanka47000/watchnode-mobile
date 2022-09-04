package app.watchnode.data.setting;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;
import app.watchnode.data.NetworkManager;
import app.watchnode.data.ResponseResult;
import app.watchnode.data.setting.model.Setting;

public class SettingRepository {

    private static volatile SettingRepository instance;

    private Setting setting = null;

    private SettingRepository() {}

    public static SettingRepository getInstance() {
        if (instance == null) {
            instance = new SettingRepository();
        }
        return instance;
    }

    public void getUserSettings(MutableLiveData<ResponseResult> result) {
        NetworkManager.getInstance().get("/api/settings",  result);
    }

    public void updateUserSettings(Boolean notificationEnabled, Integer notificationPeriod, MutableLiveData<ResponseResult> result) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("notification_enabled", notificationEnabled);
        jsonParams.put("notification_period", notificationPeriod);
        NetworkManager.getInstance().put("/api/settings",  jsonParams, result);
    }
}
