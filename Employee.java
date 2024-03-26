package AirlineManagementSystem;

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String Tel;
    private double salary;
    private String position;

    public Employee() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public void printEmployee() {
        System.out.println("id: " + getId());
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Tel: " + getTel());
        System.out.println("Email: " + getEmail());
        System.out.println("Salary: " + getSalary());
        System.out.println("Position: " + getPosition());
        System.out.println();
    }
}
