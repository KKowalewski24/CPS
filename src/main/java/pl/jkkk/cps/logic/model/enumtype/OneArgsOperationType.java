package pl.jkkk.cps.logic.model.enumtype;

public enum OneArgsOperationType {

    SAMPLING("Próbkowanie"),
    QUANTIZATION("Kwantyzacja"),
    SIGNAL_RECONSTRUCTION("Rekonstrukcja sygnału");
    private final String name;

    OneArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
    