package org.InventoryManagement;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        // Initialize the inventory with some initial quantities
        inventory = new Inventory();
        inventory.initializeInventoryData(2, 3, 1, 0, 0);
    }

    @Test
    public void testProcessOrder() {
        // Create an order with valid quantities
        Order order = new Order(1, Map.of("A", 1, "B", 2));
        boolean halt = inventory.processOrder(order);
        assertEquals(false, halt); // Assert that inventory is not depleted

        // Check if inventory is updated correctly after processing the order
        Map<String, Integer> updatedInventory = inventory.getInventory();
        assertEquals(1, updatedInventory.get("A"));
        assertEquals(1, updatedInventory.get("B"));
        assertEquals(1, updatedInventory.get("C"));
        assertEquals(0, updatedInventory.get("D"));
        assertEquals(0, updatedInventory.get("E"));
    }

    @Test
    public void testProcessOrderWithBackorder() {
        // Create an order with quantities exceeding inventory levels
        Order order = new Order(1, Map.of("A", 3, "B", 4, "C", 2));
        boolean halt = inventory.processOrder(order);
        assertEquals(false, halt); // Assert that inventory is not depleted

        // Check if inventory is updated correctly after processing the order with backorders
        Map<String, Integer> updatedInventory = inventory.getInventory();
        assertEquals(0, updatedInventory.get("A"));
        assertEquals(0, updatedInventory.get("B"));
        assertEquals(0, updatedInventory.get("C"));
        assertEquals(0, updatedInventory.get("D"));
        assertEquals(0, updatedInventory.get("E"));
    }

    @Test
    public void testAllInventoryZero() {
        // Set inventory to zero
        inventory.initializeInventoryData(0, 0, 0, 0, 0);
        assertEquals(true, inventory.allInventoryZero()); // Assert that all inventory is zero
    }
}