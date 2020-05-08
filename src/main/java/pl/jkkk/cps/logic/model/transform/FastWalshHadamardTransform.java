package pl.jkkk.cps.logic.model.transform;

public class FastWalshHadamardTransform extends RealTransform {

    @Override
    public double[] transform(final double[] x) {
        mix(x, 0, x.length);
        return x;
    }

    public void mix(final double[] x, int begin, int end) {
        int N = end - begin;
        if (N == 1) {
            return;
        }
        for (int i = 0; i < N / 2; i++) {
            double tmp = x[begin + i];
            x[begin + i] = tmp + x[begin + N / 2 + i];
            x[begin + N / 2 + i] = tmp - x[begin + N / 2 + i];
        }
        mix(x, begin, begin + N / 2);
        mix(x, begin + N / 2, end);
    }
}
