package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;

public enum QuantizationType {

    EVEN_QUANTIZATION_WITH_TRUNCATION("Kwantyzacja równomierna z obcięciem"),
    EVEN_QUANTIZATION_WITH_ROUNDING("Kwantyzacja równomierna z zaokrągleniem");

    private final String name;

    QuantizationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static QuantizationType fromString(String text) {
        return Arrays.asList(QuantizationType.values())
                .stream()
                .filter((it) -> it.name.equals(text))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
