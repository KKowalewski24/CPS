package pl.jkkk.cps.executionmode.commandline;

import pl.jkkk.cps.Main;
import pl.jkkk.cps.logic.exception.FileOperationException;
import pl.jkkk.cps.logic.model.ADC;
import pl.jkkk.cps.logic.model.DAC;
import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.Signal;
import pl.jkkk.cps.logic.report.LatexGenerator;
import pl.jkkk.cps.logic.report.ReportType;

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

    public static void caseDiscreteFourierTransformation() {

    }

    public static void caseCosineTransformation() {

    }

    public static void caseWalshHadamardTransformation() {

    }

    public static void caseWaveletTransformation() {

    }
}
    