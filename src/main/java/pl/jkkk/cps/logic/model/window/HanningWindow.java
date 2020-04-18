package pl.jkkk.cps.logic.model.window;

public class HanningWindow implements Window {

    private final int M;

    public HanningWindow(int M) {
        this.M = M;
    }

    @Override
    public double w(int n) {
        return 0.5 - 0.5 * Math.cos(2.0 * Math.PI * n / M);
    }
}
