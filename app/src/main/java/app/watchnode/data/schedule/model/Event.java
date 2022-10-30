package app.watchnode.data.schedule.model;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

public class Event {
    private String name;
    private Long start;
    private Long end;
    private String location;

    public Event(String name, Long start, Long end, String location) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return end;
    }

    public String getLocation() {
        return location;
    }

    public static @Nullable
    Event fromJson(JSONObject obj) {
        try {
            return new Event(
                    obj.getString("name"),
                    obj.getLong("start"),
                    obj.getLong("end"),
                    obj.getString("location")
            );
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
