package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Run Producer");
        System.out.println("2. Run Consumer");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();

        if (choice == 1) {
            Creator creator = new Creator();
            creator.sendOrder();
            System.out.println("Order sent successfully!");
        } else if (choice == 2) {
            Tracker tracker = new Tracker();
            tracker.startConsuming();
        } else {
            System.out.println("Invalid choice!");
        }

        scanner.close();
    }
}