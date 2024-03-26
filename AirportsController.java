package AirlineManagementSystem;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AirportsController {

    public static void AddNewAirport(Database database, Scanner s) throws SQLException {
        System.out.println("Enter city:");
        String city = s.next();

        Airport airport = new Airport();
        airport.setCity(city);

        ArrayList<Airport> airports = getAllPorts(database);
        int id;
        if (airports.size() != 0) {
            id = airports.get(airports.size()-1).getId()+1;
        } else {
            id = 0;
        }
        airport.setId(id);

        String insert = "INSERT INTO `airports`(`id`, `city`) VALUES ('"
                +airport.getId()+"','"+airport.getCity()+"')";
        database.getStatement().execute(insert);
        System.out.println("Airpost added successfully!");
    }

    public static void printAllPorts(Database database) throws SQLException {
        ArrayList<Airport> airports = getAllPorts(database);
        System.out.println("\n-----------------------------");
        System.out.println("id\tcity\n");
        for (Airport airport : airports) {
            airport.printAirport();
        }
        System.out.println("-----------------------------\n");
    }
    public static void EditAirport(Database database, Scanner s) throws SQLException {
        System.out.println("Enter Airport (int): ");
        int id = s.nextInt();
        Airport airport;
        String get = "SELECT `id`, `city` FROM `airports` WHERE `id` = 0" + id + ";";
        ResultSet rs = database.getStatement().executeQuery(get);
        Airport a = new Airport();
        rs.next();
        a.setId(rs.getInt("id"));
        a.setCity(rs.getString("city"));
        airport = a;

        System.out.println("Enter city: \n(-1 to keep old value)");
        String city = s.next();
        if(city.equals("-1")) {
            city = airport.getCity();
        }
        airport.setCity(city);
        String update = "UPDATE `airports` SET `id`='"+airport.getId()+"',`city`='"+airport.getCity()+"' WHERE `id` = "+airport.getId()+";";
        database.getStatement().execute(update);
        System.out.println("Airport added sussecfully!");
    }
    public static Airport getAirport(Database database, int id) throws SQLException {
        String get = "SELECT `id`, `city` FROM `airports` WHERE `id` = 0" + id + ";";
        ResultSet rs = database.getStatement().executeQuery(get);
        Airport a = new Airport();
        rs.next();
        a.setId(rs.getInt("id"));
        a.setCity(rs.getString("city"));
        return a;
    }

    public static void DeleteAirport(Database database, Scanner s) throws SQLException {
        System.out.println("Enter id (int): (-1 to prind all Ariport)");
        int id = s.nextInt();
        Airport airport;
        if (id == -1) {
            printAllPorts(database);
            System.out.println("Enter id (int): ");
            id = s.nextInt();
        }
        String get = "SELECT `id`, `city` FROM `airports` WHERE `id` = 0" +id+";";
        ResultSet rs = database.getStatement().executeQuery(get);
        Airport a = new Airport();
        rs.next();
        a.setId(rs.getInt("id"));
        a.setCity(rs.getString("city"));
        airport = a;
        String delete = "DELETE FROM `airports` WHERE `id` = 0" +airport.getId()+";";
        database.getStatement().execute(delete);
        System.out.println("Airport delete successfully!");
    }

    public static ArrayList<Airport> getAllPorts(Database database) throws SQLException {
        ArrayList<Airport> airports = new ArrayList<>();
        String select = "SELECT * FROM `airports`;";
        ResultSet rs = database.getStatement().executeQuery(select);
        while (rs.next()) {
            Airport a = new Airport();
            a.setId(rs.getInt("id"));
            a.setCity(rs.getString("city"));
            airports.add(a);
        }
        return airports;
    }
}
