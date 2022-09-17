package app.watchnode.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import app.watchnode.R;
import app.watchnode.data.NetworkManager;
import app.watchnode.data.auth.AuthRepository;
import app.watchnode.data.schedule.model.Schedule;
import app.watchnode.databinding.ActivityRegisterBinding;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(this);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory())
                .get(HomeViewModel.class);

        final Button registerButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;

        homeViewModel.getSchedule();

        homeViewModel.getScheduleResult().observe(this, scheduleResult -> {
            if (scheduleResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (!scheduleResult.getSuccess()) {
                Toast.makeText(getApplicationContext(), scheduleResult.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Schedule schedule = Schedule.fromJson(scheduleResult.getData());
                updateUiWithSchedule(schedule);
            }
            setResult(Activity.RESULT_OK);
        });
    }

    private void updateUiWithSchedule(Schedule schedule) {
        String welcome = getString(R.string.welcome) + AuthRepository.getInstance().getLoggedInUser().getName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }
}