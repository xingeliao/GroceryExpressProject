package edu.gatech.cs6310.service.impl;

import edu.gatech.cs6310.domain.Drone;
import edu.gatech.cs6310.domain.DronePilot;
import edu.gatech.cs6310.domain.Store;
import edu.gatech.cs6310.repository.DronePilotRepository;
import edu.gatech.cs6310.repository.DroneRepository;
import edu.gatech.cs6310.repository.StoreRepository;
import edu.gatech.cs6310.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DroneServiceImpl implements DroneService {

    //@Autowired
    private DroneRepository droneRepository;
    //@Autowired
    private StoreRepository storeRepository;
    //@Autowired
    private DronePilotRepository dronePilotRepository;

    @Override
    public Drone createDrone(String storeName, String droneID, int capacity, int remainNumOfTrips) {
        Store existingStore = storeRepository.findByStoreName(storeName);
        if (existingStore == null) {
            throw new IllegalArgumentException("ERROR:store_identifier_does_not_exist");
        }

        Drone existingDrone = droneRepository.findByStoreAndDroneID(storeRepository.findByStoreName(storeName), droneID);
        if (existingDrone != null) {
            throw new IllegalArgumentException("ERROR:drone_identifier_already_exists");
        }
        Drone drone = new Drone(existingStore, droneID, capacity, remainNumOfTrips);
        return droneRepository.save(drone);
    }

    @Override
    public List<Drone> getDronesByStore(String storeName) {
        Optional<Store> optionalStore = Optional.ofNullable(storeRepository.findByStoreName(storeName));
        if (optionalStore.isPresent()) {
            Store store = optionalStore.get();
            return droneRepository.findByStore(store);
        } else {
            throw new RuntimeException("ERROR:store_identifier_does_not_exist");
        }
    }

    @Override
    public String flyDrone(String storeName, String droneID, String account) {
        Store store = storeRepository.findByStoreName(storeName);
        if (store == null) {
            return "ERROR:store_identifier_does_not_exist";
        }

        Drone drone = droneRepository.findByStoreAndDroneID(store, droneID);
        if (drone == null) {
            return "ERROR:drone_identifier_does_not_exist";
        }

        DronePilot pilot = dronePilotRepository.findByAccount(account);
        if (pilot == null) {
            return "ERROR:pilot_identifier_does_not_exist";
        }

        DronePilot currentPilot = drone.getPilotAssigned();
        Drone currentDrone = pilot.getDroneAssigned();

        if (currentPilot != null) {
            currentPilot.delDrone();
            drone.delPilot();
        }
        if (currentDrone != null) {
            currentDrone.delPilot();
            pilot.delDrone();
        }

        drone.assignPilot(pilot);
        pilot.assignDrone(drone);

        droneRepository.save(drone);
        dronePilotRepository.save(pilot);

        return "OK:change_completed";
    }
}
