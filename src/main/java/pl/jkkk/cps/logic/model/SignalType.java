package pl.jkkk.cps.logic.model;

public enum SignalType {

    UNIFORM_NOISE("szum o rozkładzie jednostajnym"),
    GAUSSIAN_NOISE("szum gaussowski"),
    SINUSOIDAL_SIGNAL("sygnał sinusoidalny"),
    SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL("sygnał sinusoidalny wyprostowany jednopołówkowo"),
    SINUSOIDAL_RECTIFIED_IN_TWO_HALVES("sygnał sinusoidalny wyprostowany dwupołówkowo"),
    RECTANGULAR_SIGNAL("sygnał prostokątny"),
    SYMMETRICAL_RECTANGULAR_SIGNAL("sygnał prostokątny symetryczny"),
    TRIANGULAR_SIGNAL("sygnał trójkątny"),
    UNIT_JUMP("skok jednostkowy"),
    UNIT_IMPULSE("impuls jednostkowy"),
    IMPULSE_NOISE("szum impulsowy");

    private final String name;

    SignalType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
