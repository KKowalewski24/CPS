package pl.jkkk.cps.logic.model.transform;

import org.apache.commons.math3.complex.Complex;

public class InSituFastFourierTransform extends Transform {
    
    /**
     * This is in situ implementation of FFT
     */
    @Override
    public Complex[] transform(Complex[] x) {
        Complex[] X = mixSamples(x);
        Complex[] W = calculateVectorOfWParams(x.length);

        for (int N = 2; N <= X.length; N *= 2) {
            for (int i = 0; i < X.length / N; i++) { /* repeat for each N-point transform */
                for (int m = 0; m < N / 2; m++) {
                    /* butterfly */
                    int offset = i * N;
                    Complex tmp = X[offset + m + N/2].multiply(retrieveWFromVector(N, -m, W));
                    X[offset + m + N/2] = X[offset + m].subtract(tmp);
                    X[offset + m] = X[offset + m].add(tmp);
                }
            }
        }
        
        return X;
    }

    protected Complex retrieveWFromVector(int N, int k, Complex[] vectorW) {
        /* normalize to [0, N - 1] */
        k = k % N;
        if (k < 0) {
            k += N;
        }

        /* find k in new N (vectorW.length * 2) */
        k = k * ((vectorW.length * 2) / N);
        if (k < vectorW.length) {
            return vectorW[k];
        } else {
            return vectorW[k - vectorW.length].multiply(new Complex(-1));
        }
    }

    protected Complex[] calculateVectorOfWParams(int N) {
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
    protected Complex[] mixSamples(Complex[] samples) {
        /* calculate number of bits of samples.length */
        int numberOfBits = 0;
        for (int i = 0; i < 32; i++) {
            if (((0x01 << i) & samples.length) != 0) {
                numberOfBits = i;
                break;
            }
        }

        /* mix */
        Complex[] mixedSamples = new Complex[samples.length];
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
