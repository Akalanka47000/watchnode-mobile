package app.watchnode.ui.home;

import app.watchnode.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.watchnode.constants.Constants;
import app.watchnode.data.NetworkManager;
import app.watchnode.data.VolleyMultipartRequest;
import app.watchnode.data.schedule.model.Event;
import app.watchnode.data.schedule.model.Schedule;
import app.watchnode.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;

    private static final String ROOT_URL = Constants.SERVER_URL + "/api/schedules";
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private Bitmap bitmap;
    private String filePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(this);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Button uploadButton = binding.uploadBtn;
        refresh();
        uploadButton.setOnClickListener(v -> {
            onUploadClick();
        });
    }

    public void refresh () {
        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory())
                .get(HomeViewModel.class);

        homeViewModel.getSchedule();

        homeViewModel.getScheduleResult().observe(this, scheduleResult -> {
            if (scheduleResult == null) {
                return;
            }
            ImageView searchImg = findViewById(R.id.searchImgHome);
            ListView scheduleItems = findViewById(R.id.homeSchedule);
            if (!scheduleResult.getSuccess()) {
//                Toast.makeText(getApplicationContext(), scheduleResult.getMessage(), Toast.LENGTH_SHORT).show();
//                searchImg.setVisibility(View.VISIBLE);
                searchImg.setVisibility(View.GONE);
                ArrayList<Event> events= new ArrayList<>();
                events.add(new Event("Event 1", 1663495713L, 2L,"Hall A-11"));
                events.add(new Event("Event 2", 1663515713L, 4L,"Hall B-10"));
                events.add(new Event("Event 3", 1663605713L, 5L, "Hatch Works"));
                ScheduleListAdapter adapter= new ScheduleListAdapter(events,getApplicationContext());
                scheduleItems.setAdapter(adapter);
                scheduleItems.setVisibility(View.VISIBLE);
            } else {
                if (scheduleResult.getData() != null) {
                    Schedule schedule = Schedule.fromJson(scheduleResult.getData());
                    ScheduleListAdapter adapter= new ScheduleListAdapter(schedule.getEvents(),getApplicationContext());
                    scheduleItems.setAdapter(adapter);
                    scheduleItems.setVisibility(View.VISIBLE);
                    searchImg.setVisibility(View.GONE);
                } else {
                    searchImg.setVisibility(View.VISIBLE);
                }
            }
            setResult(Activity.RESULT_OK);
        });
    }

    public void onUploadClick() {
        System.out.println(898);
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Log.e("Else", "Else");
            showFileChooser();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            this.filePath = getPath(picUri);
            if (filePath != null) {
                try {
                    Log.d("filePath", String.valueOf(filePath));
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                    uploadBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(
                        HomeActivity.this,"no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }

    }
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ROOT_URL,
                response -> {
                    Toast.makeText(getApplicationContext(), "Schedule uploaded successfully", Toast.LENGTH_SHORT).show();
                    refresh();
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Failed to upload schedule", Toast.LENGTH_LONG).show();
                    Log.e("GotError",""+error.getMessage());
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("schedule", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}