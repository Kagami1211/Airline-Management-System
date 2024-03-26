package AirlineManagementSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {

    private int id;
    private Airplane airplane;
    private Airport origin;
    private Airport destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private boolean delayed;
    private int bookedEconomy;
    private int bookedBusiness;
    private Employee[] stuff;
    private Passenger[] passengers;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss");

    public Flight() {
        delayed = false;
        bookedEconomy = 0;
        bookedBusiness = 0;
        stuff = new Employee[10];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
        int totalCapacity = airplane.getBusinessCapacity()+airplane.getEconomyCapacity();
        passengers = new Passenger[totalCapacity];
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public void isDelay() {
        delayed = true;
    }


    public int getBookedEconomy() {
        return bookedEconomy;
    }

    public void setBookedEconomy(int bookedEconomy) {
        this.bookedEconomy = bookedEconomy;
    }

    public int getBookedBusiness() {
        return bookedBusiness;
    }

    public void setBookedBusiness(int bookedBusiness) {
        this.bookedBusiness = bookedBusiness;
    }

    public Employee[] getStuff() {
        return stuff;
    }

    public void setStuff(Employee[] stuff) {
        this.stuff = stuff;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public void setPassengers(Passenger[] passengers) {
        this.passengers = passengers;
    }

    public void printFlight(){
        System.out.print(id+"\t");
        System.out.print(airplane.getModel()+"\t\t");
        System.out.print(origin.getCity()+"\t\t");
        System.out.print(destination.getCity()+"\t");
        System.out.print(formatter.format(departureTime)+"\t");
        System.out.print(formatter.format(arrivalTime)+"\t");
        if (delayed == true) {
            System.out.print("delayed\t\t\t");
        } else {
            System.out.print("estimated\t\t\t");
        }
        int AvailableE = airplane.getEconomyCapacity()-bookedEconomy;
        System.out.print(AvailableE+"\t\t\t\t\t\t");
        int AvailableB = airplane.getBusinessCapacity()-bookedBusiness;
        System.out.println(AvailableB);
        System.out.println();
    }
}
