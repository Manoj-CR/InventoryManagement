package org.InventoryManagement;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
     //   System.out.println("Hello World!");
        Inventory inventory = new Inventory(); // Create the inventory
        Queue<Order> orders = new LinkedList<>(); // Create a queue for orders
        Scanner scanner = new Scanner(System.in);
        //initialize inventory data from user
        System.out.println("Enter the initial inventory levels:");
        System.out.println("Enter the quantity of product A:");
        int a = scanner.nextInt();
        System.out.println("Enter the quantity of product B:");
        int b = scanner.nextInt();
        System.out.println("Enter the quantity of product C:");
        int c = scanner.nextInt();
        System.out.println("Enter the quantity of product D:");
        int d = scanner.nextInt();
        System.out.println("Enter the quantity of product E:");
        int e = scanner.nextInt();
        inventory.initializeInventoryData(a, b, c, d, e);


        // Read orders from the user
        System.out.println("Enter the number of orders:");

        int numOrders = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        for (int i = 0; i < numOrders; i++) {
            System.out.println("Enter order header number:");
            int header = scanner.nextInt();
            Map<String, Integer> orderLines = new HashMap<>();
            scanner.nextLine(); // Consume newline
            while (true) {
                System.out.println("Enter product (or type 'done' to finish):");
                String product = scanner.nextLine().toUpperCase();
                if (product.equals("DONE")) {
                    break;
                }
                System.out.println("Enter quantity:");
                int quantity = scanner.nextInt();
                orderLines.put(product, quantity);
                scanner.nextLine(); // Consume newline
            }
            orders.add(new Order(header, orderLines));
        }
        // Start order processing streams
        Thread streamOne = new Thread(new OrderStream(inventory, orders));
        Thread streamTwo = new Thread(new OrderStream(inventory, orders));
        streamOne.start();
        streamTwo.start();

        try {
            streamOne.join(); // Wait for stream1 to finish
            streamTwo.join(); // Wait for stream2 to finish
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // Print final output log
        inventory.printOutput();
    }
}


