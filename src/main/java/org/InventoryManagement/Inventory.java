package org.InventoryManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    Map<String, Integer> inventoryMap; // Current inventory levels for each product
    List<String> output; // List to store the output log

    public Map<String, Integer> getInventory() {
        return inventoryMap;
    }

    Inventory() {
        // Initialize the inventory with the given starting quantities
        inventoryMap = new HashMap<>();
        inventoryMap.put("A", 2);
        inventoryMap.put("B", 3);
        inventoryMap.put("C", 1);
        inventoryMap.put("D", 0);
        inventoryMap.put("E", 0);
        output = new ArrayList<>();
    }
    //initiaze inventory data from user

    public void initializeInventoryData(int a, int b, int c, int d, int e) {
        inventoryMap.put("A", a);
        inventoryMap.put("B", b);
        inventoryMap.put("C", c);
        inventoryMap.put("D", d);
        inventoryMap.put("E", e);
    }
        // Synchronized method to process an order
        synchronized boolean processOrder(Order order) {
            Map<String, Integer> allocated = new HashMap<>(); // Map to store allocated quantities
            Map<String, Integer> backorder = new HashMap<>(); // Map to store backorder quantities
            boolean canFulfillOrder = true;

            // Check availability and allocate if possible
            for (Map.Entry<String, Integer> line : order.lines.entrySet()) {
                String product = line.getKey();
                int quantity = line.getValue();
                if (quantity < 1 || quantity > 5) { // Validate quantity
                    canFulfillOrder = false;
                    break;
                }
                // Allocate quantity if available, otherwise backorder the remaining quantity
                if (inventoryMap.getOrDefault(product, 0) >= quantity) {
                    allocated.put(product, quantity);
                } else {
                    allocated.put(product, inventoryMap.getOrDefault(product, 0));
                    backorder.put(product, quantity - inventoryMap.getOrDefault(product, 0));
                    canFulfillOrder = false;
                }
            }

            // Update inventory based on allocated quantities
            if (canFulfillOrder) {
                for (Map.Entry<String, Integer> line : allocated.entrySet()) {
                    inventoryMap.put(line.getKey(), inventoryMap.get(line.getKey()) - line.getValue());
                }
            }

            // Prepare output log for the order
            StringBuilder result = new StringBuilder();
            result.append(order.header).append(": ");
            appendQuantities(result, order.lines);
            result.append("::");
            appendQuantities(result, allocated);
            result.append("::");
            appendQuantities(result, backorder);

            output.add(result.toString());

            // Halt if all inventory is zero
            return allInventoryZero();
        }

        // Helper method to append quantities to the result string
        private void appendQuantities(StringBuilder builder, Map<String, Integer> quantities) {
            builder.append(quantities.getOrDefault("A", 0)).append(",")
                    .append(quantities.getOrDefault("B", 0)).append(",")
                    .append(quantities.getOrDefault("C", 0)).append(",")
                    .append(quantities.getOrDefault("D", 0)).append(",")
                    .append(quantities.getOrDefault("E", 0));
        }

        // Method to print the final output log
        void printOutput() {
            for (String line : output) {
                System.out.println(line);
            }
        }

    public boolean allInventoryZero() {
        return inventoryMap.values().stream().allMatch(qty -> qty == 0);
    }
}
