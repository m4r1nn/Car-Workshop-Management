package cars.types;

import cars.utils.CarType;
import cars.utils.Helpers;

public class Bus extends Car {
    private int seats;

    // constructors
    public Bus(int distance, int manufactureYear, String isDiesel) {
        super(distance, manufactureYear, isDiesel);
    }

    public Bus(int distance, int manufactureYear, String isDiesel, int seats) {
        this(distance, manufactureYear, isDiesel);
        this.type = CarType.Bus;
        this.seats = seats;
        this.timeToRepair = 3;
        this.maxCarsToRepair = 1;
    }

    @Override
    public float calculateInsurance() {
        float res = 1f * (Helpers.getCurrentYear() - this.manufactureYear) * 200;
        if (this.diesel) {
            res += 1000;
        }
        if (distance > 200000) {
            res += 1000;
        } else if (distance > 100000) {
            res += 500;
        }
        return res;
    }

    @Override
    public float calculateDiscountInsurance() {
        return this.calculateInsurance() * 0.9f;
    }

    @Override
    public String toString() {
        return super.toString() + "\tNumber of seats: " + this.seats;
    }
}
