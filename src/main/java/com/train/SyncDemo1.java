package com.train;


public class SyncDemo1 implements Runnable { 
    public void run() { 
        synchronized(this) { 
             for (int i = 0; i < 5; i++) { 
                  System.out.println(Thread.currentThread().getName() + " synchronized loop " + i); 
             } 
        } 
   } 
   public static void main(String[] args) { 
	    SyncDemo1 t1 = new SyncDemo1(); 
        Thread ta = new Thread(t1, "A"); 
        Thread tb = new Thread(t1, "B"); 
        ta.start(); 
        tb.start(); 
   }
}
