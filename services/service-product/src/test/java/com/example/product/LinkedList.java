package com.example.product;

public class LinkedList {
    public static class Node {
        int data;
        Node next;
        Node prev;
        public Node(int data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
    transient Node first;
    transient Node last;
}
