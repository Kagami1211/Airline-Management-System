package AirlineManagementSystem;

import javax.xml.transform.stream.StreamSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class AirplanesController {

    public static void AddNewAirplane(Database database, Scanner s) throws SQLException {
        System.out.println("Enter EconomyCapacity (int)");
        int EconomyCapacity = s.nextInt();
        System.out.println("Enter BusinessCapacity (int)");
        int BusinessCapacity = s.nextInt();
        System.out.println("Enter model");
        String model = s.next();

        Airplane airplane = new Airplane();
        airplane.setEconomyCapacity(EconomyCapacity);
        airplane.setBusinessCapacity(BusinessCapacity);
        airplane.setModel(model);

        ArrayList<Airplane> planes = getAllPlane(database);
        int id;
        if (planes.size() != 0) {
             id = planes.get(planes.size()-1).getId()+1;
        } else {
            id = 0;
        }
        airplane.setId(id);

        String insert = "INSERT INTO `airplanes`(`id`, `EconomyCapacity`, `BusinessCapacity`, `model`) VALUES ('"
                +airplane.getId()+"','"+airplane.getEconomyCapacity()+"','"
                +airplane.getBusinessCapacity()+"','"+airplane.getModel()+"');";
        database.getStatement().execute(insert);
        System.out.println("Airplane added successfully!");
    }

    public static void PrintAllPlanes(Database database) throws SQLException {
        ArrayList<Airplane> airplanes = getAllPlane(database);
        System.out.println("\n-----------------------------");
        for (Airplane plane : airplanes) {
            plane.printAirplane();
        }
        System.out.println("-----------------------------\n");
    }
    public static Airplane getPlaneById(Database database, int id) throws SQLException {
        String get = "SELECT `id`, `EconomyCapacity`, `BusinessCapacity`, `model` FROM `airplanes` WHERE `id` = 0" + id + ";";
        ResultSet rs = database.getStatement().executeQuery(get);
        rs.next();
        Airplane a = new Airplane();
        a.setId(rs.getInt("id"));
        a.setEconomyCapacity(rs.getInt("EconomyCapacity"));
        a.setBusinessCapacity(rs.getInt("BusinessCapacity"));
        a.setModel(rs.getString("model"));
        return a;
    }


    public static void EditPlane(Database database, Scanner s) throws SQLException {
        System.out.println("Enter airplane id (int): \n(-1 to show all planes)");
        int id = s.nextInt();
        Airplane airplane;
        if (id == -1) {
            PrintAllPlanes(database);
            System.out.println("Enter id (int):");
            id = s.nextInt();
        }
        String get = "SELECT `id`, `EconomyCapacity`, `BusinessCapacity`, `model` FROM `airplanes` WHERE `id` = 0" + id + ";";
        ResultSet rs = database.getStatement().executeQuery(get);
        Airplane a = new Airplane();
        rs.next();
        a.setId(rs.getInt("id"));
        a.setEconomyCapacity(rs.getInt("EconomyCapacity"));
        a.setBusinessCapacity(rs.getInt("BusinessCapacity"));
        a.setModel(rs.getString("model"));
        airplane = a;

        System.out.println("Enter Economy Capacity: \n(-1 to keep old value)");
        int EconomyCapacity = s.nextInt();
        if (EconomyCapacity == -1){
            EconomyCapacity = airplane.getEconomyCapacity();
        }
        System.out.println("Enter Business Capacity: \n(-1 to keep old value)");
        int BusinessCapacity = s.nextInt();
        if (BusinessCapacity == -1) {
            BusinessCapacity = airplane.getBusinessCapacity();
        }
        System.out.println("Enter model: \n(-1 to keep old value)");
        String modle = s.next();
        if(modle.equals("-1")) {
            modle = airplane.getModel();
        }

        airplane.setEconomyCapacity(EconomyCapacity);
        airplane.setBusinessCapacity(BusinessCapacity);
        airplane.setModel(modle);
        String update = "UPDATE `airplanes` SET `id`='"+airplane.getId()+"',`EconomyCapacity`='"+
                airplane.getEconomyCapacity()+"',`BusinessCapacity`='"+
                airplane.getBusinessCapacity()+"',`model`='"+airplane.getModel()+"' WHERE `id` = '"+airplane.getId()+"';";
        database.getStatement().execute(update);
        System.out.println("Airplane edited successfully!");
    }
    public static void DeletePlane(Database database, Scanner s) throws SQLException {
        System.out.println("Enter id (int): \n(-1 to Show all planes)");
        int id = s.nextInt();
        Airplane airplane;
        if (id == -1){
            PrintAllPlanes(database);
            System.out.println("Enter id (int):");
            id = s.nextInt();
        }
        String get = "SELECT `id`, `EconomyCapacity`, `BusinessCapacity`, `model` FROM `airplanes` WHERE `id` = 0"+ id +";";
        ResultSet rs = database.getStatement().executeQuery(get);
        Airplane a = new Airplane();
        rs.next();
        a.setId(rs.getInt("Id"));
        a.setEconomyCapacity(rs.getInt("EconomyCapacity"));
        a.setBusinessCapacity(rs.getInt("BusinessCapacity"));
        a.setModel(rs.getString("model"));
        airplane = a;
        String delete = "DELETE FROM `airplanes` WHERE `id` = "+airplane.getId()+";";
        database.getStatement().execute(delete);
        System.out.println("Plane deleted successfully!");
    }

    public static ArrayList<Airplane> getAllPlane(Database database) throws SQLException {
        ArrayList<Airplane> plane = new ArrayList<>();
        String get = "SELECT * FROM `airplanes`;";
        ResultSet rs = database.getStatement().executeQuery(get);
        while (rs.next()){
            Airplane a = new Airplane();
            a.setId(rs.getInt("id"));
            a.setEconomyCapacity(rs.getInt("EconomyCapacity"));
            a.setBusinessCapacity(rs.getInt("BusinessCapacity"));
            a.setModel(rs.getString("model"));
            plane.add(a);
        }
        return plane;
    }

}
