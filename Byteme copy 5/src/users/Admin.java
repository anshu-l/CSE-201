package users;

import services.MenuService;
import services.OrderService;
import services.ReportService;

import java.util.Scanner;

public class Admin {
    public static void adminDashboard() {
        Scanner scanner = new Scanner(System.in);
        boolean t2=true;
        if (!Authentication.adminLogin()) return;

        while (t2) {
            System.out.println("=======================================");
            System.out.println("           Admin Dashboard             ");
            System.out.println("=======================================");
            System.out.println("1. Manage Menu");
            System.out.println("2. View Pending Orders");
            System.out.println("3. Process Orders");
            System.out.println("4. Generate Daily Report");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 :
                    MenuService.menuManagement();
                    continue;
                case 2 :
//                    OrderService.viewPendingOrders();
                    OrderService.viewPendingOrdersGUI();
                    continue;
                case 3 :
                    OrderService.processOrder();
                    continue;
                case 4 :
                    ReportService.generateDailyReport(OrderService.getOrderHistory(), OrderService.getRefundedOrders());
                    continue;
                case 5 :

                    System.out.println("Logging out... Returning to main menu.");
                    t2=false;
                    continue;

                default :
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        return;
    }
}
