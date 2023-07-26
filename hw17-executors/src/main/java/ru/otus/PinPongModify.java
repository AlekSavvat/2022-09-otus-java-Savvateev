package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinPongModify {
    private static final Logger logger = LoggerFactory.getLogger(PinPongModify.class);
    private int lastActiveThread = 2;

    public static void main(String[] args) {
        PinPongModify solution = new PinPongModify();
        new Thread(() -> solution.action(1), "Thread_1").start();
        new Thread(() -> solution.action(2),"Thread_2").start();
    }

    private synchronized void action(int threadNumber) {
        int count =1;
        boolean isIncrement = true;
        while(!Thread.currentThread().isInterrupted() && count > 0) {
            try {
                while (lastActiveThread == threadNumber) {
                    this.wait();
                }

                logger.info(String.valueOf(count));
                count = isIncrement? count+1 : count-1;

                if(count == 10) isIncrement=false;

                lastActiveThread = threadNumber;
                sleep();
                notify();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
