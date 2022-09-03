package app.watchnode.data;
import org.json.JSONObject;

public class ResponseResult {
    private boolean success;
    private String message;
    private JSONObject data;

    ResponseResult(boolean success, String message, JSONObject data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject getData() {
        return data;
    }
}