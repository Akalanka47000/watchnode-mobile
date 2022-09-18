package app.watchnode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.watchnode.R;
import app.watchnode.model.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Notification> reviewsArrayList;
    private Context context;

    // creating constructor for our adapter class
    public NotificationAdapter(ArrayList<Notification> coursesArrayList, Context context) {
        this.reviewsArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Notification review = reviewsArrayList.get(position);
        holder.title.setText(review.getTitle());


    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return reviewsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.

        private final TextView title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            title = itemView.findViewById(R.id.title);


        }
    }
}



