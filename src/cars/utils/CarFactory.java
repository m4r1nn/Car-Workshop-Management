package cars.utils;

import cars.types.Bus;
import cars.types.Car;
import cars.types.Standard;
import cars.types.Truck;

import java.io.BufferedReader;
import java.io.IOException;

// for cars creation
public class CarFactory {
    private static CarFactory instance = null;
    private static BufferedReader reader = null;

    // private constructor
    private CarFactory() { }

    public static CarFactory getInstance(BufferedReader bfr) {
        if (instance == null) {
            instance = new CarFactory();
            reader = bfr;
        }
        return instance;
    }

    // create car depending by given type
    public Car createCar(CarType type) {
        try {
            String line;
            String[] params;
            switch (type) {

                case Standard:
                    System.out.println("Enter car specifications (<distance> <manufactureYear> <isDiesel = (yes | no)> <transmission type>)");
                    line = reader.readLine();
                    params = line.split("\\s");
                    if (params.length != 4) {
                        System.out.println("Invalid number of parameters");
                        return null;
                    }
                    return new Standard(Integer.parseInt(params[0]), Integer.parseInt(params[1]), params[2], params[3]);

                case Bus:
                    System.out.println("Enter car specifications (<distance> <manufactureYear> <isDiesel = (yes | no)> <number of seats>)");
                    line = reader.readLine();
                    params = line.split("\\s");
                    if (params.length != 4) {
                        System.out.println("Invalid number of parameters");
                        return null;
                    }
                    return new Bus(Integer.parseInt(params[0]), Integer.parseInt(params[1]), params[2], Integer.parseInt(params[3]));

                case Truck:
                    System.out.println("Enter car specifications (<distance> <manufactureYear> <isDiesel = (yes | no)> <tonnage>)");
                    line = reader.readLine();
                    params = line.split("\\s");
                    if (params.length != 4) {
                        System.out.println("Invalid number of parameters");
                        return null;
                    }
                    return new Truck(Integer.parseInt(params[0]), Integer.parseInt(params[1]), params[2], Integer.parseInt(params[3]));

                default:
                    System.out.println("Invalid type");
                    return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
