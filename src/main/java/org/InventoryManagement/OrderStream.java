package org.InventoryManagement;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

class OrderStream implements Runnable {
    private final Inventory inventory; // Reference to the inventory
    private final Queue<Order> orders; // Queue of orders to process
    private static final AtomicInteger headerCounter = new AtomicInteger(0); // Counter to generate unique headers

    OrderStream(Inventory inventory, Queue<Order> orders) {
        this.inventory = inventory;
        this.orders = orders;
    }

    @Override
    public void run() {
        while (!orders.isEmpty()) {
            Order order = orders.poll(); // Get the next order from the queue
            if (order != null) {
                boolean halt = inventory.processOrder(order); // Process the order
                if (halt) {
                    break; // Stop processing if all inventory is zero
                }
            }
        }
    }
}