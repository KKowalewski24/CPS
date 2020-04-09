package pl.jkkk.cps.textinput;

import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.signal.GaussianNoise;
import pl.jkkk.cps.logic.model.signal.ImpulseNoise;
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
import pl.jkkk.cps.logic.readerwriter.FileReaderWriter;

import java.io.FileWriter;
import java.util.List;

public class MainText {

    public void main(String[] args) throws Exception {
        final FileReaderWriter<Signal> readerWriter = new FileReaderWriter<>("generated_signal");

        Operation operation = Operation.fromString(args[0]);
        Signal signal;
        switch (operation) {
            case GENERATE:
                signal = generate(args);
                readerWriter.write(signal);
                break;
            case REPRESENT:
                signal = readerWriter.read();
                List<Data> data = signal.generateDiscreteRepresentation();
                FileWriter writer = new FileWriter("signal_data");
                for (Data d : data) {
                    writer.append(String.valueOf(d.getX()));
                    writer.append("\t");
                    writer.append(String.valueOf(d.getY()));
                    writer.append("\n");
                }
                writer.close();
        }
    }

    private static Signal generate(String[] args) {
        switch (SignalType.fromString(args[1])) {
            case UNIFORM_NOISE:
                return new UniformNoise(Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]));
            case GAUSSIAN_NOISE:
                return new GaussianNoise(Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]));
            case SINUSOIDAL_SIGNAL:
                return new SinusoidalSignal(Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]));
            case SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL:
                return new SinusoidalRectifiedOneHalfSignal(Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]));
            case SINUSOIDAL_RECTIFIED_IN_TWO_HALVES:
                return new SinusoidalRectifiedTwoHalfSignal(Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]));
            case RECTANGULAR_SIGNAL:
                return new RectangularSignal(Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            case SYMMETRICAL_RECTANGULAR_SIGNAL:
                return new RectangularSymmetricSignal(Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            case TRIANGULAR_SIGNAL:
                return new TriangularSignal(Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
            case UNIT_JUMP:
                return new UnitJumpSignal(Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]));
            case UNIT_IMPULSE:
                return new UnitImpulseSignal(Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]),
                        Integer.parseInt(args[6]));
            case IMPULSE_NOISE:
                return new ImpulseNoise(Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]),
                        Double.parseDouble(args[6]));
        }
        return null;
    }
}
