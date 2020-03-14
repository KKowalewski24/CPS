package pl.jkkk.cps.logic.model.signal;

import pl.jkkk.cps.logic.model.Data;

import java.util.List;

public interface Signal {

    List<Data> generate();

    double meanValue();

    double absMeanValue();

    double rmsValue();

    double varianceValue();

    double meanPowerValue();
}
