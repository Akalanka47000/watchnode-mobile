package app.watchnode.data;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
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
import app.watchnode.data.auth.AuthRepository;
import app.watchnode.data.auth.model.LoggedInUser;

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

    public void get(String path, MutableLiveData<ResponseResult> result)
    {
        String url =  Constants.SERVER_URL + path;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                successListener(result, path), errorListener(result,path)) {
            @Override
            public Map<String, String> getHeaders() {
                return getRequestHeaders();
            }
        };

        requestQueue.add(request);
    }

    public void post(String path, @Nullable Map<String, Object> payload, MutableLiveData<ResponseResult> result)
    {
        String url =  Constants.SERVER_URL + path;
        if (payload == null) {
            payload = new HashMap<>();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(payload),
                successListener(result, path), errorListener(result,path)) {
            @Override
            public Map<String, String> getHeaders() {
                return getRequestHeaders();
            }
        };

        requestQueue.add(request);
    }

    public void put(String path, @Nullable Map<String, Object> payload, MutableLiveData<ResponseResult> result)
    {
        String url =  Constants.SERVER_URL + path;
        if (payload == null) {
            payload = new HashMap<>();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(payload),
                successListener(result, path), errorListener(result,path)) {
            @Override
            public Map<String, String> getHeaders() {
                return getRequestHeaders();
            }
        };

        requestQueue.add(request);
    }

    public void delete(String path, MutableLiveData<ResponseResult> result)
    {
        String url =  Constants.SERVER_URL + path;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                successListener(result, path), errorListener(result,path)) {
            @Override
            public Map<String, String> getHeaders() {
                return getRequestHeaders();
            }
        };

        requestQueue.add(request);
    }

    private Response.Listener successListener(MutableLiveData<ResponseResult> result, String path) {
        return (Response.Listener<JSONObject>) response -> {
            if (null != response.toString()) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    String message = response.getString("message");
                    Log.d("API_REQUEST_SUCCESS", "path: " + path + "response: " + data);
                    result.setValue(new ResponseResult(true, message, data));
                } catch (Exception e) {
                    Log.d("API_REQUEST_FAILED", "path: " + path);
                }
            }
        };
    };

    private Response.ErrorListener errorListener(MutableLiveData<ResponseResult> result, String path) {
        return error -> {
            if (null != error.networkResponse)
            {
                try {
                    JSONObject res = parseVolleyResponse(error.networkResponse);
                    JSONObject data = res.has("data") ? res.getJSONObject("data") : null;
                    String message = res.has("message") ? res.getString("message") : null;
                    Log.d("API_REQUEST_FAILED", "path: " + path + "response: " + data + "status: " + error.networkResponse.statusCode);
                result.setValue(new ResponseResult(false, message, data));
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

    private Map<String, String> getRequestHeaders() {
        Map<String, String>  headers = new HashMap();
        LoggedInUser loggedInUser = AuthRepository.getInstance().getLoggedInUser();
        if (loggedInUser!= null) {
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer "+loggedInUser.getAccessToken());
        };
        return headers;
    }
}
