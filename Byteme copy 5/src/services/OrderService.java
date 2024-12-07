package services;

import models.MenuItem;
import models.Order;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class OrderService {
    // Queue for pending orders with VIP prioritization
    private static final PriorityQueue<Order> pendingOrders = new PriorityQueue<>(
            (o1, o2) -> Boolean.compare(o2.isVip(), o1.isVip()) // VIP orders have higher priority
    );

    // List to store completed or canceled orders for history
    private static ArrayList<Order> orderHistory = new ArrayList<>();
    private static final ArrayList<Order> refundedOrders = new ArrayList<>();

    static {
        loadOrderHistoryFromFile("orderHistory.dat");
    }

    // Method for customers to place a new order
    public static void placeOrder(String customerName, boolean isVip) {
        Scanner scanner = new Scanner(System.in);
        Order newOrder = new Order(generateOrderId(), customerName, isVip);

        System.out.println("Add items to your order (Enter 0 to finish):");
        while (true) {
            MenuService.displayMenu(); // Show the available menu
            System.out.print("Enter the item number to add to your order: ");
            int itemNumber = scanner.nextInt();

            if (itemNumber == 0) break; // Finish adding items

            ArrayList<MenuItem> availableItems = MenuService.getAvailableMenuItems();
            if (itemNumber > 0 && itemNumber <= availableItems.size()) {
                MenuItem selectedItem = availableItems.get(itemNumber - 1);
                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();
                newOrder.addItem(selectedItem, quantity);
                System.out.println(quantity + " x " + selectedItem.getName() + " added to your order.");
            } else {
                System.out.println("Invalid item number. Try again.");
            }
        }

        // Capture special requests
        System.out.print("Any special requests? (e.g., 'extra spicy', 'no onions') If none, leave blank: ");
        scanner.nextLine(); // Consume the newline left by nextInt()
        String specialRequest = scanner.nextLine();
        newOrder.setSpecialRequest(specialRequest);

        if (!newOrder.getItems().isEmpty()) {
            pendingOrders.add(newOrder); // Add the order to the pending queue
            System.out.println("Order placed successfully! Your order ID is: " + newOrder.getOrderId());
        } else {
            System.out.println("No items were added. Order not placed.");
        }
    }

    // Method to display pending orders in a GUI
    public static void viewPendingOrdersGUI() {
        if (pendingOrders.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No pending orders.", "Pending Orders", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create the main frame
        JFrame frame = new JFrame("Pending Orders");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 600);

        // Panel to display pending orders
        JPanel ordersPanel = new JPanel();
        ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));

        // Add each order to the panel
        for (Order order : pendingOrders) {
            ordersPanel.add(createOrderPanel(order));
        }

        // Wrap the panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(ordersPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scroll pane to the frame
        frame.add(scrollPane);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Helper method to create a panel for each order
    private static JPanel createOrderPanel(Order order) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setPreferredSize(new Dimension(450, 100));

        // Display order ID and customer name at the top
        JLabel titleLabel = new JLabel("Order ID: " + order.getOrderId() + " | Customer: " + order.getCustomerName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Display order items in the center
        JTextArea itemsArea = new JTextArea(order.getItems().toString());
        itemsArea.setLineWrap(true);
        itemsArea.setWrapStyleWord(true);
        itemsArea.setEditable(false);
        panel.add(itemsArea, BorderLayout.CENTER);

        // Display special request and status at the bottom
        JLabel statusLabel = new JLabel("Status: " + order.getStatus() + " | Special Request: " + order.getSpecialRequest());
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        panel.add(statusLabel, BorderLayout.SOUTH);

        return panel;
    }

    // Method for admins to view pending orders in the console
    public static void viewPendingOrders() {
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        System.out.println("Pending Orders:");
        for (Order order : pendingOrders) {
            System.out.println(order);
        }
    }

    // Method for admins to process an order
    public static void processOrder() {
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders to process.");
            return;
        }

        Order orderToProcess = pendingOrders.poll(); // Retrieve and remove the highest-priority order
        System.out.println("Processing Order: " + orderToProcess);
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Mark as Preparing");
        System.out.println("2. Mark as Delivered");
        System.out.println("3. Cancel Order");
        System.out.println("4. Refund Order");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                orderToProcess.setStatus("Preparing");
                System.out.println("Order status updated to 'Preparing'.");
                pendingOrders.add(orderToProcess); // Re-add to queue with updated status
            }
            case 2 -> {
                orderToProcess.setStatus("Delivered");
                orderHistory.add(orderToProcess); // Move to completed orders
                System.out.println("Order marked as 'Delivered'.");
            }
            case 3 -> {
                orderToProcess.setStatus("Cancelled");
                orderHistory.add(orderToProcess); // Move to completed orders
                System.out.println("Order has been cancelled.");
            }
            case 4 -> handleRefund(orderToProcess);
            default -> System.out.println("Invalid choice. Order status not updated.");
        }
    }

    // Refund handler
    private static void handleRefund(Order order) {
        if (order.getStatus().equals("Refunded")) {
            System.out.println("This order has already been refunded.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // Calculate total refund amount
        double refundAmount = 0;
        for (var entry : order.getItems().entrySet()) {
            refundAmount += entry.getKey().getPrice() * entry.getValue();
        }

        System.out.println("The total refund amount for this order is: ₹" + refundAmount);
        System.out.print("Do you want to confirm the refund? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            order.setStatus("Refunded");
            refundedOrders.add(order); // Add the refunded order to the list
            System.out.println("Order has been refunded successfully.");
        } else {
            System.out.println("Refund operation cancelled.");
        }
    }

    // Method for customers to view their order history
    public static void viewOrderHistory(String customerName) {
        boolean hasOrders = false;

        System.out.println("Order History:");
        for (Order order : orderHistory) {
            if (order.getCustomerName().equalsIgnoreCase(customerName)) {
                System.out.println(order);
                hasOrders = true;
            }
        }

        if (!hasOrders) {
            System.out.println("No past orders found for " + customerName + ".");
        }
    }

    // Method to view refunded orders
    public static void viewRefundedOrders() {
        if (refundedOrders.isEmpty()) {
            System.out.println("No refunded orders.");
            return;
        }

        System.out.println("Refunded Orders:");
        for (Order order : refundedOrders) {
            System.out.println(order);
        }
    }

    // Method to save order history to a file
    public static void saveOrderHistoryToFile(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(orderHistory);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to load order history from a file
    public static void loadOrderHistoryFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            orderHistory = (ArrayList<Order>) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Getter for order history
    public static ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    // Getter for refunded orders
    public static ArrayList<Order> getRefundedOrders() {
        return refundedOrders;
    }

    // Helper method to generate a unique order ID
    private static String generateOrderId() {
        return "ORD" + (orderHistory.size() + pendingOrders.size() + refundedOrders.size() + 1);
    }
}
