package models;

import java.util.HashMap;

public class Order {
    private String orderId;
    private HashMap<MenuItem, Integer> items; // Item and its quantity
    private String customerName;
    private String status; // e.g., "Pending", "Preparing", "Delivered"
    private boolean isVip; // Flag to indicate if the order is from a VIP customer
    private String specialRequest; // Field to store special requests

    // Constructor
    public Order(String orderId, String customerName, boolean isVip) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = new HashMap<>();
        this.status = "Pending";
        this.isVip = isVip; // Set VIP status
        this.specialRequest = ""; // Default to empty if no special request is provided
    }

    // Add item to the order
    public void addItem(MenuItem item, int quantity) {
        items.put(item, quantity);
    }

    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public HashMap<MenuItem, Integer> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    // To display order details
    @Override
    public String toString() {
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order ID: ").append(orderId)
                .append(", Customer: ").append(customerName)
                .append(", Status: ").append(status)
                .append(", VIP: ").append(isVip).append("\nItems:\n");
        for (MenuItem item : items.keySet()) {
            orderDetails.append("- ").append(item.getName())
                    .append(" x ").append(items.get(item)).append("\n");
        }
        if (!specialRequest.isEmpty()) {
            orderDetails.append("Special Request: ").append(specialRequest).append("\n");
        }
        return orderDetails.toString();
    }
}
