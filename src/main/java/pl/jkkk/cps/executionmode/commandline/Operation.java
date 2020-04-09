package pl.jkkk.cps.executionmode.commandline;

import java.util.Arrays;

public enum Operation {
    GENERATE("generate"),
    REPRESENT("represent"),
    ADD("add"),
    SUBTRACT("sub"),
    MULTIPLY("mult"),
    DIVIDE("div"),
    SAMPLING("sampl"),
    QUANTIZATION("quant"),
    RECONSTRUCTION("recon");

    private final String abbreviation;

    Operation(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static Operation fromString(final String text) {
        return Arrays.asList(Operation.values())
                .stream()
                .filter(operation -> operation.abbreviation.equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
