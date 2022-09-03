package app.watchnode.data.auth.model;

import java.util.List;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
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
}