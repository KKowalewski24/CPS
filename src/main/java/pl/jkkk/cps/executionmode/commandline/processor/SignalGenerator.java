package pl.jkkk.cps.executionmode.commandline.processor;

import pl.jkkk.cps.executionmode.commandline.enumtype.SignalTypeCmd;
import pl.jkkk.cps.executionmode.commandline.enumtype.WindowTypeCmd;
import pl.jkkk.cps.logic.model.signal.BandPassFilter;
import pl.jkkk.cps.logic.model.signal.GaussianNoise;
import pl.jkkk.cps.logic.model.signal.HighPassFilter;
import pl.jkkk.cps.logic.model.signal.ImpulseNoise;
import pl.jkkk.cps.logic.model.signal.LowPassFilter;
import pl.jkkk.cps.logic.model.signal.RectangularSignal;
import pl.jkkk.cps.logic.model.signal.RectangularSymmetricSignal;
import pl.jkkk.cps.logic.model.signal.Signal;
import pl.jkkk.cps.logic.model.signal.SinusoidalRectifiedOneHalfSignal;
import pl.jkkk.cps.logic.model.signal.SinusoidalRectifiedTwoHalfSignal;
import pl.jkkk.cps.logic.model.signal.SinusoidalSignal;
import pl.jkkk.cps.logic.model.signal.TriangularSignal;
import pl.jkkk.cps.logic.model.signal.UniformNoise;
import pl.jkkk.cps.logic.model.signal.UnitImpulseSignal;
import pl.jkkk.cps.logic.model.signal.UnitJumpSignal;

public final class SignalGenerator {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    private SignalGenerator() {
    }

    public static Signal generate(String[] args) {
        switch (SignalTypeCmd.fromString(args[2])) {
            case UNIFORM_NOISE: {
                return new UniformNoise(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]));
            }
            case GAUSSIAN_NOISE: {
                return new GaussianNoise(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]));
            }
            case SINUSOIDAL_SIGNAL: {
                return new SinusoidalSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            }
            case SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL: {
                return new SinusoidalRectifiedOneHalfSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            }
            case SINUSOIDAL_RECTIFIED_IN_TWO_HALVES: {
                return new SinusoidalRectifiedTwoHalfSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            }
            case RECTANGULAR_SIGNAL: {
                return new RectangularSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Double.parseDouble(args[7]));
            }
            case SYMMETRICAL_RECTANGULAR_SIGNAL: {
                return new RectangularSymmetricSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Double.parseDouble(args[7]));
            }
            case TRIANGULAR_SIGNAL: {
                return new TriangularSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Double.parseDouble(args[7]));
            }
            case UNIT_JUMP: {
                return new UnitJumpSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            }
            case UNIT_IMPULSE: {
                return new UnitImpulseSignal(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Integer.parseInt(args[7]));
            }
            case IMPULSE_NOISE: {
                return new ImpulseNoise(
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]),
                        Double.parseDouble(args[7]));
            }
            case LOW_PASS_FILTER: {
                return new LowPassFilter(
                        Double.parseDouble(args[3]),
                        Integer.parseInt(args[4]),
                        Double.parseDouble(args[5]),
                        WindowTypeCmd.fromEnum(WindowTypeCmd.fromString(args[6]),
                                Integer.parseInt(args[4])));
            }
            case BAND_PASS_FILTER: {
                return new BandPassFilter(
                        Double.parseDouble(args[3]),
                        Integer.parseInt(args[4]),
                        Double.parseDouble(args[5]),
                        WindowTypeCmd.fromEnum(WindowTypeCmd.fromString(args[6]),
                                Integer.parseInt(args[4])));
            }
            case HIGH_PASS_FILTER: {
                return new HighPassFilter(
                        Double.parseDouble(args[3]),
                        Integer.parseInt(args[4]),
                        Double.parseDouble(args[5]),
                        WindowTypeCmd.fromEnum(WindowTypeCmd.fromString(args[6]),
                                Integer.parseInt(args[4])));
            }
        }

        return null;
    }
}
    