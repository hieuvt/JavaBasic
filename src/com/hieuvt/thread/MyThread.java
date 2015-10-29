package com.hieuvt.thread;

/**
 * Created by hieuvt on 29/10/2015.
 */
public class MyThread extends Thread{

    private int tId;

    public MyThread (int tId) {
        settId(tId);
    }

    @Override
    public void run() {
        System.out.println("Thread " + gettId() + " started");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + gettId() + " stopped");
    }

    public static void main (String[] args) {
        int numThread = 10;
        for (int i = 0; i < numThread; i++) {
            MyThread myThread = new MyThread(i);
            myThread.start();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }
}
