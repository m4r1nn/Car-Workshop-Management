package workshop;

import cars.types.Car;
import cars.utils.CarAction;
import cars.utils.CarFactory;
import cars.utils.CarType;
import employees.types.Employee;
import employees.utils.EmployAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

// engine for workshop actions
public class Workshop {
    private static Workshop instance;

    private static boolean isOpen;
    private static EmployAction employAction;
    private static CarAction carAction;
    private static CarFactory carFactory;
    private static BufferedReader reader;

    // private constructor
    private Workshop() { }

    public static Workshop getInstance(BufferedReader bfr) {
        if (instance == null) {
            instance = new Workshop();
            isOpen = false;
            reader = bfr;
            employAction = EmployAction.getInstance(bfr);
            carAction = CarAction.getInstance(bfr);
            carFactory = CarFactory.getInstance(bfr);
        }
        return instance;
    }

    public void runEngine() {
        while (true) {
            System.out.println("Enter action type (<employeeAction> | <carAction> | <workshopAction> | <exit>)");
            try {
                // get action type (employee, car, workshop, exit)
                String type = reader.readLine();

                // execute workshop action if workshop is open
                if (type.equals("workshopAction")) {
                    if (!isOpen) {
                        System.out.println("Workshop closed");
                    } else {
                        System.out.println("Enter workshop action (<incrementTime> | <repairCar> | <printStatistics>)");
                        String action = reader.readLine();
                        this.execute(action);
                    }

                } else if (type.equals("employeeAction")) {
                    System.out.println("Enter employee action (<addEmployee> | <printEmployees> | <deleteEmployee> | <editEmployee> | <calculateSalary>)");
                    String action = reader.readLine();
                    employAction.execute(action);

                } else if (type.equals("carAction")) {
                    System.out.println("Enter car action (<addCar> | <removeCar> | <printCars> | <calculateInsurance> | <calculateDiscountInsurance>)");
                    String action = reader.readLine();
                    carAction.execute(action);

                } else if (type.equals("exit")) {
                    break;

                } else {
                    System.out.println("Wrong action");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // check if there are employees in workshop to keep it open, close it if not
            if (employAction.getEmployees().size() > 0 && !isOpen) {
                isOpen = true;
                System.out.println("Workshop open");
            } else if (employAction.getEmployees().size() == 0 && isOpen) {
                isOpen = false;
                System.out.println("Workshop closed");
            }
        }
    }

    // get action type and execute it
    public void execute(String action) {
        try {
            switch (action) {
                case "incrementTime":
                    this.incrementTime();
                    break;

                case "repairCar":
                    System.out.println("enter car type");
                    String carType = reader.readLine();
                    Car car = carFactory.createCar(CarType.valueOf(carType));
                    assignCar(car);
                    break;

                case "printStatistics":
                    System.out.println("Choose statistics (<biggestLoadWorker> <mostChosenWorker> <workersTips>)");
                    String type = reader.readLine();
                    this.printStatistics(type);
                    break;

                default:
                    System.out.println("Wrong action");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get statistics type and print them
    private void printStatistics(String type) {
        switch (type) {
            case "biggestLoadWorker":
                this.printBiggestLoadWorker();
                break;

            case "mostChosenWorker":
                this.printMostChosenWorker();
                break;

            case "workersTips":
                this.printWorkersTips();
                break;

            default:
                System.out.println("Wrong action");
        }
    }

    private void printWorkersTips() {
        for (Map.Entry<Integer, Employee> entry : employAction.getEmployees().entrySet()) {
            Employee employee = entry.getValue();
            System.out.println(employee + "\tTip: " + employee.getTip());
        }
    }

    // print employee with the most cars on 'repairing queue'
    private void printBiggestLoadWorker() {
        Employee res = null;
        int max = 0;
        for (Map.Entry<Integer, Employee> entry : employAction.getEmployees().entrySet()) {
            Employee employee = entry.getValue();
            if (max < employee.getCarsToRepair().size()) {
                max = employee.getCarsToRepair().size();
                res = employee;
            }
        }
        if (res == null) {
            System.out.println("No employees");
            return;
        }
        System.out.println(res);
    }

    // print employee with the most requests for repairing
    public void printMostChosenWorker() {
        Employee res = null;
        int max = 0;
        for (Map.Entry<Integer, Employee> entry : employAction.getEmployees().entrySet()) {
            Employee employee = entry.getValue();
            if (max < employee.getChosenTimes()) {
                max = employee.getChosenTimes();
                res = employee;
            }
        }
        if (res == null) {
            System.out.println("No employees");
            return;
        }
        System.out.println(res);
    }

    // increment time for every employee (finish repairing cars and get to the next one)
    private void incrementTime() {
        for (Map.Entry<Integer, Employee> entry : employAction.getEmployees().entrySet()) {
            entry.getValue().passTime();
        }
    }

    // try to assign car to one worker
    private void assignCar(Car car) {
        try {
            ArrayList<Employee> availableEmployees = showAvailableWorkers(car);
            String line;

            if (availableEmployees.size() == 0) {
                System.out.println("No available workers. Do you want to wait? (<yes> | <no>)");
                line = reader.readLine();
                if (line.equals("no")) {
                    return;
                }

                this.resolveWaiting(car);

            } else {
                System.out.println("Available workers:\n" + availableEmployees);
                System.out.println("Choose one (enter id) or choose to wait (<wait>) or choose to leave (<leave>)");
                line = reader.readLine();

                if (line.equals("leave")) {
                    return;
                }

                if (line.equals("wait")) {
                    this.resolveWaiting(car);
                    return;
                }

                // repair car
                int id = Integer.parseInt(line);
                employAction.getEmployees().get(id).repairCar(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // if client has chosen to wait, he can choose one of busy workers to repair his cars in the future, or choose to leave
    private void resolveWaiting(Car car) {
        try {
            System.out.println("These are all workers:");
            System.out.println(employAction.getEmployees());
            System.out.println("Do you want one of them (enter id) or do you want to leave (<leave>) ?");
            String line = reader.readLine();

            if (line.equals("leave")) {
                return;
            }

            // add car to 'repairing queue' and increase number of requests for that client
            int id = Integer.parseInt(line);
            Employee employee = employAction.getEmployees().get(id);
            employee.addCarToRepair(car);
            employee.increaseChosenTime();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get available employees for a car type
    private ArrayList<Employee> showAvailableWorkers(Car car) {
        ArrayList<Employee> res = new ArrayList<>();
        for (Map.Entry<Integer, Employee> entry : employAction.getEmployees().entrySet()) {
            Employee employee = entry.getValue();
            if (employee.getCarsList(car.getType()).size() < car.getMaxCarsToRepair()) {
                res.add(employee);
            }
        }
        return res;
    }
}
