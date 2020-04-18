package pl.jkkk.cps.logic.model.enumtype;

import java.util.Arrays;

public enum SignalType {

    UNIFORM_NOISE("Szum o rozkładzie jednostajnym"),
    GAUSSIAN_NOISE("Szum gaussowski"),
    SINUSOIDAL_SIGNAL("Sygnał sinusoidalny"),
    SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL("Sygnał sinusoidalny wyprostowany jednopołówkowo"),
    SINUSOIDAL_RECTIFIED_IN_TWO_HALVES("Sygnał sinusoidalny wyprostowany dwupołówkowo"),
    RECTANGULAR_SIGNAL("Sygnał prostokątny"),
    SYMMETRICAL_RECTANGULAR_SIGNAL("Sygnał prostokątny symetryczny"),
    TRIANGULAR_SIGNAL("Sygnał trójkątny"),
    UNIT_JUMP("Skok jednostkowy"),
    UNIT_IMPULSE("Impuls jednostkowy"),
    IMPULSE_NOISE("Szum impulsowy"),
    LOW_PASS_FILTER("Filtr dolnoprzepustowy"),
    BAND_PASS_FILTER("Filtr pasmowy"),
    HIGH_PASS_FILTER("Filtr górnoprzepustowy");

    private final String name;

    SignalType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SignalType fromString(final String text) {
        return Arrays.asList(SignalType.values())
                .stream()
                .filter(operation -> operation.name.equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
