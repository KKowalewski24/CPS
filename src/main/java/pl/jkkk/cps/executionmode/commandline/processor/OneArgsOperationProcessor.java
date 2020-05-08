package pl.jkkk.cps.executionmode.commandline.processor;

import pl.jkkk.cps.Main;
import pl.jkkk.cps.executionmode.commandline.CommandLineMode;
import pl.jkkk.cps.executionmode.commandline.enumtype.OperationCmd;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.model.ADC;
import pl.jkkk.cps.logic.model.DAC;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.Signal;
import pl.jkkk.cps.logic.model.transform.Transformer;
import pl.jkkk.cps.logic.report.LatexGenerator;
import pl.jkkk.cps.logic.report.ReportType;

import java.util.concurrent.Callable;

public final class OneArgsOperationProcessor {

    /*------------------------ FIELDS REGION ------------------------*/
    private static final ADC adc = new ADC();
    private static final DAC dac = new DAC();
    private static LatexGenerator latexGenerator;

    /*------------------------ METHODS REGION ------------------------*/
    public static void caseSampling() throws FileOperationException {
        Signal signal = CommandLineMode.readSignal(Main.getMainArgs().get(1));
        signal = adc.sampling((ContinuousSignal) signal,
                Double.valueOf(Main.getMainArgs().get(3)));
        CommandLineMode.writeSignal(signal, Main.getMainArgs().get(2));

        latexGenerator = new LatexGenerator("Input_sampling");
        latexGenerator.createSummaryForInputParameters(Main.getMainArgs(), 3);
        latexGenerator.generate(ReportType.INPUT_PARAMETERS);
    }

    public static void caseQuantization() throws FileOperationException {
        Signal signal = CommandLineMode.readSignal(Main.getMainArgs().get(1));

        if (OperationCmd.EVEN_QUANTIZATION_WITH_TRUNCATION
                == OperationCmd.fromString(Main.getMainArgs().get(3))) {

            signal = adc.truncatingQuantization((DiscreteSignal) signal,
                    Integer.valueOf(Main.getMainArgs().get(4)));

        } else if (OperationCmd.EVEN_QUANTIZATION_WITH_ROUNDING
                == OperationCmd.fromString(Main.getMainArgs().get(3))) {

            signal = adc.roundingQuantization((DiscreteSignal) signal,
                    Integer.valueOf(Main.getMainArgs().get(4)));

        }

        CommandLineMode.writeSignal(signal, Main.getMainArgs().get(2));

        latexGenerator = new LatexGenerator("Input_Quant");
        latexGenerator.createSummaryForInputParameters(Main.getMainArgs(), 4);
        latexGenerator.generate(ReportType.INPUT_PARAMETERS);
    }

    public static void caseReconstruction() throws FileOperationException {
        Signal signal = CommandLineMode.readSignal(Main.getMainArgs().get(1));

        if (OperationCmd.ZERO_ORDER_EXTRAPOLATION
                == OperationCmd.fromString(Main.getMainArgs().get(3))) {
            signal = dac.zeroOrderHold((DiscreteSignal) signal);

        } else if (OperationCmd.FIRST_ORDER_INTERPOLATION
                == OperationCmd.fromString(Main.getMainArgs().get(3))) {
            signal = dac.firstOrderHold((DiscreteSignal) signal);

        } else if (OperationCmd.RECONSTRUCTION_BASED_FUNCTION_SINC
                == OperationCmd.fromString(Main.getMainArgs().get(3))) {

            signal = dac.sincBasic((DiscreteSignal) signal,
                    Integer.valueOf(Main.getMainArgs().get(4)));
        }

        CommandLineMode.writeSignal(signal, Main.getMainArgs().get(2));

        latexGenerator = new LatexGenerator("Input_Recon");
        latexGenerator.createSummaryForInputParameters(Main.getMainArgs(), 3);
        latexGenerator.generate(ReportType.INPUT_PARAMETERS);
    }

