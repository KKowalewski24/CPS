package pl.jkkk.cps.logic.report;

import pl.jkkk.cps.Main;
import pl.jkkk.cps.logic.readerwriter.ReportWriter;

import java.text.DecimalFormat;
import java.util.List;

public class LatexGenerator {

    /*------------------------ FIELDS REGION ------------------------*/
    private ReportWriter reportWriter = new ReportWriter();
    private final String filename;
    private StringBuilder inputParameters = new StringBuilder();
    private StringBuilder summaryForSignal = new StringBuilder();
    private StringBuilder summaryForComparison = new StringBuilder();
    DecimalFormat df = new DecimalFormat("##.####");

    /*------------------------ METHODS REGION ------------------------*/
    public LatexGenerator(String filename) {
        this.filename = filename;
    }

    public void createSummaryForInputParameters(List<String> args, int startNumber) {
        for (int i = startNumber; i < args.size(); i++) {
            inputParameters.append(args.get(i)).append(" & ");
        }
        inputParameters.toString();
    }

    public void createSummaryForSignal(double meanValue, double absMeanValue, double rmsValue,
                                       double varianceValue, double meanPowerValue) {
        summaryForSignal
                .append("Wyliczone parametry:\n")
                .append("\\begin{itemize}\n")
                .append("\t\\item Wartość średnia sygnału: ")
                .append(df.format(meanValue) + "\n")
                .append("\t\\item Wartość średnia bezwzględna sygnału: ")
                .append(df.format(absMeanValue) + "\n")
                .append("\t\\item Wartość skuteczna sygnału: ")
                .append(df.format(rmsValue) + "\n")
                .append("\t\\item Wariancja sygnału: ")
                .append(df.format(varianceValue) + "\n")
                .append("\t\\item Moc średnia sygnału: ")
                .append(df.format(meanPowerValue) + "\n")
                .append("\\end{itemize}\n")
                .toString();
    }

    public void createSummaryForComparison(double meanSquaredError, double signalToNoiseRatio,
                                           double peakSignalToNoiseRatio, double maximumDifference,
                                           double effectiveNumberOfBits, double overallTime) {
        //        summaryForComparison
        //                .append("Wyliczone parametry:\n")
        //                .append("\\begin{itemize}\n")
        //                .append("\t\\item Błąd średniokwadratowy: ")
        //                .append(df.format(meanSquaredError) + "\n")
        //                .append("\t\\item Stosunek sygnał - szum: ")
        //                .append(df.format(signalToNoiseRatio) + "\n")
        //                .append("\t\\item Szczytowy stosunek sygnał - szum: ")
        //                .append(df.format(peakSignalToNoiseRatio) + "\n")
        //                .append("\t\\item Maksymalna różnica: ")
        //                .append(df.format(maximumDifference) + "\n")
        //                .append("\t\\item Efektywna liczba bitów: ")
        //                .append(df.format(effectiveNumberOfBits) + "\n")
        //                .append("\t\\item Czas transformacji: ")
        //                .append(df.format(overallTime) + "\n")
        //                .append("\\end{itemize}\n");

        summaryForComparison
                .append(df.format(meanSquaredError))
                .append(" & ")
                .append(df.format(signalToNoiseRatio))
                .append(" & ")
                .append(df.format(peakSignalToNoiseRatio))
                .append(" & ")
                .append(df.format(maximumDifference))
                .append(" & ")
                .append(df.format(effectiveNumberOfBits))
                .toString();
    }

    public void generate(ReportType reportType) {
        if (ReportType.INPUT_PARAMETERS == reportType) {
            reportWriter.writePlainText(filename, Main.getMainArgs(),
                    new StringBuilder()
                            .append(inputParameters)
                            .toString());
        } else if (ReportType.SIGNAL == reportType) {
            reportWriter.writePlainText(filename, Main.getMainArgs(),
                    new StringBuilder()
                            .append(summaryForSignal)
                            .toString());
        } else if (ReportType.COMPARISON == reportType) {
            reportWriter.writePlainText(filename, Main.getMainArgs(),
                    new StringBuilder()
                            .append(summaryForComparison)
                            .toString());
        }
    }
}
    