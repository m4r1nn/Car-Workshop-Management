package cars.utils;

import cars.types.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// engine for car interaction
public class CarAction {
    private static CarAction instance = null;
    private static CarFactory carFactory = null;

    private static BufferedReader reader = null;

    // for storing all cars
    private static HashMap<Integer, Car> cars = null;

    // private constructor
    private CarAction() { }

    public static CarAction getInstance(BufferedReader bfr) {
        if (instance == null) {
            instance = new CarAction();
            carFactory = CarFactory.getInstance(bfr);
            cars = new HashMap<>();
            reader = bfr;
        }
        return instance;
    }

    // receive action type and execute it
    public void execute(String action) {
        try {
            switch (action) {
                case "addCar":
                    System.out.println("Enter type");
                    String type = reader.readLine();
                    addCar(type);
                    break;

                case "removeCar":
                    while (true) {
                        System.out.println("Enter id");
                        int id = Integer.parseInt(reader.readLine());
                        if (removeCar(id)) {
                            break;
                        }
                    }
                    break;

                case "printCars":
                    printCars();
                    break;

                case "calculateInsurance":
                    while (true) {
                        System.out.println("Enter id");
                        int id = Integer.parseInt(reader.readLine());
                        if (calculateInsurance(id)) {
                            break;
                        }
                    }
                    break;

                case "calculateDiscountInsurance":
                    while (true) {
                        System.out.println("Enter id");
                        int id = Integer.parseInt(reader.readLine());
                        if (calculateDiscountInsurance(id)) {
                            break;
                        }
                    }
                    break;

                default:
                    System.out.println("Wrong acion");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // add car if all stats are valid
    public void addCar(String type) {
        Car car = carFactory.createCar(CarType.valueOf(type));
        if (car != null) {
            cars.put(car.getId(), car);
            System.out.println("car added successfully");
        }
    }

    // remove car if possible
    public boolean removeCar(int id) {
        if (!cars.containsKey(id)) {
            System.out.println("Wrong id");
            return false;
        }
        cars.remove(id);
        System.out.println("car removed successfully");
        return true;
    }

    // print all cars
    public void printCars() {
        if (cars.isEmpty()) {
            System.out.println("No employees");
            return;
        }
        for (Map.Entry<Integer, Car> entry : cars.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    // calculate insurance for car if it is in stock
    public boolean calculateInsurance(int id) {
        if (!cars.containsKey(id)) {
            System.out.println("Wrong id");
            return false;
        }
        Car car = cars.get(id);
        System.out.println(car.calculateInsurance());
        return true;
    }

    // calculate insurance with discount
    public boolean calculateDiscountInsurance(int id) {
        if (!cars.containsKey(id)) {
            System.out.println("Wrong id");
            return false;
        }
        Car car = cars.get(id);
        System.out.println(car.calculateDiscountInsurance());
        return true;
    }
}
