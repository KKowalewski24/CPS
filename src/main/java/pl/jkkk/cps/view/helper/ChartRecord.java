package pl.jkkk.cps.view.helper;

public class ChartRecord<T1, T2> {

    /*------------------------ FIELDS REGION ------------------------*/
    private T1 xAxis;
    private T2 yAxis;

    /*------------------------ METHODS REGION ------------------------*/
    public ChartRecord(T1 xAxis, T2 yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public T1 getxAxis() {
        return xAxis;
    }

    public void setxAxis(T1 xAxis) {
        this.xAxis = xAxis;
    }

    public T2 getyAxis() {
        return yAxis;
    }

    public void setyAxis(T2 yAxis) {
        this.yAxis = yAxis;
    }
}
    