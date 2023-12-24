package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.domain.Item;
import edu.gatech.cs6310.domain.Store;
import edu.gatech.cs6310.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StoreController {

    //@Autowired
    private StoreService storeService;

    @PostMapping("/store")
    public ResponseEntity<String> createStore(@RequestParam String name, @RequestParam int revenue) {
        Store existingStore = storeService.findByName(name);
        if (existingStore != null) {
            return ResponseEntity.badRequest().body("ERROR:store_identifier_already_exists");
        }
        Store newStore = storeService.create(name, revenue);
        return ResponseEntity.ok("OK:change_completed");
    }

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    @PostMapping("/store/sellItem")
    public ResponseEntity<String> addItemToStore(@RequestParam String storeName, @RequestParam String itemName, @RequestParam int weight) {
        try {
            storeService.addItemToStore(storeName, itemName, weight);
            return ResponseEntity.ok("OK:change_completed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/store/items")
    public ResponseEntity<List<Item>> getAllItemsByStore(@RequestParam String storeName) {
        try {
            List<Item> items = storeService.getAllItemsByStore(storeName);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            List<Item> errorList = new ArrayList<>();
            errorList.add(new Item(e.getMessage(), 0, null));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorList);
        }
    }

    @PostMapping("/transfer_order")
    public ResponseEntity<String> transferOrder(@RequestParam String storeName, @RequestParam String orderID, @RequestParam String droneID) {
        try {
            storeService.transferOrder(storeName, orderID, droneID);
            return ResponseEntity.ok("OK:change_completed");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/display_efficiency")
    public ResponseEntity<String> displayEfficiency() {
        StringBuilder sb = new StringBuilder();
        List<Store> stores = storeService.getAllStores();
        stores.forEach(store -> sb.append("name:").append(store.getName())
                .append(",purchases:").append(store.getPurchases())
                .append(",overloads:").append(store.getOverloads())
                .append(",transfers:").append(store.getTransfers())
                .append("\n"));
        sb.append("OK:display_completed");
        return ResponseEntity.ok(sb.toString());
    }

}
