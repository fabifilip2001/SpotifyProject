package app.page.system.artistPages.utils;

import lombok.Getter;

public class Merch {
    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final int price;

    public Merch(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return name
                +
                " - "
                +
                price
                +
                ":\n\t"
                +
                description;
    }
}
