package pl.jkkk.cps.logic.model;

public class Range {

    private final double begin;
    private final double end;
    private final int quantity;

    public Range(double begin, double end, int quantity) {
        this.begin = begin;
        this.end = end;
        this.quantity = quantity;
    }

    public double getBegin() {
        return begin;
    }

    public double getEnd() {
        return end;
    }

    public int getQuantity() {
        return quantity;
    }
}
