package employees.types;

import employees.utils.EmployeeType;

import java.util.Date;

public class Mechanic extends Employee {

    // constructor
    public Mechanic(String firstName, String lastName, Date birthDate, Date employDate) {
        super(firstName, lastName, birthDate, employDate);
        this.type = EmployeeType.Mechanic;
        this.wageCoefficient = 1.5f;
    }
}
