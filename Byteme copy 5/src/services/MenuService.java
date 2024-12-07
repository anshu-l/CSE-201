package services;

import models.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuService {
    // Stores the menu items
    private static final ArrayList<MenuItem> menuItems = new ArrayList<>();

    static {
        // Example items
        menuItems.add(new MenuItem("Burger", 100, "Fast Food", true));
        menuItems.add(new MenuItem("Pizza", 200, "Fast Food", false)); // Unavailable item
    }

    // Main method to handle menu management
    public static void menuManagement() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("=======================================");
            System.out.println("            Menu Management            ");
            System.out.println("=======================================");
            System.out.println("1. Add New Item");
            System.out.println("2. Update Item");
            System.out.println("3. Remove Item");
            System.out.println("4. View Menu");
            System.out.println("5. Back to Admin Dashboard");
            System.out.println("6. Search Items");
            System.out.println("7. Filter by Category");
            System.out.println("8. Sort by Price");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addNewItem();
                case 2 -> updateItem();
                case 3 -> removeItem();
                case 4 -> displayMenu();
                case 5 -> {
                    System.out.println("Returning to Admin Dashboard...");
                    running = false;
                }
                case 6 -> searchItems();
                case 7 -> filterByCategory();
                case 8 -> sortByPrice();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to add a new item to the menu
    private static void addNewItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Category (e.g., Snacks, Beverages): ");
        String category = scanner.nextLine();
        System.out.print("Enter Availability (true/false): ");
        boolean isAvailable = scanner.nextBoolean();

        MenuItem newItem = new MenuItem(name, price, category, isAvailable);
        menuItems.add(newItem);
        System.out.println("Item added successfully!");
    }

    // Method to update an existing menu item
    private static void updateItem() {
        displayMenu(); // Show the current menu for reference
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the item you want to update: ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < menuItems.size()) {
            MenuItem itemToUpdate = menuItems.get(index);

            System.out.println("Updating: " + itemToUpdate.getName());
            System.out.print("Enter New Price: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter New Availability (true/false): ");
            boolean newAvailability = scanner.nextBoolean();

            itemToUpdate.setPrice(newPrice);
            itemToUpdate.setAvailable(newAvailability);
            System.out.println("Item updated successfully!");
        } else {
            System.out.println("Invalid item number. Please try again.");
        }
    }

    // Method to remove an item from the menu
    private static void removeItem() {
        displayMenu(); // Show the current menu for reference
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the item you want to remove: ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < menuItems.size()) {
            MenuItem removedItem = menuItems.remove(index);
            System.out.println("Removed: " + removedItem.getName());
        } else {
            System.out.println("Invalid item number. Please try again.");
        }
    }

    // Method to display all menu items in the console
    public static void displayMenu() {
        if (menuItems.isEmpty()) {
            System.out.println("The menu is currently empty. Add some items!");
            return;
        }

        System.out.println("=======================================");
        System.out.println("                  Menu                 ");
        System.out.println("=======================================");
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            System.out.println((i + 1) + ". " + item);
        }
    }

    // Method to get only available menu items
    public static ArrayList<MenuItem> getAvailableMenuItems() {
        return (ArrayList<MenuItem>) menuItems.stream()
                .filter(MenuItem::isAvailable)
                .collect(Collectors.toList());
    }

    // Method to search for items by name or keyword
    private static void searchItems() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a keyword to search: ");
        String keyword = scanner.nextLine().toLowerCase();

        ArrayList<MenuItem> results = (ArrayList<MenuItem>) menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("No items found matching the keyword: " + keyword);
        } else {
            System.out.println("Search Results:");
            results.forEach(System.out::println);
        }
    }

    // Method to filter items by category
    private static void filterByCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a category to filter (e.g., Snacks, Beverages): ");
        String category = scanner.nextLine().toLowerCase();

        ArrayList<MenuItem> results = (ArrayList<MenuItem>) menuItems.stream()
                .filter(item -> item.getCategory().toLowerCase().equals(category))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("No items found in category: " + category);
        } else {
            System.out.println("Items in category '" + category + "':");
            results.forEach(System.out::println);
        }
    }

    // Method to sort items by price
    private static void sortByPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sort by price:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        ArrayList<MenuItem> sortedItems = new ArrayList<>(menuItems);
        if (choice == 1) {
            sortedItems.sort(Comparator.comparingDouble(MenuItem::getPrice));
            System.out.println("Menu sorted by price (ascending):");
        } else if (choice == 2) {
            sortedItems.sort(Comparator.comparingDouble(MenuItem::getPrice).reversed());
            System.out.println("Menu sorted by price (descending):");
        } else {
            System.out.println("Invalid choice. Returning to menu management.");
            return;
        }

        sortedItems.forEach(System.out::println);
    }

    // GUI Method to display the menu
    public static void displayMenuGUI() {
        if (menuItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The menu is currently empty. Add some items!", "Menu Empty", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create the main frame
        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 600);

        // Panel to display menu items
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // Add each MenuItem to the panel
        for (MenuItem item : menuItems) {
            menuPanel.add(createMenuItemPanel(item));
        }

        // Wrap the panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scroll pane to the frame
        frame.add(scrollPane);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Helper method to create a panel for each MenuItem
    private static JPanel createMenuItemPanel(MenuItem item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setPreferredSize(new Dimension(350, 60));

        // Display name and price at the top
        JLabel nameLabel = new JLabel(item.getName() + " (₹" + item.getPrice() + ")", SwingConstants.LEFT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Display category and availability at the bottom
        JLabel detailsLabel = new JLabel("Category: " + item.getCategory() + " | Available: " + item.isAvailable());
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Add labels to the panel
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(detailsLabel, BorderLayout.SOUTH);

        return panel;
    }

    // Getter for menuItems
    public static ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }
}
