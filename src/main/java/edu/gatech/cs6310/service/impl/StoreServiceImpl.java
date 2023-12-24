package edu.gatech.cs6310.service.impl;

import edu.gatech.cs6310.domain.Drone;
import edu.gatech.cs6310.domain.Item;
import edu.gatech.cs6310.domain.Order;
import edu.gatech.cs6310.domain.Store;
import edu.gatech.cs6310.repository.DroneRepository;
import edu.gatech.cs6310.repository.OrderRepository;
import edu.gatech.cs6310.repository.StoreRepository;
import edu.gatech.cs6310.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    //@Autowired
    private StoreRepository storeRepository;

    //@Autowired
    private OrderRepository orderRepository;

   // @Autowired
    private DroneRepository droneRepository;

    @Override
    public Store create(String name, int revenue) {
        Store existingStore = storeRepository.findByStoreName(name);
        if (existingStore != null) {
            throw new IllegalArgumentException("ERROR:store_identifier_already_exists");
        }
        Store newStore = new Store(name, revenue);
        return storeRepository.save(newStore);
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store findByName(String name) {
        return storeRepository.findByStoreName(name);
    }

    @Override
    public void addItemToStore(String storeName, String itemName, int weight) {
        Optional<Store> optionalStore = Optional.ofNullable(storeRepository.findByStoreName(storeName));
        if (optionalStore.isPresent()) {
            Store store = optionalStore.get();
            if (store.getItemByName(itemName) != null) {
                throw new RuntimeException("ERROR:item_identifier_already_exists");
            }
            Item newItem = new Item(itemName, weight, store);
            store.addItem(itemName, newItem);
            storeRepository.save(store);
        } else {
            throw new RuntimeException("ERROR:store_identifier_does_not_exist");
        }
    }

    @Override
    public List<Item> getAllItemsByStore(String storeName) {
        Optional<Store> optionalStore = Optional.ofNullable(storeRepository.findByStoreName(storeName));
        if (optionalStore.isPresent()) {
            Store store = optionalStore.get();
            List<Item> itemList = store.getItems().values().stream()
                    .sorted(Comparator.comparing(Item::getItemName))
                    .collect(Collectors.toList());
            return itemList;
        } else {
            throw new RuntimeException("ERROR:store_identifier_does_not_exist");
        }
    }

    @Override
    public void transferOrder(String storeName, String orderID, String droneID) {
        Store store = storeRepository.findByStoreName(storeName);
        if (store == null) {
            throw new IllegalArgumentException("ERROR:store_identifier_does_not_exist");
        }

        Order order = orderRepository.findByStoreAndOrderID(store, orderID);
        if (order == null) {
            throw new IllegalArgumentException("ERROR:order_identifier_does_not_exist");
        }

        Drone newDrone = droneRepository.findByStoreAndDroneID(store, droneID);
        if (newDrone == null) {
            throw new IllegalArgumentException("ERROR:drone_identifier_does_not_exist");
        }

        if (newDrone.getRemainCap() < order.getTotalWeight()) {
            throw new IllegalArgumentException("ERROR:new_drone_does_not_have_enough_capacity");
        }

        Drone oldDrone = order.getDrone();

        if (oldDrone == newDrone) {
            throw new IllegalArgumentException("OK: new_drone_is_current_drone_no_change");
        }

        oldDrone.getOrdersAssigned().remove(orderID);
        oldDrone.setRemainCap(oldDrone.getRemainCap() + order.getTotalWeight());
        newDrone.assignOrder(orderID, order);
        order.setDrone(newDrone);
        newDrone.setRemainCap(newDrone.getRemainCap() - order.getTotalWeight());

        // update efficiency
        store.setTransfers(store.getTransfers() + 1);
    }
}