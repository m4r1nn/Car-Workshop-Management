package employees.utils;

import employees.types.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// engine for employee interaction
public class EmployAction {

    // for storing all employees
    private static HashMap<Integer, Employee> employees;

    private static EmployeesFactory employeesFactory = null;
    private static EmployAction instance = null;

    private static BufferedReader reader = null;
    private static SimpleDateFormat format = null;

    // private constructor
    private EmployAction() { }

    public static EmployAction getInstance(BufferedReader bfr) {
        if (instance == null) {
            instance = new EmployAction();
            employeesFactory = EmployeesFactory.getInstance();
            employees = new HashMap<>();
            reader = bfr;
            format = new SimpleDateFormat("dd-MM-yyyy");
        }
        return instance;
    }

    // receive action type and execute it
    public void execute(String action) {
        try {
            String line;
            switch (action) {

                case "addEmployee":
                    System.out.println("Enter new employee (<type> <first name> <last name> <birth date> <employ date>) \t date format = dd-MM-yyyy");
                    line = reader.readLine();
                    String[] params = line.split("\\s");
                    if (params.length != 5) {
                        System.out.println("Invalid number of parameters");
                        return;
                    }
                    addEmployee(params[0], params[1], params[2], params[3], params[4]);
                    break;

                case "printEmployees":
                    printEmployees();
                    break;

                case "deleteEmployee":
                    while (true) {
                        System.out.println("Enter id");
                        line = reader.readLine();
                        int id = Integer.parseInt(line);
                        if (deleteEmployee(id)) {
                            break;
                        }
                    }
                    break;

                case "editEmployee":
                    while (true) {
                        System.out.println("Enter id");
                        line = reader.readLine();
                        int id = Integer.parseInt(line);
                        if (editEmployee(id)) {
                            break;
                        }
                    }
                    break;

                case "calculateSalary":
                    while (true) {
                        System.out.println("Enter id");
                        line = reader.readLine();
                        int id = Integer.parseInt(line);
                        if (calculateSalary(id)) {
                            break;
                        }
                    }
                    break;

                default:
                    System.out.println("Wrong action");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // add employee if all stats are valid
    public void addEmployee(String type, String firstName, String lastName, String birthDate, String employDate) {
        try {
            Date birthD = format.parse(birthDate);
            Date employD = format.parse(employDate);
            Employee employee = employeesFactory.createEmployee(EmployeeType.valueOf(type), firstName, lastName, birthD, employD);
            if(employee != null) {
                employees.put(employee.getId(), employee);
                System.out.println("employee added successfully");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // print all employees
    public void printEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees");
            return;
        }
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    // remove employee if possible
    public boolean deleteEmployee(int id) {
        if (employees.containsKey(id)) {
            employees.remove(id);
            System.out.println("employee deleted successfully");
            return true;
        }
        System.out.println("Wrong id");
        return false;
    }

    // change employee stats if possible
    public boolean editEmployee(int id) {
        if (employees.containsKey(id)) {
            Employee employee = employees.get(id);

            System.out.println("Enter new stats (<first name> <last name> <birth date> <employ date>) \t date format = dd-MM-yyyy");
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] params = line.split("\\s");
            if (params.length != 4) {
                System.out.println("Invalid number of parameters");
                return false;
            }

            String firstName = params[0];
            String lastName = params[1];
            Date birthDate, employDate;

            try {
                birthDate = format.parse(params[2]);
                employDate = format.parse(params[3]);

                if (firstName == null || firstName.length() > 30) {
                    System.out.println("Invalid first name");
                } else {
                    employee.setFirstName(firstName);
                }

                if (lastName == null || lastName.length() > 30) {
                    System.out.println("Invalid last name");
                } else {
                    employee.setLastName(lastName);
                }

                if (!Helpers.checkDate(birthDate, employDate)) {
                    System.out.println("Invalid birth/employ date");
                } else {
                    employee.setEmployDate(employDate);
                    employee.setBirthDate(birthDate);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }

        System.out.println("Wrong id");
        return false;
    }

    // calculate salary for employee if he exists
    public boolean calculateSalary(int id) {
        if (employees.containsKey(id)) {
            Employee employee = employees.get(id);

            // calculate years of working
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            int currentYear = cal.get(Calendar.YEAR);
            int currentDays = cal.get(Calendar.DAY_OF_YEAR);

            cal.setTime(employee.getEmployDate());
            int employYear = cal.get(Calendar.YEAR);
            int employDays = cal.get(Calendar.DAY_OF_YEAR);

            float res;
            if (currentDays >= employDays) {
                res = 1f * (currentYear - employYear) * employee.getWageCoefficient() * 1000;
            } else {
                res = 1f * (currentYear - employYear - 1) * employee.getWageCoefficient() * 1000;
            }

            System.out.println(res);

            return true;
        }
        System.out.println("Wrong id");
        return false;
    }

    public HashMap<Integer, Employee> getEmployees() {
        return employees;
    }
}
