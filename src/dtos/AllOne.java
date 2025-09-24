package dtos;

import java.util.*;

/*
    https://leetcode.com/problems/all-oone-data-structure/
 */

public class AllOne {

    private BucketNode head;
    private BucketNode tail;
    private final Map<String, BucketNode> keyToBucket;

    public AllOne() {
        head = null;
        tail = null;
        keyToBucket = new HashMap<>();
    }

    public void inc(String key) {
        if (keyToBucket.containsKey(key)) {
            BucketNode current = keyToBucket.get(key);
            BucketNode nextBucket = current.next;

            current.keys.remove(key);

            if (nextBucket == null || nextBucket.frequency > current.frequency + 1) {
                BucketNode newBucket = new BucketNode(current.frequency + 1);
                insertBucketAfter(newBucket, current);
                nextBucket = newBucket;
            }
            nextBucket.keys.add(key);
            keyToBucket.put(key, nextBucket);

            if (current.keys.isEmpty()) {
                removeBucket(current);
            }
        } else {
            // Key not present, add to frequency 1 bucket
            if (head == null || head.frequency > 1) {
                BucketNode newBucket = new BucketNode(1);
                insertBucketAfter(newBucket, null); // insert at head
            }
            head.keys.add(key);
            keyToBucket.put(key, head);
        }
    }

    public void dec(String key) {
        BucketNode current = keyToBucket.get(key);
        current.keys.remove(key);

        if (current.frequency == 1) {
            // Remove completely
            keyToBucket.remove(key);
        } else {
            BucketNode prevBucket = current.prev;

            if (prevBucket == null || prevBucket.frequency < current.frequency - 1) {
                BucketNode newBucket = new BucketNode(current.frequency - 1);
                insertBucketAfter(newBucket, current.prev);
                prevBucket = newBucket;
            }
            prevBucket.keys.add(key);
            keyToBucket.put(key, prevBucket);
        }

        if (current.keys.isEmpty()) {
            removeBucket(current);
        }
    }

    public String getMaxKey() {
        if (tail == null) return "";
        return tail.keys.iterator().next();
    }

    public String getMinKey() {
        if (head == null) return "";
        return head.keys.iterator().next();
    }

    private void insertBucketAfter(BucketNode newBucket, BucketNode prevBucket) {
        if (prevBucket == null) {
            // Insert at head
            newBucket.next = head;
            if (head != null) head.prev = newBucket;
            head = newBucket;
            if (tail == null) tail = newBucket;
        } else {
            newBucket.prev = prevBucket;
            newBucket.next = prevBucket.next;
            if (prevBucket.next != null) prevBucket.next.prev = newBucket;
            prevBucket.next = newBucket;
            if (prevBucket == tail) tail = newBucket;
        }
    }

    private void removeBucket(BucketNode bucket) {
        if (bucket.prev == null) {
            // Removing head
            head = bucket.next;
            if (head != null) head.prev = null;
        } else {
            bucket.prev.next = bucket.next;
        }

        if (bucket.next == null) {
            // Removing tail
            tail = bucket.prev;
            if (tail != null) tail.next = null;
        } else {
            bucket.next.prev = bucket.prev;
        }
    }
}

// Doubly linked list node representing a frequency bucket
class BucketNode {
    int frequency;
    Set<String> keys;
    BucketNode prev;
    BucketNode next;

    BucketNode(int frequency) {
        this.frequency = frequency;
        this.keys = new HashSet<>();
    }
}
