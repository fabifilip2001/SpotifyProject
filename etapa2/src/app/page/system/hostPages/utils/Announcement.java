package app.page.system.hostPages.utils;

import lombok.Getter;

public final class Announcement {
    @Getter
    private final String name;
    @Getter
    private final String description;

    public Announcement(final String name, final String description) {
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
