package services;

import models.MenuItem;
import models.Order;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportService {

    // Method to generate a daily sales report and write it to a file
    public static void generateDailyReport(ArrayList<Order> orderHistory, ArrayList<Order> refundedOrders) {
        double totalSales = 0;
        double totalRefunds = 0;
        int totalOrders = 0;
        HashMap<MenuItem, Integer> itemPopularity = new HashMap<>();

        // Process each delivered order in the history
        for (Order order : orderHistory) {
            if (order.getStatus().equals("Delivered")) {
                totalOrders++;
                for (Map.Entry<MenuItem, Integer> entry : order.getItems().entrySet()) {
                    MenuItem item = entry.getKey();
                    int quantity = entry.getValue();
                    totalSales += item.getPrice() * quantity;

                    // Update item popularity
                    itemPopularity.put(item, itemPopularity.getOrDefault(item, 0) + quantity);
                }
            }
        }

        // Calculate total refunds
        for (Order order : refundedOrders) {
            for (Map.Entry<MenuItem, Integer> entry : order.getItems().entrySet()) {
                MenuItem item = entry.getKey();
                int quantity = entry.getValue();
                totalRefunds += item.getPrice() * quantity;
            }
        }

        // Determine the most popular item(s)
        MenuItem mostPopularItem = null;
        int maxQuantity = 0;
        for (Map.Entry<MenuItem, Integer> entry : itemPopularity.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                mostPopularItem = entry.getKey();
                maxQuantity = entry.getValue();
            }
        }

        // Write the report to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DailySalesReport.txt"))) {
            writer.write("=======================================\n");
            writer.write("           Daily Sales Report          \n");
            writer.write("=======================================\n");
            writer.write("Total Orders Processed: " + totalOrders + "\n");
            writer.write("Total Sales: ₹" + totalSales + "\n");
            writer.write("Total Refunds Issued: ₹" + totalRefunds + "\n");
            writer.write("Net Sales (After Refunds): ₹" + (totalSales - totalRefunds) + "\n");
            if (mostPopularItem != null) {
                writer.write("Most Popular Item: " + mostPopularItem.getName() + " (Sold: " + maxQuantity + ")\n");
            } else {
                writer.write("Most Popular Item: None\n");
            }
            writer.write("=======================================\n");
            writer.write("Order Details:\n");

            // Write details of each delivered order
            for (Order order : orderHistory) {
                if (order.getStatus().equals("Delivered")) {
                    writer.write(order.toString() + "\n");
                }
            }

            // Write details of refunded orders
            writer.write("=======================================\n");
            writer.write("Refunded Orders:\n");
            for (Order order : refundedOrders) {
                writer.write(order.toString() + "\n");
            }

            System.out.println("Report generated successfully: DailySalesReport.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the report: " + e.getMessage());
        }
    }
}
