/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.philosopher;

import java.util.Random;


/**
 *
 * @author Boot
 */
public class Philosopher extends Thread {
    final private Chopstick first, second;
    final private Random random;
    private static int philosopherNo = 0;
    private int thisPhilosopherNo;
    
    public Philosopher(Chopstick left, Chopstick right) {
        if (left.getId() < right.getId())
        {
            first = left;
            second = right;
        }
        else
        {
            first = right;
            second = left;
        }
        this.random = new Random();
        thisPhilosopherNo = philosopherNo;
        philosopherNo++;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000)); // Think for a while
                System.out.println("Philosopher No: " + thisPhilosopherNo);
                synchronized(first) { // Grab left chopstick
                    System.out.println("Philosopher No: " + 
                            thisPhilosopherNo + " grabs first chopstick");
                    synchronized(second) { // Grab right chopstick
                        System.out.println("Philosopher No: " + 
                                thisPhilosopherNo + " grabs second chopstick");
                        Thread.sleep(random.nextInt(1000)); // Eat for a while
                    }
                }
            }
        } catch (InterruptedException e) {}
    }
}
