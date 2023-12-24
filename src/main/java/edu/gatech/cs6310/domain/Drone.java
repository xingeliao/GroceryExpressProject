package edu.gatech.cs6310.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.*;

@Entity
@JsonIgnoreProperties("store")
public class Drone {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    // A drone’s identifier is not limited to integers – it could be a string.
    private String droneID;
    private int capacity;
    private int remainNumOfTrips;
    @OneToMany(mappedBy = "drone")
    @JsonIgnoreProperties("drone")
    private Map<String, Order> ordersAssigned = new TreeMap<>();
    @OneToOne
    @JoinColumn(name = "pilot_id", referencedColumnName = "uid")
    @JsonIgnoreProperties("drone")
    private DronePilot pilotAssigned;

    private int remainCap;


    public Drone(Store store, String droneID, int capacity, int remainNumOfTrips) {
        this.store = store;
        this.droneID = droneID;
        this.capacity = capacity;
        this.remainNumOfTrips = remainNumOfTrips;
        this.remainCap = capacity;
    }

    public Drone() {

    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public void setDroneID(String droneID) {
        this.droneID = droneID;
    }

    public String getDroneID() {
        return droneID;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setRemainNumOfTrips(int remainNumOfTrips) {
        this.remainNumOfTrips = remainNumOfTrips;
    }

    public void assignOrder(String orderID, Order order) {
        ordersAssigned.put(orderID, order);
    }

    public Map<String, Order> getOrdersAssigned() {
        return ordersAssigned;
    }

    public void assignPilot(DronePilot dronePilot) {
        pilotAssigned = dronePilot;
    }

    public DronePilot getPilotAssigned() {
        return pilotAssigned;
    }

    public void delPilot() {
        pilotAssigned = null;
    }

    public int getRemainNumOfTrips() {
        return remainNumOfTrips;
    }

    public void setRemainCap(int remainCap) {
        this.remainCap = remainCap;
    }

    public int getRemainCap() {
        return remainCap;
    }

    @Override
    public String toString() {
        return "droneID:" + droneID +
                ",total_cap:" + capacity +
                ",num_orders:" + ordersAssigned.size() +
                ",remaining_cap:" + getRemainCap() +
                ",trips_left:" + remainNumOfTrips +
                (pilotAssigned != null ? ",flown_by:" + pilotAssigned.getFirstName() + "_" + pilotAssigned.getLastName() : "");
    }
}