    public static void caseDiscreteFourierTransformation() throws Exception {
        final Signal loadedSignal = CommandLineMode.readSignal(Main.getMainArgs().get(1));
        Signal signal = null;
        Transformer transformer = new Transformer();

        if (OperationCmd.BY_DEFINITION == OperationCmd.fromString(Main.getMainArgs().get(3))) {
            signal = generateSummaryForTransformationTime(() -> transformer
                    .discreteFourierTransform((DiscreteSignal) loadedSignal));
        } else if (OperationCmd.FAST_TRANSFORMATION_IN_SITU == OperationCmd
                .fromString(Main.getMainArgs().get(3))) {
            signal = generateSummaryForTransformationTime(() -> transformer
                    .fastFourierTransformInSitu((DiscreteSignal) loadedSignal));
        } else if (OperationCmd.FAST_TRANSFORMATION_RECURSIVE == OperationCmd
                .fromString(Main.getMainArgs().get(3))) {
            signal = generateSummaryForTransformationTime(() -> transformer
                    .fastFourierTransformRecursive((DiscreteSignal) loadedSignal));
        }

        CommandLineMode.writeSignal(signal, Main.getMainArgs().get(2));
    }

    public static void caseCosineTransformation() throws Exception {
        final Signal loadedSignal = CommandLineMode.readSignal(Main.getMainArgs().get(1));
        Signal signal = null;
        Transformer transformer = new Transformer();

        if (OperationCmd.BY_DEFINITION == OperationCmd.fromString(Main.getMainArgs().get(3))) {
            signal = generateSummaryForTransformationTime(() -> transformer
                    .discreteCosineTransform((DiscreteSignal) loadedSignal));
        } else if (OperationCmd.FAST_TRANSFORMATION_IN_SITU == OperationCmd
                .fromString(Main.getMainArgs().get(3))) {
            signal = generateSummaryForTransformationTime(() -> transformer
                    .fastCosineTransform((DiscreteSignal) loadedSignal));
        }

        CommandLineMode.writeSignal(signal, Main.getMainArgs().get(2));
    }

    public static void caseWalshHadamardTransformation() throws Exception {
        final Signal loadedSignal = CommandLineMode.readSignal(Main.getMainArgs().get(1));
        Signal signal = null;
        Transformer transformer = new Transformer();

        if (OperationCmd.BY_DEFINITION == OperationCmd.fromString(Main.getMainArgs().get(3))) {
            signal = generateSummaryForTransformationTime(() -> transformer
                    .walshHadamardTransform((DiscreteSignal) loadedSignal));
        } else if (OperationCmd.FAST_TRANSFORMATION_IN_SITU == OperationCmd
                .fromString(Main.getMainArgs().get(3))) {
            signal = generateSummaryForTransformationTime(() -> transformer
                    .fastWalshHadamardTransform((DiscreteSignal) loadedSignal));
        }

        CommandLineMode.writeSignal(signal, Main.getMainArgs().get(2));
    }

    public static void caseWaveletTransformation() throws Exception {
        final Signal loadedSignal = CommandLineMode.readSignal(Main.getMainArgs().get(1));
        Signal signal = null;
        Transformer transformer = new Transformer();

        if (OperationCmd.DB4 == OperationCmd.fromString(Main.getMainArgs().get(3))) {
            signal = generateSummaryForTransformationTime(() -> transformer
                    .discreteWaveletTransform((DiscreteSignal) loadedSignal));
        }

        CommandLineMode.writeSignal(signal, Main.getMainArgs().get(2));
    }

    private static Signal generateSummaryForTransformationTime(Callable<Signal> callable)
            throws Exception {
        long begin = System.currentTimeMillis();
        Signal signal = callable.call();
        double end = ((System.currentTimeMillis() - begin) / 1000.0);

        latexGenerator = new LatexGenerator("trans_gene_time");
        latexGenerator.createSummaryForTransformationGeneratingTime(end);
        latexGenerator.generate(ReportType.TRANSFORMATION_GENERATING);

        return signal;
    }
}
    