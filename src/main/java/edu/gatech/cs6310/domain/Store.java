package edu.gatech.cs6310.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Map;
import java.util.TreeMap;


@Entity
@JsonIgnoreProperties("drones")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String storeName;
    private int initialRev;
    @OneToMany(mappedBy="store", cascade = CascadeType.ALL)
    private Map<String, Item> items = new TreeMap<>();

    @OneToMany
    @JoinColumn(name = "store_id")
    @MapKey(name = "droneID")
    private Map<String, Drone> drones = new TreeMap<>();

    @OneToMany(mappedBy="store", cascade = CascadeType.ALL)
    private Map<String, Order> ownedOrders = new TreeMap<>();

    private int purchases;
    private int overloads;
    private int transfers;

    public Store(String storeName, int initialRev) {
        this.storeName = storeName;
        this.initialRev = initialRev;
    }

    public Store() {

    }

    public void setName(String storeName) {
        this.storeName = storeName;
    }

    public String getName() {
        return storeName;
    }

    public void setInitialRev(int initialRev) {
        this.initialRev = initialRev;
    }

    public int getInitialRev() {
        return initialRev;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public Map<String, Drone> getDrones() {
        return drones;
    }

    public Map<String, Order> getOwnedOrders() {
        return ownedOrders;
    }

    public void setPurchases(int purchases) {
        this.purchases = purchases;
    }

    public void setOverloads(int overloads) {
        this.overloads = overloads;
    }

    public void setTransfers(int transfers) {
        this.transfers = transfers;
    }

    public int getPurchases() {
        return purchases;
    }

    public int getOverloads() {
        return overloads;
    }

    public int getTransfers() {
        return transfers;
    }

    @Override
    public String toString() {
        return "name:" + storeName + ",revenue:" + initialRev;
    }

    public void addItem(String itemName, Item newItem) {
        // check "ERROR:item_identifier_already_exists"
        if (items.containsKey(itemName)) {
            System.out.println("ERROR:item_identifier_already_exists");
        } else {
            items.put(itemName, newItem);
            System.out.println("OK:change_completed");
        }
    }

    public void addOrder(String orderID, Order order) {
        // check "ERROR:order_identifier_already_exists"
        if (!ownedOrders.containsKey(orderID)) {
            ownedOrders.put(orderID, order);
            System.out.println("OK:change_completed");
        } else {
            System.out.println("ERROR:order_identifier_already_exists");
        }
    }

    public Item getItemByName(String itemName) {
        for (Item item : items.values()) {
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

}