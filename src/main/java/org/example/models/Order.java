package org.example.models;

public class Order {

    private String orderId;
    private String user;
    private String item;
    private int quantity;

    // Constructor
    public Order(String orderId, String user, String item, int quantity) {
        this.orderId = orderId;
        this.user = user;
        this.item = item;
        this.quantity = quantity;
    }

    // Default constructor (needed for JSON deserialization)
    public Order() {
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getUser() {
        return user;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}