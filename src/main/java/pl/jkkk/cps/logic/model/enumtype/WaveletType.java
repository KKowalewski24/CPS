package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WaveletType {

    /*------------------------ FIELDS REGION ------------------------*/
    DB4("DB4");

    private final String name;

    /*------------------------ METHODS REGION ------------------------*/
    WaveletType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WaveletType fromString(final String text) {
        return Arrays.asList(WaveletType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(WaveletType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
