package app.watchnode.data;

import org.json.JSONException;
import org.json.JSONObject;

public interface NetworkListener
{
    void onSuccess(boolean success, String message, JSONObject data) throws JSONException;
    void onError(boolean success, String message, JSONObject data) throws JSONException;
}
