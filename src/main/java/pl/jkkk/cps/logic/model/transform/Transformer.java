package pl.jkkk.cps.logic.model.transform;

import org.apache.commons.math3.complex.Complex;
import pl.jkkk.cps.logic.model.signal.DiscreteComplexSignal;
import pl.jkkk.cps.logic.model.signal.DiscreteSignal;
import pl.jkkk.cps.logic.model.signal.StaticDiscreteComplexSignal;
import pl.jkkk.cps.logic.model.signal.StaticDiscreteSignal;

public class Transformer {

    /* discrete fourier transform */
    final private DiscreteFourierTransform discreteFourierTransform;
    final private InSituFastFourierTransform inSituFastFourierTransform;
    final private RecursiveFastFourierTransform recursiveFastFourierTransform;

    /* discrete cosine transform */
    final private DiscreteCosineTransform discreteCosineTransform;
    final private FastCosineTransform fastCosineTransform;

    /* discrete Walsh-Hadamrd transform */
    final private WalshHadamardTransform walshHadamardTransform;
    final private FastWalshHadamardTransform fastWalshHadamardTransform;

    /* discrete wavelet transform */
    final private DiscreteWaveletTransform discreteWaveletTransform;

    public Transformer() {
        discreteFourierTransform = new DiscreteFourierTransform();
        inSituFastFourierTransform = new InSituFastFourierTransform();
        recursiveFastFourierTransform = new RecursiveFastFourierTransform();
        discreteCosineTransform = new DiscreteCosineTransform();
        fastCosineTransform = new FastCosineTransform();
        walshHadamardTransform = new WalshHadamardTransform();
        fastWalshHadamardTransform = new FastWalshHadamardTransform();
        discreteWaveletTransform = new DiscreteWaveletTransform();
    }

    public DiscreteComplexSignal discreteFourierTransform(DiscreteSignal discreteSignal) {
        return transformRealSignalToComplexSignal(discreteSignal, discreteFourierTransform);
    }

    public DiscreteComplexSignal fastFourierTransformInSitu(DiscreteSignal discreteSignal) {
        return transformRealSignalToComplexSignal(discreteSignal, inSituFastFourierTransform);
    }

    public DiscreteComplexSignal fastFourierTransformRecursive(DiscreteSignal discreteSignal) {
        return transformRealSignalToComplexSignal(discreteSignal, recursiveFastFourierTransform);
    }

    public DiscreteSignal discreteCosineTransform(DiscreteSignal discreteSignal) {
        return transformRealSignalToRealSignal(discreteSignal, discreteCosineTransform);
    }

    public DiscreteSignal fastCosineTransform(DiscreteSignal discreteSignal) {
        return transformRealSignalToRealSignal(discreteSignal, fastCosineTransform);
    }

    public DiscreteSignal walshHadamardTransform(DiscreteSignal discreteSignal) {
        return transformRealSignalToRealSignal(discreteSignal, walshHadamardTransform);
    }

    public DiscreteSignal fastWalshHadamardTransform(DiscreteSignal discreteSignal) {
        return transformRealSignalToRealSignal(discreteSignal, fastWalshHadamardTransform);
    }

    public DiscreteSignal discreteWaveletTransform(DiscreteSignal discreteSignal) {
        return transformRealSignalToRealSignal(discreteSignal, discreteWaveletTransform);
    }

    private DiscreteSignal transformRealSignalToRealSignal(DiscreteSignal signal, RealTransform realTransform) {
        double[] samples = new double[signal.getNumberOfSamples()];
        for (int i = 0; i < signal.getNumberOfSamples(); i++) {
            samples[i] = signal.value(i);
        }
        samples = realTransform.transform(samples);
        return new StaticDiscreteSignal(samples, signal.getSampleRate());
    }

    private DiscreteComplexSignal transformComplexSignalToComplexSignal(DiscreteComplexSignal signal,
            ComplexTransform complexTransform) {
        Complex[] samples = new Complex[signal.getNumberOfSamples()];
        for (int i = 0; i < signal.getNumberOfSamples(); i++) {
            samples[i] = signal.value(i);
        }
        samples = complexTransform.transform(samples);
        return new StaticDiscreteComplexSignal(samples, signal.getSampleRate());
    }

    private DiscreteComplexSignal transformRealSignalToComplexSignal(DiscreteSignal signal,
            ComplexTransform complexTransform) {
        double[] samples = new double[signal.getNumberOfSamples()];
        for (int i = 0; i < signal.getNumberOfSamples(); i++) {
            samples[i] = signal.value(i);
        }
        Complex[] resultSamples = complexTransform.transform(samples);
        return new StaticDiscreteComplexSignal(resultSamples, signal.getSampleRate());
    }
}
