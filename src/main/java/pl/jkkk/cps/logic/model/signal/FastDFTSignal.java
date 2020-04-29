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

        /* calculate FFT */
        Complex[] FFT = new Complex[samples.length];
        for (int i = 0; i < samples.length; i++) {
            FFT[i] = recursiveFFT(samples, i);
        }

        return FFT;
    }

    /**
     * This is in situ implementation of FFT
     */
    protected Complex[] inSituFFT(double[] samples) {
        samples = mixSamples(samples);

        Complex[] X = new Complex[samples.length];
        for (int i = 0; i < X.length; i++) {
            X[i] = new Complex(samples[i]);
        }

        for (int N = 2; N <= X.length; N *= 2) {
            for (int i = 0; i < X.length / N; i++) { /* repeat for each N-point transform */
                for (int m = 0; m < N / 2; m++) {
                    /* w param */
                    double Warg = 2.0 * Math.PI / N;
                    Complex w = new Complex(Math.cos(Warg), Math.sin(Warg)).pow(-m);
                    /* butterfly */
                    int offset = i * N;
                    Complex tmp = X[offset + m + N/2].multiply(w);
                    X[offset + m + N/2] = X[offset + m].subtract(tmp);
                    X[offset + m] = X[offset + m].add(tmp);
                }
            }
        }
        
        return X;
    }

    /**
     * This is recursive implementation of FFT, each call require new
     * memory allocation
     */
    protected Complex recursiveFFT(double[] samples, int m) {

        /* stop condition */
        if (samples.length == 1) {
            return new Complex(samples[0]);
        }

        /* split into odd indexed and even indexed samples */
        double[] even = new double[samples.length / 2];
        double[] odd = new double[samples.length / 2];
        int evenIterator = 0, oddIterator = 0;
        for (int i = 0; i < samples.length; i++) {
            if (i % 2 == 0)
                even[evenIterator++] = samples[i];
            else
                odd[oddIterator++] = samples[i];
        }

        /* call recursively for each group of samples */
        Complex a = recursiveFFT(even, m);
        Complex b = recursiveFFT(odd, m);

        /* calculate value of W_{samples.length}^{-m} */
        double Warg = 2.0 * Math.PI / samples.length;
        Complex w = new Complex(Math.cos(Warg), Math.sin(Warg)).pow(-m);

        /* return result */
        return a.add(w.multiply(b));
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
