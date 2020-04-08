package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;

public enum OneArgsOperationType {

    SAMPLING("Próbkowanie"),
    QUANTIZATION("Kwantyzacja"),
    SIGNAL_RECONSTRUCTION("Rekonstrukcja sygnału");
    private final String name;

    OneArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OneArgsOperationType fromString(String text) {
        return Arrays.asList(OneArgsOperationType.values())
                .stream()
                .filter((it) -> it.name.equals(text))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
    