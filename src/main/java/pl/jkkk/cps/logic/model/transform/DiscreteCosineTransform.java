package pl.jkkk.cps.logic.model.transform;

public class DiscreteCosineTransform extends RealTransform {

    @Override
    public double[] transform(double[] x) {

        int N = x.length;
        double[] X = new double[N];

        for (int m = 0; m < N; m++) {
            double sum = 0.0;
            for (int n = 0; n < N; n++) {
                sum += x[n] * Math.cos(Math.PI * (2.0 * n + 1) * m / (2.0 * N));
            }
            X[m] = c(m, N) * sum;
        }

        return X;
    }

    private double c(int m, int N) {
        if (m == 0) {
            return Math.sqrt(1.0 / N);
        } else {
            return Math.sqrt(2.0 / N);
        }
    }
}
