package pl.jkkk.cps.logic.model.signal;

import java.util.List;

import pl.jkkk.cps.logic.model.Data;
import pl.jkkk.cps.logic.model.Range;

public interface Signal {

    List<Data> generate();

    List<Range> generateHistogram(int numberOfRanges);

    double meanValue();

    double absMeanValue();

    double rmsValue();

    double varianceValue();

    double meanPowerValue();
}
