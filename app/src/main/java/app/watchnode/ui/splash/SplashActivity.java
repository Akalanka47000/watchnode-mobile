package app.watchnode.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import app.watchnode.databinding.ActivitySplashBinding;
import app.watchnode.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);

    }
}