package app.watchnode.ui.home;

import app.watchnode.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import app.watchnode.data.NetworkManager;
import app.watchnode.data.schedule.model.Schedule;
import app.watchnode.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(this);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory())
                .get(HomeViewModel.class);

        final Button uploadButton = binding.uploadBtn;
        final ProgressBar loadingProgressBar = binding.homeLoader;

        homeViewModel.getSchedule();

        homeViewModel.getScheduleResult().observe(this, scheduleResult -> {
            if (scheduleResult == null) {
                return;
            }
            ImageView searchImg = findViewById(R.id.searchImgHome);
            ListView scheduleItems = findViewById(R.id.homeSchedule);
            loadingProgressBar.setVisibility(View.GONE);
            if (!scheduleResult.getSuccess()) {
//                Toast.makeText(getApplicationContext(), scheduleResult.getMessage(), Toast.LENGTH_SHORT).show();
//                searchImg.setVisibility(View.VISIBLE);
                searchImg.setVisibility(View.GONE);
                System.out.println(678888);
                String tutorials[]
                        = { "Algorithms", "Data Structures",
                        "Languages", "Interview Corner",
                        "GATE", "ISRO CS",
                        "UGC NET CS", "CS Subjects",
                        "Web Technologies" };
                ArrayAdapter<String> arr = new ArrayAdapter<String>(
                        this,
                        R.layout.activity_home,
                        tutorials);
                scheduleItems.setAdapter(arr);
            } else {
                if (scheduleResult.getData() != null) {
                    Schedule schedule = Schedule.fromJson(scheduleResult.getData());
                    searchImg.setVisibility(View.GONE);
                    updateUiWithSchedule(schedule);
                } else {
                    searchImg.setVisibility(View.VISIBLE);
                }
            }
            setResult(Activity.RESULT_OK);
        });
    }

    private void updateUiWithSchedule(Schedule schedule) {

    }
}