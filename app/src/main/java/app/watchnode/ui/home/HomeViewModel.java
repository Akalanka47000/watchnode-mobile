package app.watchnode.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import app.watchnode.data.ResponseResult;
import app.watchnode.data.schedule.ScheduleRepository;
import app.watchnode.data.schedule.model.Event;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ResponseResult> scheduleResult = new MutableLiveData<>();
    private MutableLiveData<ResponseResult> allSchedulesResult = new MutableLiveData<>();
    private MutableLiveData<ResponseResult> updateScheduleResult = new MutableLiveData<>();
    private ScheduleRepository scheduleRepository;

    HomeViewModel(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    LiveData<ResponseResult> getScheduleResult() {
        return scheduleResult;
    }
    LiveData<ResponseResult> getAllSchedulesResult() {
        return allSchedulesResult;
    }
    LiveData<ResponseResult> getUpdateScheduleResult() {
        return updateScheduleResult;
    }

    public void getSchedule() {
        try {
            scheduleRepository.getLatestUserSchedule(scheduleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllSchedules() {
        try {
            scheduleRepository.getUserSchedules(allSchedulesResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSchedule(String id, List<Event> events) {
        try {
            scheduleRepository.updateUserSchedule(id, events, updateScheduleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}