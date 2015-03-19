package com.train;


public class FinallyTest {
	public static void main(String[] args) {
		int count = 0;
		try {
			count++;
			//if (count == 1)
			//	throw new Exception("Exception in try");
			System.exit(0);
		} catch (Exception e) {
			System.out.println("catch block");
		}
		finally
		{
			System.out.println("finally block");
		}
	}

}
