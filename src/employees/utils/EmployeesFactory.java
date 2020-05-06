package employees.utils;

import employees.types.Assistant;
import employees.types.Director;
import employees.types.Employee;
import employees.types.Mechanic;

import java.util.Date;

// for employee creation
public class EmployeesFactory {
    private static EmployeesFactory instance = null;

    // private constructor
    private EmployeesFactory() { }

    public static EmployeesFactory getInstance() {
        if (instance == null) {
            instance = new EmployeesFactory();
        }
        return instance;
    }

    public Employee createEmployee(EmployeeType type, String firstName, String lastName, Date birthDate, Date employDate) {
        if (firstName == null || lastName == null || firstName.length() > 30 || lastName.length() > 30) {
            System.out.println("Invalid name or surname");
            return null;
        }
        if (!Helpers.checkDate(birthDate, employDate)) {
            System.out.println("Invalid birth/employ date");
            return null;
        }

        switch (type) {
            case Director:
                return new Director(firstName, lastName, birthDate, employDate);
            case Mechanic:
                return new Mechanic(firstName, lastName, birthDate, employDate);
            case Assistant:
                return new Assistant(firstName, lastName, birthDate, employDate);
            default:
                System.out.println("Invalid type");
                return null;
        }
    }
}
