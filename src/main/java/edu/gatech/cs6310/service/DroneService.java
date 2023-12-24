package edu.gatech.cs6310.service;

import edu.gatech.cs6310.domain.Drone;

import java.util.List;

public interface DroneService {
    Drone createDrone(String storeName, String droneID, int capacity, int remainNumOfTrips);

    List<Drone> getDronesByStore(String storeName);
    String flyDrone(String storeName, String droneID, String account);
}
