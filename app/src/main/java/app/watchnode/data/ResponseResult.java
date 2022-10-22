package app.watchnode.data;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseResult {
    private boolean success;
    private String message;
    private JSONObject data;
    private JSONArray dataList;

    ResponseResult(boolean success, String message, JSONObject data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    ResponseResult(boolean success, String message, JSONArray data) {
        this.success = success;
        this.message = message;
        this.dataList = data;
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

    public JSONArray getDataList() {
        return dataList;
    }
}