package com.cache;

import java.util.HashMap;
import java.util.Map;

class Node{
    String key;
    String value;
    Node prev;
    Node next;

    public  Node(String key ,String value ){

        this.key = key;
        this.value = value;

    }



}
public class LocalCache {

    private Map<String ,Node> cache;
    private int capcity;
    private Node head;
    private Node tail;
    private  int cacheHits;
    private int cacheMisses;

    public LocalCache(int capacity){
        this.capcity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(null,null);

        this.tail = new Node(null,null);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public  String get(String key){
        if (cache.containsKey(key)){
            Node node = cache.get(key);
            remove(node);
            insert(node);
            cacheHits ++;
            return  node.value;

        }
        cacheMisses ++;

        return null ;
    }

    public void put (String key, String value){
        if (cache.containsKey(key)){
            Node node = cache.get(key);
            remove(node);
            insert(node);
        }else{

            if (cache.size() == capcity) {
                System.out.println(" removing the capacity "+tail.prev.value);
                cache.remove(tail.prev.key);
                remove(tail.prev);
             }
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            insert(newNode);

        }

    }
    private void insert(Node node) {
        node.next = head.next;
        node.next.prev = node;
        head.next=node;
        node.prev=head;

    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public  Integer getCacheHits(){
        return  cacheHits;
    }


    public  Integer getCacheMisses(){
        return  cacheMisses;
    }

    public static void main(String[] args) {

        LocalCache cache = new LocalCache(2);
        cache.put("key1", "Value 1");
        cache.put("key2", "Value 2");

        System.out.println(cache.get("key1")); // Output: Value 1
        System.out.println("Cache Hits: " + cache.getCacheHits()); // Output: 1
        System.out.println("Cache Misses: " + cache.getCacheMisses()); // Output: 0

        System.out.println(cache.get("key3")); // Output: null
        System.out.println("Cache Hits: " + cache.getCacheHits()); // Output: 1
        System.out.println("Cache Misses: " + cache.getCacheMisses()); // Output: 1

        cache.put("key3", "Value 3");
        System.out.println(cache.get("key2")); // Output: null
        System.out.println(cache.get("key3")); // Output: Value 3
        System.out.println("Cache Hits: " + cache.getCacheHits()); // Output: 2
        System.out.println("Cache Misses: " + cache.getCacheMisses());
    }


}
