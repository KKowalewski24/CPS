package pl.jkkk.cps.logic.model.signal;

import pl.jkkk.cps.logic.model.window.Window;

public class LowPassFilter extends DiscreteSignal {

    final int M;
    final double f0;
    final double K;
    final Window window;

    public LowPassFilter(double sampleRate, int M, double f0, Window window) {
        super(0.0, M, sampleRate);
        if((M & 0x01) == 0){
            throw new IllegalArgumentException("M must be odd value!");
        }
        this.M = M;
        this.f0 = f0;
        this.K = sampleRate / f0;
        this.window = window;
    }

    @Override
    public double value(int n) {
        int c = (M - 1) / 2; /* center sample */
        if (n == c) {
            return window.w(2.0 / K);
        } else {
            return window.w(Math.sin(2.0 * Math.PI * (n - c) / K)
                / Math.PI * (n - c));
        }
    }
}
