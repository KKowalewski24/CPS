package pl.jkkk.cps.executionmode.commandline.enumtype;

import java.util.Arrays;

public enum OperationCmd {
    GENERATE("generate"),
    REPRESENT("represent"),

    ADD("add"),
    SUBTRACT("sub"),
    MULTIPLY("mult"),
    DIVIDE("div"),
    CONVOLUTION("conv"),
    CORRELATION("corr"),

    SAMPLING("sampl"),

    QUANTIZATION("quant"),
    EVEN_QUANTIZATION_WITH_TRUNCATION("qu_trun"),
    EVEN_QUANTIZATION_WITH_ROUNDING("qu_roud"),

    RECONSTRUCTION("recon"),
    ZERO_ORDER_EXTRAPOLATION("zero_order"),
    FIRST_ORDER_INTERPOLATION("first_order"),
    RECONSTRUCTION_BASED_FUNCTION_SINC("sinc"),

    DISCRETE_FOURIER_TRANSFORMATION("fou_trans"),
    COSINE_TRANSFORMATION("cos_trans"),
    WALSH_HADAMARD_TRANSFORMATION("wals_trans"),
    WAVELET_TRANSFORMATION("wave_trans"),
    BY_DEFINITION("def"),
    FAST_TRANSFORMATION_IN_SITU("fast_in"),
    FAST_TRANSFORMATION_RECURSIVE("fast_rec"),
    DB4("DB4"),
    DB6("DB6"),
    DB8("DB8"),

    COMPARISON("comp"),
    DRAW_CHARTS("draw");

    private final String abbreviation;

    OperationCmd(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static OperationCmd fromString(final String text) {
        return Arrays.asList(OperationCmd.values())
                .stream()
                .filter(operationCmd -> operationCmd.abbreviation.equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
