package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private String orderId;
    private String customerName;
    private boolean isVip;
    private Map<MenuItem, Integer> items; // Map to store items and their quantities
    private String specialRequest;
    private String status;

    public Order(String orderId, String customerName, boolean isVip) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.isVip = isVip;
        this.items = new HashMap<>();
        this.status = "Pending";
    }

    public void addItem(MenuItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public boolean isVip() {
        return isVip;
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order ID: ").append(orderId)
                .append("\nCustomer: ").append(customerName)
                .append("\nVIP: ").append(isVip)
                .append("\nStatus: ").append(status)
                .append("\nSpecial Request: ").append(specialRequest)
                .append("\nItems:\n");

        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            builder.append("  - ").append(entry.getKey().getName())
                    .append(" x ").append(entry.getValue())
                    .append(" (₹").append(entry.getKey().getPrice()).append(" each)\n");
        }
        return builder.toString();
    }
}
