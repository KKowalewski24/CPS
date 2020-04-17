package pl.jkkk.cps.logic.model.signal;

public class ConvolutionSignal extends DiscreteSignal {

    private final DiscreteSignal s1;
    private final DiscreteSignal s2;

    public ConvolutionSignal(DiscreteSignal s1, DiscreteSignal s2){
        super(s1.getRangeStart(), 
                (s1.getNumberOfSamples() + s2.getNumberOfSamples() - 1) * (1.0 / s1.getSampleRate()), 
                s1.getSampleRate());
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public double value(int i){
        double sum = 0.0;
        
        for (int k = (i < s1.getNumberOfSamples() ? 0 : i - s1.getNumberOfSamples() + 1); 
                k <= i; k++){
            sum += s2.value(k) * s1.value(i - k);
        }
        
        return sum;
    }
}
