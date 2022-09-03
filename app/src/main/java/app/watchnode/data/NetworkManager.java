package app.watchnode.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import app.watchnode.constants.Constants;

public class NetworkManager
{
    private static NetworkManager instance = null;

    public RequestQueue requestQueue;

    private NetworkManager(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized NetworkManager getInstance(Context context)
    {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    public static synchronized NetworkManager getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void get(String path, NetworkListener listener)
    {
        String url =  Constants.SERVER_URL + path;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                successListener(listener, path), errorListener(listener,path));

        requestQueue.add(request);
    }

    public void post(String path, @Nullable Map<String, Object> payload, NetworkListener listener)
    {
        String url =  Constants.SERVER_URL + path;
        if (payload == null) {
            payload = new HashMap<>();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(payload),
                successListener(listener, path), errorListener(listener,path));

        requestQueue.add(request);
    }

    public void put(String path, @Nullable Map<String, Object> payload, NetworkListener listener)
    {
        String url =  Constants.SERVER_URL + path;
        if (payload == null) {
            payload = new HashMap<>();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(payload),
                successListener(listener, path), errorListener(listener,path));

        requestQueue.add(request);
    }

    public void delete(String path, NetworkListener listener)
    {
        String url =  Constants.SERVER_URL + path;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                successListener(listener, path), errorListener(listener,path));

        requestQueue.add(request);
    }

    private Response.Listener successListener(NetworkListener listener, String path) {
        return (Response.Listener<JSONObject>) response -> {
            if (null != response.toString()) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    String message = response.getString("message");
                    Log.d("API_REQUEST_SUCCESS", "path: " + path + "response: " + data);
                    listener.onSuccess(true, message, data);
                } catch (Exception e) {
                    Log.d("API_REQUEST_FAILED", "path: " + path);
                }
            }
        };
    };

    private Response.ErrorListener errorListener(NetworkListener listener, String path) {
        return error -> {
            if (null != error.networkResponse)
            {
                try {
                JSONObject res = parseVolleyResponse(error.networkResponse);
                JSONObject data = res.has("data") ? res.getJSONObject("data") : null;
                String message = res.has("message") ? res.getString("message") : null;
                Log.d("API_REQUEST_FAILED", "path: " + path + "response: " + data + "status: " + error.networkResponse.statusCode);
                listener.onError(false, message, data);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("API_REQUEST_FAILED", "path: " + path  + "status: " + error.networkResponse.statusCode);
                }
            }
        };
    };

    private @Nullable JSONObject parseVolleyResponse(NetworkResponse res) throws Exception {
        String responseBody = new String(res.data, "utf-8");
        JSONObject data = new JSONObject(responseBody);
        return data;
    }
}
