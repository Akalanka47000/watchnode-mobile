package app.watchnode.data.schedule.model;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

public class Event {
    private String name;
    private Integer start;
    private Integer end;
    private String location;

    public Event(String name, Integer start, Integer end, String location) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
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
                    obj.getInt("start"),
                    obj.getInt("end"),
                    obj.getString("location")
            );
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
