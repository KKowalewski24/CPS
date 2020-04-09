package pl.jkkk.cps.textinput;

import java.util.Arrays;

public enum SignalType {
    UNIFORM_NOISE("uni_noise"),
    GAUSSIAN_NOISE("gauss_noise"),
    SINUSOIDAL_SIGNAL("sin"),
    SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL("sin_one_half"),
    SINUSOIDAL_RECTIFIED_IN_TWO_HALVES("sin_two_half"),
    RECTANGULAR_SIGNAL("rect"),
    SYMMETRICAL_RECTANGULAR_SIGNAL("rect_symm"),
    TRIANGULAR_SIGNAL("triang"),
    UNIT_JUMP("unit_jump"),
    UNIT_IMPULSE("unit_impulse"),
    IMPULSE_NOISE("impulse_noise");

    private final String abbreviation;

    SignalType(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static SignalType fromString(final String text) {
        return Arrays.asList(SignalType.values())
                .stream()
                .filter(signal -> signal.abbreviation.equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
