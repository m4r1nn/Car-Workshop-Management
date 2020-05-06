package cars.types;

import cars.utils.CarType;

public abstract class Car {
    protected CarType type;
    protected int id;
    protected int distance;
    protected int manufactureYear;
    protected boolean diesel;

    protected int timeToRepair;

    // maximum numbers of cars of that type that a worker can repair at a time
    protected int maxCarsToRepair;

    // for automatically increment the id
    private static int idCounter = 0;

    // constructor
    public Car(int distance, int manufactureYear, String isDiesel) {
        this.distance = distance;
        this.manufactureYear = manufactureYear;
        if (isDiesel.equals("yes")) {
            this.diesel = true;
        } else {
            this.diesel = false;
        }
        this.id = idCounter++;
    }

    public abstract float calculateInsurance();
    public abstract float calculateDiscountInsurance();

    public int getId() {
        return this.id;
    }

    public CarType getType() {
        return this.type;
    }

    public int getMaxCarsToRepair() {
        return this.maxCarsToRepair;
    }

    public int getTimeToRepair() {
        return this.timeToRepair;
    }

    @Override
    public String toString() {
        return "" + this.id + " " + this.type + "\tDistance: " + this.distance + "\tYear: " + this.manufactureYear
                + "\nDiesel=" + this.diesel;
    }
}
