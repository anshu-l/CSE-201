//package users;
//
//import java.util.HashMap;
//import java.util.Scanner;
//
//public class Authentication {
//    private static final HashMap<String, String> adminCredentials = new HashMap<>();
//    private static final HashMap<String, String> customerCredentials = new HashMap<>();
//
//    static {
//        // Default admin account
//        adminCredentials.put("admin", "admin123");
//    }
//
//    public static boolean adminLogin() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter Admin Username: ");
//        String username = scanner.nextLine();
//        System.out.print("Enter Admin Password: ");
//        String password = scanner.nextLine();
//
//        if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password)) {
//            System.out.println("Welcome, Admin!");
//            return true;
//        } else {
//            System.out.println("Invalid credentials. Please try again.");
//            return false;
//        }
//    }
//
//    public static boolean customerLogin() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter Email: ");
//        String email = scanner.nextLine();
//        System.out.print("Enter Password: ");
//        String password = scanner.nextLine();
//
//        if (customerCredentials.containsKey(email) && customerCredentials.get(email).equals(password)) {
//            System.out.println("Welcome back, " + email + "!");
//            return true;
//        } else {
//            System.out.println("Invalid credentials. Please try again.");
//            return false;
//        }
//    }
//
//    public static void customerSignup() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter Full Name: ");
//        String name = scanner.nextLine();
//        System.out.print("Enter Email: ");
//        String email = scanner.nextLine();
//        System.out.print("Set Password: ");
//        String password = scanner.nextLine();
//
//        customerCredentials.put(email, password);
//        System.out.println("Account created successfully! Please log in to continue.");
//    }
//}
