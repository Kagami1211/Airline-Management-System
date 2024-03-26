package AirlineManagementSystem;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FlightsController {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss");

    public static void AddNewFlight(Database database, Scanner s) throws SQLException {

        System.out.println("Enter plane id (int): \n(-1 to show all planes");
        int planeID = s.nextInt();
        if (planeID == -1) {
            AirplanesController.PrintAllPlanes(database);
            System.out.println("Enter plane id (int): ");
            planeID = s.nextInt();
        }
        Airplane plane = AirplanesController.getPlaneById(database, planeID);

        System.out.println("Enter origin airport id (int): \n(-1 to show all airports)");
        int originID = s.nextInt();
        if (originID == -1) {
            AirportsController.printAllPorts(database);
            System.out.println("Enter origin airport id (int): ");
            originID = s.nextInt();
        }
        Airport origin = AirportsController.getAirport(database, originID);

        System.out.println("Enter destination airport id (int): \n(-1 to show all airports)");
        int destinationID = s.nextInt();
        if (destinationID == -1) {
            AirportsController.printAllPorts(database);
            System.out.println("Enter destination airport id (int):");
            destinationID = s.nextInt();
        }
        Airport destination = AirportsController.getAirport(database, destinationID);

        System.out.println("Enter departure Time (yyyy-MM-dd::HH:mm:ss): ");
        String dTime = s.next();
        LocalDateTime departureTime = LocalDateTime.parse(dTime, formatter);

        System.out.println("Enter arrivalTime Time (yyyy-MM-dd::HH:mm:ss): ");
        String aTime = s.next();
        LocalDateTime arrivalTime = LocalDateTime.parse(aTime, formatter);

        Flight flight = new Flight();
        flight.setAirplane(plane);
        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);

        ArrayList<Flight> flights = getAllFlight(database);
        int id = 0;
        if (flights.size() != 0) {
            id = flights.size();
        }
        flight.setId(id);
        String insert = "INSERT INTO `flights`(`id`, `airplane`, `origin`, `destination`, `departureTime`," +
                " `arrivalTime`, `isDelayed`, `bookedEconomy`, `bookedBusiness`, `stuff`, `passengers`) VALUES " +
                "('"+flight.getId()+"','"+planeID+"','"+originID+"','"+destinationID+"','"+dTime+"','"+aTime+
                "','false','0','0','<%%/>','<%%/>')";
        database.getStatement().execute(insert);
        System.out.println("Flight added successfully!");

    }
    public static ArrayList<Flight> getAllFlight(Database database) throws SQLException {
        ArrayList<Flight> flights = new ArrayList<>();
        String select = "SELECT * FROM `flights`;";
        ResultSet rs = database.getStatement().executeQuery(select);

        ArrayList<Integer> Ids = new ArrayList<>();
        ArrayList<Integer> planeIds = new ArrayList<>();
        ArrayList<Integer> originIds = new ArrayList<>();
        ArrayList<Integer> destinationIds = new ArrayList<>();
        ArrayList<String> depTimes = new ArrayList<>();
        ArrayList<String> arrTimes = new ArrayList<>();
        ArrayList<String> dels = new ArrayList<>();
        ArrayList<Integer> bookedEconomySeats = new ArrayList<>();
        ArrayList<Integer> bookedBusinessSeats = new ArrayList<>();
        ArrayList<String> sts = new ArrayList<>();
        ArrayList<String> pass = new ArrayList<>();

        while (rs.next()) {
            Ids.add(rs.getInt("id"));
            planeIds.add(rs.getInt("airplane"));
            originIds.add(rs.getInt("origin"));
            destinationIds.add(rs.getInt("destination"));
            depTimes.add(rs.getString("departureTime"));
            arrTimes.add(rs.getString("arrivalTime"));
            dels.add(rs.getString("isDelayed"));
            bookedEconomySeats.add(rs.getInt("bookedEconomy"));
            bookedBusinessSeats.add(rs.getInt("bookedBusiness"));
            sts.add(rs.getString("stuff"));
            pass.add(rs.getString("passengers"));
        }
        for (int i=0; i<Ids.size();i++) {
            Flight flight = new Flight();
            flight.setId(Ids.get(i));
            int planeId = planeIds.get(i);
            int originId = originIds.get(i);
            int destinationId = destinationIds.get(i);
            String depTime = depTimes.get(i);
            String arrTime = arrTimes.get(i);
            String del = dels.get(i);
            boolean delayed = Boolean.parseBoolean(del);
            flight.setBookedEconomy(bookedEconomySeats.get(i));
            flight.setBookedBusiness(bookedBusinessSeats.get(i));
            String st = sts.get(i);
            String pas = pass.get(i);

            Airplane plane = AirplanesController.getPlaneById(database, planeId);
            flight.setAirplane(plane);
            flight.setOrigin(AirportsController.getAirport(database, originId));
            flight.setDestination(AirportsController.getAirport(database, destinationId));
            LocalDateTime departure = LocalDateTime.parse(depTime, formatter);
            flight.setDepartureTime(departure);
            LocalDateTime arrival = LocalDateTime.parse(arrTime, formatter);
            flight.setArrivalTime(arrival);
            if(delayed) {
                flight.isDelay();
            }

            String[] stuffId = st.split("<%%/>");
            Employee[] stuff = new Employee[10];
            for (int j=0; j<stuffId.length; j++){
                int id = Integer.parseInt(stuffId[j]);
                stuff[j] = EmployeeController.getEmployeeById(database, id);
            }
            flight.setStuff(stuff);

            String[] passengersId = pas.split("<%%/>");
            int totalCapacity = plane.getEconomyCapacity()+plane.getBusinessCapacity();
            Passenger[] passengers = new Passenger[totalCapacity];
            for (int j=0; j<passengersId.length; j++) {
                int id = Integer.parseInt(passengersId[j]);
                passengers[j] = PassengerController.getPassengerById(database, id);
            }
            flight.setPassengers(passengers);

            flights.add(flight);
        }

        return flights;
    }

    public static void showAllFlights(Database database) throws SQLException {
        ArrayList<Flight> flights = getAllFlight(database);
        System.out.println("id\tAirplane \tOrigin\t\tArrival\t\tDeparture Time" +
                "\t\t\tArrival Time\t\t\tstatus\t\t\tAvailable Economy\t\tAvailable Business");
        for (Flight flight : flights) {
            flight.printFlight();
        }
    }

    public static void delayFlight(Database database, Scanner s) throws SQLException {
        System.out.println("Enter flight id (int): \n(-1 to show all flights)");
        int id = s.nextInt();
        if (id == -1) {
            showAllFlights(database);
            System.out.println("Enter flight id:");
            id = s.nextInt();
        }

        String update = "UPDATE `flights` SET `isDelayed`='true' WHERE `id`= "+id+" ;";
        database.getStatement().execute(update);
        System.out.println("Flight delayed successfully!");
    }

    public static void bookFlight(Database database, Scanner s) throws SQLException {
        System.out.println("Enter flight id (int): \n(-1 to show all flight)");
        int id = s.nextInt();
        if (id == -1) {
            showAllFlights(database);
            System.out.println("Enter flight id:");
            id = s.nextInt();
        }

        Flight flight = getFlight(database, id);

        Passenger passenger;
        System.out.println("Enter passenger id (int):(-1 to get passenger by name)");
        int passengerId = s.nextInt();
        if (passengerId == -1) {
            passenger = PassengerController.getPassengerByName(database, s);
        } else {
            passenger = PassengerController.getPassengerById(database, passengerId);
        }

        System.out.println("1. Economy seat");
        System.out.println("2. Business seat");
        int n = s.nextInt();

        System.out.println("Enter number of seats (int):");
        int num = s.nextInt();

        if (n==1) {
            flight.setBookedEconomy(flight.getBookedEconomy()+num);
        } else {
            flight.setBookedBusiness(flight.getBookedBusiness()+num);
        }

        Passenger[] passengers = flight.getPassengers();
        for (int i=0; i< passengers.length; i++) {
            if (passengers[i]==null) {
                passengers[i] = passenger;
                break;
            }
        }

        StringBuffer sb = new StringBuffer();
        for (Passenger p : flight.getPassengers()) {
            if (p!=null) {
                sb.append(p.getId()).append("<%%/>");
            }
        }

        String update = "UPDATE `flights` SET `bookedEconomy`='"+flight.getBookedEconomy()+"',`bookedBusiness`='"
                +flight.getBookedBusiness()+"',`passengers`='"+sb.toString()+"' WHERE `id` = "+flight.getId()+";";
        database.getStatement().execute(update);
        System.out.println("Booked successfully!");
    }

    public static Flight getFlight(Database database, int id) throws SQLException {
        Flight flight = new Flight();
        String select = "SELECT `id`, `airplane`, `origin`, `destination`, `departureTime`" +
                ", `arrivalTime`, `isDelayed`, `bookedEconomy`, `bookedBusiness`, `stuff`, " +
                "`passengers` FROM `flights` WHERE `id` = "+ id +";";
        ResultSet rs = database.getStatement().executeQuery(select);
        rs.next();
        int ID = rs.getInt("id");
        int planeId = rs.getInt("airplane");
        int originId = rs.getInt("origin");
        int destId = rs.getInt("destination");
        String depTime = rs.getString("departureTime");
        String arrTime = rs.getString("arrivalTime");
        String del = rs.getString("isDelayed");
        int bookedEconomy = rs.getInt("bookedEconomy");
        int bookedBusiness = rs.getInt("bookedBusiness");
        String st = rs.getString("stuff");
        String pas = rs.getString("passengers");
        boolean delayed = Boolean.parseBoolean(del);

        flight.setId(ID);
        Airplane plane = AirplanesController.getPlaneById(database, planeId);
        flight.setAirplane(plane);
        flight.setOrigin(AirportsController.getAirport(database, originId));
        flight.setDestination(AirportsController.getAirport(database, destId));
        LocalDateTime departure = LocalDateTime.parse(depTime, formatter);
        flight.setDepartureTime(departure);
        LocalDateTime arrival = LocalDateTime.parse(arrTime, formatter);
        flight.setArrivalTime(arrival);
        if(delayed) {
            flight.isDelay();
        }
        flight.setBookedEconomy(bookedEconomy);
        flight.setBookedBusiness(bookedBusiness);

        String[] stuffId = st.split("<%%/>");
        Employee[] stuff = new Employee[10];
        for (int j=0; j<stuffId.length; j++){
            int idst = Integer.parseInt(stuffId[j]);
            stuff[j] = EmployeeController.getEmployeeById(database, idst);
        }
        flight.setStuff(stuff);

        String[] passengersId = pas.split("<%%/>");
        int totalCapacity = plane.getEconomyCapacity()+plane.getBusinessCapacity();
        Passenger[] passengers = new Passenger[totalCapacity];
        for (int j=0; j<passengersId.length; j++) {
            int idpass = Integer.parseInt(passengersId[j]);
            passengers[j] = PassengerController.getPassengerById(database, idpass);
        }
        flight.setPassengers(passengers);

        return flight;
    }

    public static void setFlightStuff (Database database, Scanner s) throws SQLException {
        System.out.println("Enter flight id (int): \n(-1 to show all flight)");
        int id = s.nextInt();
        if (id == -1) {
            showAllFlights(database);
            System.out.println("Enter flight id:");
            id = s.nextInt();
        }
        Flight flight = getFlight(database, id);

        System.out.println("1. Show all employees");
        System.out.println("2. Continue");
        int j = s.nextInt();
        if (j == 1) {
            EmployeeController.printAllEmployees(database);
        }
        System.out.println("Enter employees ids (int): ");
        Employee[] employees = new Employee[10];
        for (int i=0; i<10; i++) {
            System.out.println("id: "+(i+1)+ "/10");
            int ID = s.nextInt();
            employees[i] = EmployeeController.getEmployeeById(database, ID);

        }

        flight.setStuff(employees);

        StringBuffer bd = new StringBuffer();
        for (Employee e : flight.getStuff()) {
            if (e!=null) {
                bd.append(e.getId()).append("<%%/>");
            }
        }
        String update = "UPDATE `flights` SET `stuff`='"+bd.toString()+
                "' WHERE `id` = "+flight.getId()+";";
        database.getStatement().execute(update);
        System.out.println("Struff update successfully!");
    }
    public static void cancelFlight(Database database, Scanner s) throws SQLException {
        System.out.println("Enter flight id (int): \n(-1 to show all flight)");
        int id = s.nextInt();
        if (id == -1) {
            showAllFlights(database);
            System.out.println("Enter flight id:");
            id = s.nextInt();
        }
        String delete = "DELETE FROM `flights` WHERE `id` = "+id+";";
        database.getStatement().execute(delete);
        System.out.println("Flight cancelled successfully!");
    }
    public static void printFlightStuff(Database database, Scanner s) throws SQLException {
        System.out.println("Enter flight id (int): \n(-1 to show all flight)");
        int id = s.nextInt();
        if (id == -1) {
            showAllFlights(database);
            System.out.println("Enter flight id:");
            id = s.nextInt();
        }
        Flight f = getFlight(database, id);

        System.out.println("id\tFirst Name\tLast Name\tEmail\tTel\tPosition");
        for (Employee e : f.getStuff()){
            if (e!=null) {
                System.out.print(e.getId()+"\t");
                System.out.print(e.getFirstName()+"\t\t");
                System.out.print(e.getLastName()+"\t\t\t");
                System.out.print(e.getEmail()+"\t");
                System.out.print(e.getTel()+"\t\t");
                System.out.print(e.getPosition());
                System.out.println();
            }
        }
    }
    public static void printFlightPassenger(Database database, Scanner s) throws SQLException {
        System.out.println("Enter flight id (int): \n(-1 to show all flight)");
        int id = s.nextInt();
        if (id == -1) {
            showAllFlights(database);
            System.out.println("Enter flight id:");
            id = s.nextInt();
        }
        Flight f = getFlight(database, id);

        System.out.println("id\tFirst Name\tLast Name\tEmail\tTel");
        for (Passenger p : f.getPassengers()){
            if (p!=null) {
                System.out.print(p.getId()+"\t");
                System.out.print(p.getFirstName()+"\t\t");
                System.out.print(p.getLastName()+"\t\t\t");
                System.out.print(p.getEmail()+"\t");
                System.out.print(p.getTel()+"\t\t");
                System.out.println();
            }
        }
    }
}
