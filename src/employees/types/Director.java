package employees.types;

import employees.utils.EmployeeType;

import java.util.Date;

public class Director extends Employee {

    // constructor
    public Director(String firstName, String lastName, Date birthDate, Date employDate) {
        super(firstName, lastName, birthDate, employDate);
        this.type = EmployeeType.Director;
        this.wageCoefficient = 2f;
    }
}
