package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum QuantizationType {

    /*------------------------ FIELDS REGION ------------------------*/
    EVEN_QUANTIZATION_WITH_TRUNCATION("Kwantyzacja równomierna z obcięciem"),
    EVEN_QUANTIZATION_WITH_ROUNDING("Kwantyzacja równomierna z zaokrągleniem");

    private final String name;

    /*------------------------ METHODS REGION ------------------------*/
    QuantizationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static QuantizationType fromString(final String text) {
        return Arrays.asList(QuantizationType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(QuantizationType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
