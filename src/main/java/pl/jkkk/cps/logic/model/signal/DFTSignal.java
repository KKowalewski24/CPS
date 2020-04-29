package pl.jkkk.cps.logic.model.signal;

import org.apache.commons.math3.complex.Complex;

public class DFTSignal extends ComplexDiscreteSignal {
    
    protected final DiscreteSignal discreteSignal;

    public DFTSignal(DiscreteSignal discreteSignal) {
        super(0.0, discreteSignal.getNumberOfSamples(), discreteSignal.getSampleRate());
        this.discreteSignal = discreteSignal;
    }

    @Override
    public Complex[] calculate() {
        Complex[] samples = new Complex[discreteSignal.getNumberOfSamples()];
        for (int i = 0; i < samples.length; i++) {
            samples[i] = value(i);
        }
        return samples;
    }

    private Complex value(int n) {
        double Warg = 2.0 * Math.PI / discreteSignal.getNumberOfSamples();
        Complex W = new Complex(Math.cos(Warg), Math.sin(Warg));

        Complex sum = new Complex(0.0);
        for (int i = 0; i < discreteSignal.getNumberOfSamples(); i++) {
            sum = sum.add(new Complex(discreteSignal.value(i)).multiply(W.pow(-n * i)));
        }
        return sum.divide(discreteSignal.getNumberOfSamples());
    }
}
