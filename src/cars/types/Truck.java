package cars.types;

import cars.utils.CarType;
import cars.utils.Helpers;

public class Truck extends Car {
    private int tonnage;

    // constructors
    public Truck(int distance, int manufactureYear, String isDiesel) {
        super(distance, manufactureYear, isDiesel);
    }

    public Truck(int distance, int manufactureYear, String isDiesel, int tonnage) {
        this(distance, manufactureYear, isDiesel);
        this.type = CarType.Truck;
        this.tonnage = tonnage;
        this.timeToRepair = 4;
        this.maxCarsToRepair = 1;
    }

    @Override
    public float calculateInsurance() {
        float res = 1f * (Helpers.getCurrentYear() - this.manufactureYear) * 300;
        if (distance > 800000) {
            res += 700;
        }
        return res;
    }

    @Override
    public float calculateDiscountInsurance() {
        return this.calculateInsurance() * 0.85f;
    }

    @Override
    public String toString() {
        return super.toString() + "\tTonnage: " + this.tonnage;
    }
}
