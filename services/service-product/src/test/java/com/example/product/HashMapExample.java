package com.example.product;

import java.util.HashMap;

public class HashMapExample {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Alice", 25);
        map.put("Bob", 30);
        System.out.println(map.get("Alice"));
        map.remove("Bob");
        System.out.println(map.containsKey("Bob"));
    }
}
