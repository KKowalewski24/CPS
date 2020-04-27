package pl.jkkk.cps.executionmode.commandline;

import java.util.Arrays;

public enum SignalTypeCmd {
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
    IMPULSE_NOISE("impulse_noise"),
    LOW_PASS_FILTER("low_fil"),
    BAND_PASS_FILTER("band_fil"),
    HIGH_PASS_FILTER("high_fil");

    private final String abbreviation;

    SignalTypeCmd(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static SignalTypeCmd fromString(final String text) {
        return Arrays.asList(SignalTypeCmd.values())
                .stream()
                .filter(signal -> signal.abbreviation.equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
