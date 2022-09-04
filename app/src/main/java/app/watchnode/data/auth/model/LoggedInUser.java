package app.watchnode.data.auth.model;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoggedInUser {

    private String id;
    private String name;
    private String email;
    private String role;
    private List<String> fcmTokens;

    public LoggedInUser(String id, String name, String email, String role, List<String> fcmTokens) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.fcmTokens = fcmTokens;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public List<String> getFcmTokens() {
        return fcmTokens;
    }

    public static @Nullable LoggedInUser fromJson(JSONObject obj) {
        try {
            return new LoggedInUser(
                    obj.getString("_id"),
                    obj.getString("name"),
                    obj.getString("email"),
                    obj.getString("role"),
                    new ArrayList(Arrays.asList(obj.getJSONArray("fcm_tokens")))
            );
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}