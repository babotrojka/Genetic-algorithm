package hr.fer.mekorac.genetic;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Beta implements Comparable<Beta>{
    public Double[] bs;
    public double msq;

    public void msq(List<GeneticAlgorithm.Param> params) {
        msq = 0;
        params.forEach(p -> msq += Math.pow(func(p.x, p.y) - p.out, 2));
        msq /= params.size();
    }

    public double func(double x, double y) {
        return Math.sin(bs[0] + bs[1] * x) + bs[2] * Math.cos(x * (bs[3] + y)) * (1 / (1 + Math.exp(Math.pow(x - bs[4], 2))));
    }

    public Beta(Double[] bs) {
        this.bs = bs;
    }

    @Override
    public int compareTo(Beta o) {
        return Double.compare(msq, o.msq);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beta)) return false;
        Beta beta = (Beta) o;
        return Double.compare(beta.msq, msq) == 0 && Arrays.equals(bs, beta.bs);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(msq);
        result = 31 * result + Arrays.hashCode(bs);
        return result;
    }
}
