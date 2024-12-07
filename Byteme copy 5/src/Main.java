import services.OrderService;
import users.Admin;
import users.Customer;
import java.util.Scanner;

import static users.Authentication.loadHashMapFromFile;
import static users.Authentication.saveHashMapToFile;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=======================================");
            System.out.println("         Welcome to Byte Me!           ");
            System.out.println("========================================");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 :
                    Admin.adminDashboard();
                    continue;
                case 2 :
                    Customer.customerDashboard();
                    continue;
                case 3 :

                    System.out.println("Thank you for using Byte Me! Have a great day!");
                    saveHashMapToFile("file.txt");
                    OrderService.saveOrderHistoryToFile("file1.txt");
                    System.exit(0);
                default :
                    System.out.println("Invalid choice. Please try again.");

            }

        }
    }
}
