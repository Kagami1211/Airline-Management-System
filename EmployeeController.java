package AirlineManagementSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeController {

    public static void AddNewEmployee(Database database, Scanner s) throws SQLException {
        System.out.println("Enter first name: ");
        String firstName = s.next();
        System.out.println("Enter last name: ");
        String lastName = s.next();
        System.out.println("Enter email: " );
        String email = s.next();
        System.out.println("Enter Tel: ");
        String Tel = s.next();
        System.out.println("Enter salary (Double): ");
        double salary = s.nextDouble();
        System.out.println("Enter position: ");
        String position = s.next();

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setTel(Tel);
        employee.setSalary(salary);
        employee.setPosition(position);

        ArrayList<Employee> employees = getAllEmployees(database);
        int id;
        if (employees.size() != 0 ){
            id = employees.get(employees.size()-1).getId() + 1;
        } else {
            id = 0;
        }
        employee.setId(id);
        String insert = "INSERT INTO `employees`(`id`, `firstName`, `lastName`, `Tel`, " +
                "`email`, `salary`, `position`) VALUES ('"+employee.getId()+"','"+employee.getFirstName()+"','"+
                employee.getLastName()+"','"+employee.getTel()+"','"+employee.getEmail()+"','"+
                employee.getSalary()+"','"+employee.getPosition()+"');";
        database.getStatement().execute(insert);

        System.out.println("Employee added successfully!");
    }

    public static void EditEmployee(Database database, Scanner s) throws SQLException {
        System.out.println("Enter employee id (int): \n(-1 to get employee by name)");
        int id = s.nextInt();
        Employee employee;
        if (id == - 1) {
            employee = findEmployeeByName(database, s);
        } else {
            String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email`, `salary`, `position` FROM `employees` WHERE `id` = 0" + id + ";";
            ResultSet rs = database.getStatement().executeQuery(get);
            Employee e = new Employee();
            rs.next();
            e.setId(Integer.parseInt(rs.getString("id")));
            e.setFirstName(rs.getString("firstName"));
            e.setLastName(rs.getString("lastName"));
            e.setTel(rs.getString("Tel"));
            e.setEmail(rs.getString("email"));
            e.setSalary(Double.parseDouble(rs.getString("salary")));
            e.setPosition(rs.getString("position"));
            employee = e;
        }

        System.out.println("Enter first name: \n(-1 to keep old value)");
        String firstName = s.next();
        if (firstName.equals("-1")) {
            firstName = employee.getFirstName();
        }
        System.out.println("Enter last name: \n(-1 to keep old value)");
        String lastName = s.next();
        if (lastName.equals("-1")) {
            lastName = employee.getLastName();
        }
        System.out.println("Enter Tel: \n(-1 to keep old value)");
        String Tel = s.next();
        if (Tel.equals("-1")) {
            Tel = employee.getTel();
        }
        System.out.println("Enter Email: \n(-1 to keep old value)");
        String email = s.next();
        if (email.equals("-1")) {
            email = employee.getEmail();
        }
        System.out.println("Enter salary (double): \n(-1 to keep old value)");
        double salary = s.nextDouble();
        if (salary == -1) {
            salary = employee.getSalary();
        }
        System.out.println("Enter position: \n(-1 to keep old value");
        String position = s.next();
        if (position.equals("-1")) {
            position = employee.getPosition();
        }

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setTel(Tel);
        employee.setEmail(email);
        employee.setSalary(salary);
        employee.setPosition(position);
        String update = "UPDATE `employees` SET `id`='"+employee.getId()+"',`firstName`='"+employee.getFirstName()+
                "',`lastName`='"+employee.getLastName()+"',`Tel`='"+employee.getTel()+"',`email`='"+employee.getEmail()+
                "',`salary`='"+employee.getSalary()+"',`position`='"+employee.getPosition()+"' WHERE `id` = '"+employee.getId()+"';";

        database.getStatement().execute(update);
        System.out.println("Employee edited successfully!");
    }

    public static Employee findEmployeeByName(Database database , Scanner s) throws SQLException {
        System.out.println("Enter first name: ");
        String firstName = s.next();
        System.out.println("Enter last name: ");
        String lastName = s.next();
        String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email`, `salary`, `position` FROM `employees` WHERE " +
                "`firstName` = \"" + firstName + "\";";
        ResultSet rs = database.getStatement().executeQuery(get);
        Employee employee = new Employee();
        while (rs.next()) {
            Employee e = new Employee();
            e.setId(Integer.parseInt(rs.getString("id")));
            e.setFirstName(rs.getString("firstName"));
            e.setLastName(rs.getString("lastName"));
            e.setTel(rs.getString("Tel"));
            e.setEmail(rs.getString("email"));
            e.setSalary(Double.parseDouble(rs.getString("salary")));
            e.setPosition(rs.getString("position"));

            if (e.getLastName().equals(lastName)){
                employee = e;
                break;
            }
        }
        employee.printEmployee();
        return employee;
    }

    public static Employee getEmployeeById(Database database , int id) throws SQLException {
        String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email`, `salary`, `position` FROM `employees` WHERE " +
                "`id` = " + id + ";";
        ResultSet rs = database.getStatement().executeQuery(get);
        rs.next();
        Employee e = new Employee();
        e.setId(Integer.parseInt(rs.getString("id")));
        e.setFirstName(rs.getString("firstName"));
        e.setLastName(rs.getString("lastName"));
        e.setTel(rs.getString("Tel"));
        e.setEmail(rs.getString("email"));
        e.setSalary(Double.parseDouble(rs.getString("salary")));
        e.setPosition(rs.getString("position"));
        return e;
    }
    public static void printAllEmployees(Database database) throws SQLException {
        ArrayList<Employee> employees = getAllEmployees(database);
        System.out.println("\n-----------------------------");
        for (Employee p : employees) {
            p.printEmployee();
        }
        System.out.println("-----------------------------\n");
    }
    public static void DeleteEmployee(Database database, Scanner s) throws SQLException {
        System.out.println("Enter id (int): \n(-1 to get passenger by name)");
        int id = s.nextInt();
        Employee employee;
        if (id == -1) {
            employee = findEmployeeByName(database, s);
        } else {
            String get = "SELECT `id`, `firstName`, `lastName`, `Tel`, `email`, `salary`, `position` FROM `employees` WHERE `id` = 0" + id + ";";
            ResultSet rs = database.getStatement().executeQuery(get);
            Employee e = new Employee();
            rs.next();
            e.setId(Integer.parseInt(rs.getString("id")));
            e.setFirstName(rs.getString("firstName"));
            e.setLastName(rs.getString("lastName"));
            e.setTel(rs.getString("Tel"));
            e.setEmail(rs.getString("email"));
            e.setSalary(Double.parseDouble(rs.getString("salary")));
            e.setPosition(rs.getString("position"));
            employee = e;
        }
        String delete = "DELETE FROM `employees` WHERE `id`="+employee.getId()+";";
        database.getStatement().execute(delete);
        System.out.println("Passenger deleted successfully!");
    }
    private static ArrayList<Employee> getAllEmployees(Database database) throws SQLException {
        String get = "SELECT * FROM `employees`;";
        ResultSet rs = database.getStatement().executeQuery(get);
        ArrayList<Employee> employees = new ArrayList<>();
        while (rs.next()) {
            Employee e = new Employee();
            e.setId(Integer.parseInt(rs.getString("id")));
            e.setFirstName(rs.getString("firstName"));
            e.setLastName(rs.getString("lastName"));
            e.setTel(rs.getString("Tel"));
            e.setEmail(rs.getString("email"));
            e.setSalary(Double.parseDouble(rs.getString("salary")));
            e.setPosition(rs.getString("position"));
            employees.add(e);
        }
        return employees;
    }
}
