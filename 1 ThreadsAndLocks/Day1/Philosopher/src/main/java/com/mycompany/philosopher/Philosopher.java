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
    final private Chopstick left, right;
    final private Random random;
    private static int philosopherNo = 0;
    private int thisPhilosopherNo;
    
    public Philosopher(Chopstick left, Chopstick right) {
        this.left = left;
        this.right = right;
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
                synchronized(left) { // Grab left chopstick
                    System.out.println("Philosopher No: " + 
                            thisPhilosopherNo + " grabs left chopstick");
                    synchronized(right) { // Grab right chopstick
                        System.out.println("Philosopher No: " + 
                                thisPhilosopherNo + " grabs right chopstick");
                        Thread.sleep(random.nextInt(1000)); // Eat for a while
                    }
                }
            }
        } catch (InterruptedException e) {}
    }
}
