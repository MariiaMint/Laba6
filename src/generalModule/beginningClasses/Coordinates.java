package generalModule.beginningClasses;

public class Coordinates {
    private Double x; //Значение поля должно быть больше -386, Поле не может быть null
    private double y;


    public Coordinates(Double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}