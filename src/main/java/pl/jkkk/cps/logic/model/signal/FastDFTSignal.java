package pl.jkkk.cps.logic.model.signal;

import org.apache.commons.math3.complex.Complex;

public class FastDFTSignal extends DFTSignal {
    
    public FastDFTSignal(DiscreteSignal discreteSignal) {
        super(discreteSignal);
    }

    @Override
    public Complex[] calculate() {
        /* prepare vector of samples */
        double[] samples = new double[discreteSignal.getNumberOfSamples()];
        for (int i = 0; i < samples.length; i++) {
            samples[i] = discreteSignal.value(i);
        }
        samples = mixSamples(samples);

        /* prepare vector of W */
        Complex[] W = calculateW(samples.length);

        return null;
    }

    protected Complex fft(double[] samples, int begin, int end, Complex[] W, int m) {
        /*int N = end - begin;

        if (N == 1) {
            return new Complex(samples[begin]);
        }

        Complex a = fft(samples, begin, begin + N / 2, W);
        Complex b = fft(samples, begin + N / 2, end, W);
        Complex w = W[W.length * 2 / N*/
        return null;
    }
    
    protected Complex[] calculateW(int N) {
        double Warg = 2.0 * Math.PI / N;
        Complex W = new Complex(Math.cos(Warg), Math.sin(Warg));
        Complex[] allW = new Complex[N/2];
        for (int i = 0; i < allW.length; i++) {
            allW[i] = W.pow(i);
        }
        return allW;
    }

    /**
     * This function 'recursively' splits vector of samples into two vectors (in situ),
     * one contains odd indexed samples and the other contains even indexed samples,
     * it does it many times till vectors will contain only one sample.
     *
     * Don't worry, there is no real recursive calls, it use some tricky algorithm.
     */
    protected double[] mixSamples(double[] samples) {
        /* calculate number of bits of samples.length */
        int numberOfBits = 0;
        for (int i = 0; i < 32; i++) {
            if (((0x01 << i) & samples.length) != 0) {
                numberOfBits = i;
                break;
            }
        }

        /* mix */
        double[] mixedSamples = new double[samples.length];
        for (int i = 0; i < samples.length; i++) {
            int newIndex = reverseBits(i, numberOfBits);
            mixedSamples[newIndex] = samples[i];
        }
        return mixedSamples;
    }

    protected int reverseBits(int value, int numberOfBits) {
        for (int i = 0; i < numberOfBits / 2; i++) {
            int j = numberOfBits - i - 1;
            if (((value >> i) & 0x01) != ((value >> j) & 0x01)) {
                value ^= ((0x01 << i) | (0x01 << j));
            }
        }
        return value;
    }
}
