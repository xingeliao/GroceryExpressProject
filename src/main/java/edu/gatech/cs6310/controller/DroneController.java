package edu.gatech.cs6310.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gatech.cs6310.domain.Drone;
import edu.gatech.cs6310.domain.Item;
import edu.gatech.cs6310.domain.Store;
import edu.gatech.cs6310.service.DroneService;
import edu.gatech.cs6310.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DroneController {

    //@Autowired
    private DroneService droneService;

    @PostMapping("/drone/{storeName}")
    public ResponseEntity createDrone(@PathVariable String storeName, @RequestParam String droneID, @RequestParam int capacity, @RequestParam int remainNumOfTrips) {
        try {
            droneService.createDrone(storeName, droneID, capacity, remainNumOfTrips);
            return ResponseEntity.ok("OK:change_completed");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/stores/{storeName}/drones")
    public ResponseEntity<List<Drone>> getDronesByStore(@PathVariable String storeName) {
        try {
            List<Drone> drones = droneService.getDronesByStore(storeName);
            return ResponseEntity.ok(drones);
        } catch (RuntimeException e) {
            List<Drone> errorList = new ArrayList<>();
            errorList.add(new Drone(null, e.getMessage(), 0, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorList);
        }
    }


    @PostMapping("/drone/fly")
    public String flyDrone(@RequestParam String storeName, @RequestParam String droneID, @RequestParam String account) {
        return droneService.flyDrone(storeName, droneID, account);
    }
}
