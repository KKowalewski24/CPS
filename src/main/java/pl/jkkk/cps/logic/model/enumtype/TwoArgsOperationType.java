package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;

public enum TwoArgsOperationType {

    ADDITION("Dodawanie"),
    SUBTRACTION("Ddejmowanie"),
    MULTIPLICATION("Mnożenie"),
    DIVISION("Dzielenie"),
    CONVOLUTION("Splot");

    private final String name;

    TwoArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TwoArgsOperationType fromString(final String text) {
        return Arrays.asList(TwoArgsOperationType.values())
                .stream()
                .filter(operation -> operation.name.equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
