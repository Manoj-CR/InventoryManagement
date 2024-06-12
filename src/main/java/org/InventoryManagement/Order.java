package org.InventoryManagement;

import java.util.Map;

public class Order {
    int header;
    Map<String,Integer> lines;
    Order(int header, Map<String,Integer> lines){
        this.header = header;
        this.lines = lines;
    }
}
