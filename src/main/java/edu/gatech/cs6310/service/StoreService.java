package edu.gatech.cs6310.service;

import edu.gatech.cs6310.domain.Item;
import edu.gatech.cs6310.domain.Store;

import java.util.List;

public interface StoreService {

    Store create(String name, int revenue);

    List<Store> getAllStores();

    Store findByName(String name);

    void addItemToStore(String storeName, String itemName, int weight);

    List<Item> getAllItemsByStore(String storeName);

    void transferOrder(String storeName, String orderID, String droneID);

}
