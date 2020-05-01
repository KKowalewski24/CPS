package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AlgorithmType {

    /*------------------------ FIELDS REGION ------------------------*/
    BY_DEFINITION("Z definicji"),
    FAST_TRANSFORMATION_IN_SITU("Szybka transformacja in situ"),
    FAST_TRANSFORMATION_RECURSIVE("Szybka transformacja rekurencyjna");

    private final String name;

    /*------------------------ METHODS REGION ------------------------*/
    AlgorithmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AlgorithmType fromString(final String text) {
        return Arrays.asList(AlgorithmType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(AlgorithmType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }
}
