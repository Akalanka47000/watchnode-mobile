package app.watchnode.data.schedule;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.watchnode.data.NetworkManager;
import app.watchnode.data.ResponseResult;
import app.watchnode.data.schedule.model.Event;
public class ScheduleRepository {
    private static volatile ScheduleRepository instance;

    private ScheduleRepository() {}

    public static ScheduleRepository getInstance() {
        if (instance == null) {
            instance = new ScheduleRepository();
        }
        return instance;
    }

    public void getLatestUserSchedule(MutableLiveData<ResponseResult> result) {
        NetworkManager.getInstance().get("/api/schedules?limit=1",  result);
    }

    public void getUserSchedules(MutableLiveData<ResponseResult> result) {
        NetworkManager.getInstance().get("/api/schedules",  result);
    }

    public void updateUserSchedule(String scheduleId, List<Event> events, MutableLiveData<ResponseResult> result) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("events", events);
        NetworkManager.getInstance().put("/api/schedules/"+scheduleId,  jsonParams, result);
    }
}
