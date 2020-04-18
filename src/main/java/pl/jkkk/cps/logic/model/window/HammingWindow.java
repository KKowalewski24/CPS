package pl.jkkk.cps.logic.model.window;

public class HammingWindow implements Window {

    private final int M;

    public HammingWindow(int M) {
        this.M = M;
    }

    @Override
    public double w(int n) {
        return 0.53836 - 0.46164 * Math.cos(2.0 * Math.PI * n / M);
    }
}
