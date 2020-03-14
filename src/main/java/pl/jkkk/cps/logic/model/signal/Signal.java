package pl.jkkk.cps.logic.model.signal;

import pl.jkkk.cps.logic.model.Data;
import java.util.List;

public interface Signal {

    public List<Data> generate();

    public double meanValue();

    public double absMeanValue();

    public double rmsValue();

    public double varianceValue();

    public double meanPowerValue();
}
