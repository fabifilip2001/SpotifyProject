package app.page.system.hostPages.utils;

import lombok.Getter;

public final class Announcement {
    @Getter
    private final String name;
    @Getter
    private final String description;

    public Announcement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name
                +
                ":\n\t"
                +
                description
                +
                "\n";
    }
}
