package users;

import services.MenuService;
import services.OrderService;

import java.util.Scanner;

public class Customer {
    public static void customerDashboard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=======================================");
        System.out.println("           Customer Portal             ");
        System.out.println("=======================================");
        System.out.println("1. Login");
        System.out.println("2. Signup");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        if (choice == 2) {
            Authentication.customerSignup();
        }

        if (!Authentication.customerLogin()) return;

        System.out.print("Enter your name: ");
        String customerName = scanner.next();
        System.out.print("Are you a VIP customer? (true/false): ");
        boolean isVip = scanner.nextBoolean();

        while (true) {
            System.out.println("=======================================");
            System.out.println("          Customer Dashboard           ");
            System.out.println("=======================================");
            System.out.println("1. Browse Menu");
            System.out.println("2. Place Order");
            System.out.println("3. View Order History");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    // Call displayMenuGUI() instead of displayMenu()
                    MenuService.displayMenuGUI();
                    continue;

                case 2:
                    OrderService.placeOrder(customerName, isVip);
                    continue;

                case 3:
                    OrderService.viewOrderHistory(customerName);
                    continue;

                case 4:
                    System.out.println("Logging out... Returning to main menu.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
