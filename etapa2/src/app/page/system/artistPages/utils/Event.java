package app.page.system.artistPages.utils;

import lombok.Getter;

public class Event {
    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final String date;

    public Event(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return name
                +
                " - "
                +
                date
                +
                ":\n\t"
                +
                description;
    }
}
