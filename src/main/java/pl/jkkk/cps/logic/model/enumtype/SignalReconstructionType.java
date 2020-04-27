package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SignalReconstructionType {

    /*------------------------ FIELDS REGION ------------------------*/
    ZERO_ORDER_EXTRAPOLATION("Ekstrapolacja zerowego rzędu"),
    FIRST_ORDER_INTERPOLATION("Interpolacja pierwszego rzędu"),
    RECONSTRUCTION_BASED_FUNCTION_SINC("Rekonstrukcja w oparciu o funkcję sinc");

    private final String name;

    /*------------------------ METHODS REGION ------------------------*/
    SignalReconstructionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SignalReconstructionType fromString(final String text) {
        return Arrays.asList(SignalReconstructionType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(SignalReconstructionType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
