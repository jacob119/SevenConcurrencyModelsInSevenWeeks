/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wordcount;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Boot
 * 
 * Consumer
 */
public class Counter implements Runnable {
    private BlockingQueue<Page> queue;
    private Map<String, Integer> counts;
    private static ReentrantLock lock;
    
    public Counter(BlockingQueue<Page> queue, Map<String, Integer> counts) {
        this.queue = queue;
        this.counts = counts;
        lock = new ReentrantLock();
    }
    
    public void run() {
        try {
            while (true) {
                Page page = queue.take();
                if (page.isPoisonPill()) {
                    System.out.println("Poison pill consumed. Thread: " + this);
                    break;
                }
                
                Iterable<String> words = new Words(page.getText());
                for (String word: words) {
                    countWord(word);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void countWord(String word) {
        lock.lock();
        try {
            Integer currentCount  = counts.get(word);
            if (currentCount == null) {
                counts.put(word, 1);
            } else {
                counts.put(word, currentCount + 1);
            }
        } finally {
            lock.unlock();
        }
    }
}
