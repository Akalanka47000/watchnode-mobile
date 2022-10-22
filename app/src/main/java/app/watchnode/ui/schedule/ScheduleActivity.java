package app.watchnode.ui.schedule;

import app.watchnode.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import app.watchnode.data.NetworkManager;
import app.watchnode.data.schedule.model.Schedule;
import app.watchnode.databinding.ActivityAllSchedulesBinding;
import app.watchnode.ui.home.HomeViewModel;
import app.watchnode.ui.home.HomeViewModelFactory;

public class ScheduleActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(this);
        ActivityAllSchedulesBinding binding = ActivityAllSchedulesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory())
                .get(HomeViewModel.class);

        final ProgressBar loadingProgressBar = binding.homeLoader;

        homeViewModel.getAllSchedules();

        homeViewModel.getAllSchedulesResult().observe(this, scheduleResult -> {
            if (scheduleResult == null) {
                return;
            }
            ListView scheduleItems = findViewById(R.id.allSchedules);
            loadingProgressBar.setVisibility(View.GONE);
            if (!scheduleResult.getSuccess()) {
                ArrayList<Schedule> schedules= new ArrayList<>();
                schedules.add(new Schedule("1", "Lecture Schedule", new ArrayList<>()));
                schedules.add(new Schedule("1", "Exam Schedule", new ArrayList<>()));
                schedules.add(new Schedule("1", "Lecture Schedule 2", new ArrayList<>()));
                AllScheduleAdapter adapter= new AllScheduleAdapter(schedules,getApplicationContext());
                scheduleItems.setAdapter(adapter);
                scheduleItems.setVisibility(View.VISIBLE);
            } else {
                if (scheduleResult.getData() != null) {
                    Schedule schedule = Schedule.fromJson(scheduleResult.getData());
                    updateUiWithSchedule(schedule);
                }
            }
            setResult(Activity.RESULT_OK);
        });
    }

    private void updateUiWithSchedule(Schedule schedule) {

    }
}