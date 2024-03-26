package AirlineManagementSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        Scanner s = new Scanner(System.in);

        int i = 0;
        do {
            System.out.println("Welcome to Airline Management System");
            System.out.println("1. Add new passenger");
            System.out.println("2. Get passenger id by name");
            System.out.println("3. Print all passengers");
            System.out.println("4. Edit passenger");
            System.out.println("5. Delete passenger");
            System.out.println("6. Add new employee");
            System.out.println("7. Get employee by name");
            System.out.println("8. Print all passengers");
            System.out.println("9. Edit employee");
            System.out.println("10. Delete employee");
            System.out.println("11. Add new plane");
            System.out.println("12. Print all planes");
            System.out.println("13. Edit plane");
            System.out.println("14. Delete plane");
            System.out.println("15. add new airport");
            System.out.println("16. Print all airports");
            System.out.println("17. Edit airport");
            System.out.println("18. Delete airport");
            System.out.println("19. Create new flight");
            System.out.println("20. Show all flight");
            System.out.println("21. Delay flight");
            System.out.println("22. Book flight");
            System.out.println("23. Set flight stuff");
            System.out.println("24. Cancel Flight");
            System.out.println("25. Show flight stuff");
            System.out.println("26. Show flight passengers");
            System.out.println("30. Quit");

            i = s.nextInt();
            switch (i) {
                case 1:
                    PassengerController.AddNewPassenger(database, s);
                    break;
                case 2:
                    PassengerController.findPassengerByName(database, s);
                    break;
                case 3:
                    PassengerController.printAllPassengers(database);
                    break;
                case 4:
                    PassengerController.EditPassenger(database, s);
                    break;
                case 5:
                    PassengerController.DeletePassenger(database, s);
                    break;
                case 6:
                    EmployeeController.AddNewEmployee(database, s);
                    break;
                case 7:
                    EmployeeController.findEmployeeByName(database, s);
                    break;
                case 8:
                    EmployeeController.printAllEmployees(database);
                    break;
                case 9:
                    EmployeeController.EditEmployee(database, s);
                    break;
                case 10:
                    EmployeeController.DeleteEmployee(database, s);
                    break;
                case 11:
                    AirplanesController.AddNewAirplane(database, s);
                    break;
                case 12:
                    AirplanesController.PrintAllPlanes(database);
                    break;
                case 13:
                    AirplanesController.EditPlane(database, s);
                    break;
                case 14:
                    AirplanesController.DeletePlane(database, s);
                    break;
                case 15:
                    AirportsController.AddNewAirport(database, s);
                    break;
                case 16:
                    AirportsController.printAllPorts(database);
                    break;
                case 17:
                    AirportsController.EditAirport(database, s);
                    break;
                case 18:
                    AirportsController.DeleteAirport(database, s);
                    break;
                case 19:
                    FlightsController.AddNewFlight(database, s);
                    break;
                case 20:
                    FlightsController.showAllFlights(database);
                    break;
                case 21:
                    FlightsController.delayFlight(database, s);
                    break;
                case 22:
                    FlightsController.bookFlight(database, s);
                    break;
                case 23:
                    FlightsController.setFlightStuff(database, s);
                    break;
                case 24:
                    FlightsController.cancelFlight(database, s);
                    break;
                case 25:
                    FlightsController.printFlightStuff(database, s);
                    break;
                case 26:
                    FlightsController.printFlightPassenger(database, s);
                    break;

            }
        } while (i != 30);
    }
}
