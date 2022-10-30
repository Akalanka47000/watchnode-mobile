package app.watchnode;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    Switch simpleSwitch1, simpleSwitch2, simpleSwitch3, simpleSwitch4, simpleSwitch5, simpleSwitch6;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // initiate view's
        simpleSwitch1 = (Switch) findViewById(R.id.simpleSwitch1);
        simpleSwitch2 = (Switch) findViewById(R.id.simpleSwitch2);
        simpleSwitch3 = (Switch) findViewById(R.id.simpleSwitch3);
        simpleSwitch4 = (Switch) findViewById(R.id.simpleSwitch4);
        simpleSwitch5 = (Switch) findViewById(R.id.simpleSwitch5);
        simpleSwitch6 = (Switch) findViewById(R.id.simpleSwitch6);

        save = (Button) findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2;

                /*
                if (simpleSwitch1.isChecked())
                    statusSwitch1 = simpleSwitch1.getTextOn().toString();
                if (simpleSwitch2.isChecked())
                    //send notification at the same time of event
                    if (simpleSwitch3.isChecked())
                        //send notification 10 mins before
                        if (simpleSwitch4.isChecked())
                            //send notification 30 mins before
                            if (simpleSwitch5.isChecked())
                                //send notification 1 hour before
                                if (simpleSwitch6.isChecked())
                                    //send notification 1 day before
*/
            }
        });
    }

        private void setAlarm(long time){
            //getting the alarm manager
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            //creating a new intent specifying the broadcast receiver
            Intent i = new Intent(this, Notification.class);

            //creating a pending intent using the intent
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

            //setting the repeating alarm that will be fired every day
            am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
            Toast.makeText(this, "Setting is set", Toast.LENGTH_SHORT).show();
        }


}
