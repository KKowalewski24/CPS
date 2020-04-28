package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DecimationType {

    /*------------------------ FIELDS REGION ------------------------*/
    TIME_DOMAIN("Dziedzina czasu"),
    FREQUENCY_DOMAIN("Dziedzina częstotliwości");

    private final String name;

    /*------------------------ METHODS REGION ------------------------*/
    DecimationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DecimationType fromString(final String text) {
        return Arrays.asList(DecimationType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(DecimationType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
