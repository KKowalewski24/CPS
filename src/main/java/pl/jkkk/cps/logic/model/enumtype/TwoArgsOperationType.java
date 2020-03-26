package pl.jkkk.cps.logic.model.enumtype;

public enum TwoArgsOperationType {

    ADDITION("dodawanie"),
    SUBTRACTION("odejmowanie"),
    MULTIPLICATION("mnożenie"),
    DIVISION("dzielenie");

    private final String name;

    TwoArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
