package pl.jkkk.cps.logic.model.enumtype;

public enum TwoArgsOperationType {

    ADDITION("Dodawanie"),
    SUBTRACTION("Ddejmowanie"),
    MULTIPLICATION("Mnożenie"),
    DIVISION("Dzielenie");

    private final String name;

    TwoArgsOperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
