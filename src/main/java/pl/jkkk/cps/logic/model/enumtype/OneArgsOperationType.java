package pl.jkkk.cps.logic.model.enumtype;

public enum OneArgsOperationType {

    SAMPLING("próbkowanie"),
    QUANTIZATION("kwantyzacja"),
    SIGNAL_RECONSTRUCTION("rekonstrukcja sygnału");
    private final String name;

    OneArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
    