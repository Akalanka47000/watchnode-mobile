package app.watchnode.data.setting.model;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

public class Setting {

    private @Nullable String user;
    private @Nullable String schedule;
    private Boolean notificationEnabled;
    private Integer notificationPeriod;

    public Setting(@Nullable String user, @Nullable String schedule, Boolean notificationEnabled, Integer notificationPeriod) {
        this.user = user;
        this.schedule = schedule;
        this.notificationEnabled = notificationEnabled;
        this.notificationPeriod = notificationPeriod;
    }

    public static @Nullable Setting fromJson(JSONObject obj) {
        try {
            return new Setting(
                    obj.getString("user"),
                    obj.getString("schedule"),
                    obj.getBoolean("notification_enabled"),
                    obj.getInt("notification_period")
            );
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}