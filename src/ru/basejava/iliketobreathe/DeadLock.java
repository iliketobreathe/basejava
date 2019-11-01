package ru.basejava.iliketobreathe;

public class DeadLock {
    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
    }

    private static class Thread1 extends Thread {
        public void run() {
            synchronized (LOCK_1) {
                System.out.println("Thread 1: holding LOCK_1...");

                try { Thread.sleep(10); }
                catch (InterruptedException e) {}
                System.out.println("Thread 1: waiting for LOCK_2...");

                synchronized (LOCK_2) {
                    System.out.println("Thread 1: holding LOCK_1 & 2...");
                }
            }
        }
    }

    private static class Thread2 extends Thread {
        public void run() {
            synchronized (LOCK_2) {
                System.out.println("Thread 2: holding LOCK_2...");

                try { Thread.sleep(10); }
                catch (InterruptedException e) {}
                System.out.println("Thread 2: waiting for LOCK_1...");

                synchronized (LOCK_1) {
                    System.out.println("Thread 2: holding LOCK_1 & LOCK_2...");
                }
            }
        }
    }
}
