package pl.jkkk.cps.logic.model.signal;

import pl.jkkk.cps.logic.model.window.Window;

public class LowPassFilter extends DiscreteSignal {

    final int M;
    final double fo;
    final double K;
    final Window window;

    public LowPassFilter(double sampleRate, int M, double fo, Window window) {
        super(0.0, M, sampleRate);
        if ((M & 0x01) == 0) {
            throw new IllegalArgumentException("M must be odd value!");
        }
        this.M = M;
        this.fo = fo;
        this.K = sampleRate / fo;
        this.window = window;
    }

    @Override
    public double value(int n) {
        int c = (M - 1) / 2; /* center sample */
        double result;
        if (n == c) {
            result = 2.0 / K;
        } else {
            result = Math.sin(2.0 * Math.PI * (n - c) / K)
                    / (Math.PI * (n - c));
        }
        return result * window.w(n);
    }
}
