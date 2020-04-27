package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OneArgsOperationType {

    /*------------------------ FIELDS REGION ------------------------*/
    SAMPLING("Próbkowanie"),
    QUANTIZATION("Kwantyzacja"),
    SIGNAL_RECONSTRUCTION("Rekonstrukcja sygnału");

    private final String name;

    /*------------------------ METHODS REGION ------------------------*/
    OneArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OneArgsOperationType fromString(final String text) {
        return Arrays.asList(OneArgsOperationType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(OneArgsOperationType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
    