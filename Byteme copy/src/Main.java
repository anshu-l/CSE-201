import users.Admin;
import users.Customer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=======================================");
            System.out.println("         Welcome to Byte Me!           ");
            System.out.println("=======================================");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> Admin.adminDashboard();
                case 2 -> Customer.customerDashboard();
                case 3 -> {
                    System.out.println("Thank you for using Byte Me! Have a great day!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
