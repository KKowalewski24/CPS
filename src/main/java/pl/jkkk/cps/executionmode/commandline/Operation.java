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
    EVEN_QUANTIZATION_WITH_TRUNCATION("qu_trun"),
    EVEN_QUANTIZATION_WITH_ROUNDING("qu_roud"),

    RECONSTRUCTION("recon"),
    ZERO_ORDER_EXTRAPOLATION("zero_order"),
    FIRST_ORDER_INTERPOLATION("first_order"),
    RECONSTRUCTION_BASED_FUNCTION_SINC("sinc"),

    COMPARISON("comp"),
    DRAW_CHARTS("draw");

    private final String abbreviation;

    Operation(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static Operation fromString(final String text) {
        return Arrays.asList(Operation.values())
                .stream()
                .filter(operation -> operation.abbreviation.equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
