package pl.jkkk.cps.logic.model.enumtype;

public enum SignalReconstructionType {

    ZERO_ORDER_EXTRAPOLATION("ekstrapolacja zerowego rzędu"),
    FIRST_ORDER_INTERPOLATION("interpolacja pierwszego rzędu"),
    RECONSTRUCTION_BASED_FUNCTION_SINC("Rekonstrukcja w oparciu o funkcję sinc");

    private final String name;

    SignalReconstructionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
