package pl.jkkk.cps.view.helper;

public class ChartRecord<T1, T2> {

    /*------------------------ FIELDS REGION ------------------------*/
    private T1 axisX;
    private T2 axisY;

    /*------------------------ METHODS REGION ------------------------*/
    public ChartRecord(T1 axisX, T2 axisY) {
        this.axisX = axisX;
        this.axisY = axisY;
    }

    public T1 getAxisX() {
        return axisX;
    }

    public void setAxisX(T1 axisX) {
        this.axisX = axisX;
    }

    public T2 getAxisY() {
        return axisY;
    }

    public void setAxisY(T2 axisY) {
        this.axisY = axisY;
    }
}
    