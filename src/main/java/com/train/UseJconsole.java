package com.train;

public class UseJconsole {

	public static void main(String[] args) {
		while(true)
		{
			//String a = new String("abc");
			String a = "abc";
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			a = null;
		}

	}

}
