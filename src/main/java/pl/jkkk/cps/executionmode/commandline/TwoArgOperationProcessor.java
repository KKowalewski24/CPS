package pl.jkkk.cps.executionmode.commandline;

import pl.jkkk.cps.Main;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.model.Operation;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.ConvolutionSignal;
import pl.jkkk.cps.logic.model.signal.CorrelationSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.OperationResultContinuousSignal;
import pl.jkkk.cps.logic.model.signal.OperationResultDiscreteSignal;
import pl.jkkk.cps.logic.model.signal.Signal;
import pl.jkkk.cps.logic.readerwriter.FileReaderWriter;

public class TwoArgOperationProcessor {

    private static int argCounter = 1;

    private static Signal readSignal() throws FileOperationException {
        FileReaderWriter<Signal> readerWriter = 
            new FileReaderWriter<>(Main.getMainArgs().get(argCounter++));
        return readerWriter.read();
    }

    private static void writeSignal(Signal signal) throws FileOperationException {
        FileReaderWriter<Signal> readerWriter = 
            new FileReaderWriter<>(Main.getMainArgs().get(argCounter++));
        readerWriter.write(signal);
    }

    private static void basicOperation(Operation operation) throws FileOperationException {
        Signal s1 = readSignal();
        Signal s2 = readSignal();
        Signal result;
        if (s1 instanceof DiscreteSignal) {
            result = new OperationResultDiscreteSignal(
                    (DiscreteSignal) s1,
                    (DiscreteSignal) s2,
                    operation);
        } else {
            result = new OperationResultContinuousSignal(
                    (ContinuousSignal) s1,
                    (ContinuousSignal) s2,
                    operation);
        }
        writeSignal(result);
    }

    public static void add() throws FileOperationException {
        basicOperation((a, b) -> a + b);
    }

    public static void subtract() throws FileOperationException {
        basicOperation((a, b) -> a - b);
    }

    public static void multiply() throws FileOperationException {
        basicOperation((a, b) -> a * b);
    }

    public static void divide() throws FileOperationException {
        basicOperation((a, b) -> a / b);
    }

    public static void convolution() throws FileOperationException {
        Signal s1 = readSignal();
        Signal s2 = readSignal();
        writeSignal(new ConvolutionSignal(
                    (DiscreteSignal) s1,
                    (DiscreteSignal) s2));
    }

    public static void correlation() throws FileOperationException {
        Signal s1 = readSignal();
        Signal s2 = readSignal();
        writeSignal(new CorrelationSignal(
                    (DiscreteSignal) s1,
                    (DiscreteSignal) s2));
    }
}
