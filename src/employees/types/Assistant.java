package employees.types;

import employees.utils.EmployeeType;

import java.util.Date;

public class Assistant extends Employee {

    // constructor
    public Assistant(String firstName, String lastName, Date birthDate, Date employDate) {
        super(firstName, lastName, birthDate, employDate);
        this.type = EmployeeType.Assistant;
        this.wageCoefficient = 1f;
    }
}
