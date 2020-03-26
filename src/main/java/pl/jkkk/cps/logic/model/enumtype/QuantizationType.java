package pl.jkkk.cps.logic.model.enumtype;

public enum QuantizationType {

    EVEN_QUANTIZATION_WITH_TRUNCATION("kwantyzacja równomierna z obcięciem"),
    EVEN_QUANTIZATION_WITH_ROUNDING("Kwantyzacja równomierna z zaokrągleniem");

    private final String name;

    QuantizationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
