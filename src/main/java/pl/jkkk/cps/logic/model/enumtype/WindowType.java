package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;

public enum WindowType {
    RECTANGULAR_WINDOW("Okno Prostokątne"),
    HAMMING_WINDOW("Okno Hamminga"),
    HANNING_WINDOW("Okno Hanninga"),
    BLACKMAN_WINDOW("Okno Blackmana");

    private final String name;

    WindowType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WindowType fromString(final String text) {
        return Arrays.asList(WindowType.values())
                .stream()
                .filter(operation -> operation.name.equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
