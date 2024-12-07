package services;

import models.MenuItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuService {
    // Stores the menu items
    private static final ArrayList<MenuItem> menuItems = new ArrayList<>();

    // Main method to handle menu management
    public static void menuManagement() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
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
                    return;
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

    // Method to display all menu items
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

    // Getter for menuItems
    public static ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }
}
