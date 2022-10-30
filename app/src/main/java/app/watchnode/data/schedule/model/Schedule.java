package app.watchnode.data.schedule.model;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private String user;
    private String name;
    private ArrayList<Event> events;

    public Schedule(String user, String name, ArrayList<Event> events) {
        this.user = user;
        this.name = name;
        this.events = events;
    }

    public String getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public static
    Schedule fromJson(JSONObject obj) {
        try {
            ArrayList<Event> events = new ArrayList<Event>();
            JSONArray arr = obj.getJSONArray("events");
            for(int i = 0; i < arr.length(); i++){
                events.add(Event.fromJson(arr.getJSONObject(i)));
            }
            return new Schedule(
                    obj.getString("user"),
                    obj.getString("name"),
                    events
            );
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
