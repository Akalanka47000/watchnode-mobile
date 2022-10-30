package app.watchnode.model;

public class Notification {
    private String title;

    public Notification() {
    }

    public Notification(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
