package com.pranay;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        for(int j=0;j<100;j++) {
            int threadPoolSize = 3;
            TaskManager tm = new TaskManagerImpl(threadPoolSize);
            Random r = new Random();
            int size = 10;
            while(size-- > 0) {
                int randomInteger = r.nextInt(100);
                System.out.println("Random job generated : " + randomInteger);
                tm.add(randomInteger);
            }
            tm.stop();
        }
    }
}
