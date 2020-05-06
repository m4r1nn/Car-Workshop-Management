package cars.types;

import cars.utils.CarType;
import cars.utils.Helpers;
import cars.utils.TransmissionType;

public class Standard extends Car {
    private TransmissionType transmission;

    // constructors
    public Standard(int distance, int manufactureYear, String isDiesel) {
        super(distance, manufactureYear, isDiesel);
    }

    public Standard(int distance, int manufactureYear, String isDiesel, String transmission) {
        this(distance, manufactureYear, isDiesel);
        this.type = CarType.Standard;
        this.transmission = TransmissionType.valueOf(transmission);
        this.timeToRepair = 2;
        this.maxCarsToRepair = 3;
    }

    @Override
    public float calculateInsurance() {
        float res = 1f * (Helpers.getCurrentYear() - this.manufactureYear) * 100;
        if (this.diesel) {
            res += 500;
        }
        if (distance > 200000) {
            res += 500;
        }
        return res;
    }

    @Override
    public float calculateDiscountInsurance() {
        return this.calculateInsurance() * 0.95f;
    }

    @Override
    public String toString() {
        return super.toString() + "\tTransmission: " + this.transmission;
    }
}
