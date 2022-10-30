package app.watchnode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList event_id, event_title, event_message;

    CustomAdapter(Activity activity, Context context, ArrayList event_id, ArrayList event_title, ArrayList event_message){
        this.activity = activity;
        this.context = context;
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_message = event_message;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.event_id_txt.setText(String.valueOf(event_id.get(position)));
        holder.event_title_txt.setText(String.valueOf(event_title.get(position)));
        holder.event_message_txt.setText(String.valueOf(event_message.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(event_id.get(position)));
                intent.putExtra("title", String.valueOf(event_title.get(position)));
                intent.putExtra(",message", String.valueOf(event_message.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return event_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView event_id_txt, event_title_txt, event_message_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event_id_txt = itemView.findViewById(R.id.event_id_txt);
            event_title_txt = itemView.findViewById(R.id.event_title_txt);
            event_message_txt = itemView.findViewById(R.id.event_message_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }

    }

}
