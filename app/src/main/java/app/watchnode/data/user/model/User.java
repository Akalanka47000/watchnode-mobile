package app.watchnode.data.user.model;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

public class User {

    private String id;
    private String name;
    private String email;
    private String role;

    public User(String id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
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


    public static @Nullable
    User fromJson(JSONObject obj) {
        try {
            return new User(
                    obj.getString("_id"),
                    obj.getString("name"),
                    obj.getString("email"),
                    obj.getString("role")
            );
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
