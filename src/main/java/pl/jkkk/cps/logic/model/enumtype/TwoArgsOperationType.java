package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;

public enum TwoArgsOperationType {

    ADDITION("Dodawanie"),
    SUBTRACTION("Ddejmowanie"),
    MULTIPLICATION("Mnożenie"),
    DIVISION("Dzielenie");

    private final String name;

    TwoArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TwoArgsOperationType fromString(String text) {
        return Arrays.asList(TwoArgsOperationType.values())
                .stream()
                .filter((it) -> it.name.equals(text))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
