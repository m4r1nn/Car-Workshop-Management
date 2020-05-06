package employees.types;

import cars.types.Car;
import cars.utils.CarType;
import employees.utils.EmployeeType;
import workshop.utils.Pair;

import java.util.ArrayList;
import java.util.Date;

public abstract class Employee {
    protected EmployeeType type;
    protected int id;
    protected String firstName;
    protected String lastName;
    protected Date birthDate;
    protected Date employDate;

    // for calculating salary
    protected float wageCoefficient;

    // for automatically increment the id
    private static int idCount = 0;

    // store the number of times when busy worker has been chosen by a client to repair a car in the future (for statistics)
    protected int chosenTimes = 0;

    // store tip for statistics
    private float tip = 0f;

    // for storing all cars that the employee works to
    protected ArrayList<Pair<Car, Integer>> standardCars = null;
    protected ArrayList<Pair<Car, Integer>> busCars = null;
    protected ArrayList<Pair<Car, Integer>> truckCar = null;

    // for storing cars which follow to be repaired
    protected ArrayList<Pair<Car, Integer>> carsToRepair = null;

    // constructor
    public Employee(String firstName, String lastName, Date birthDate, Date employDate) {
        this.id = idCount++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.employDate = employDate;
        this.standardCars = new ArrayList<>();
        this.busCars = new ArrayList<>();
        this.truckCar = new ArrayList<>();
        this.carsToRepair = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getEmployDate() {
        return this.employDate;
    }

    public void setEmployDate(Date employDate) {
        this.employDate = employDate;
    }

    public float getWageCoefficient() {
        return this.wageCoefficient;
    }

    public ArrayList<Pair<Car, Integer>> getCarsToRepair() {
        return this.carsToRepair;
    }

    // add car to 'repairing queue'
    public void addCarToRepair(Car car) {
        this.carsToRepair.add(new Pair(car, car.getTimeToRepair()));
    }

    public int getChosenTimes() {
        return this.chosenTimes;
    }

    public void increaseChosenTime() {
        this.chosenTimes++;
    }

    public float getTip() {
        return this.tip;
    }

    // worker starts repairing a new car
    public void repairCar(Car car) {
        switch (car.getType()) {
            case Standard:
                this.standardCars.add(new Pair<>(car, car.getTimeToRepair()));
                break;
            case Bus:
                this.busCars.add(new Pair<>(car, car.getTimeToRepair()));
                break;
            case Truck:
                this.truckCar.add(new Pair<>(car, car.getTimeToRepair()));
                break;
        }
    }

    public ArrayList<Pair<Car, Integer>> getCarsList(CarType type) {
        switch (type) {
            case Standard:
                return this.standardCars;
            case Bus:
                return this.busCars;
            case Truck:
                return this.truckCar;
            default:
                return null;
        }
    }

    // decrease time for every car type that employee works to
    public void passTime() {
        finishRepairingCars(standardCars);
        finishRepairingCars(busCars);
        finishRepairingCars(truckCar);
    }

    private void finishRepairingCars(ArrayList<Pair<Car, Integer>> carList) {
        ArrayList<Pair<Car, Integer>> toAddNext = new ArrayList<>();
        ArrayList<Pair<Car, Integer>> toRemoveNext = new ArrayList<>();

        // if car is in the last unity of time of its reparation, finish it
        for (Pair<Car, Integer> entry : carList) {
            if (entry.second == 1) {
                Car car = entry.first;
                this.tip += car.calculateDiscountInsurance();
                System.out.println(car + "\trepaired");
                toRemoveNext.add(entry);
                repairNextCar(car.getType(), toAddNext);
            } else {
                entry.second--;
            }
        }

        // replace finished car with another one of the same type (if exists)
        for (Pair<Car, Integer> entry : toRemoveNext) {
            carList.remove(entry);
        }
        carList.addAll(toAddNext);
    }

    // get next car from 'repairing queue' to be repaired
    private void repairNextCar(CarType type, ArrayList<Pair<Car, Integer>> toAddNext) {
        int i = 0;
        while (i < carsToRepair.size()) {
            Pair<Car, Integer> pair = carsToRepair.get(i);
            if (pair.first.getType() == type) {
                toAddNext.add(pair);
                carsToRepair.remove(pair);
                break;
            }
            i++;
        }
    }

    @Override
    public String toString() {
        return "" + this.id + " " + this.lastName + " " + this.firstName + " " + this.type
                + "\nBirth Date: " + this.birthDate + "\tEmploy Date: " + this.employDate;
    }
}
