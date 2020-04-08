package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;

public enum SignalReconstructionType {

    ZERO_ORDER_EXTRAPOLATION("Ekstrapolacja zerowego rzędu"),
    FIRST_ORDER_INTERPOLATION("Interpolacja pierwszego rzędu"),
    RECONSTRUCTION_BASED_FUNCTION_SINC("Rekonstrukcja w oparciu o funkcję sinc");

    private final String name;

    SignalReconstructionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SignalReconstructionType fromString(String text) {
        return Arrays.asList(SignalReconstructionType.values())
                .stream()
                .filter((it) -> it.name.equals(text))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
